package edu.tcu.cs.peerevalbackend.student;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Student {
    @Id
    private Integer  student_id;
    private String first_name;
    private String middle_initial;
    private String last_name;
    private String password;
    private String team_name;
    private String section_name;

    public  Student(){

    }

}
