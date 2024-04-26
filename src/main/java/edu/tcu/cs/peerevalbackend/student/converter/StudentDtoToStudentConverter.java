package edu.tcu.cs.peerevalbackend.student.converter;
import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.student.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoToStudentConverter implements Converter<StudentDto, Student>{
    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public StudentDtoToStudentConverter(SectionDtoToSectionConverter sectionDtoToSectionConverter) {
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }

    @Override
    public Student convert(StudentDto source){
        Student student = new Student();
        student.setStudentId(source.id());
        student.setFirstName(source.firstName());
        student.setMiddleInitial(source.middleInitial());
        student.setLastName(source.lastName());
        if (source.section() != null) {
            student.setSection(this.sectionDtoToSectionConverter.convert(source.section()));
        } else {
            student.setSection(null);
        }
        student.setAcademicYear(source.academicYear());
        return student;
    }
}
