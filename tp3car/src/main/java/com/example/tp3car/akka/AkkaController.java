package com.example.tp3car.akka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AkkaController {

    private final AkkaService akkaService;

    @Autowired
    public AkkaController(AkkaService akkaService) {
        this.akkaService = akkaService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @PostMapping("/addFile")
    public String addFile(@RequestParam("fileInput") String fileContent) {
    	return "redirect:/";
    }

    @GetMapping("/getOccurrences")
    @ResponseBody
    public String getOccurrences(@RequestParam("word") String word) {
    	return "redirect:/";
    }
}
