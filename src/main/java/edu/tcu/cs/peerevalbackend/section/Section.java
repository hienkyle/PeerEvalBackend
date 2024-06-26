package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.team.Team;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Section implements Serializable {

    @Id //this field 'sectionName' can be used as the primary key
    private String sectionName;

    private String academicYear;

    private String firstAndLastDate;

    //collection/list of teams it 'owns'; mapped by value has to be the same as the many side value name
    //the one side, Section, gives up responsibility of maintaining the foreign key (many side (Team) will store the FK), ORM will be used so that references become FK
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "section")
    private List<Team> teams;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "instructorId")
    private List<Instructor> instructors;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "studentId")
    private List<Student> students;

    //@ManyToOne
    //private Rubric rubricName;

    //CHECK THIS!!
    @ElementCollection
    @CollectionTable(name = "section_active_weeks", joinColumns = @JoinColumn(name = "section_name"))
    @Column(name = "active_week")
    private List<Date> activeWeeks;

    public Section() {
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getFirstAndLastDate() {
        return firstAndLastDate;
    }

    public void setFirstAndLastDate(String firstAndLastDate) {
        this.firstAndLastDate = firstAndLastDate;
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

    //public Rubric getRubricName() {
    //    return rubricName;
    //}

    //public void setRubricName(Rubric rubricName) {
    //    this.rubricName = rubricName;
    //}

    public List<Date> getActiveWeeks() {
        return activeWeeks;
    }


    public void setActiveWeeks(List<Date> activeWeeks) {
        this.activeWeeks = activeWeeks;
    }


    //public void setActiveWeeks(List<Date> activeWeeks) {
    //    this.activeWeeks = activeWeeks;
    //}

}
