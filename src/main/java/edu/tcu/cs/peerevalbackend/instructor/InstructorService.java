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

    public Instructor findById(String instructorId) {
        return this.instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ObjectNotFoundException("instructor", instructorId));
    }
}
