package io.getint.recruitment_task.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssue {
    private String id;
    private String key;
    private SearchIssueFields fields;

    public SearchIssue() {
    }

    public SearchIssue(String id, String key, SearchIssueFields fields) {
        this.id = id;
        this.key = key;
        this.fields = fields;
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

    public SearchIssueFields getFields() {
        return fields;
    }

    public void setFields(SearchIssueFields fields) {
        this.fields = fields;
    }
}
