package edu.tcu.cs.peerevalbackend.student;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Integer id){
        super("Could not find student with Id: " + id);
    }
}
