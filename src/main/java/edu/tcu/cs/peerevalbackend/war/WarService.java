package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.StudentRepository;
import jakarta.transaction.Transactional;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WarService {
    private final WarRepository warRepository;
    private final StudentRepository studentRepository;
    public WarService(WarRepository warRepository, StudentRepository studentRepository) {
        this.warRepository = warRepository;
        this.studentRepository = studentRepository;
    }
    public WeeklyActivityReport findById(Integer warId){
        return this.warRepository.findById(warId)
                .orElseThrow(()->
                        new ObjectNotFoundException("Weekly Activity Report", warId));
    }
    public List<WeeklyActivityReport> findAll(){
        return this.warRepository.findAll();
    }
    public WeeklyActivityReport save(Integer authorId, WeeklyActivityReport newWar){
        Student author = studentRepository.findById(authorId).orElseThrow(()->
                new ObjectNotFoundException("Student", authorId));
        newWar.setAuthor(author);
        author.addWar(newWar);
        return this.warRepository.save(newWar);
    }
    public void delete(Integer warId){
        WeeklyActivityReport warToBeDeleted = this.warRepository.findById(warId)
                .orElseThrow(()->
                        new ObjectNotFoundException("Weekly Activity Report", warId));
        this.warRepository.deleteById(warId);
    }
    public WeeklyActivityReport update(Integer warId, WeeklyActivityReport update) {
        return this.warRepository.findById(warId)
                .map(oldWar -> {
                    oldWar.setTask(update.getTask());
                    oldWar.setTaskDesc(update.getTaskDesc());
                    oldWar.setTaskCategory(update.getTaskCategory());
                    oldWar.setStatus(update.getStatus());
                    oldWar.setPlannedHours(update.getPlannedHours());
                    oldWar.setActualHours(update.getActualHours());
                    oldWar.setActiveWeek(update.getActiveWeek());
                    return this.warRepository.save(oldWar);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Weekly Activity Report", warId));
    }


}
