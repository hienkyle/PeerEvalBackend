package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.rubric.Rubric;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.team.Team;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Section implements Serializable {

    @Id
    private String sectionName;

    //@ManyToOne
    private Rubric rubricName;

    //CHECK THIS!!
    //@OneToMany ??
    private List<Date> activeWeeks;

    private String academicYear;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "sectionName")
    private List<Team> teams;

    //@OneToMany({CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "instructor.name??")
    private List<Instructor> instructors;

    //@OneToMany({CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "student.name??")
    private List<Student> students;

    public Section() {
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Rubric getRubricName() {
        return rubricName;
    }

    public void setRubricName(Rubric rubricName) {
        this.rubricName = rubricName;
    }

    public List<Date> getActiveWeeks() {
        return activeWeeks;
    }

    public void setActiveWeeks(List<Date> activeWeeks) {
        this.activeWeeks = activeWeeks;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
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
}
