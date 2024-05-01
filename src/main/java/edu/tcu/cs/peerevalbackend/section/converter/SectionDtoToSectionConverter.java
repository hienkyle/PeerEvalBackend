package edu.tcu.cs.peerevalbackend.section.converter;

import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorDtoToInstructorConverter;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevalbackend.team.converter.TeamDtoToTeamConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class SectionDtoToSectionConverter implements Converter<SectionDto, Section> {

    private final TeamDtoToTeamConverter teamDtoToTeamConverter;
    private final InstructorDtoToInstructorConverter instructorDtoToInstructorConverter;
    private final StudentDtoToStudentConverter studentDtoToStudentConverter;

    public SectionDtoToSectionConverter(TeamDtoToTeamConverter teamDtoToTeamConverter, InstructorDtoToInstructorConverter instructorDtoToInstructorConverter, StudentDtoToStudentConverter studentDtoToStudentConverter) {
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
        this.instructorDtoToInstructorConverter = instructorDtoToInstructorConverter;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    }

    @Override
    public Section convert(SectionDto source) {

        if (source == null) {
            return null; // Handle null input gracefully
        }

        Section section = new Section();
        section.setSectionName(source.sectionName());
        section.setAcademicYear(source.academicYear());
        section.setFirstAndLastDate(source.firstAndLastDate());
        if(source.teamDtos() != null) {
            section.setTeams(source.teamDtos().stream()
                    .map(teamDtoToTeamConverter::convert).collect(Collectors.toList()));
        }
        else{
            section.setTeams(new ArrayList<>());
        }
        if(source.instructorDtos() != null) {
            section.setInstructors(source.instructorDtos().stream()
                    .map(instructorDtoToInstructorConverter::convert).collect(Collectors.toList()));
        }
        else{
            section.setInstructors(new ArrayList<>());
        }
        if(source.studentDtos() != null) {
            section.setStudents(source.studentDtos().stream()
                    .map(studentDtoToStudentConverter::convert).collect(Collectors.toList()));
        }
        else{
            section.setInstructors(new ArrayList<>());
        }
        return section;
    }

}
