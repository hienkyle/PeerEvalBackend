package edu.tcu.cs.peerevalbackend.student.converter;

import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.team.converter.TeamToTeamDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;
    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public StudentToStudentDtoConverter(TeamToTeamDtoConverter teamToTeamDtoConverter, SectionToSectionDtoConverter sectionToSectionDtoConverter){
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }

    @Override
    public StudentDto convert(Student source){
        StudentDto studentDto = new StudentDto(
                source.getStudentId(),
                source.getFirstName(),
                source.getMiddleInitial(),
                source.getLastName(),
                source.getSection() != null
                        ? this.sectionToSectionDtoConverter.convert(source.getSection())
                        : null,
                source.getAcademicYear(),
                source.getNumberOfWars(),
                source.getTeam() != null
                        ? this.teamToTeamDtoConverter.convert(source.getTeam())
                        : null);
        return studentDto;
    }
}
