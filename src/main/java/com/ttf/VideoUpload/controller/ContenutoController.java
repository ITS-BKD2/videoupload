package com.ttf.VideoUpload.controller;

import com.ttf.VideoUpload.model.Contenuto;
import com.ttf.VideoUpload.service.ContenutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.io.File;

@Controller
@RequestMapping("/")
public class ContenutoController {

    private final ContenutoService service;

    public ContenutoController(ContenutoService service) {
        this.service = service;
    }

    @GetMapping("/contenuti")
    public String listContenuti(Model model) {
        model.addAttribute("contenuti", service.getAllContenuti());
        return "contenuti"; 
    }

    @GetMapping("/contenuti/new")
    public String createForm(Model model) {
        model.addAttribute("contenuto", new Contenuto());
        return "form";
    }

    @PostMapping("/contenuti/save")
    public String saveContenuto(@ModelAttribute Contenuto contenuto, 
                                @RequestParam("videoFile") MultipartFile videoFile) {
        if (!videoFile.isEmpty()) {
            try {
                // Genera un nome univoco per il file
                String filename = UUID.randomUUID().toString() + "_" + videoFile.getOriginalFilename();
                File storageDir = new File("storage");
    
                if (!storageDir.exists()) {
                    storageDir.mkdir();
                    System.out.println("Cartella 'storage' creata.");
                }
    
                String filePath = storageDir.getAbsolutePath() + File.separator + filename;
                System.out.println("Salvando file in: " + filePath);
    
                // Salva il file nella cartella
                videoFile.transferTo(new File(filePath));
    
                // Salva il nome del file nel database
                contenuto.setVideo(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        service.saveContenuto(contenuto);
        return "redirect:/contenuti";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteContenuto(@PathVariable Long id) {
        Optional<Contenuto> contenutoOpt = service.getContenutoById(id);
        if (contenutoOpt.isPresent()) {
            Contenuto contenuto = contenutoOpt.get();
            Path filePath = Paths.get("storage", contenuto.getVideo());

            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    System.out.println("File eliminato: " + filePath);
                }
                service.deleteContenuto(id);
                System.out.println("Contenuto eliminato con ID: " + id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/contenuti";
    }
}
