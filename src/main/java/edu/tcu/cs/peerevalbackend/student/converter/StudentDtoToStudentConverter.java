package edu.tcu.cs.peerevalbackend.student.converter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.student.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoToStudentConverter implements Converter<StudentDto, Student>{
    @Override
    public Student convert(StudentDto source){
        Student student = new Student();
        student.setStudentId(source.id());
        student.setFirstName(source.firstName());
        student.setMiddleInitial(source.middleInitial());
        student.setLastName(source.lastName());
        student.setSectionName(source.sectionName());
        student.setAcademicYear(source.academicYear());
        student.setTeam(source.team());
        return student;
    }
}
