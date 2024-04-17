package edu.tcu.cs.peerevalbackend.team;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String teamName) {
        super("Could not find team with team name " + teamName + " :(");
    }
}