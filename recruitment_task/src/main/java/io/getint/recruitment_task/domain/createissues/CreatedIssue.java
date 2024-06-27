package io.getint.recruitment_task.domain.createissues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.getint.recruitment_task.domain.search.SearchIssue;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedIssue {

    private String id;
    private String key;
    private SearchIssue originIssue;

    public CreatedIssue() {
    }

    public CreatedIssue(String id, String key, SearchIssue originIssue) {
        this.id = id;
        this.key = key;
        this.originIssue = originIssue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchIssue getOriginIssue() {
        return originIssue;
    }

    public void setOriginIssue(SearchIssue originIssue) {
        this.originIssue = originIssue;
    }
}
