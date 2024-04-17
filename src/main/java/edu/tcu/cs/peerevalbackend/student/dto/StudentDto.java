package edu.tcu.cs.peerevalbackend.student.dto;

public record StudentDto(
    Integer id,
    String firstName,
    String middleInitial,
    String lastName,
    String sectionName,
    String academicYear,
    TeamDto team
    )
{}
