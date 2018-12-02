package ru.geekbrains.superblog.service.impl;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.superblog.domain.Post;
import ru.geekbrains.superblog.dto.PostDTO;
import ru.geekbrains.superblog.service.api.IPostService;
import ru.geekbrains.superblog.service.api.StorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("memPostService")
public class MemPostService implements IPostService {

    private Faker faker = new Faker();
    private final StorageService storageService;

    private List<Post> posts = new ArrayList<>(Arrays.asList(
            Post.builder()
                    .id(0L)
                    .title(faker.lorem().fixedString(3))
                    .body(faker.lorem().sentence(30000))
                    .img("/img/1.jpg")
                    .build(),
            Post.builder()
                    .id(1L)
                    .title(faker.lorem().fixedString(3))
                    .body(faker.lorem().sentence(30000))
                    .img("/img/2.jpg")
                    .build(),
            Post.builder()
                    .id(2L)
                    .title(faker.lorem().fixedString(3))
                    .body(faker.lorem().sentence(30000))
                    .img("/img/1.jpg")
                    .build(),
            Post.builder()
                    .id(3L)
                    .title(faker.lorem().fixedString(3))
                    .body(faker.lorem().sentence(30000))
                    .img("/img/2.jpg")
                    .build()
    ));

    public MemPostService(StorageService storageService) {
        this.storageService = storageService;
    }


    @Override
    public Post findById(Long id) {
        return posts.stream().filter(post -> post.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public List<Post> search(String query) {
        return query != null && !query.isEmpty() ?
                posts.stream().filter(postDTO ->
                        postDTO.getTitle().toLowerCase().matches(".*" + query.toLowerCase() + ".*") ||
                                postDTO.getBody().toLowerCase().matches(".*" + query.toLowerCase() + ".*")
                ).collect(Collectors.toList())
                :
                posts;
    }

    @Override
    public void save(PostDTO postDTO) {

        String prefix = UUID.randomUUID().toString();
        String filename =  prefix + postDTO.getImageFile().getOriginalFilename();
        storageService.store(postDTO.getImageFile(), prefix);
        postDTO.setId((long) posts.size());
        postDTO.setImg(filename);
        posts.add(Post.builder()
                .id((long) posts.size())
                .img(postDTO.getImg())
                .title(postDTO.getTitle())
                .body(postDTO.getBody())
                .build());
    }

}
