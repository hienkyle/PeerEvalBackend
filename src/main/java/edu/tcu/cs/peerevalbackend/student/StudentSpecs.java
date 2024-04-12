package edu.tcu.cs.peerevalbackend.student;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecs {
    public static Specification<Student> containsFirstName(String providedName){
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
