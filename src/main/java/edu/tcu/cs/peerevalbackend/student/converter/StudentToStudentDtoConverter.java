package edu.tcu.cs.peerevalbackend.student.converter;

import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
<<<<<<< HEAD
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
=======
import edu.tcu.cs.peerevalbackend.team.converter.TeamDtoToTeamConverter;
>>>>>>> main
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {
    private final TeamDtoToTeamConverter teamDtoToTeamConverter;

    public StudentToStudentDtoConverter(TeamDtoToTeamConverter teamDtoToTeamConverter){
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
