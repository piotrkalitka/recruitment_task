package io.getint.recruitment_task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.getint.recruitment_task.domain.IssueType;
import io.getint.recruitment_task.domain.Project;
import io.getint.recruitment_task.domain.addcomment.AddCommentRequest;
import io.getint.recruitment_task.domain.createissues.CreateIssues;
import io.getint.recruitment_task.domain.createissues.CreateIssuesRequest;
import io.getint.recruitment_task.domain.createissues.CreateIssuesResponse;
import io.getint.recruitment_task.domain.createissues.CreatedIssue;
import io.getint.recruitment_task.domain.search.SearchIssue;
import io.getint.recruitment_task.domain.search.SearchIssueComment;
import io.getint.recruitment_task.domain.search.SearchIssueResponse;
import io.getint.recruitment_task.domain.transition.GetTransitionsResponse;
import io.getint.recruitment_task.domain.transition.SetTransitionRequest;
import io.getint.recruitment_task.domain.transition.Transition;

public class JiraSynchronizer {
    private final String jiraUrl;
    private final String jiraUsername;
    private final String jiraApiToken;
    private final String targetProjectId;

    private final ObjectMapper objectMapper;
    private final CloseableHttpClient httpClient;

    public JiraSynchronizer() {
        EnvLoader.loadEnv(".env");
        this.jiraUrl = EnvLoader.get("JIRA_URL");
        this.jiraUsername = EnvLoader.get("JIRA_USERNAME");
        this.jiraApiToken = EnvLoader.get("JIRA_API_TOKEN");
        this.targetProjectId = EnvLoader.get("TARGET_PROJECT_ID");

        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClientBuilder.create().build();
    }

