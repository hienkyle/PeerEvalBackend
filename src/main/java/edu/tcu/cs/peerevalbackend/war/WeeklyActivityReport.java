package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WeeklyActivityReport {
    @Id
    Integer warId;

    @ManyToOne
    Student author;


    String task;

    String taskDesc;

    String taskCategory;

    String status;

    String activeWeek;

    Integer plannedHours;

    Integer actualHours;
}

