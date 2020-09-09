package com.cocktailpick.back.tag2.dto;

import javax.validation.constraints.NotBlank;

import com.cocktailpick.back.tag2.domain.Tag;
import com.cocktailpick.back.tag2.domain.TagType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TagRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String tagType;

	public Tag toTag() {
		return Tag.builder()
			.name(name)
			.tagType(TagType.of(tagType))
			.build();
	}
}