package org.firstinspires.ftc.teamcode.controller;

import androidx.annotation.NonNull;

/**
 * The class defining Buttons on a GameController
 */
public class Button {
    private boolean state;
    private boolean previousState;

    /**
     * Creates a Button Object
     */
    public Button() {
        this.state = false;
        this.previousState = false;
    }

    /**
     * Whether the button is pressed
     *
     * @return true if the button is pressed, false otherwise
     */
    public boolean get() {
        return state;
    }

    /**
     * Whether the button's state just changed
     *
     * @return true if the button's state just changed, false otherwise
     */
    public boolean stateJustChanged() {
        return state != previousState;
    }

    /**
     * Whether the button was just pressed
     *
     * @return true if the button was just pressed, false otherwise
     */
    public boolean wasJustPressed() {
        return state && !previousState;
    }

    /**
     * Whether the button was just released
     *
     * @return true if the button was just released, false otherwise
     */
    public boolean wasJustReleased() {
        return !state && previousState;
    }

    /**
     * Updates the button's current and previous states
     *
     * @param newState the state to set the current state to
     */
    public void updateStates(boolean newState) {
        previousState = state;
        state = newState;
    }

    /**
     * Return the current state, for telemetry
     *
     * @return the button's value as String
     */
    @NonNull
    public String toString() {
        return String.valueOf(get());
    }
}