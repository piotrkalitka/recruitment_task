package io.getint.recruitment_task.domain.createissues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import io.getint.recruitment_task.domain.IssueType;
import io.getint.recruitment_task.domain.Project;
import io.getint.recruitment_task.domain.search.SearchIssue;
import io.getint.recruitment_task.domain.search.SearchIssueFields;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateIssues {

    private String id;
    private String key;
    private CreateIssuesFields fields;

    public CreateIssues() {
    }

    public CreateIssues(String id, String key, CreateIssuesFields fields) {
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

    public CreateIssuesFields getFields() {
        return fields;
    }

    public void setFields(CreateIssuesFields fields) {
        this.fields = fields;
    }

    public static CreateIssues from(SearchIssue searchIssue, Long targetProjectId, List<IssueType> targetProjectIssueTypes) {
        CreateIssues createIssues = new CreateIssues();
        createIssues.setId(searchIssue.getId());
        createIssues.setKey(searchIssue.getKey());
        createIssues.setFields(new CreateIssuesFields());

        Long targetProjectIssueTypeId = targetProjectIssueTypes
                .stream()
                .filter(issueType -> issueType.getDescription().equals(searchIssue.getFields().getIssueType().getDescription()))
                .findFirst()
                .map(IssueType::getId)
                .orElseThrow(() -> new RuntimeException("Could not find matching issue type"));

        SearchIssueFields fields = new SearchIssueFields();
        fields.setSummary(searchIssue.getFields().getSummary());
        fields.setDescription(searchIssue.getFields().getDescription());

        IssueType issueType = new IssueType(targetProjectIssueTypeId, null);
        fields.setIssueType(issueType);
        createIssues.setFields(CreateIssuesFields.from(fields));

        Project project = new Project();
        project.setId(targetProjectId);
        createIssues.fields.setProject(project);

        return createIssues;
    }

}
