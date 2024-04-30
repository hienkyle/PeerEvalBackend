package edu.tcu.cs.peerevalbackend.team;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecs {
    public static Specification<Team> hasTeamName(String teamName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("teamName"), teamName);
    }

    public static Specification<Team> hasSectionName(String sectionName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("sectionName")), sectionName.toLowerCase());
    }

    public static Specification<Team> hasAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("academicYear")), "%" + academicYear.toLowerCase() + "%");
    }

    public static Specification<Team> hasInstructor(String instructor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("instructorName")), "%" + instructor.toLowerCase() + "%");
    }
}
