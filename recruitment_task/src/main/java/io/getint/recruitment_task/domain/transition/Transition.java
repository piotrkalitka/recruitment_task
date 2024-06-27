package io.getint.recruitment_task.domain.transition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transition {

    private String id;
    private String name;
    private boolean hasScreen;
    private boolean isGlobal;
    private boolean isInitial;
    private boolean isAvailable;
    private boolean isConditional;
    private boolean isLooped;
    private TransitionTo to;

    public Transition() {
    }

    public Transition(String id, String name, boolean hasScreen, boolean isGlobal, boolean isInitial, boolean isAvailable, boolean isConditional, boolean isLooped, TransitionTo to) {
        this.id = id;
        this.name = name;
        this.hasScreen = hasScreen;
        this.isGlobal = isGlobal;
        this.isInitial = isInitial;
        this.isAvailable = isAvailable;
        this.isConditional = isConditional;
        this.isLooped = isLooped;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasScreen() {
        return hasScreen;
    }

    public void setHasScreen(boolean hasScreen) {
        this.hasScreen = hasScreen;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isConditional() {
        return isConditional;
    }

    public void setConditional(boolean conditional) {
        isConditional = conditional;
    }

    public boolean isLooped() {
        return isLooped;
    }

    public void setLooped(boolean looped) {
        isLooped = looped;
    }

    public TransitionTo getTo() {
        return to;
    }

    public void setTo(TransitionTo to) {
        this.to = to;
    }
}
