package edu.tcu.cs.peerevalbackend.war.dto;

import edu.tcu.cs.peerevalbackend.student.dto.StudentDto;

public record WarDto(Integer id,
                     String task,
                     String taskDesc,
                     String taskCat,
                     String status,
                     String activeWeek,
                     Integer plannedHours,
                     Integer actualHours) {

}
