package com.samspetitions.samspetitions;  // package declaration

import org.springframework.stereotype.Controller;  // imports the Controller annotation
import org.springframework.ui.Model;  // imports the Model interface for passing data to the view
import org.springframework.web.bind.annotation.GetMapping;  // imports GetMapping for GET requests
import org.springframework.web.bind.annotation.PostMapping;  // imports PostMapping for POST requests
import org.springframework.web.bind.annotation.RequestParam;  // imports RequestParam for query parameters

import java.util.ArrayList;
import java.util.List;

// Mark this class as a Spring MVC controller
@Controller
public class PetitionController {

    // Create a List to hold petition data in memory (no database used)
    private final List<Petition> petitions = new ArrayList<>();

    // Home page route
    @GetMapping("/")  // Maps the root URL ("/") to this method
    public String home() {
        return "index";  // Returns the name of the view (index.html)
    }

    // Route for the 'create petition' page
    @GetMapping("/create")
    public String createPetition() {
        return "createPetition";  // Returns the name of the view (createPetition.html)
    }

    // Handle form submission for creating a petition
    @PostMapping("/create")
    public String savePetition(@RequestParam String title, @RequestParam String description) {
        petitions.add(new Petition(title, description));  // Create a new petition and add it to the list
        return "redirect:/view";  // Redirects to the 'view petitions' page after creation
    }

    // Route to view all petitions
    @GetMapping("/view")
    public String viewPetitions(Model model) {
        model.addAttribute("petitions", petitions);  // Pass the petitions list to the view
        return "viewPetitions";  // Returns the name of the view (viewPetitions.html)
    }

    // Route to search for a petition
    @GetMapping("/search")
    public String searchPetition() {
        return "searchPetitions";  // Returns the name of the view (searchPetitions.html)
    }

    // Handle search results for petitions
    @PostMapping("/search")
    public String searchPetitionResult(@RequestParam String search, Model model) {
        List<Petition> results = new ArrayList<>();
        for (Petition petition : petitions) {
            if (petition.getTitle().contains(search) || petition.getDescription().contains(search)) {
                results.add(petition);  // If the petition title or description contains the search term, add to results
            }
        }
        model.addAttribute("petitions", results);  // Pass the search results to the view
        return "searchResults";  // Returns the name of the view (searchResults.html)
    }

    // Route to view a specific petition and sign it
    @GetMapping("/sign")
    public String signPetition(@RequestParam int id, Model model) {
        Petition petition = petitions.get(id);  // Get the petition based on the index (ID)
        model.addAttribute("petition", petition);  // Pass the petition to the view
        return "signPetition";  // Returns the name of the view (signPetition.html)
    }

    // Handle form submission for signing a petition
    @PostMapping("/sign")
    public String signPetition(@RequestParam int id, @RequestParam String name, @RequestParam String email) {
        Petition petition = petitions.get(id);  // Get the petition by ID
        petition.addSignature(name, email);  // Add the signature (name and email) to the petition
        return "redirect:/view";  // Redirects to the 'view petitions' page after signing
    }

    // Inner class to represent a Petition
    public static class Petition {
        private String title;
        private String description;
        private List<String> signatures = new ArrayList<>();

        public Petition(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getSignatures() {
            return signatures;
        }

        public void addSignature(String name, String email) {
            signatures.add(name + " (" + email + ")");
        }
    }
}
