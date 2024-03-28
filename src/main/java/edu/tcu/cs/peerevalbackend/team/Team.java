package edu.tcu.cs.peerevalbackend.team;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Team implements Serializable {
    @Id
    private String teamName;

    /*
    * Has an error because Section is not annotated with the type of relationship
    * Ensure there is a field List<Team> teams in Section
    * Ensure field in Section class is annotated w/ @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "sectionName")
    */
    @ManyToOne
    private Section sectionName;

    private List<Date> activeWeeks;

    /*
    * Has an error because Instructor is not annotated with the type of relationship
    * Many to many relationship between teams and instructors
    * Ensure there is a field List<Team> teams in Section
    * Ensure Instructor is also annotated with @ManyToMany(mappedBy = "instructors")
    */
    @ManyToMany
    @JoinTable (
            name = "teams_and_instructors",
            joinColumns = @JoinColumn(name = "instructorId"),
            inverseJoinColumns = @JoinColumn(name = "teamName")
    )
    private List<Instructor> instructors;

    /*
    * Has an error because Student is not annotated with the type of relationship
    * Meaning: one team has many members (students)
    * This should eventually work, ensure Student class has something along the lines of Team teamName as one of its fields
    * The new annotation should be: @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "teamName")
    */
    @OneToMany(mappedBy = "teamName")
    private List<Student> members;

    //Dr. Wei has an empty constructor
    public Team() {
    }

    //Dr. Wei does not have an overloaded constructor, I included it just in case
    public Team(String teamName, Section sectionName, List<Date> activeWeeks, List<Instructor> instructors, List<Student> members) {
        this.teamName = teamName;
        this.sectionName = sectionName;
        this.activeWeeks = activeWeeks;
        this.instructors = instructors;
        this.members = members;
    }

    /* Below will be getters and setters */

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Section getSectionName() {
        return sectionName;
    }

    public void setSectionName(Section sectionName) {
        this.sectionName = sectionName;
    }

    public List<Date> getActiveWeeks() {
        return activeWeeks;
    }

    public void setActiveWeeks(List<Date> activeWeeks) {
        this.activeWeeks = activeWeeks;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getMembers() {
        return members;
    }

    public void setMembers(List<Student> members) {
        this.members = members;
    }
}
