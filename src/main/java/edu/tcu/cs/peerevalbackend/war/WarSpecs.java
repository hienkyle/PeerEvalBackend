package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import org.springframework.data.jpa.domain.Specification;

public class WarSpecs {
    public static Specification<Student> betweenActiveWeeks(String startWeek,String endWeek){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + providedName.toLowerCase() + "%");
    }
    public static Specification<Student> containsLastName(String providedName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + providedName.toLowerCase() + "%");
    }
    public static Specification<Student> hasAcademicYear(String providedTeam){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("team").get("teamName")), providedTeam.toLowerCase());
    }
    public static Specification<Student> hasTeamName(String providedAcademicYear){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("team").get("teamName")), providedAcademicYear.toLowerCase());
    }

}
