package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Instructor implements Serializable {
    @Id
    private String instructorId;

    private String name;

    private String status;

    private String password;

    private String deactivateReason;

    @ManyToMany
    private List<Team> teams;

    @ManyToMany
    private Section section;

    public Instructor() {

    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeactivateReason() {
        return deactivateReason;
    }

    public void setDeactivateReason(String deactivateReason) {
        this.deactivateReason = deactivateReason;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<String> getTeamNames(){
        return this.teams != null ?
                this.teams.stream().map(team -> team.getTeamName()).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
