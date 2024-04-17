package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class SectionController {

    private final SectionService sectionService;

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public SectionController(SectionService sectionService, SectionToSectionDtoConverter sectionToSectionDtoConverter, SectionDtoToSectionConverter sectionDtoToSectionConverter) { //injection
        this.sectionService = sectionService;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }

    @GetMapping("/peerEval/section/{sectionName}")
    public Result findSectionById(@PathVariable String sectionName) {
        Section foundSection = this.sectionService.findById(sectionName);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

    @PostMapping("/peerEval/section")
    public Result addSection(@Valid @RequestBody SectionDto sectionDto){
        //convert sectionDto to section
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);
    }

}
