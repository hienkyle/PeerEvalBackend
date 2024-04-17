package edu.tcu.cs.peerevalbackend.section.converter;

import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionDtoToSectionConverter implements Converter<SectionDto, Section> {

    @Override
    public Section convert(SectionDto source) {
        Section section = new Section();
        section.setSectionName(source.sectionName());
        section.setAcademicYear(source.academicYear());
        section.setFirstAndLastDate(source.firstAndLastDate());
        return section;
    }

}
