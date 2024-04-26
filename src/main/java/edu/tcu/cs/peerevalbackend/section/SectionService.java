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

      //mv: add rubric used to this one when you can
    public Section update(String sectionName, Section update) {
        return this.sectionRepository.findById(sectionName)
                .map(oldSection -> {
                    oldSection.setSectionName(update.getSectionName());
                    oldSection.setAcademicYear(update.getAcademicYear());
                    oldSection.setFirstAndLastDate(update.getFirstAndLastDate());

                    Section updatedSection = this.sectionRepository.save(oldSection);
                    return updatedSection;
                })
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));

    }

    public void delete(String sectionName) {
        this.sectionRepository.findById(sectionName)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));
        this.sectionRepository.deleteById(sectionName);
    }

}
