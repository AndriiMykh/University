package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.LessonDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.LessonDto;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.LessonMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.LessonMapper.lessonDtoToLesson;
import static org.foxminded.university.mapper.LessonMapper.lessonToLessonDto;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonDao lessonDao;

    public List<LessonDto> findAll() {
        return lessonDao.findAll()
                .stream()
                .map(LessonMapper::lessonToLessonDto)
                .collect(Collectors.toList());
    }

    public Pageable<LessonDto> findAll(Page page) {
        List<LessonDto> lessonDtos = lessonDao.findAll(page).getItems()
                .stream()
                .map(LessonMapper::lessonToLessonDto)
                .collect(Collectors.toList());
        return new Pageable<>(lessonDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public LessonDto findById(Long id) {
        Optional<Lesson> lesson = lessonDao.findById(id);
        if(lesson.isEmpty()){
            throw new ServiceException("Lesson not found with id: "+ id);
        }
        return lessonToLessonDto(lesson.get());
    }

    public void createLesson(LessonDto lesson) {
        lessonDao.create(lessonDtoToLesson(lesson));
    }

    public void updateLesson(LessonDto lesson) {
        lessonDao.update(lessonDtoToLesson(lesson));
    }

    public void deleteLessonById(Long id) {
        lessonDao.delete(id);
    }
}
