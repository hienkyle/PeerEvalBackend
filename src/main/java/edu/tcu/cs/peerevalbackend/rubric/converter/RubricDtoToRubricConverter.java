package edu.tcu.cs.peerevalbackend.rubric.converter;

import edu.tcu.cs.peerevalbackend.rubric.Rubric;
import edu.tcu.cs.peerevalbackend.rubric.dto.RubricDto;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter.RubricCriteriaDtoToRubricCriteriaConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RubricDtoToRubricConverter implements Converter<RubricDto, Rubric> {

    private final RubricCriteriaDtoToRubricCriteriaConverter rubricCriteriaDtoToRubricCriteraConverter;

    public RubricDtoToRubricConverter(RubricCriteriaDtoToRubricCriteriaConverter rubricCriteriaDtoToRubricCriteraConverter) {
        this.rubricCriteriaDtoToRubricCriteraConverter = rubricCriteriaDtoToRubricCriteraConverter;
    }

    @Override
    public Rubric convert(RubricDto source) {

        if (source == null) {
            return null; // Handle null input gracefully
        }

        Rubric rubric = new Rubric();
        rubric.setRubricName(source.rubricName());
        rubric.setRubricCriteria(source.rubricCriteriaDtos().stream()
                .map(rubricCriteriaDtoToRubricCriteraConverter::convert).collect(Collectors.toList()));

        return rubric;
    }
}
