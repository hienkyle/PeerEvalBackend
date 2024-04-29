package edu.tcu.cs.peerevalbackend.student;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student newStudent){
        return this.studentRepository.save(newStudent);
    }

    public Student findById(Integer studentId){
        return this.studentRepository.findById(studentId).orElseThrow(() -> new ObjectNotFoundException("Student",studentId));
    }

    public void deleteStudent(Integer studentId){
        this.studentRepository.findById(studentId)
                .orElseThrow(()->new ObjectNotFoundException("Student",studentId));
        this.studentRepository.deleteById(studentId);
    }

    public Student updateStudent(Integer studentId, Student update){
        return this.studentRepository.findById(studentId)
                .map(oldStudent -> {
                    oldStudent.setFirstName(update.getFirstName());
                    oldStudent.setMiddleInitial(update.getMiddleInitial());
                    oldStudent.setLastName(update.getLastName());
                    return this.studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Student",studentId));
    }
    public List<Student> findAll(){
        return this.studentRepository.findAll();
    }

    public Page<Student> findByCriteria(Map<String, String> searchCriteria, Pageable pageable) {
        Specification<Student> spec = Specification.where(null);

        if (StringUtils.hasLength(searchCriteria.get("academicYear"))) {
            spec = spec.and(StudentSpecs.hasAcademicYear(searchCriteria.get("academicYear")));
        }
        if (StringUtils.hasLength(searchCriteria.get("firstName"))) {
            spec = spec.and(StudentSpecs.containsFirstName(searchCriteria.get("firstName")));
        }
        if (StringUtils.hasLength(searchCriteria.get("lastName"))) {
            spec = spec.and(StudentSpecs.containsLastName(searchCriteria.get("lastName")));
        }
        if (StringUtils.hasLength(searchCriteria.get("teamName"))) {
            spec = spec.and(StudentSpecs.hasTeamName(searchCriteria.get("teamName")));
        }
        if (StringUtils.hasLength(searchCriteria.get("sectionName"))) {
            spec = spec.and(StudentSpecs.hasSectionName(searchCriteria.get("sectionName")));
        }
        return this.studentRepository.findAll(spec, pageable);
    }
}
