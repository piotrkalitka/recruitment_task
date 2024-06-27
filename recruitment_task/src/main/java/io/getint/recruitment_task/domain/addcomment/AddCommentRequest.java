package io.getint.recruitment_task.domain.addcomment;

import com.fasterxml.jackson.databind.JsonNode;

public class AddCommentRequest {

    private JsonNode body;

    public AddCommentRequest() {
    }

    public AddCommentRequest(JsonNode body) {
        this.body = body;
    }

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }
}
