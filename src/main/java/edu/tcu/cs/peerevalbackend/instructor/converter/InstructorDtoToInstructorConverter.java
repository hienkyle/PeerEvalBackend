package edu.tcu.cs.peerevalbackend.instructor.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
* Annotation added by Ana
*/
@Component
public class InstructorDtoToInstructorConverter implements Converter<InstructorDto, Instructor> {
    @Override
    public Instructor convert(InstructorDto source) {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(source.instructorId());
        instructor.setName(source.name());
        instructor.setStatus(source.status());
        return instructor;
    }
}
