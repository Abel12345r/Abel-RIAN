
package com.lovestory;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/notes")
public class LoveController {

    private final List<LoveNote> notes =
        new CopyOnWriteArrayList<>();
    private final AtomicLong heartbeats = new AtomicLong();

    @GetMapping
    public List<LoveNote> getNotes() {
        return new ArrayList<>(notes);
    }

    @PostMapping
    public LoveNote addNote(@RequestBody LoveNote note) {
        if (note.sender == null || note.message == null ||
            note.sender.isBlank() || note.message.isBlank() ||
            note.sender.length() > 40 ||
            note.message.length() > 1000) {
            throw new IllegalArgumentException("Invalid note");
        }

        LoveNote saved = new LoveNote(note.sender.trim(), note.message.trim());
        notes.add(saved);

        return saved;
    }

    @GetMapping("/heartbeats")
    public long getHeartbeats() {
        return heartbeats.get();
    }

    @PostMapping("/heartbeats")
    public long sendHeartbeat() {
        return heartbeats.incrementAndGet();
    }

    public static class LoveNote {
        public String sender;
        public String message;

        public LoveNote() {}

        public LoveNote(String sender, String message) {
            this.sender = sender;
            this.message = message;
        }
    }
}
