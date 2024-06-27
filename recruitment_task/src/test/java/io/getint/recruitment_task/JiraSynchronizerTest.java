package io.getint.recruitment_task;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import io.getint.recruitment_task.domain.search.SearchIssue;
import io.getint.recruitment_task.domain.search.SearchIssueComment;
import io.getint.recruitment_task.domain.search.SearchIssueResponse;
import io.getint.recruitment_task.domain.transition.GetTransitionsResponse;
import io.getint.recruitment_task.domain.transition.Transition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JiraSynchronizerTest {

    @Mock
    private CloseableHttpClient mockHttpClient;

    @Mock
    private CloseableHttpResponse mockHttpResponse;

    @Mock
    private HttpEntity mockHttpEntity;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private JiraSynchronizer jiraSynchronizer;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jiraSynchronizer = new JiraSynchronizer(mockHttpClient, mockObjectMapper);
    }


    @Test
    public void testSearchIssues() throws Exception {
        // Given
        List<String> issueKeys = Arrays.asList("TEST-1", "TEST-2");
        String jsonResponse = "{\"issues\": [{\"key\": \"TEST-1\"}, {\"key\": \"TEST-2\"}]}";
        SearchIssueResponse searchIssueResponse = new SearchIssueResponse();
        searchIssueResponse.setIssues(Arrays.asList(new SearchIssue("1", "TEST-1", null), new SearchIssue("2", "TEST-2", null)));

        when(mockHttpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));
        when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);
        when(mockObjectMapper.readValue(anyString(), ArgumentMatchers.eq(SearchIssueResponse.class))).thenReturn(searchIssueResponse);

        // When
        List<SearchIssue> result = jiraSynchronizer.searchIssues(issueKeys);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TEST-1", result.get(0).getKey());
        assertEquals("TEST-2", result.get(1).getKey());
    }

    @Test
    public void testCreateIssues() throws Exception {
        // Given
        List<String> issueKeys = Arrays.asList("TEST-1", "TEST-2");
        String jsonResponse = "{\"issues\": [{\"key\": \"TEST-1\"}, {\"key\": \"TEST-2\"}]}";


        HttpEntity mockHttpEntity = mock(HttpEntity.class);
        when(mockHttpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));

        when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);

        SearchIssueResponse searchIssueResponse = new SearchIssueResponse();
        searchIssueResponse.setIssues(Arrays.asList(new SearchIssue("1", "TEST-1", null), new SearchIssue("2", "TEST-2", null)));
        when(mockObjectMapper.readValue(anyString(), eq(SearchIssueResponse.class))).thenReturn(searchIssueResponse);

        // When
        List<SearchIssue> result = jiraSynchronizer.searchIssues(issueKeys);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TEST-1", result.get(0).getKey());
        assertEquals("TEST-2", result.get(1).getKey());
    }

    @Test
    public void testGetTransitions() throws Exception {
        // Given
        String issueKey = "TEST-1";
        String jsonResponse = "{\"transitions\": [{\"id\": \"1\", \"name\": \"To Do\"}, {\"id\": \"2\", \"name\": \"In Progress\"}]}";

        HttpEntity mockHttpEntity = mock(HttpEntity.class);
        when(mockHttpEntity.getContent()).thenReturn(new ByteArrayInputStream(jsonResponse.getBytes()));

        when(mockHttpResponse.getEntity()).thenReturn(mockHttpEntity);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockHttpResponse);

        GetTransitionsResponse transitionsResponse = new GetTransitionsResponse();
        transitionsResponse.setTransitions(Arrays.asList(new Transition("1", "To Do", false, false, false, false, false, false, null), new Transition("2", "In Progress", false, false, false, false, false, false, null)));
        when(mockObjectMapper.readValue(anyString(), eq(GetTransitionsResponse.class))).thenReturn(transitionsResponse);

        // When
        List<Transition> result = jiraSynchronizer.getTransitions(issueKey);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("To Do", result.get(0).getName());
        assertEquals("2", result.get(1).getId());
        assertEquals("In Progress", result.get(1).getName());
    }

    @Test
    public void testCommentIssue() throws Exception {
        // Given
        String issueKey = "TEST-1";
        SearchIssueComment comment = new SearchIssueComment();

        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");
        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 201, ""));

        // When
        jiraSynchronizer.commentIssue(issueKey, comment);

        // Then
        ArgumentCaptor<HttpPost> postCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(mockHttpClient).execute(postCaptor.capture());
    }

    @Test
    public void testTransitionIssue() throws Exception {
        // Given
        String issueKey = "TEST-1";
        String newTransitionId = "1";

        when(mockHttpClient.execute(any(HttpPost.class))).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, 204, ""));
        when(mockObjectMapper.writeValueAsString(any())).thenReturn("{}");

        // When
        jiraSynchronizer.transitionIssue(issueKey, newTransitionId);

        // Then
        ArgumentCaptor<HttpPost> postCaptor = ArgumentCaptor.forClass(HttpPost.class);
        verify(mockHttpClient).execute(postCaptor.capture());
    }

    @Test
    public void testGetBasicAuthenticationHeader() {
        String testUsername = "testUsername";
        String testPassword = "testPassword";
        String expectedResult = "Basic dGVzdFVzZXJuYW1lOnRlc3RQYXNzd29yZA==";

        assertEquals(expectedResult, JiraSynchronizer.getBasicAuthenticationHeader(testUsername, testPassword));
    }

    @Test
    public void testGetSearchQueryParamList() {
        List<String> issueKeys = List.of("KEY-1", "KEY-2", "KEY-3");
        String expectedResult = "(\"KEY-1\",\"KEY-2\",\"KEY-3\")";

        assertEquals(expectedResult, JiraSynchronizer.getSearchQueryParamList(issueKeys));
    }

}
