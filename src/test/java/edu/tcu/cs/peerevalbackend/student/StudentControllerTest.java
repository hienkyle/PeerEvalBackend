package edu.tcu.cs.peerevalbackend.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Autowired
    ObjectMapper objectMapper;

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
    void testFindAllStudentsSuccess() throws Exception {
        // Given.
        given(this.studentService.findAll()).willReturn(this.students);

        // When and then
        this.mockMvc.perform(get("/peereval/students").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.students.size())))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Albus Dumbledore"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Harry Potter"));
    }

    @Test
    void testFindStudentByIdSuccess() throws Exception {
        // Given.
        given(this.studentService.findById(1)).willReturn(this.students.get(0));

        // When and then
        this.mockMvc.perform(get("/peereval/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Albus Dumbledore"));
    }

    @Test
    void testFindStudentByIdNotFound() throws Exception {
        // Given.
        given(this.studentService.findById(5)).willThrow(new StudentNotFoundException(5));

        // When and then
        this.mockMvc.perform(get("/peereval/students/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find student with Id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testAddStudentSuccess() throws Exception {
        StudentDto studentDto = new StudentDto(null, "Mason", "D","OConnor",null, "2024", 15, null);

        String json = this.objectMapper.writeValueAsString(studentDto);

        Student savedStudent = new Student();
        savedStudent.setStudentId(4);
        savedStudent.setFirstName("Hermione");

        // Given.
        given(this.studentService.addStudent(Mockito.any(Student.class))).willReturn(savedStudent);

        // When and then
        this.mockMvc.perform(post("/peereval/students").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.studentId").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("Hermione"));
    }
    @Test
    void testUpdateStudentSuccess() throws Exception {
        StudentDto studentDto = new StudentDto(null, "Mason", "d","OConnor",null, "2024",15,null);

        Student updatedStudent = new Student();
        updatedStudent.setStudentId(1);
        updatedStudent.setFirstName("Updated student name");

        String json = this.objectMapper.writeValueAsString(updatedStudent);

        // Given.
        given(this.studentService.updateStudent(eq(1), Mockito.any(Student.class))).willReturn(updatedStudent);

        // When and then
        this.mockMvc.perform(put("/peereval/students/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("Updated student name"));
    }
    @Test
    void testUpdateStudentErrorWithNonExistentId() throws Exception {
        // Given.
        given(this.studentService.updateStudent(eq(5), Mockito.any(Student.class))).willThrow(new StudentNotFoundException(5));

        StudentDto studentDto = new StudentDto(5, "Mason", "D","OConnor",null, "2024", 15, null);

        String json = this.objectMapper.writeValueAsString(studentDto);

        // When and then
        this.mockMvc.perform(put("/peereval/students/5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find student with Id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testDeleteStudentSuccess() throws Exception {
        // Given.
        doNothing().when(this.studentService).deleteStudent(3);

        // When and then
        this.mockMvc.perform(delete("/peereval/students/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    void testDeleteStudentErrorWithNonExistentId() throws Exception {
        // Given.
        doThrow(new StudentNotFoundException(5)).when(this.studentService).deleteStudent(5);

        // When and then
        this.mockMvc.perform(delete("/peereval/students/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find student with Id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
