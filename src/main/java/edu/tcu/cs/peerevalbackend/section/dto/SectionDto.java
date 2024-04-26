package edu.tcu.cs.peerevalbackend.section.dto;

import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SectionDto(
        @NotEmpty(message = "name is required.")
                         String sectionName,

        @NotEmpty(message = "academic year is required.")
                         String academicYear,

        @NotEmpty(message = "first and last date of academic year is required.")
                         String firstAndLastDate,

        List<TeamDto> teamDtos,

        List<InstructorDto> instructorDtos,

        List<StudentDto> studentDtos) {


    @Override
    public List<TeamDto> teamDtos() {
        return teamDtos;
    }

    @Override
    public List<InstructorDto> instructorDtos() {
        return instructorDtos;
    }

    @Override
    public List<StudentDto> studentDtos() {
        return studentDtos;
    }
}


