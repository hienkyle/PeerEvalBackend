package edu.tcu.cs.peerevalbackend.team;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.InstructorRepository;
import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    //Must be fixed
    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    /*
    * At the moment just a filler method
    */
    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    /*
    * Make sure to add findByName
    * Use case 8
    * This is the view team method
    */
    public Team findByName(String teamName) {
        return this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));
    }

    /*
    * This creates a new team
    * I think this is Maribel's
    */
    public Team save(Team newTeam) {
        /*
        * //Uncomment this one you do the security stuff
        * //Getting literal password, then getting back encrypted password
        newHogwartsUser.setPassword(this.passwordEncoder.encode(newHogwartsUser.getPassword())); */

        return this.teamRepository.save(newTeam);
    }

    /*
    * Use case 10
    */
    public Team update(String teamName, Team update) {
        return this.teamRepository.findById(teamName)
                .map(oldTeam -> {
                    oldTeam.setTeamName(update.getTeamName());
                    return this.teamRepository.save(oldTeam);
                })
                .orElseThrow(() -> new TeamNotFoundException(teamName));
    }

    /*
    * Use case 14
    * Just a place in to give me an idea
    * Fix this if needed
    */
    public void delete(String teamName) {
        Team teamToBeDeleted = this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));

        //Remove all students and instructors from team
        teamToBeDeleted.removeAllStudents();
        teamToBeDeleted.removeAllInstructors();

        this.teamRepository.deleteById(teamName);
    }

    /*
    * Use case 12
    * Make sure to fix these errors
    */
    public void assignStudent(String teamName, String studentName){
        //Find this student by student name (which is the ID per se) from database
        Student studentToBeAssigned = this.studentRepository.findById(studentName)
                .orElseThrow(() -> new StudentNotFoundException(studentName));

        //Find this team by team name from DB
        Team teamAssigned = this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));

        //Student assignment
        //This removes student from current team, if any, in order to reassign
        if (studentToBeAssigned.getTeamName() != null) {
            studentToBeAssigned.getTeamName().removeStudent(studentToBeAssigned);
        }

        teamAssigned.addStudent(studentToBeAssigned);
    }

    /*
     * Do I need this or does the method in Team class suffice?
     * Use case 13
     */
    public void removeStudent(String teamName, String studentName) {
        //Find this student by student name (which is the ID per se) from database
        Student studentToBeAssigned = this.studentRepository.findById(studentName)
                .orElseThrow(() -> new StudentNotFoundException(studentName));

        //Find this team by team name from DB
        Team teamWhereStuDel = this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));

        //Check if the student is in a team and remove
        if (studentToBeAssigned.getTeamName() != null) {
            studentToBeAssigned.getTeamName().removeStudent(studentToBeAssigned);
        }
    }

    /*
     * Use case 19
     * Make sure to fix these errors
     */
    public void assignInstructor(String teamName, String instructorName){
        //Find this instructor by instructor name (which is the ID per se) from database
        Instructor instructorToBeAssigned = this.instructorRepository.findById(instructorName)
                .orElseThrow(() -> new InstructorNotFoundException(instructorName));

        //Find this team by team name from DB
        Team teamAssigned = this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));

        //Instructor assignment
        //This removes instructor from current team, if any, in order to reassign
        if (instructorToBeAssigned.getTeamName() != null) {
            instructorToBeAssigned.getTeamName().removeInstructor(instructorToBeAssigned);
        }

        teamAssigned.addInstructor(instructorToBeAssigned);
    }

    /*
     * Do I need this or does the method in Team class suffice?
     * Use case 20
     */
    public void removeInstructor(String teamName, String instructorName) {
        //Find this instructor by instructor name (which is the ID per se) from database
        Instructor instructorToBeAssigned = this.instructorRepository.findById(instructorName)
                .orElseThrow(() -> new InstructorNotFoundException(instructorName));

        //Find this team by team name from DB
        Team teamWhereStuDel = this.teamRepository.findById(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));

        //Check if the instructor is in a team, and remove
        if (instructorToBeAssigned.getTeamName() != null) {
            instructorToBeAssigned.getTeamName().removeInstructor(instructorToBeAssigned);
        }
    }

    /*
    * Use case 7
    */
    /*public Page<Team> findByCriteria(Map<String, String> searchCriteria, Pageable pageable) {
        Specification<Team> spec = Specification.where(null);

        if (StringUtils.hasLength(searchCriteria.get("teamName"))) {
            spec = spec.and(TeamSpecs.hasTeamName(searchCriteria.get("teamName")));
        }

        if (StringUtils.hasLength(searchCriteria.get("sectionName"))) {
            spec = spec.and(TeamSpecs.hasSectionName(searchCriteria.get("sectionName")));
        }

        if (StringUtils.hasLength(searchCriteria.get("academicYear"))) {
            spec = spec.and(TeamSpecs.hasAcademicYear(searchCriteria.get("academicYear")));
        }

        if (StringUtils.hasLength(searchCriteria.get("instructor"))) {
            spec = spec.and(TeamSpecs.hasInstructor(searchCriteria.get("instructor")));
        }

        if(spec ==) {

        }

        return this.teamRepository.findAll(spec, pageable);
    }*/
}
