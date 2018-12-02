package ru.geekbrains.superblog.service.api;

import ru.geekbrains.superblog.domain.Post;
import ru.geekbrains.superblog.dto.PostDTO;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    Post findById(Long id);
    Post save(Post post);
    List<Post> search(String query);

    void save(PostDTO postDTO) throws IOException;
}
