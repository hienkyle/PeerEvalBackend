package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional //will make transaction processing easier later on
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) { //injection
        this.sectionRepository = sectionRepository;
    }
    public Section findById(String sectionName) {
        return this.sectionRepository.findById(sectionName)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));
    }
    //to save the section
    public Section save(Section newSection) {
        return this.sectionRepository.save(newSection);
      }
 }
}
