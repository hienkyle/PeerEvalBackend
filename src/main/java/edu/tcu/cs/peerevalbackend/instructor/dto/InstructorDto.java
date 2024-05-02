package edu.tcu.cs.peerevalbackend.instructor.dto;

import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/*
 * InstructorDto
 *
 * @param instructorId - the id of the instructor
 * @param message - the name of the instructor
 * @param status - active or deactivated
 * @param teamNames - a list of team names supervised by this instructor
 */
public record InstructorDto(String instructorId,
                            @NotEmpty(message = "name is required.")
                            String name,
                            @NotEmpty(message = "status is required.")
                            ActiveStatus status,
                            List<String> teamNames
                            ){
}
