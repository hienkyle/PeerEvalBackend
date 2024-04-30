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

    /* DONE
    * **** Confirmed with Maribel ****
    * Has an error because Section is not annotated with the type of relationship
    * Ensure there is a field List<Team> teams in Section
    * Ensure field in Section class is annotated w/ @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "section")
    */
    @ManyToOne
    private Section section;

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
    @OneToMany(mappedBy = "team")
    private List<Student> students;

    String academicYear;

    //Dr. Wei has an empty constructor
    public Team() {
    }

    //Dr. Wei does not have an overloaded constructor, I included it just in case
    public Team(String teamName, Section section, List<Instructor> instructors, List<Student> students, String academicYear) {
        this.teamName = teamName;
        this.section = section;
        this.instructors = instructors;
        this.students = students;
        this.academicYear = academicYear;
    }

    /* Below will be getters and setters */

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    // *** THESE ARE NEEDED IN service AND controller

    /*
    * For use case 12
    */
    public void addStudent(Student student) {
        student.setTeam(this);
        this.students.add(student);
    }

    public Integer getNumberOfStudents() {
        return this.students.size();
    }

    /*
     * For use case 19
     */
    public void addInstructor(Instructor instructor) {
        instructor.addTeam(this);
        this.instructors.add(instructor);
    }

    public Integer getNumberOfInstructors() {
        return this.instructors.size();
    }

    /* For the different remove methods:
    * (1) Make sure Student class has setTeamName() method or something similar
    * (2) Make sure Instructor class has setTeamName() method or something similar
    */

    /*
    * removeAll methods used when deleting a team
    * For use case 14
    */
    public void removeAllStudents() {
        if(this.students != null) {
            //Iterate through list of students in a team and set team to null
            this.students.stream().forEach(student -> student.setTeam(null));
        }

        this.students = new ArrayList<>();
    }

    public void removeAllInstructors() {
        if(this.instructors != null) {
            //Iterate through list of instructors in a team and set team to null
            this.instructors.stream().forEach(instructor -> instructor.removeTeam(this));
        }

        this.instructors = new ArrayList<>();
    }

    /*
    * For use case 13
    */
    public void removeStudent(Student studentToBeAssigned) {
        //Remove specific student from team
        studentToBeAssigned.setTeam(null);
        this.students.remove(studentToBeAssigned);
    }

    /*
    * For use case 20
    */
    public void removeInstructor(Instructor instructorToBeAssigned) {
        //Remove specific instructor from team
        instructorToBeAssigned.removeTeam(this);
        this.instructors.remove(instructorToBeAssigned);
    }
}
