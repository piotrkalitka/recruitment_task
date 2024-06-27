package io.getint.recruitment_task.domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIssueComment {
    private String id;
    private JsonNode body;
    private Date created;
    private Date updated;
    private boolean jsdPublic;

    public SearchIssueComment() {
    }

    public SearchIssueComment(String id, JsonNode body, Date created, Date updated, boolean jsdPublic) {
        this.id = id;
        this.body = body;
        this.created = created;
        this.updated = updated;
        this.jsdPublic = jsdPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isJsdPublic() {
        return jsdPublic;
    }

    public void setJsdPublic(boolean jsdPublic) {
        this.jsdPublic = jsdPublic;
    }
}
