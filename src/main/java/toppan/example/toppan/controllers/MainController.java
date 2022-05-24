package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j   // Логер
@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "РСЦ ГСЦ в Житомирскій області");
        return "Home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Про РСЦ ГСЦ в Житомирскій області");
        return "about";
    }
}