package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.instructor.converter.InstructorToInstructorDtoConverter;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    public InstructorController(InstructorService instructorService, InstructorToInstructorDtoConverter instructorToInstructorDtoConverter) {
        this.instructorService = instructorService;
        this.instructorToInstructorDtoConverter = instructorToInstructorDtoConverter;
    }


//    public Result inviteInstructors(){
//        return null;
//    }

    @GetMapping() // figure what to map this thing to
    public Result findInstructors(){
        return null;
    }

    /*
     * Use case 22: View an instructor
     *
     * @param instructorId - the id of the instructor
     *
     * @return Result
     */
    @GetMapping("/{instructorId}")
    public Result viewInstructor(@PathVariable String instructorId){
        Instructor foundInstructor = this.instructorService.findById(instructorId);
        InstructorDto instructorDto = this.instructorToInstructorDtoConverter.convert(foundInstructor);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", instructorDto);
    }

    public Result deactivateInstructor(){
        return null;
    }

    public Result reactivateInstructor(){
        return null;
    }
}
