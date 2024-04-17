package edu.tcu.cs.peerevalbackend.section.converter;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionToSectionDtoConverter implements Converter<Section, SectionDto> {

    @Override
    public SectionDto convert(Section source) {
        SectionDto sectionDto = new SectionDto(source.getSectionName(),
                                               source.getAcademicYear(),
                                               source.getFirstAndLastDate());
        return sectionDto;
    }

}
