package edu.freelance.booking.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.freelance.booking.Models.Images;
import edu.freelance.booking.Repositories.ImageRepository;

@Controller
public class ImageController {

    private ImageRepository imageRepository;

    public ImageController(ImageRepository repo){
        imageRepository = repo;
    }
    
    
    @PostMapping("/upload")
    //Прием и сохранение картинки
    public String uploadImage(@RequestParam("title") String title, 
                              @RequestParam("picture") MultipartFile imageFile) {
        try {
            String uploadPath = "src/main/resources/static/images";
            Path uploadDir = Paths.get(uploadPath);
            if(!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

           //String filename = imageFile.getOriginalFilename();
           String ext = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
           String newFilename = UUID.randomUUID().toString() + ext; 
           Path targetPath = uploadDir.resolve(newFilename);
            imageFile.transferTo(targetPath);

            Images img = new Images();
            img.setTitle(title);
            img.setFilename(newFilename);
            imageRepository.save(img);

        } catch (IOException e) {

        }
        return "redirect:/upload";
    }

    @GetMapping("/upload")
    public String formUpload() {
        return "upload/form";
    }

    @GetMapping("/gallery")
    public String gallery(Model data) {
        List<Images> pics =  imageRepository.findAll();
        data.addAttribute("pictures", pics);
        return "images/catalog";
    }
}
