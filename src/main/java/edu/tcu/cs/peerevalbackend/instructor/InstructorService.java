package edu.tcu.cs.peerevalbackend.instructor;

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
        return null;
    }
}
