package io.getint.recruitment_task.domain.createissues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateIssuesResponse {

    private List<CreatedIssue> issues;

    public CreateIssuesResponse() {
    }

    public CreateIssuesResponse(List<CreatedIssue> issues) {
        this.issues = issues;
    }

    public List<CreatedIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<CreatedIssue> issues) {
        this.issues = issues;
    }
}
