package edu.freelance.booking.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.freelance.booking.Models.Location;
import edu.freelance.booking.Repositories.LocationRepository;

@Controller
public class LocationController {
    private LocationRepository locationRepository;
    public LocationController(LocationRepository repo) {
        locationRepository = repo;
    }

    @GetMapping("/locations")
    public String locations(Model data) {
        List<Location> locations = locationRepository.findAll();
        data.addAttribute("locations", locations);
        return "location/locations";
    }

    @GetMapping("/locations/add")
    public String addLocation (Model data) {
        data.addAttribute("location", new Location());
        return "location/addLocations";
    }

    @GetMapping("/locations/edit/{id}")
    public String editLocation(@PathVariable("id") long id, Model data) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location Id: " + id));
        data.addAttribute("location", location);
        return "location/addLocations";
    }

    @GetMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable("id") long id) {
        locationRepository.deleteById(id);
        return "redirect:/locations";
    }

    @PostMapping("/locations/save")
    public String saveLocation(@ModelAttribute Location location) {
        locationRepository.save(location);
        return "redirect:/locations";
    }
    
}
