package io.getint.recruitment_task.domain.transition;

public class SetTransitionRequest {

    private Transition transition;

    public SetTransitionRequest() {
    }

    public SetTransitionRequest(Transition transition) {
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }
}
