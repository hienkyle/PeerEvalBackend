package edu.tcu.cs.peerevalbackend.team;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecs {
    public static Specification<Team> hasTeamName(String teamName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("team_name"), teamName);
    }

    public static Specification<Team> hasSectionName(String sectionName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("section_name")), sectionName.toLowerCase());
    }

    public static Specification<Team> hasAcademicYear(String academicYear) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("academic_year")), "%" + academicYear.toLowerCase() + "%");
    }

    public static Specification<Team> hasInstructor(String instructor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("instructor_name")), "%" + instructor.toLowerCase() + "%");
    }
}
