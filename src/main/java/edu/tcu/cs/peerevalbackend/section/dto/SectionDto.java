package edu.tcu.cs.peerevalbackend.section.dto;

import jakarta.validation.constraints.NotEmpty;

public record SectionDto(
                         @NotEmpty(message = "name is required.")
                         String sectionName,

                         @NotEmpty(message = "academic year is required.")
                         String academicYear,

                         @NotEmpty(message = "first and last date of academic year is required.")
                         String firstAndLastDate) {
}


