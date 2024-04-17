package edu.tcu.cs.peerevalbackend.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> { //< [domain type], [type of id of entity]>
}
