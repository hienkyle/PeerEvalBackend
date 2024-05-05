package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter.RubricCriteriaDtoToRubricCriteriaConverter;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter.RubricCriteriaToRubricCriteriaDtoConverter;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto.RubricCriteriaDto;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peerEval/rubricCriteria")
public class RubricCriteriaController {

    private final RubricCriteriaService rubricCriteriaService;

    private final RubricCriteriaToRubricCriteriaDtoConverter rubricCriteriaToRubricCriteriaDtoConverter;
    private final RubricCriteriaDtoToRubricCriteriaConverter rubricCriteriaDtoToRubricCriteriaConverter;

    public RubricCriteriaController(RubricCriteriaService rubricCriteriaService, RubricCriteriaToRubricCriteriaDtoConverter rubricCriteriaToRubricCriteriaDtoConverter, RubricCriteriaDtoToRubricCriteriaConverter rubricCriteriaDtoToRubricCriteriaConverter) {
        this.rubricCriteriaService = rubricCriteriaService;
        this.rubricCriteriaToRubricCriteriaDtoConverter = rubricCriteriaToRubricCriteriaDtoConverter;
        this.rubricCriteriaDtoToRubricCriteriaConverter = rubricCriteriaDtoToRubricCriteriaConverter;
    }

    @GetMapping("/{criteriaNum}")
    public Result findRubricCriteriaById(@PathVariable Integer criteriaNum) {
        RubricCriteria foundRubricCriteria = this.rubricCriteriaService.findById(criteriaNum);
        RubricCriteriaDto rubricCriteriaDto = this.rubricCriteriaToRubricCriteriaDtoConverter.convert(foundRubricCriteria);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", rubricCriteriaDto);
    }

    @PostMapping()
    public Result addRubricCritera(@Valid @RequestBody RubricCriteriaDto rubricCriteriaDto) {
        RubricCriteria newRubricCriteria = this.rubricCriteriaDtoToRubricCriteriaConverter.convert(rubricCriteriaDto);
        RubricCriteria savedRubricCriteria = this.rubricCriteriaService.save(newRubricCriteria);
        RubricCriteriaDto savedRubricCriteriaDto = this.rubricCriteriaToRubricCriteriaDtoConverter.convert(savedRubricCriteria);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedRubricCriteriaDto);
    }

/*
    @PostMapping()
    public Result addSection(@Valid @RequestBody SectionDto sectionDto) {
        //convert sectionDto to section
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);
    }
*/

}
