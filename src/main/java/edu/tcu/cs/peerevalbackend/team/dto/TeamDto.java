package edu.tcu.cs.peerevalbackend.team.dto;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/*
* Attributes/properties that can be seen to client
*/
public record TeamDto(@NotEmpty(message = "Team name is required.")
                      String teamName,

                      List<Date> activeWeeks,
                      String academicYear,

                      //Change to InstructorDto
                      List<Instructor> instructors,

                      //Change to StudentDto
                      List<Student> students,

                      //Change to SectionDto
                      Section sectionName) {
}