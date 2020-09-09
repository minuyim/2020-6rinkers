package com.cocktailpick.back.tag2.ui;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.tag2.domain.TagType;
import com.cocktailpick.back.tag2.dto.TagRequest;
import com.cocktailpick.back.tag2.dto.TagResponse;
import com.cocktailpick.back.tag2.application.TagService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagController {
	private final TagService tagService;

	@GetMapping
	public ResponseEntity<List<TagResponse>> findTags(@RequestParam(required = false) TagType tagType,
		@RequestParam(required = false) Integer size, @RequestParam(defaultValue = "false") boolean random) {
		return ResponseEntity.ok(tagService.findTags(tagType, size, random));
	}

	@PostMapping
	public ResponseEntity<Void> createTag(@RequestBody @Valid TagRequest tagRequest) {
		Long tagId = tagService.createTag(tagRequest);

		return ResponseEntity.created(URI.create("/api/tags/" + tagId)).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TagRequest tagRequest) {
		tagService.update(id, tagRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		tagService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
