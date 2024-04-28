package edu.tcu.cs.peerevalbackend.student.converter;
import edu.tcu.cs.peerevalbackend.section.SectionRepository;
import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.team.TeamRepository;
import edu.tcu.cs.peerevalbackend.team.converter.TeamDtoToTeamConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoToStudentConverter implements Converter<StudentDto, Student>{

    private final TeamRepository teamRepository;
    private final SectionRepository sectionRepository;

    public StudentDtoToStudentConverter(TeamRepository teamRepository, SectionRepository sectionRepository) {
        this.teamRepository = teamRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public Student convert(StudentDto source){
        Student student = new Student();
        student.setStudentId(source.id());
        student.setFirstName(source.firstName());
        student.setMiddleInitial(source.middleInitial());
        student.setLastName(source.lastName());
        student.setAcademicYear(source.academicYear());
        student.setTeam(this.teamRepository.findById(source.teamName()).orElse(null));
        student.setSection(this.sectionRepository.findById(source.sectionName()).orElse(null));
        return student;
    }
}
