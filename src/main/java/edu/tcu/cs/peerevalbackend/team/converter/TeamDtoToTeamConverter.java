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
        Team team = new Team();
        team.setTeamName(source.teamName());
        team.setAcademicYear(source.academicYear());

        List<Instructor> instructors = new ArrayList<>();
        if(!source.instructorDtos().isEmpty()) {
            for(int i = 0; i < source.instructorDtos().size(); i++) {
                instructors.add(instructorDtoToInstructorConverter.convert(source.instructorDtos().get(i)));
            }
            team.setInstructors(instructors);
        }

        List<Student> students = new ArrayList<>();
        if(!source.studentDtos().isEmpty()) {
            for(int i = 0; i < source.studentDtos().size(); i++) {
                students.add(studentDtoToStudentConverter.convert(source.studentDtos().get(i)));
            }
            team.setStudents(students);
        }

        //Need to fix this
        if(source.sectionName() != null) {
            team.setSectionName(this.sectionRepository.findById(source.sectionName()).orElse(null));
        }

        return team;
    }
}