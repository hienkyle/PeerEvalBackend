package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InstructorController {
    InstructorService instructorService;

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

    @GetMapping()
    public Result viewInstructor(){
        return null;
    }

    public Result deactivateInstructor(){
        return null;
    }

    public Result reactivateInstructor(){
        return null;
    }
}
