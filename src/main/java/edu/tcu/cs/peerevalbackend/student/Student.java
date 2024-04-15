package edu.tcu.cs.peerevalbackend.student;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.war.WeeklyActivityReport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {
    @Id
    private Integer  studentId;

    private String firstName;

    private String middleInitial;

    private String lastName;

    private String password;

    private String academicYear;

    @ManyToOne
    private Team team;

    private String sectionName;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "author")
    private List<WeeklyActivityReport> wars = new ArrayList<>();

    public  Student(){

    }
    public void assignToTeam(Team team){
        this.team =  team;
        List<Student> currentStudents = team.getMembers();
        currentStudents.add(this);
        return;
    }

}
