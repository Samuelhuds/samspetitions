package com.samspetitions.samspetitions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// Mark this class as a Spring MVC controller
@Controller
public class PetitionController {

    // In-memory list to store petitions
    private final List<Petition> petitions = new ArrayList<>();

    // Home page route (maps to index.html)
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Show the 'Create Petition' form (maps to createPetition.html)
    @GetMapping("/create")
    public String createPetition() {
        return "createPetition";
    }

    // Handle form submission for creating a new petition
    @PostMapping("/create")
    public String savePetition(@RequestParam String title, @RequestParam String description) {
        petitions.add(new Petition(title, description));  // Add a new petition to the list
        return "redirect:/view";  // Redirect to the 'View Petitions' page
    }

    // View all petitions (maps to viewPetitions.html)
    @GetMapping("/view")
    public String viewPetitions(Model model) {
        model.addAttribute("petitions", petitions);  // Pass the list of petitions to the view
        return "viewPetitions";
    }

    // Show the 'Search Petitions' form (maps to searchPetitions.html)
    @GetMapping("/search")
    public String searchPetition() {
        return "searchPetitions";
    }

    // Handle search results and display matches (maps to searchResults.html)
    @PostMapping("/search")
    public String searchPetitionResult(@RequestParam String search, Model model) {
        List<Petition> results = new ArrayList<>();
        for (int i = 0; i < petitions.size(); i++) {
            Petition petition = petitions.get(i);
            if (petition.getTitle().contains(search) || petition.getDescription().contains(search)) {
                results.add(new Petition(petition.getTitle(), petition.getDescription(), i));  // Add index to the petition
            }
        }

        model.addAttribute("petitions", results);
        return "searchResults";  // Make sure this corresponds to your HTML file
    }

    // Show a specific petition for signing (maps to signPetition.html)
    @GetMapping("/sign")
    public String signPetition(@RequestParam int id, Model model) {
        if (id < 0 || id >= petitions.size()) {
            return "error";  // Return error page if petition not found
        }
        Petition petition = petitions.get(id);
        model.addAttribute("petition", petition);
        model.addAttribute("petitionId", id);  // Pass petition ID for form handling
        return "signPetition";
    }

    // Handle form submission for signing a petition
    @PostMapping("/sign")
    public String submitSignature(@RequestParam int id, @RequestParam String name, @RequestParam String email) {
        if (id < 0 || id >= petitions.size()) {
            return "error";  // Return error page if petition not found
        }
        Petition petition = petitions.get(id);
        petition.addSignature(name, email);  // Add signature to the petition
        return "redirect:/view";  // Redirect to the 'View Petitions' page
    }
}
