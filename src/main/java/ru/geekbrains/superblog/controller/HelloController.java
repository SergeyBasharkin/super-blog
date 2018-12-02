package ru.geekbrains.superblog.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.superblog.dto.PostDTO;
import ru.geekbrains.superblog.service.api.IPostService;
import ru.geekbrains.superblog.service.api.StorageService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class HelloController {

    private final IPostService postService;
    private final StorageService storageService;

    public HelloController(@Qualifier("dbPostService") IPostService postService, StorageService storageService) {
        this.postService = postService;
        this.storageService = storageService;
    }

    @GetMapping("/post")
    public String post(Model model) {
        model.addAttribute("hasErrors", false);
        model.addAttribute("postDTO", new PostDTO());
        return "postForm";
    }

    @PostMapping("/post")
    public String savePost(@Valid @ModelAttribute PostDTO postDTO, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("hasErrors", bindingResult.hasErrors());

        if (bindingResult.hasErrors()) return "postForm";

        String mimeType = postDTO.getImageFile().getContentType();
        String type = mimeType.split("/")[0];

        if ( type.equalsIgnoreCase("image")) {
            postService.save(postDTO);
            return "redirect:/search";
        } else {
            return "postForm";
        }
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable Long id) {
        model.addAttribute("post", postService.findById(id));
        return "post";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(required = false) String query) {
        model.addAttribute("posts", postService.search(query));
        return "search";
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> file(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
