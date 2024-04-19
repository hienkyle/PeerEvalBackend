package edu.tcu.cs.peerevalbackend.peereval;

import edu.tcu.cs.peerevalbackend.student.Student;
import jakarta.persistence.*;

@Entity
public class PeerEvalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer peerEvalId;
    @ManyToOne
    private Student studentAbout;
    @ManyToOne
    private Student studentBy;

}
