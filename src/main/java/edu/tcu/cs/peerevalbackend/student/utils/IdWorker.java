package edu.tcu.cs.peerevalbackend.student.utils;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IdWorker {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int generateUniqueId() {
        return counter.incrementAndGet();
    }
}
