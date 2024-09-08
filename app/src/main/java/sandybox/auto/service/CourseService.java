package sandybox.auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandybox.auto.models.Course;
import sandybox.auto.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {return courseRepository.findById(id).orElse(null);}

    public void save(Course course) {
        courseRepository.save(course);
    }
}
