package com.cocktailpick.back.tag2.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByTagType(TagType tagType);

	Optional<Tag> findByName(String name);
}
