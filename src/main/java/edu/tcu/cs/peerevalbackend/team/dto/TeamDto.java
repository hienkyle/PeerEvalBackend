package edu.tcu.cs.peerevalbackend.team.dto;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/*
* Attributes/properties that can be seen to client
*/

//Make changes
public record TeamDto(@NotEmpty(message = "Team name is required.")
                      String teamName,

                      String academicYear,

                      /*
                      * Change all of these below
                      */

                      //Change to InstructorDto
                      List<InstructorDto> instructorDtos,

                      //Change to StudentDto
                      List<StudentDto> studentDtos,

                      //Change to SectionDto
                      String sectionName) {
}

