package edu.tcu.cs.peerevalbackend.instructor.dto;

import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/*
 * InstructorDto
 *
 * @param instructorId - the id of the instructor
 * @param message - the name of the instructor
 * @param status - active or deactivated
 * @param teamDtos - a list of TeamDtos supervised by this instructor
 */
public record InstructorDto(String instructorId,
                            @NotEmpty(message = "name is required.")
                            String name,
                            @NotEmpty(message = "status is required.")
                            String status,
                            List<TeamDto> teamDtos
                            ){
}
