package io.getint.recruitment_task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private Long id;
    private List<IssueType> issueTypes;

    public Project() {
    }

    public Project(Long id, List<IssueType> issueTypes) {
        this.id = id;
        this.issueTypes = issueTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(List<IssueType> issueTypes) {
        this.issueTypes = issueTypes;
    }
}
