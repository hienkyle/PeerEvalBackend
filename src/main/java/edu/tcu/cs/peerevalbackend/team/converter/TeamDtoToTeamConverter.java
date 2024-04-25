package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorDtoToInstructorConverter;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {
    private final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;
    private final StudentDtoToStudentConverter studentDtoToStudentConverter;

    public TeamDtoToTeamConverter(InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, StudentDtoToStudentConverter studentDtoToStudentConverter) {
        this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    }

    /*
     * Please ensure that these are changed to their respective Dto types
     */
    @Override
    public Team convert(TeamDto source) {
        Team team = new Team();
        team.setTeamName(source.teamName());
        team.setActiveWeeks(source.activeWeeks());
        team.setAcademicYear(source.academicYear());

        List<Instructor> instructors = new ArrayList<>();
        if(!source.instructorDtos().isEmpty() || source.instructorDtos() != null) {
            for(int i = 0; i < source.instructorDtos().size(); i++) {
                instructors.add(instructorDtoToInstructorConverter.convert(source.instructorDtos().get(i)));
            }
            team.setInstructors(instructors);
        }

        List<Student> students = new ArrayList<>();
        if(!source.studentDtos().isEmpty() || source.studentDtos() != null) {
            for(int i = 0; i < source.studentDtos().size(); i++) {
                students.add(studentDtoToStudentConverter.convert(source.studentDtos().get(i)));
            }
            team.setStudents(students);
        }

        if(source.sectionDto() != null) {
        }

//
//        team.setSectionName(source.sectionName());

        return team;
    }
}
/*
* source.getOwner() != null
                ? this.wizardToWizardDtoConverter.convert(source.getOwner())
                : null);
* */