package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Make changes
@Component
public class TeamToTeamDtoConverter implements Converter<Team, TeamDto> {
    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;
    private final StudentToStudentDtoConverter studentToStudentDtoConverter;

    public TeamToTeamDtoConverter(InstructorToInstructorDtoConverter instructorToInstructorDtoConverter, StudentToStudentDtoConverter studentToStudentDtoConverter) {
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    }

    /*
    * Please ensure that these are changed to their respective Dto types
    */
    @Override
    public TeamDto convert(Team source) {
        TeamDto teamDto = new TeamDto(
                source.getTeamName(),
                source.getAcademicYear(),
                source.getInstructors() != null ? source.getInstructors().stream()
                        .map(instructor -> instructorToInstructorDtoConverter.convert(instructor)).collect(Collectors.toList()) : null,
                source.getStudents() != null ? source.getStudents().stream()
                        .map(student -> studentToStudentDtoConverter.convert(student)).collect(Collectors.toList()) : null,
                source.getSection() != null
                        ? source.getSection().getSectionName()
                        : null);
        return teamDto;
    }
}