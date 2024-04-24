package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarRepository extends JpaRepository<WeeklyActivityReport, Integer>, JpaSpecificationExecutor<WeeklyActivityReport> {

}
