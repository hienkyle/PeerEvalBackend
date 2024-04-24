package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamDtoConverter implements Converter<Team, TeamDto> {
    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public TeamToTeamDtoConverter(SectionToSectionDtoConverter sectionToSectionDtoConverter) {
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }

    /*
    * Please ensure that these are changed to their respective Dto types
    */
    @Override
    public TeamDto convert(Team source) {
        TeamDto teamDto = new TeamDto(
                source.getTeamName(),
                source.getActiveWeeks(),
                source.getAcademicYear(),
                source.getInstructors(),
                source.getStudents(),
                
                //Need to change to Dto type 
                source.getSectionName() != null
                        ? source.getSectionName()
                        : null);
        return teamDto;
    }

}