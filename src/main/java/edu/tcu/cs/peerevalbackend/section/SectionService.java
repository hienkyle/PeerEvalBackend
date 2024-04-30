package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //will make transaction processing easier later on
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) { //injection
        this.sectionRepository = sectionRepository;
    }

    public List<Section> findAll() {
        return sectionRepository.findAll();
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
    // setup active weeks
    public void setupActiveWeeks(String sectionName, List<Date> activeWeeks) {
        Optional<Section> optionalSection = sectionRepository.findById(sectionName);
        if (optionalSection.isPresent()) {
            Section section = optionalSection.get();
            section.setActiveWeeks(activeWeeks);
            sectionRepository.save(section);
        }
    }
    public List<Date> getActiveWeeks(String sectionName) {
        Section section = sectionRepository.findById(sectionName)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));

        return section.getActiveWeeks();
    }

    public Page<Section> findAll(Pageable pageable) {
        return this.sectionRepository.findAll(pageable);
    }
}
