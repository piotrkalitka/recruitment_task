package io.getint.recruitment_task.domain.createissues;

import java.util.List;

public class CreateIssuesRequest {

    private List<CreateIssues> issueUpdates;

    public CreateIssuesRequest(List<CreateIssues> issueUpdates) {
        this.issueUpdates = issueUpdates;
    }

    public List<CreateIssues> getIssueUpdates() {
        return issueUpdates;
    }

    public void setIssueUpdates(List<CreateIssues> issueUpdates) {
        this.issueUpdates = issueUpdates;
    }
}
