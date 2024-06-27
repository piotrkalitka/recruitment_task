package io.getint.recruitment_task;

public class JiraApiPaths {

    public static final String SEARCH_ISSUES = "rest/api/3/search";
    public static final String CREATE_ISSUES = "rest/api/3/issue/bulk";
    public static final String COMMENT_ISSUE = "rest/api/3/issue/%s/comment";
    public static final String GET_ISSUE_TRANSITIONS = "rest/api/3/issue/%s/transitions";
    public static final String TRANSITION_ISSUE = "rest/api/3/issue/%s/transitions";

    public static final String GET_PROJECT = "/rest/api/3/project/%d";

}
