package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

import edu.tcu.cs.peerevalbackend.rubric.Rubric;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class RubricCriteria implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criteria_number_generator")
    @SequenceGenerator(name="criteria_number_generator", sequenceName = "criteria_number_seq", allocationSize=1)
    private Integer criteriaNum;
    private String criteriaName;
    private String criteriaDesc;
    private Integer criteriaMaxScore;

    @ManyToMany(mappedBy = "rubricCriteria")
    private List<Rubric> rubrics;

    public RubricCriteria(int criteriaNum, String criteriaName, String criteriaDesc, int criteriaMaxScore) {
        this.criteriaNum = criteriaNum;
        this.criteriaName = criteriaName;
        this.criteriaDesc = criteriaDesc;
        this.criteriaMaxScore = criteriaMaxScore;
    }

    public RubricCriteria() {

    }

    public Integer getCriteriaNum() {
        return criteriaNum;
    }

    public void setCriteriaNum(Integer criteriaNum) {
        this.criteriaNum = criteriaNum;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getCriteriaDesc() {
        return criteriaDesc;
    }

    public void setCriteriaDesc(String criteriaDesc) {
        this.criteriaDesc = criteriaDesc;
    }

    public Integer getCriteriaMaxScore() {
        return criteriaMaxScore;
    }

    public void setCriteriaMaxScore(Integer criteriaMaxScore) {
        this.criteriaMaxScore = criteriaMaxScore;
    }

    public List<Rubric> getRubrics() {
        return rubrics;
    }

    public void setRubrics(List<Rubric> rubrics) {
        this.rubrics = rubrics;
    }

}
