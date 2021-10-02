package toppan.example.toppan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toppan.example.toppan.models.Post;
import toppan.example.toppan.models.repo.PostRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;
    private String full_text;
    private PostRepository repository;

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
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, HttpServletRequest request, Model model) {
        Post post = new Post(title,anons,full_text,request.getRemoteAddr());
        postRepository.save(post);
//        openTransactionSession();

//        repository.toString();
        return "redirect:/blog-main";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable("id") long id, Model model) {
//      смотри или есть такая запись
        if (!postRepository.existsById(id)) {
            return "redirect:/blog-main";  // если нет то на главную
        }
//      находим и передаем єту одну запись на вюшку
//        postRepository.toString()
//        все запист получают в post от  Optional<Post> post . С обктом на основе Optional работаьб сложно поєтому его преобразуем в
//        обект ArrayList.
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>(); // выделли свободную память " res = new ArrayList<>()" ;
//        ifPresent - переводит все из класа Optional<> в клас ArrayList<>
//        ifPresent - у нее такой ситакис.
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog-main";
        }
//      находим и передаем єту одну запись на вюшку
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
//       Находим запись в базе и заталкиваем о объект
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setTitle(anons);
        post.setTitle(full_text);
//        Обновление существующего объекта в базе
        postRepository.save(post);
        return "redirect:/blog-main";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable("id") long id, Model model) {
//       Находим запись в базе и заталкиваем о объект
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog-main";
    }
}