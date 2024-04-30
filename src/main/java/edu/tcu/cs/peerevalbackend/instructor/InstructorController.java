package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    public InstructorController(InstructorService instructorService, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter) {
        this.instructorService = instructorService;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    }

    /*
     * Use case 18: Invite an instructor
     *
     * Name: Hien
     *
     * @param emails - a list of emails of instructors
     *
     * @return Result
     *
     * Note: NOT TESTED
     */
    @PostMapping("/invite")
    public Result inviteInstructors(@Valid @RequestBody List<String> emails){
        List<String> sentEmails = this.instructorService.invite(emails);
        return new Result(true, StatusCode.SUCCESS, "Invite Success", sentEmails);
    }

    /*
     * Use case 21: Search for instructors using criteria
     *
     * Name: Hien
     *
     * @param searchCriteria - a map that contains attributes and their search value
     * @param pageable
     *
     * @return Result
     *
     * Note: NOT TESTED
     */
    @PostMapping("/search")
    public Result findInstructorsByCriteria(@RequestBody Map<String, String> searchCriteria, Pageable pageable){
        Page<Instructor> instructorPage = this.instructorService.findByCriteria(searchCriteria, pageable);
        Page<InstructorDto> instructorDtoPage = instructorPage.map(instructor -> this.instructorToInstructorDtoConverter.convert(instructor));
        return new Result(true, StatusCode.SUCCESS, "Search Success", instructorDtoPage);
    }

    /*
     * Use case 22: View an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     *
     * @return Result
     *
     * Note: NOT TESTED
     */
    @GetMapping("/{instructorId}")
    public Result findInstructorById(@PathVariable String instructorId){
        Instructor foundInstructor = this.instructorService.findById(instructorId);
        InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(foundInstructor);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", instructorDto);
    }

    /*
     * Use case 23: Deactivate an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     * @param reason - reason for deactivation
     *
     * @return Result
     *
     * Note: NOT TESTED
     */
    @PutMapping("/deactivate/{instructorId}")
    public Result deactivateInstructor(@PathVariable String instructorId, @Valid @RequestBody String reason){
        Instructor deactivatedInstructor = this.instructorService.deactivate(instructorId, reason);
        InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(deactivatedInstructor);
        return new Result(true, StatusCode.SUCCESS, "Deactivate Success", instructorDto);
    }

    /*
     * Use case 24: Reactivate an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     *
     * @return Result
     *
     * Note: NOT TESTED
     */
    @PutMapping("/activate/{instructorId}")
    public Result reactivateInstructor(@PathVariable String instructorId){
        Instructor reactivatedInstructor = this.instructorService.reactivate(instructorId);
        InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(reactivatedInstructor);
        return new Result(true, StatusCode.SUCCESS, "Reactivate Success", instructorDto);
    }


}
