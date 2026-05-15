package edu.freelance.booking.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import edu.freelance.booking.Models.User;
import edu.freelance.booking.Repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepo) {
        userRepository = userRepo;
    }

    @GetMapping("/users")
    public String users(Model data) {
        List<User> users = userRepository.findAll();
        if(users == null) {
            users = new ArrayList<>();
        }
        data.addAttribute("users", users);

        return "user/users";
    }

    @GetMapping("/users/add")
    public String addUser (Model data) {
        data.addAttribute("user", new User());
        return "user/add";

    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        if (user.getId()==0){
            userRepository.save(user);
        } else {
            User userDb = userRepository.getReferenceById(user.getId());
            userDb.setName(user.getName());
            userDb.setSurname(user.getSurname());
            userDb.setPhone(user.getPhone());
            userDb.setEmail(user.getEmail());
            userRepository.save(userDb);
        }
        return "redirect:/users";
    }
    
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userRepository.getReferenceById(id);
        if (user != null) {
            userRepository.delete(user);
        }
        return "redirect:/users";
    }
    
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable long id, Model data) {
        User user = userRepository.getReferenceById(id);
        if( user == null) {
            return "redirect:/users";
        }
        data.addAttribute("user", user);
        return "user/add";
    }
    
    
}
