package edu.tcu.cs.peerevalbackend.instructor;

import org.springframework.data.jpa.domain.Specification;

public class InstructorSpecs {
    public static Specification<Instructor> containsName(String providedName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + providedName + "%");
    }

    public static Specification<Instructor> hasYear(String providedYear){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("academicYear"), providedYear);
    }

//    public static Specification<Instructor> containsTeam(String providedTeamName){
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.like(criteriaBuilder.lower(root.get("teams").get("teams")), "%" + providedTeamName + "%");
//    }

    public static Specification<Instructor> hasStatus(String providedStatus){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), providedStatus);
    }
}
