package edu.tcu.cs.peerevalbackend.student;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    List<Student> students;

    @BeforeEach
    void setUp(){
        this.students = new ArrayList<>();
        Student s1 = new Student();
        s1.setStudentId(1);
        s1.setFirstName("Mason");
        s1.setMiddleInitial("D");
        s1.setLastName("OConnor");
        s1.setAcademicYear("2024");
        s1.setPassword("12345");
        this.students.add(s1);

        Student s2 = new Student();
        s2.setStudentId(2);
        s2.setFirstName("Jake");
        s2.setMiddleInitial("B");
        s2.setLastName("OConnor");
        s2.setAcademicYear("2024");
        s2.setPassword("54321");
        this.students.add(s2);

        Student s3 = new Student();
        s3.setStudentId(3);
        s1.setFirstName("Asher");
        s1.setMiddleInitial("D");
        s1.setLastName("OConnor");
        s1.setAcademicYear("2024");
        s1.setPassword("1518123");
        this.students.add(s3);

    }
    @AfterEach
    void tearDown(){

    }

    @Test
    void testFindByIdSucces(){
        //Given
        Student s1 = new Student();
        s1.setStudentId(1);
        s1.setFirstName("Mason");
        s1.setMiddleInitial("D");
        s1.setLastName("OConnor");
        s1.setAcademicYear("2024");
        s1.setPassword("12345");

        given(this.studentRepository.findById(1)).willReturn(Optional.of(s1));

        //When
        Student returnedStudent = this.studentService.findById(1);

        //Then
        assertThat(returnedStudent.getStudentId()).isEqualTo(s1.getStudentId());
        assertThat(returnedStudent.getFirstName()).isEqualTo(s1.getFirstName());
        verify(this.studentRepository, times(1)).findById(1);

    }
    @Test
    void testFindByIdNotFound(){
        //Given
        given(this.studentRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(()->{
            Student returnedStudent = this.studentService.findById(1);
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Could not find student with Id 1");
        verify(this.studentRepository,times(1)).findById(1);
    }
    @Test
    void testFindAllSuccess(){
        //Given
        given(this.studentRepository.findAll()).willReturn(this.students);

        //When
        List<Student> actualStudents = this.studentService.findAll();

        //Then
        assertThat(actualStudents.size()).isEqualTo(this.students.size());
        verify(this.studentRepository, times(1)).findAll();

    }
    @Test
    void testUpdateSuccess(){
        //Given
        Student oldStudent = new Student();
        oldStudent.setStudentId(1);
        oldStudent.setFirstName("Mason");

        Student update = new Student();
        update.setFirstName("Mason - update");

        given(this.studentRepository.findById(1)).willReturn(Optional.of(oldStudent));
        given(this.studentRepository.save(oldStudent)).willReturn(oldStudent);

        //When
        Student updatedStudent = this.studentService.updateStudent(1,update);

        //Then
        assertThat(updatedStudent.getStudentId()).isEqualTo(1);
        assertThat(updatedStudent.getFirstName()).isEqualTo(update.getFirstName());
        verify(this.studentRepository,times(1)).findById(1);
        verify(this.studentRepository,times(1)).save(oldStudent);

    }
    @Test
    void testUpdateStudentNotFound(){
        Student update = new Student();
        update.setFirstName("Mason - update");

        given(this.studentRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, ()->{
            this.studentService.updateStudent(1, update);
        });

        verify(this.studentRepository, times(1)).findById(1);

    }
    @Test
    void testAddStudentSuccess(){
        //Given
        Student newStudent = new Student();
        newStudent.setFirstName("Jasmine");

        given(this.studentRepository.save(newStudent)).willReturn(newStudent);

        //When
        Student returnedStudent = this.studentService.addStudent(newStudent);

        //Then
        assertThat(returnedStudent.getFirstName()).isEqualTo(newStudent.getFirstName());
        verify(this.studentRepository, times(1)).save(newStudent);
    }

    @Test
    void testDeleteStudentSuccess(){
        Student student = new Student();
        student.setStudentId(1);
        student.setFirstName("Albus Dumbledore");

        given(this.studentRepository.findById(1)).willReturn(Optional.of(student));
        doNothing().when(this.studentRepository).deleteById(1);

        this.studentService.deleteStudent(1);
        verify(this.studentRepository,times(1)).deleteById(1);

    }
    @Test
    void testDeleteStudentNotFound(){
        given(this.studentRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, ()->{
            this.studentService.deleteStudent(1);
        });

        verify(this.studentRepository,times(1)).findById(1);
    }
}
