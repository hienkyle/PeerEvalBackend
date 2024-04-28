package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevalbackend.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/peerEval/section")
public class SectionController {

    private final SectionService sectionService;

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public SectionController(SectionService sectionService, SectionToSectionDtoConverter sectionToSectionDtoConverter, SectionDtoToSectionConverter sectionDtoToSectionConverter) { //injection
        this.sectionService = sectionService;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }

    @GetMapping("/{sectionName}")
    public Result findSectionById(@PathVariable String sectionName) {
        Section foundSection = this.sectionService.findById(sectionName);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);
    }

    @PostMapping()
    public Result addSection(@Valid @RequestBody SectionDto sectionDto) {
        //convert sectionDto to section
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);
    }

    @PutMapping("/{sectionName}")
    public Result updateSection(@PathVariable String sectionName, @Valid @RequestBody SectionDto sectionDto) {
        Section update = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section updatedSection = this.sectionService.update(sectionName, update);
        SectionDto updatedSectionDto = this.sectionToSectionDtoConverter.convert(updatedSection);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedSectionDto);
    }

    @DeleteMapping("/{sectionName}")
    public Result deleteSection(@PathVariable String sectionName) {
        this.sectionService.delete(sectionName);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PostMapping("/{sectionName}/active-weeks")
    public Result setActiveWeeks(@PathVariable String sectionName, @RequestBody List<Date> activeWeeks) {
        sectionService.setupActiveWeeks(sectionName, activeWeeks);
        return new Result(true, StatusCode.SUCCESS, "Active weeks set up successfully for section: " + sectionName);
    }

    @GetMapping("/{sectionName}/active-weeks")
    public Result getActiveWeeks(@PathVariable String sectionName) {
        List<Date> activeWeeks = sectionService.getActiveWeeks(sectionName);
        return new Result(true, StatusCode.SUCCESS, "Active weeks retrieved successfully for section: " + sectionName, activeWeeks);
    }
}


