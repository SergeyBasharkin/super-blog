package ru.geekbrains.superblog.service.impl;

import org.springframework.stereotype.Service;
import ru.geekbrains.superblog.domain.Post;
import ru.geekbrains.superblog.dto.PostDTO;
import ru.geekbrains.superblog.exception.PageNotFoundException;
import ru.geekbrains.superblog.jpa.PostRepository;
import ru.geekbrains.superblog.service.api.IPostService;
import ru.geekbrains.superblog.service.api.StorageService;

import java.util.List;
import java.util.UUID;

@Service("dbPostService")
public class DBPostService implements IPostService {

    private final PostRepository postRepository;
    private final StorageService storageService;

    public DBPostService(PostRepository postRepository, StorageService storageService) {
        this.postRepository = postRepository;
        this.storageService = storageService;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(PageNotFoundException::new);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> search(String query) {
        return query != null && !query.isEmpty() ?
                postRepository.findAllByTitleLikeOrBodyLike("%" + query + "%","%" + query + "%")
                :
                postRepository.findAll();
    }

    @Override
    public void save(PostDTO postDTO) {
        String filename = postDTO.getImg();
        if (postDTO.getImageFile() != null) {
            String prefix = UUID.randomUUID().toString();
            filename = "/file/" + prefix + postDTO.getImageFile().getOriginalFilename();
            storageService.store(postDTO.getImageFile(), prefix);
        }
        postDTO.setImg(filename);
        postRepository.save(Post.builder()
                .img(postDTO.getImg())
                .title(postDTO.getTitle())
                .body(postDTO.getBody())
                .build());
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void deleteAll() {
        postRepository.deleteAll();
    }
}
