package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WeeklyActivityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

