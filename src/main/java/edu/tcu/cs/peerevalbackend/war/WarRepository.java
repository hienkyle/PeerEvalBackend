package edu.tcu.cs.peerevalbackend.war;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarRepository extends JpaRepository<WeeklyActivityReport, Integer> {

}
