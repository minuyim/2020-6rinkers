package com.cocktailpick.back.users2.favorite.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.cocktailpick.back.common.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
public class Favorite extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_sequence_gen")
	@SequenceGenerator(name = "favorite_sequence_gen", sequenceName = "favorite_sequence")
	private Long id;

	private Long userId;

	private Long cocktailId;

	public boolean containCocktailId(Long cocktailId) {
		return this.cocktailId.equals(cocktailId);
	}
}
