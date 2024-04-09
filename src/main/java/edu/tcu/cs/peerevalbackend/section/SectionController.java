package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.system.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SectionController {

    private final SectionService sectionService;


    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/section/{sectionName}")
    public Result findSectionByIdAndYear(@PathVariable String sectionName, String academicYear) {
        return null;
    }

}
