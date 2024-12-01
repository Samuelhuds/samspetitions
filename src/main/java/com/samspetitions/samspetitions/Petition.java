package com.samspetitions.samspetitions;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private static int idCounter = 0;  // Static counter to ensure unique IDs for each petition
    private int id;
    private String title;
    private String description;
    private List<String> signatures = new ArrayList<>();
    public Petition(String title, String description) {
        this.title = title;
        this.description = description;
      
    }
    public Petition(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = idCounter++; // Automatically assign a unique ID
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void addSignature(String name, String email) {
        signatures.add(name + " (" + email + ")");
    }
}
