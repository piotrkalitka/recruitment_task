package io.getint.recruitment_task.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssueComments {
    private Long maxResults;
    private Long total;
    private Long startAt;
    private List<SearchIssueComment> comments;

    public SearchIssueComments() {
    }

    public SearchIssueComments(Long maxResults, Long total, Long startAt, List<SearchIssueComment> comments) {
        this.maxResults = maxResults;
        this.total = total;
        this.startAt = startAt;
        this.comments = comments;
    }

    public Long getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Long maxResults) {
        this.maxResults = maxResults;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public List<SearchIssueComment> getComments() {
        return comments;
    }

    public void setComments(List<SearchIssueComment> comments) {
        this.comments = comments;
    }
}
