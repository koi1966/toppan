package toppan.example.toppan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "Home";
    }

//    @GetMapping("/blog")
//    public String blog(Model model) {
//        model.addAttribute("title", "Єто блог");
//        return "blog";
//    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Cтраница про нас");
        return "about";
    }

//   токен - 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

}