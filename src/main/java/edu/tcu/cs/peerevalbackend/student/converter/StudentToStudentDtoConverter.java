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

    public StudentToStudentDtoConverter(){}

    @Override
    public StudentDto convert(Student source){
        StudentDto studentDto = new StudentDto(
                source.getStudentId(),
                source.getFirstName(),
                source.getMiddleInitial(),
                source.getLastName(),
                source.getAcademicYear(),
                source.getNumberOfWars(),
                source.getTeam() != null
                        ? source.getTeamName()
                        : null,
                source.getSection() != null
                        ? source.getSectionName()
                        : null);
        return studentDto;
    }
}