    public JiraSynchronizer(CloseableHttpClient closeableHttpClient, ObjectMapper objectMapper) {
        EnvLoader.loadEnv(".env");
        this.jiraUrl = EnvLoader.get("JIRA_URL");
        this.jiraUsername = EnvLoader.get("JIRA_USERNAME");
        this.jiraApiToken = EnvLoader.get("JIRA_API_TOKEN");
        this.targetProjectId = EnvLoader.get("TARGET_PROJECT_ID");

        this.httpClient = closeableHttpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Search for 5 tickets in one project, and move them
     * to the other project within same Jira instance.
     * When moving tickets, please move following fields:
     * - summary (title)
     * - description
     * - priority
     * Bonus points for syncing comments.
     */
    public void moveTasksToOtherProject(List<String> issueKeys) {
        System.out.println("Searching issues to copy...");
        List<SearchIssue> issues = searchIssues(issueKeys);

        System.out.println("Fetching target project...");
        Project targetProject = getProject(Long.parseLong(targetProjectId));

        System.out.println("Creating issues...");
        List<CreatedIssue> createdIssues = createIssues(issues, targetProject.getIssueTypes());

        System.out.println("Processing comments...");
        processComments(createdIssues);

        System.out.println("Processing transitions...");
        processTransitions(issues);

        System.out.println("Successfully recreated " + issues.size() + " issues");
    }


    List<SearchIssue> searchIssues(List<String> issueKeys) {
        try {
            final HttpGet request = new HttpGet(jiraUrl + JiraApiPaths.SEARCH_ISSUES);
            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));

            URI uri = new URIBuilder(request.getURI())
                    .addParameter(JiraApiConstants.JQL, JiraApiConstants.JQL_VALUE + getSearchQueryParamList(issueKeys))
                    .addParameter(JiraApiConstants.FIELDS, JiraApiConstants.FIELDS_VALUE)
                    .addParameter(JiraApiConstants.MAX_RESULTS, JiraApiConstants.MAX_RESULTS_VALUE)
                    .build();
            request.setURI(uri);

            CloseableHttpResponse response = httpClient.execute(request);

            SearchIssueResponse searchResult = objectMapper.readValue(EntityUtils.toString(response.getEntity()), SearchIssueResponse.class);

            return searchResult.getIssues();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("An error occurred while searching for issues", e);
        }
    }

    List<CreatedIssue> createIssues(List<SearchIssue> issues, List<IssueType> targetProjectIssueTypes) {
        try {
            final HttpPost request = new HttpPost(jiraUrl + JiraApiPaths.CREATE_ISSUES);
            request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));
            request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

            String jsonBody = objectMapper.writeValueAsString(new CreateIssuesRequest(issues
                    .stream()
                    .map(searchIssue -> CreateIssues.from(searchIssue, Long.parseLong(targetProjectId), targetProjectIssueTypes))
                    .collect(Collectors.toList())));
            StringEntity entity = new StringEntity(jsonBody);
            request.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(request);
            CreateIssuesResponse createIssuesResponse = objectMapper.readValue(EntityUtils.toString(response.getEntity()), CreateIssuesResponse.class);

            IntStream.range(0, issues.size())
                    .forEach(i -> createIssuesResponse.getIssues().get(i).setOriginIssue(issues.get(i)));

            return createIssuesResponse.getIssues();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while creating issues in target jira", e);
        }
    }

    void commentIssue(String issueKey, SearchIssueComment comment) throws IOException {
        final HttpPost request = new HttpPost(jiraUrl + String.format(JiraApiPaths.COMMENT_ISSUE, issueKey));
        request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        String jsonBody = objectMapper.writeValueAsString(new AddCommentRequest(comment.getBody()));
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            EntityUtils.consumeQuietly(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("An error occurred while commenting issue " + issueKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Transition> getTransitions(String issueKey) {
        final HttpGet request = new HttpGet(jiraUrl + String.format(JiraApiPaths.GET_ISSUE_TRANSITIONS, issueKey));
        request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            GetTransitionsResponse result = objectMapper.readValue(EntityUtils.toString(response.getEntity()), GetTransitionsResponse.class);
            return result.getTransitions();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while getting transitions for issue " + issueKey);
        }
    }

    void transitionIssue(String issueKey, String newTransitionId) throws IOException {
        final HttpPost request = new HttpPost(jiraUrl + String.format(JiraApiPaths.TRANSITION_ISSUE, issueKey));
        request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

        Transition newTransition = new Transition();
        newTransition.setId(newTransitionId);
        SetTransitionRequest requestBody = new SetTransitionRequest(newTransition);

        String jsonBody = objectMapper.writeValueAsString(requestBody);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            EntityUtils.consumeQuietly(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new RuntimeException("An error occurred while setting transition for issue: " + issueKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Project getProject(Long projectId) {
        final HttpGet request = new HttpGet(jiraUrl + String.format(JiraApiPaths.GET_PROJECT, projectId));
        request.addHeader(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(jiraUsername, jiraApiToken));

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(EntityUtils.toString(response.getEntity()), Project.class);
        } catch (IOException e) {
            throw new RuntimeException("An err occurred while getting target project");
        }
    }


    private void findAndSetTransition(SearchIssue searchIssue) throws IOException {
        List<Transition> transitions = this.getTransitions(searchIssue.getKey());

        String originName = searchIssue.getFields().getStatus().getName();
        List<Transition> matchingTransitions = transitions
                .stream()
                .filter(transition -> transition.getName().equals(originName))
                .collect(Collectors.toList());

        if (matchingTransitions.size() == 1) {
            transitionIssue(searchIssue.getKey(), matchingTransitions.get(0).getId());
        } else if (matchingTransitions.size() < 1) {
            throw new RuntimeException("Could not find matching transition for issue: " + searchIssue.getKey());
        } else {
            throw new RuntimeException("Found more than 1 matching transitions for issue: " + searchIssue.getKey());
        }
    }

    private void processComments(List<CreatedIssue> createdIssues) {
        try {
            for (CreatedIssue createdIssue : createdIssues) {
                for (SearchIssueComment originComment : createdIssue.getOriginIssue().getFields().getSearchIssueComment().getComments()) {
                    commentIssue(createdIssue.getKey(), originComment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processTransitions(List<SearchIssue> searchIssues) {
        searchIssues.forEach(searchIssue -> {
            try {
                findAndSetTransition(searchIssue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static String getSearchQueryParamList(List<String> issueKeys) {
        return issueKeys
                .stream()
                .map(key -> "\"" + key + "\"")
                .collect(Collectors.joining(",", "(", ")"));
    }

    public static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
