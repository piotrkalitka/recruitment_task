package io.getint.recruitment_task.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import io.getint.recruitment_task.domain.IssueType;
import io.getint.recruitment_task.domain.Project;
import io.getint.recruitment_task.domain.TaskStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssueFields {
    private String summary;
    private JsonNode description;
    private TaskStatus status;
    private SearchIssueComments comment;
    private Project project;

    @JsonProperty("issuetype")
    private IssueType issueType;

    public SearchIssueFields() {
    }

    public SearchIssueFields(String summary, JsonNode description, TaskStatus status, SearchIssueComments comment, Project project, IssueType issueType) {
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.comment = comment;
        this.project = project;
        this.issueType = issueType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public JsonNode getDescription() {
        return description;
    }

    public void setDescription(JsonNode description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public SearchIssueComments getSearchIssueComment() {
        return comment;
    }

    public void setComment(SearchIssueComments comment) {
        this.comment = comment;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }
}
