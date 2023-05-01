package com.a101.fakediary.card.dto.response;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardMadeDiaryResponseDto {
    //해당 카드로 만들어진 다이어리 반환

    private Long diaryId;

    private String title;

    private String summary;

    private String diaryImageUrl;//표지

}