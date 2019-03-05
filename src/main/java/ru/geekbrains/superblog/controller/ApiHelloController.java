package ru.geekbrains.superblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.superblog.domain.Post;
import ru.geekbrains.superblog.dto.PostDTO;
import ru.geekbrains.superblog.service.impl.DBPostService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiHelloController {

    private final DBPostService postService;

    public ApiHelloController(DBPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public List<Post> posts(@RequestParam(value = "query", required = false) String query){
        return postService.search(query);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> post(@RequestBody PostDTO postDTO, BindingResult bindingResult){
       if (bindingResult.hasErrors()) ResponseEntity.badRequest();
        postService.save(postDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{id}")
    public void deleteById(@PathVariable Long id){
        postService.delete(id);
    }

    @DeleteMapping("/post")
    public void deleteAll(){
        postService.deleteAll();
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id){
        return ResponseEntity.ok(postService.findById(id));
    }
}
