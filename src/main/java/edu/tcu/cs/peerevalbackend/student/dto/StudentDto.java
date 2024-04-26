package edu.tcu.cs.peerevalbackend.student.dto;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;

public record StudentDto(
    Integer id,
    String firstName,
    String middleInitial,
    String lastName,
    String academicYear,
    Integer numberOfWars,
    String teamName,
    String sectionName
    )
{}
