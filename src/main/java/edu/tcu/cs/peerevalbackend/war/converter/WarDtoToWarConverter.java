package edu.tcu.cs.peerevalbackend.war.converter;

import edu.tcu.cs.peerevalbackend.war.WeeklyActivityReport;
import edu.tcu.cs.peerevalbackend.war.dto.WarDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WarDtoToWarConverter implements Converter<WarDto, WeeklyActivityReport> {
    @Override
    public WeeklyActivityReport convert(WarDto source) {
        WeeklyActivityReport war = new WeeklyActivityReport();
        war.setWarId(source.id());
        war.setTask(source.task());
        war.setTaskDesc(source.taskDesc());
        war.setTaskCategory(source.taskCat());
        war.setStatus(source.status());
        war.setActiveWeek(source.activeWeek());
        war.setPlannedHours(source.plannedHours());
        war.setActualHours(source.plannedHours());
        return war;
    }
}
