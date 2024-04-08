package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InstructorController {
    private final InstructorService instructorService;

    private final InstructorToInstructorDtoConverter instructorToInstructorDtoConverter;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }


//    public Result inviteInstructors(){
//        return null;
//    }

    @GetMapping() // figure what to map this thing to
    public Result findInstructors(){
        return null;
    }

    @GetMapping("/instructors/{instructorId}")
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
