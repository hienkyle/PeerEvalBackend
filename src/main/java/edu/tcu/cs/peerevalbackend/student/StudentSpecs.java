package edu.tcu.cs.peerevalbackend.student;

import org.springframework.data.jpa.domain.Specification;

public class StudentSpecs {
    public static Specification<Student> containsFirstName(String providedName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + providedName.toLowerCase() + "%");
    }
    public static Specification<Student> containsLastName(String providedName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + providedName.toLowerCase() + "%");
    }
    public static Specification<Student> hasAcademicYear(String providedAcademicYear){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("academicYear")), providedAcademicYear.toLowerCase());
    }
    public static Specification<Student> hasTeamName(String providedTeamName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("teamName")), providedTeamName.toLowerCase());
    }
    public static Specification<Student> hasSectionName(String providedSectionName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("sectionName")), providedSectionName.toLowerCase());
    }
}
