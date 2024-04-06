package com.example.tp3car.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AkkaController {

    private final AkkaService akkaService;

    @Autowired 
    public AkkaController(AkkaService akkaService) {
        this.akkaService = akkaService;
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @PostMapping("/initializeActors")
    public String initializeActors() {
        akkaService.initialize();
        return "redirect:/home";
    }
    
    @PostMapping("/addFile")
    public String addFile(@RequestParam("fileInput") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/home"; 
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String fileContent = contentBuilder.toString();
            akkaService.processFileContent(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/home"; 
    }
    
    @GetMapping("/getOccurrences")
    public String getOccurrences(@RequestParam("word") String word, Model model) {
        Map<String, Integer> occurrences = akkaService.getWordOccurrences(word);
        int wordCount = occurrences.getOrDefault(word, 0);
        model.addAttribute("word", word);
        model.addAttribute("wordCount", wordCount);
        return "home";
    }
    
}
