package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InstructorService {
    InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
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
     * Note: NOT TESTED
     */
    public Instructor findById(String instructorId) {
        return this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
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
     * Note: NOT TESTED
     */
    public Instructor deactivate(String instructorId, String reason) {
        return this.instructorRepository.findById(instructorId)
                .map(activeInstructor -> {
                    activeInstructor.setStatus("deactivated");
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
     * Note: NOT TESTED
     */
    public Instructor reactivate(String instructorId) {
        return this.instructorRepository.findById(instructorId)
                .map(deactivatedInstructor -> {
                    deactivatedInstructor.setStatus("active");
                    return this.instructorRepository.save(deactivatedInstructor);
                })
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }
}
