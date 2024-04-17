package edu.tcu.cs.peerevalbackend.student.converter;

import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {
    private final TeamDtoToTeamConverter teamDtoToTeamConverter;

    public StudentDtoToStudentConverter(TeamDtoToTeamConverter teamDtoToTeamConverter){
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
    }

    @Override
    public StudentDto convert(Student source){
        StudentDto studentDto = new StudentDto(source.getStudentId(),
                source.getFirstName(),
                source.getMiddleInitial(),
                source.getMiddleInitial(),
                source.getLastName(),
                source.getSectionName(),
                source.getAcademicYear(),
                source.getNumberOfWars(),
                source.getTeam() != null
                        ? this.teamDtoToTeamConverter.convert(source.getTeam())
                        : null);
        return studentDto;
    }
}
