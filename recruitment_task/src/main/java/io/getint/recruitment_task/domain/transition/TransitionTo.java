package io.getint.recruitment_task.domain.transition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransitionTo {

    private String id;
    private String description;
    private String iconUrl;
    private String name;
    private StatusCategory statusCategory;

    public TransitionTo() {
    }

    public TransitionTo(String id, String description, String iconUrl, String name, StatusCategory statusCategory) {
        this.id = id;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
        this.statusCategory = statusCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }

    public void setStatusCategory(StatusCategory statusCategory) {
        this.statusCategory = statusCategory;
    }
}
