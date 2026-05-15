package edu.freelance.booking.Controllers;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.freelance.booking.Models.Item;
import edu.freelance.booking.Repositories.ItemRepository;

@Controller

public class ItemController {

    private ItemRepository itemRepository;
    public ItemController(ItemRepository repo) {
        itemRepository = repo;
    }
    
    @GetMapping("/items")
    public String getAll(Model data) {
        List<Item> items = itemRepository.findAll();
        data.addAttribute("items", items);
        return "item/items";
    }

    @GetMapping("/item/add")
    public String formItem(Model data) {
        Item item = new Item();
        data.addAttribute("item", item);
        return "item/add";
    }

    @PostMapping("/items/save")
    public String saveItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String editItem(@PathVariable Long id, Model data) {
        Item item = itemRepository.getReferenceById(id);
        data.addAttribute("item", item);
        return "item/add";
    }

    @GetMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        Item item = itemRepository.getReferenceById(id);
        itemRepository.delete(item);
        if(item != null) {
            itemRepository.delete(item);
        }
        return "redirect:/items";
    }

}
