package edu.tcu.cs.peerevalbackend.student;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import edu.tcu.cs.peerevalbackend.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
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

    public  Student(){

    }

}
