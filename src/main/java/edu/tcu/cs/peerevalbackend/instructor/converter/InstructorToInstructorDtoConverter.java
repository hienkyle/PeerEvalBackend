package edu.tcu.cs.peerevalbackend.instructor.converter;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.dto.InstructorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InstructorToInstructorDtoConverter implements Converter<Instructor, InstructorDto> {

    @Override
    public InstructorDto convert(Instructor source) {
        InstructorDto instructorDto = new InstructorDto(
                source.getInstructorId(),
                source.getName(),
                source.getStatus(),
                source.getTeamNames()
        );
        return instructorDto;
    }
}
