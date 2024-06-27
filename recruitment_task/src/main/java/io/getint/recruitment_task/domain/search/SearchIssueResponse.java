package io.getint.recruitment_task.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssueResponse {

    private List<SearchIssue> issues;

    public List<SearchIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<SearchIssue> issues) {
        this.issues = issues;
    }
}
