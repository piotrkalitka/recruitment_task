package io.getint.recruitment_task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskStatus {
    private long id;
    private String self;
    private String description;
    private String iconUrl;
    private String name;

    public TaskStatus() {
    }

    public TaskStatus(long id, String self, String description, String iconUrl, String name) {
        this.id = id;
        this.self = self;
        this.description = description;
        this.iconUrl = iconUrl;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
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
}
