package io.getint.recruitment_task.domain.transition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTransitionsResponse {

    private List<Transition> transitions;

    public GetTransitionsResponse() {
    }

    public GetTransitionsResponse(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
}
