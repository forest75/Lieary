package com.a101.fakediary.diaryimage.service;

import com.a101.fakediary.diary.repository.DiaryRepository;
import com.a101.fakediary.diaryimage.entity.DiaryImage;
import com.a101.fakediary.diaryimage.repository.DiaryImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DiaryImageService {

    private final DiaryRepository diaryRepository;

    private final DiaryImageRepository diaryImageRepository;

    public void createDiaryImages(Long diaryId, List<String> diaryImageUrls) {
        for (String diaryImageUrl : diaryImageUrls) {
            DiaryImage diaryImage = new DiaryImage();
            diaryImage.setDiary(diaryRepository.findById(diaryId).orElseThrow());
            diaryImage.setDiaryImageUrl(diaryImageUrl);

            diaryImageRepository.save(diaryImage);
        }
    }
}
