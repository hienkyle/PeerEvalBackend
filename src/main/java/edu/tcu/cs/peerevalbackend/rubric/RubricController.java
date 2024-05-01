package edu.tcu.cs.peerevalbackend.rubric;

import edu.tcu.cs.peerevalbackend.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevalbackend.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevalbackend.rubric.dto.RubricDto;
import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peerEval/rubric")
public class RubricController {

    private final RubricService rubricService;

    private final RubricToRubricDtoConverter rubricToRubricDtoConverter;
    private final RubricDtoToRubricConverter rubricDtoToRubricConverter;

    public RubricController(RubricService rubricService, RubricToRubricDtoConverter rubricToRubricDtoConverter, RubricDtoToRubricConverter rubricDtoToRubricConverter) {
        this.rubricService = rubricService;
        this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
        this.rubricDtoToRubricConverter = rubricDtoToRubricConverter;
    }

    @GetMapping("/{rubricName}")
    public Result findSectionById(@PathVariable String rubricName) {
        Rubric foundRubric = this.rubricService.findById(rubricName);
        RubricDto rubricDto = this.rubricToRubricDtoConverter.convert(foundRubric);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", rubricDto);
    }
    @PostMapping()
    public Result addRubric(@Valid @RequestBody RubricDto rubricDto) {
        Rubric newRubric = this.rubricDtoToRubricConverter.convert(rubricDto);
        Rubric savedRubric = this.rubricService.save(newRubric);
        RubricDto savedRubricDto = this.rubricToRubricDtoConverter.convert(savedRubric);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedRubricDto);
    }

}
