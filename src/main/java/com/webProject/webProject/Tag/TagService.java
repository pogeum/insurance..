package com.webProject.webProject.Tag;

import com.webProject.webProject.DataNotFoundException;
import com.webProject.webProject.Store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public Tag getTagById(Integer tag) {
        Optional<Tag> Tag = this.tagRepository.findById(tag);
        if (Tag.isPresent()) {
            return Tag.get();
        } else {
            throw new DataNotFoundException("Tag not found");
        }
    }

    public List<Tag> getAllTags() {
        return this.tagRepository.findAll();
    }
}
