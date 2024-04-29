package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Transactional
public class InstructorService {
    InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    /*
     * Use case 18: Search for instructors using criteria
     *
     * Name: Hien
     *
     * @param searchCriteria- attributes and their search values
     * @param pageable
     *
     * @return Page<Instructor>
     *
     * Note: NOT TESTED
     */
    public Page<Instructor> findByCriteria(Map<String, String> searchCriteria, Pageable pageable) {
        Specification<Instructor> spec = Specification.where(null);

        if(StringUtils.hasLength(searchCriteria.get("name"))){
            spec = spec.and(InstructorSpecs.containsName(searchCriteria.get("name")));
        }

        if(StringUtils.hasLength(searchCriteria.get("academicYear"))){
            spec = spec.and(InstructorSpecs.hasYear(searchCriteria.get("academicYear")));
        }

        if(StringUtils.hasLength(searchCriteria.get("status"))){
            spec = spec.and(InstructorSpecs.hasStatus(searchCriteria.get("status")));
        }

        return this.instructorRepository.findAll(spec, pageable);
    }

    /*
     * Use case 22: View an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     *
     * @return Instructor - the instructor with the specified id
     *
     * @throw ObjectNotFoundException
     *
     * Note: TESTED - SUCCESS and NOT FOUND
     */
    public Instructor findById(String instructorId) {
        return this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }

    /*
     * Supplemental use case: Save an instructor
     *
     * Name: Hien
     *
     * @param instructor - the instructor
     *
     * @return Instructor - the instructor
     *
     * Note: TESTED - SUCCESS
     */
    public Instructor save(Instructor instructor) {
        return this.instructorRepository.save(instructor);
    }

    /*
     * Use case 23: Deactivate an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     * @param reason - the reason to deactivate this instructor
     *
     * @return Instructor - the deactivated instructor
     *
     * @throw ObjectNotFoundException
     *
     * Note: TESTED - SUCCESS and NOT FOUND
     */
    public Instructor deactivate(String instructorId, String reason) {
        return this.instructorRepository.findById(instructorId)
                .map(activeInstructor -> {
                    activeInstructor.setStatus(ActiveStatus.IS_DEACTIVATED);
                    activeInstructor.setDeactivateReason(reason);
                    return this.instructorRepository.save(activeInstructor);
                })
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }

    /*
     * Use case 24: Reactivate an instructor
     *
     * Name: Hien
     *
     * @param instructorId - the id of the instructor
     *
     * @return Instructor - the reactivated instructor
     *
     * @throw ObjectNotFoundException
     *
     * Note: TESTED - SUCCESS and NOT FOUND
     */
    public Instructor reactivate(String instructorId) {
        return this.instructorRepository.findById(instructorId)
                .map(deactivatedInstructor -> {
                    deactivatedInstructor.setStatus(ActiveStatus.IS_ACTIVE);
                    return this.instructorRepository.save(deactivatedInstructor);
                })
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }
}
