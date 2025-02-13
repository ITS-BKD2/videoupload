package com.ttf.VideoUpload.controller;

import com.ttf.VideoUpload.model.Contenuto;
import com.ttf.VideoUpload.service.ContenutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/contenuti")
public class ContenutoController {

    @Autowired
    private ContenutoService service;

    @GetMapping
    public String listContenuti(Model model) {
        model.addAttribute("contenuti", service.getAllContenuti());
        return "contenuti";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("contenuto", new Contenuto());
        return "form";
    }

    @PostMapping("/save")
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
}
