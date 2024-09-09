package sandybox.auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.models.dto.CourseDTO;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public Course getCourseFromDTO(CourseDTO courseDTO) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setFree(courseDTO.isFree());
        Set<Student> students = new HashSet<>(studentRepository.findByCourseTitle(courseDTO.getTitle()));
        course.setStudents(students);
        return course;
    }
}
