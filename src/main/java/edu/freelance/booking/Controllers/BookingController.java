package edu.freelance.booking.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import edu.freelance.booking.Models.Booking;
import edu.freelance.booking.Models.Item;
import edu.freelance.booking.Models.Location;
import edu.freelance.booking.Repositories.BookingRepository;
import edu.freelance.booking.Repositories.ItemRepository;
import edu.freelance.booking.Repositories.LocationRepository;
import edu.freelance.booking.Utils.Status;

@Controller
public class BookingController {
    private BookingRepository bookingRepository;
    private ItemRepository itemRepository;
    private LocationRepository locationRepository;

    public BookingController(BookingRepository repo, ItemRepository itemRepo, LocationRepository locationRepo) {
        bookingRepository = repo;
        itemRepository = itemRepo;
        locationRepository = locationRepo;
    }

    @GetMapping("/booking")
    public String index(Model data) {
        List<Booking> bookings = bookingRepository.findAll();
        data.addAttribute("booking", bookings);
        return "booking/index";
    }

    @PostMapping("/booking/approve/{id}")
    public String approve(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setApproved(true);
        booking.setStatus(Status.PROCESS);
        bookingRepository.save(booking);

        return "redirect:/booking";
    }

    @PostMapping("/booking/complete/{id}")
    public String complete(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setApproved(true);
        booking.setStatus(Status.SUCCESS);
        bookingRepository.save(booking);
        return "redirect:/booking";
    }

    @GetMapping("/online")
    public String booking (Model data) {
        List<Item> items = itemRepository.findAll();
        List<Location> locations = locationRepository.findAll();
        data.addAttribute("items", items);
        data.addAttribute("locations", locations);
        data.addAttribute("minDate", LocalDate.now().toString());
        data.addAttribute("booking", new Booking());
        return "booking/create";
    }

    @PostMapping("/booking/save")
    public String save(@ModelAttribute Booking booking) {
        if (booking.getItem() != null && booking.getItem().getId() > 0) {
            booking.setItem(itemRepository.findById(booking.getItem().getId()).orElseThrow());
        }
        if (booking.getLocation() != null && booking.getLocation().getId() > 0) {
            booking.setLocation(locationRepository.findById(booking.getLocation().getId()).orElseThrow());
        }
        booking.setApproved(false);
        booking.setStatus(Status.CREATED);
        booking = bookingRepository.save(booking);
        return "redirect:/status/" + booking.getId();
    }

    @GetMapping("/status/{id}")
    public String status(@PathVariable Long id, Model data) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        data.addAttribute("booking", booking);
        return "booking/status";
    }
}