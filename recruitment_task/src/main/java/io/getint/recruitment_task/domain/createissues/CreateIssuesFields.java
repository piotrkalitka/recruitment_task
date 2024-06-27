package io.getint.recruitment_task.domain.createissues;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import io.getint.recruitment_task.domain.IssueType;
import io.getint.recruitment_task.domain.Project;
import io.getint.recruitment_task.domain.search.SearchIssueFields;

public class CreateIssuesFields {

    private String summary;
    private JsonNode description;
    private Project project;
    @JsonProperty("issuetype")
    private IssueType issueType;

    public CreateIssuesFields() {
    }

    public CreateIssuesFields(String summary, JsonNode description, Project project, IssueType issueType) {
        this.summary = summary;
        this.description = description;
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

    public static CreateIssuesFields from(SearchIssueFields searchIssueFields) {
        CreateIssuesFields createIssuesFields = new CreateIssuesFields();
        createIssuesFields.setSummary(searchIssueFields.getSummary());
        createIssuesFields.setDescription(searchIssueFields.getDescription());
        createIssuesFields.setIssueType(searchIssueFields.getIssueType());
        createIssuesFields.setProject(searchIssueFields.getProject());
        return createIssuesFields;
    }

}
