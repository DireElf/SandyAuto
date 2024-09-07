package sandy.auto.controllers.api;

import org.springframework.web.bind.annotation.*;
import sandy.auto.models.Course;
import sandy.auto.repository.CourseRepository;

import java.util.List;

@RestController
public class CourseControllerAPI {
    private final CourseRepository courseRepository;

    public CourseControllerAPI(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("api/courses")
    List<Course> getAllStudents() {
        return courseRepository.findAll();
    }

    @GetMapping("api/courses/{id}")
    Course getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("api/courses")
    Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("api/course/{id}")
    Course replaceCourse(@RequestBody Course course, @PathVariable Long id) {
        return courseRepository.findById(id)
                .map(s -> {
                    s.setTitle(course.getTitle());
                    s.setFree(course.isFree());
                    s.setStudents(course.getStudents());
                    return courseRepository.save(s);
                }).orElseGet(() -> courseRepository.save(course));
    }

    @DeleteMapping("api/courses/{id}")
    void deleteCoursesById(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
}
