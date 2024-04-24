package edu.tcu.cs.peerevalbackend.team.converter;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamDtoToTeamConverter implements Converter<TeamDto, Team> {
    /*
     * Please ensure that these are changed to their respective Dto types
     */
    @Override
    public Team convert(TeamDto source) {
        Team team = new Team();
        team.setTeamName(source.teamName());
        team.setActiveWeeks(source.activeWeeks());
        team.setAcademicYear(source.academicYear());
        team.setInstructors(source.instructors());
        team.setStudents(source.students());
        team.setSectionName(source.sectionName());
        return team;
    }
}