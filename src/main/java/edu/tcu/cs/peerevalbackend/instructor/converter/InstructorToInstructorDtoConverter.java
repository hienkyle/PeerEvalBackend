package edu.tcu.cs.peerevalbackend.instructor.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.team.converter.TeamToTeamDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InstructorToInstructorDtoConverter implements Converter<Instructor, InstructorDto> {
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    public InstructorToInstructorDtoConverter(TeamToTeamDtoConverter teamToTeamDtoConverter) {
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
    }

    @Override
    public InstructorDto convert(Instructor source) {
        InstructorDto instructorDto = new InstructorDto(
                source.getInstructorId(),
                source.getName(),
                source.getStatus(),
                source.getTeams() != null ? source.getTeams().stream()
                        .map(team -> team.getTeamName()).collect(Collectors.toList()) : null
        );
        return instructorDto;
    }
}
