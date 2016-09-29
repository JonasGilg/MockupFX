package de.mockup.ui.controller.logic;

import de.mockup.system.model.Project;

class ControlSettings {

    public static synchronized boolean projectIsValid(Project p) {
        return (!p.getName().equals("") || p.getStoragePath().equals("") || p.getName() == null || p.getStoragePath() == null);
    }

    public static synchronized boolean viewCanBeDeleted(Project p, int i) {
        return (p.getViewStorage().containsKey(i));
    }

    //TODO: Check if Project Structure is valid to be played
}
