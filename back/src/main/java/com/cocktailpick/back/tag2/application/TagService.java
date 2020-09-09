package com.cocktailpick.back.tag2.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.exceptions.InvalidValueException;
import com.cocktailpick.back.tag2.domain.Tag;
import com.cocktailpick.back.tag2.domain.TagRepository;
import com.cocktailpick.back.tag2.domain.TagType;
import com.cocktailpick.back.tag2.dto.TagRequest;
import com.cocktailpick.back.tag2.dto.TagResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class TagService {
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<TagResponse> findTags(TagType tagType, Integer size, boolean random) {
		List<Tag> tags = Optional.ofNullable(tagType)
			.map(tagRepository::findByTagType)
			.orElseGet(tagRepository::findAll);

		if (random) {
			Collections.shuffle(tags);
		}

		return TagResponse.listOf(findTagsBySize(tags, size));
	}

	private List<Tag> findTagsBySize(List<Tag> tags, Integer size) {
		if (Objects.isNull(size) || isSizeOver(tags, size)) {
			return tags;
		}
		return tags.subList(0, size);
	}

	private boolean isSizeOver(List<Tag> tags, Integer size) {
		return size >= tags.size();
	}

	@Transactional
	public Long createTag(TagRequest tagRequest) {
		validateDuplicated(tagRequest);
		Tag tag = tagRepository.save(tagRequest.toTag());

		return tag.getId();
	}

	@Transactional
	public void update(Long id, TagRequest tagRequest) {
		validateDuplicated(tagRequest);
		Tag tag = tagRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.TAG_NOT_FOUND));

		tag.update(tagRequest.getName(), TagType.of(tagRequest.getTagType()));
	}

	private void validateDuplicated(TagRequest tagRequest) {
		boolean isPresent = tagRepository.findByName(tagRequest.getName()).isPresent();

		if (isPresent) {
			throw new InvalidValueException(ErrorCode.TAG_DUPLICATED);
		}
	}

	@Transactional
	public void delete(Long id) {
		tagRepository.deleteById(id);
	}
}
