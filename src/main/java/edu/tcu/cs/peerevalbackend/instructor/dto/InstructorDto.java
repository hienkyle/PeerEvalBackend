package edu.tcu.cs.peerevalbackend.instructor.dto;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.team.Team;

import java.util.List;

public record InstructorDto(String instructorId,
                            String firstName,
                            String middleName,
                            String lastName,
                            String status,
                            String password,
                            String deactivateReason,
                            List<TeamDto> teamDtos,
                            SectionDto sectionDto) {
}
