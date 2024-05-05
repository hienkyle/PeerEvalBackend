package edu.tcu.cs.peerevalbackend.student;

import edu.tcu.cs.peerevalbackend.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevalbackend.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import edu.tcu.cs.peerevalbackend.system.Result;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peerEval/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentDtoToStudentConverter studentDtoToStudentConverter;
    private final StudentToStudentDtoConverter studentToStudentDtoConverter;

    public StudentController(StudentService studentService, StudentDtoToStudentConverter studentDtoToStudentConverter, StudentToStudentDtoConverter studentToStudentDtoConverter) {
        this.studentService = studentService;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    }

    @PostMapping("/")
    public Result addStudent(@RequestBody Student newStudent){
        Student savedStudent = studentService.addStudent(newStudent);
        StudentDto savedDto = studentToStudentDtoConverter.convert(savedStudent);

        return new Result(true, StatusCode.SUCCESS, "Successfully saved student", savedDto);
    }

    @DeleteMapping("/{studentId}")
    public Result deleteStudentById(@PathVariable Integer studentId){
        this.studentService.deleteStudent(studentId);
        return new Result(true, StatusCode.SUCCESS, "Successfully deleted student");
    }

    @PutMapping("/{studentId}")
    public Result updateStudent(@PathVariable Integer studentId, @RequestBody StudentDto studentDto){
        Student update = studentDtoToStudentConverter.convert(studentDto);
        Student updatedStudent = studentService.updateStudent(studentId, update);
        StudentDto updatedStudentDto = studentToStudentDtoConverter.convert(updatedStudent);
        return new Result(true, StatusCode.SUCCESS, "Successfully updated student", updatedStudentDto);
    }

    @GetMapping("/{studentId}")
    public Result getStudentById(@PathVariable Integer studentId){
        Student foundStudent = studentService.findById(studentId);
        StudentDto foundDto = studentToStudentDtoConverter.convert(foundStudent);
        return new Result(true, StatusCode.SUCCESS, "Successfully found student", foundDto);
    }

    @GetMapping("/")
    public Result getAllStudents(){
        List<Student> foundStudents = studentService.findAll();
        List<StudentDto>  studentDtos = foundStudents.stream().map(this.studentToStudentDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Found all students", studentDtos);
    }
    @PostMapping("/search")
    public Result findStudentsByCriteria(@RequestBody Map<String, String> searchCriteria, Pageable pagable){
        Page<Student> studnetPage = this.studentService.findByCriteria(searchCriteria, pagable);
        Page<StudentDto> studentDtoPage = studnetPage.map(this.studentToStudentDtoConverter::convert);
        return new Result(true, StatusCode.SUCCESS, "Found by criteria success", studentDtoPage);
    }

}
