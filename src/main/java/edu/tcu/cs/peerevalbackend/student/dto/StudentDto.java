package edu.tcu.cs.peerevalbackend.student.dto;

import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;

public record StudentDto(
    Integer id,
    String firstName,
    String middleInitial,
    String lastName,
    String sectionName,
    String academicYear,
    Integer numberOfWars,
    TeamDto team
    )
{}
