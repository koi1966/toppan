package toppan.example.toppan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.models.Post;
import toppan.example.toppan.models.repo.PostRepository;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;
    private String full_text;

    @GetMapping("/blog-main")
  public String blogMain(Model model) {
//      находим и передаем все записи на вюшку
      Iterable<Post> posts = postRepository.findAll();
      model.addAttribute("post",posts);
      return "blog-main";
  }

    @GetMapping("/blog-add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog-add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/blog-main";
    }
}