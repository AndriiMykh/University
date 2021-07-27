package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.CourseDto;
import org.foxminded.university.entity.Course;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.CourseMapper.courseDtoToCourse;
import static org.foxminded.university.mapper.CourseMapper.courseToCourseDto;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseDao courseDao;

    public List<CourseDto> findAll() {
        return courseDao.findAll()
                .stream()
                .map(CourseMapper::courseToCourseDto)
                .collect(Collectors.toList());
    }

    public Pageable<CourseDto> findAll(Page page) {
        List<CourseDto> courseDtos = courseDao.findAll(page).getItems()
                .stream()
                .map(CourseMapper::courseToCourseDto)
                .collect(Collectors.toList());
        return new Pageable<>(courseDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public CourseDto findById(Long id) {
        Optional<Course> course = courseDao.findById(id);
        if(course.isEmpty()){
            throw new ServiceException("Course not found with id: "+ id);
        }
        return courseToCourseDto(course.get());
    }

    public void createCourse(CourseDto courseDto) {
        courseDao.create(courseDtoToCourse(courseDto));
    }

    public void updateCourse(CourseDto courseDto) {
        courseDao.update(courseDtoToCourse(courseDto));
    }

    public void deleteCourseById(Long id) {
        courseDao.delete(id);
    }
}
