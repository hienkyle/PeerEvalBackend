package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.section.SectionRepository;
import edu.tcu.cs.peerevalbackend.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        /*
        * Convert each instructor to instructor dto
        */
        List<InstructorDto> instructorDtos = new ArrayList<>();
        if(!source.getInstructors().isEmpty()) {
            for(int i = 0; i < source.getNumberOfInstructors(); i++) {
                instructorDtos.add(instructorToInstructorDtoConverter.convert(source.getInstructors().get(i)));
            }
            ;
        }

        /*
         * Convert each student to student dto
         */
        List<StudentDto> studentsDtos = new ArrayList<>();
        if(!source.getInstructors().isEmpty()) {
            for(int i = 0; i < source.getNumberOfStudents(); i++) {
                studentsDtos.add(studentToStudentDtoConverter.convert(source.getStudents().get(i)));
            }
        }

        TeamDto teamDto = new TeamDto(
                source.getTeamName(),
                source.getAcademicYear(),
                instructorDtos,
                studentsDtos,
                source.getSectionName() != null
                        ? source.getSectionName().getSectionName()
                        : null);
        return teamDto;
    }

    /*
    * source.getSectionName() != null
                        ? this.sectionToSectionDtoConverter.convert(source.getSectionName())
                        : null
    */

}