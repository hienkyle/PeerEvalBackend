package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.SectionRepository;
import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorDtoToInstructorConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Make changes
@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {
    private final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;
    private final StudentDtoToStudentConverter studentDtoToStudentConverter;
    private final SectionRepository sectionRepository;

    public TeamDtoToTeamConverter(InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, StudentDtoToStudentConverter studentDtoToStudentConverter, SectionRepository sectionRepository) {
        this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
        this.sectionRepository = sectionRepository;
    }

    /*
     * Please ensure that these are changed to their respective Dto types
     */
    @Override
    public Team convert(TeamDto source) {
        if(source == null) {
            return null; //Handle null input gracefully
        }

        Team team = new Team();
        team.setTeamName(source.teamName());
        team.setAcademicYear(source.academicYear());

        if(source.instructorDtos() != null) {
            team.setInstructors(source.instructorDtos().stream()
                    .map(instructorDtoToInstructorConverter::convert).collect(Collectors.toList()));
        } else {
            team.setInstructors(new ArrayList<>());
        }

        if(source.studentDtos() != null) {
            team.setStudents(source.studentDtos().stream()
                    .map(studentDtoToStudentConverter::convert).collect(Collectors.toList()));
        } else {
            team.setStudents(new ArrayList<>());
        }

        if(source.sectionName() != null) {
            team.setSection(this.sectionRepository.findById(source.sectionName()).orElse(null));
        } else {
            team.setSection(null);
        }

        return team;
    }
}