package edu.tcu.cs.peerevalbackend.rubric;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.RubricCriteria;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.io.Serializable;
import java.util.List;

@Entity
public class Rubric implements Serializable {

    @Id
    private String rubricName;

    @ManyToMany
    private List<RubricCriteria> rubricCriteria;

    public String getRubricName() {
        return rubricName;
    }

    public void setRubricName(String rubricName) {
        this.rubricName = rubricName;
    }

    public List<RubricCriteria> getRubricCriteria() {
        return rubricCriteria;
    }

    public void setRubricCriteria(List<RubricCriteria> rubricCriteria) {
        this.rubricCriteria = rubricCriteria;
    }
}
