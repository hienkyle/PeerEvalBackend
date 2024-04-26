package edu.tcu.cs.peerevalbackend.section.converter;

import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevalbackend.team.converter.TeamToTeamDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class  SectionToSectionDtoConverter implements Converter<Section, SectionDto> {

    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    private final StudentToStudentDtoConverter studentToStudentDtoConverter;

    public SectionToSectionDtoConverter(TeamToTeamDtoConverter teamToTeamDtoConverter, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter, StudentToStudentDtoConverter studentToStudentDtoConverter) {
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    }

    @Override
    public SectionDto convert(Section source) {
        SectionDto sectionDto = new SectionDto(source.getSectionName(),
                                               source.getAcademicYear(),
                                               source.getFirstAndLastDate(),
                                               source.getTeams() != null ? source.getTeams().stream()
                                                       .map(team -> teamToTeamDtoConverter.convert(team)).collect(Collectors.toList()) : null,
                                               source.getInstructors() != null ? source.getInstructors().stream()
                                                       .map(instructor -> instructorToInstructorDtoConverter.convert(instructor)).collect(Collectors.toList()) : null,
                                               source.getStudents() != null ? source.getStudents().stream()
                                                       .map(student -> studentToStudentDtoConverter.convert(student)).collect(Collectors.toList()) : null

        );

        return sectionDto;
    }

}
