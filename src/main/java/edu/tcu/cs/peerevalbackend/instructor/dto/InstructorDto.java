package edu.tcu.cs.peerevalbackend.instructor.dto;

import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record InstructorDto(String instructorId,
                            @NotEmpty(message = "name is required.")
                            String name,
                            @NotEmpty(message = "status is required.")
                            String status,
                            @NotEmpty(message = "list of teams is required.")
                            List<String> teamNames
                            ){
}
