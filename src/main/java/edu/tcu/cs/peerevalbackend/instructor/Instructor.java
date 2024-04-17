package edu.tcu.cs.peerevalbackend.instructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Instructor implements Serializable {

    @Id
    private String instructorName;
}
