package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WarSpecs {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    //This method needs some work, i need to figure out how active weeks will work
    public static Specification<WeeklyActivityReport> betweenActiveWeeks(String startWeek,String endWeek){
        return (root, query, builder) -> {
            // Assuming the database supports STR_TO_DATE or a similar function
            // and the JPA provider allows custom functions in criteria queries.
            Expression<LocalDate> activeWeekDate = builder.function("STR_TO_DATE", LocalDate.class,
                    root.get("activeWeek"), builder.literal("%m-%d-%Y"));

            LocalDate start = LocalDate.parse(startWeek, formatter);
            LocalDate end = LocalDate.parse(endWeek, formatter);

            return builder.between(activeWeekDate, start, end);
        };
    }
    public static Specification<WeeklyActivityReport> containsAuthorFirstName(String providedFirstName){
        return (root, query, criteriaBuilder) -> {
            // Join the WeeklyActivityReport with the Student entity
            Join<WeeklyActivityReport, Student> authorJoin = root.join("author");

            // Use a case-insensitive search to match the name
            return criteriaBuilder.like(criteriaBuilder.lower(authorJoin.get("firstName")), "%" + providedFirstName.toLowerCase() + "%");
        };
    }

}
