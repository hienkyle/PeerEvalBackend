package edu.tcu.cs.peerevalbackend.team;

import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.team.converter.TeamDtoToTeamConverter;
import edu.tcu.cs.peerevalbackend.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;

@RestController
@RequestMapping("/peerEval/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamDtoToTeamConverter teamDtoToTeamConverter;
    private final TeamToTeamDtoConverter teamToTeamDtoConverter;

    public TeamController(TeamService teamService, TeamDtoToTeamConverter teamDtoToTeamConverter, TeamToTeamDtoConverter teamToTeamDtoConverter) {
        this.teamService = teamService;
        this.teamDtoToTeamConverter = teamDtoToTeamConverter;
        this.teamToTeamDtoConverter = teamToTeamDtoConverter;
    }

    /*
     * At the moment just a filler method
     */
    @GetMapping
    public Result findAllTeams(Pageable pageable) {
        Page<Team> teamPage = this.teamService.findAll(pageable);

        //Convert teamPage to a page of teamDtos
        Page<TeamDto> teamDtosPage = teamPage.map(this.teamToTeamDtoConverter::convert);
        return new Result(true, StatusCode.SUCCESS, "Find All Success", teamDtosPage);
    }

    /*
    * Use case 8
    * This is the view method
    */
    @GetMapping("/{teamName}")
    public Result findTeamByName(@PathVariable String teamName) {
        Team foundTeam = this.teamService.findByName(teamName);
        TeamDto teamDto = this.teamToTeamDtoConverter.convert(foundTeam);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", teamDto);
    }

    /*
    * I think this is Maribel's method
    */
    @PostMapping
    public Result addTeam(@Valid @RequestBody TeamDto teamDto) {
        //Convert teamDto to team
        Team newTeam = this.teamDtoToTeamConverter.convert(teamDto);
        Team savedTeam = this.teamService.save(newTeam);
        TeamDto savedTeamDto = this.teamToTeamDtoConverter.convert(savedTeam);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedTeamDto);
    }

    /*
    * Use case 10
    */
    @PutMapping("/{teamName}")
    public Result updateTeam(@PathVariable String teamName, @Valid @RequestBody TeamDto teamDto) {
        Team update = this.teamDtoToTeamConverter.convert(teamDto);
        Team updatedTeam = this.teamService.update(teamName, update);
        TeamDto updatedTeamDto = this.teamToTeamDtoConverter.convert(updatedTeam);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedTeamDto);
    }

    /*
    * Use case 14
    */
    @DeleteMapping("/{teamName}")
    public Result deleteTeam(@PathVariable String teamName) {
        this.teamService.delete(teamName);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    /*
     * Use case 13
     * Similar to update
     * Order of parameters matters
     * Whatever is in {} will be assigned to parameters, which is why order matters
     */
    @PutMapping("/{teamName}/assign/students/{studentId}")
    public Result assignStudent(@PathVariable String teamName, @PathVariable Integer studentId) {
        this.teamService.assignStudent(teamName, studentId);
        return new Result(true, StatusCode.SUCCESS, "Student Assignment Success");
    }

    /*
     * Use case 14
     * Make sure the annotation is correct
     */
    @PutMapping("/{teamName}/remove/students/{studentId}")
    public Result removeStudent(@PathVariable String teamName, @PathVariable Integer studentId) {
        this.teamService.removeStudent(teamName, studentId);
        return new Result(true, StatusCode.SUCCESS, "Remove Student Success");
    }

    /*
     * Use case 19
     * Similar to update
     * Order of parameters matters
     * Whatever is in {} will be assigned to parameters, which is why order matters
     */
    @PutMapping("/{teamName}/assign/instructors/{instructorId}")
    public Result assignInstructor(@PathVariable String teamName, @PathVariable String instructorId) {
        this.teamService.assignInstructor(teamName, instructorId);
        return new Result(true, StatusCode.SUCCESS, "Instructor Assignment Success");
    }

    /*
     * Use case 20
     * Make sure the annotation is correct
     */
    @PutMapping("/{teamName}/remove/instructors/{instructorId}")
    public Result removeInstructor(@PathVariable String teamName, @PathVariable String instructorId) {
        this.teamService.removeInstructor(teamName, instructorId);
        return new Result(true, StatusCode.SUCCESS, "Remove Instructor Success");
    }

    /*
    * Use case 7
    * Search by criteria
    */
    @PostMapping("/search")
    public Result findTeamsByCriteria(@RequestBody Map<String, String> searchCriteria, Pageable pageable) {
        Page<Team> teamPage = this.teamService.findByCriteria(searchCriteria, pageable);

        //Make a team dto class
        Page<TeamDto> teamDtosPage = teamPage.map(this.teamToTeamDtoConverter::convert);
        return new Result(true, StatusCode.SUCCESS, "Search Success", teamDtosPage);
    }
}
