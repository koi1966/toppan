package toppan.example.toppan.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j   // Логер
@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "РСЦ ГСЦ в Житомирскій області__ооо");
        model.addAttribute("standardDate", new Date());

        return "Home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Про РСЦ ГСЦ в Житомирскій області");
        return "about";
    }

    @GetMapping("/user")
    public String user() {

        return "<h2> Hey, user </h2> ";
    }

    @GetMapping("/admin")
    public String admin() {

        return "<h2> Hey, admin </h2> ";
    }
}