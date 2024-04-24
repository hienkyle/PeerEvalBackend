package edu.tcu.cs.peerevalbackend.student.converter;

import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.team.converter.TeamToTeamDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    public StudentToStudentDtoConverter(TeamToTeamDtoConverter teamToTeamDtoConverter){

        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
    }

    @Override
    public StudentDto convert(Student source){
        StudentDto studentDto = new StudentDto(source.getStudentId(),
                source.getFirstName(),
                source.getMiddleInitial(),
                source.getLastName(),
                source.getSection().getSectionName(),
                source.getAcademicYear(),
                source.getNumberOfWars(),
                source.getTeam() != null
                        ? this.teamToTeamDtoConverter.convert(source.getTeam())
                        : null);
        return studentDto;
    }
}
