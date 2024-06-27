package io.getint.recruitment_task.domain.transition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCategory {

    private Long id;
    private String key;
    private String colorName;
    private String name;

    public StatusCategory() {
    }

    public StatusCategory(Long id, String key, String colorName, String name) {
        this.id = id;
        this.key = key;
        this.colorName = colorName;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
