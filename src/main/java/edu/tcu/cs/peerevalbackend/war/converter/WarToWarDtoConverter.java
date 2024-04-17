package edu.tcu.cs.peerevalbackend.war.converter;

import edu.tcu.cs.peerevalbackend.war.WeeklyActivityReport;
import edu.tcu.cs.peerevalbackend.war.dto.WarDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WarToWarDtoConverter implements Converter<WeeklyActivityReport, WarDto> {
    @Override
    public WarDto convert(WeeklyActivityReport source) {
        return new WarDto(
                source.getWarId(),
                source.getTask(),
                source.getTaskDesc(),
                source.getTaskCategory(),
                source.getStatus(),
                source.getActiveWeek(),
                source.getPlannedHours(),
                source.getActualHours()
                );
    }
}
