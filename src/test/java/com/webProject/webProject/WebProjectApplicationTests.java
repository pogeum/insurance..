package com.webProject.webProject;

import com.webProject.webProject.Tag.Tag;
import com.webProject.webProject.Tag.TagRepository;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
class WebProjectApplicationTests {
	@Autowired
	private TagRepository tagRepository;

	@Test
	void contextLoads() {
		Tag tag = new Tag();
		tag.setTageName("양이 많아요");
		this.tagRepository.save(tag);
	}
}
