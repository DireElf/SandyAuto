package sandybox.auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.models.dto.CourseDTO;
import sandybox.auto.models.dto.StudentDTO;
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

    @Autowired
    private StudentService studentService;

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
        Set<Student> students = courseDTO.getStudents().stream()
                .map(studentDTO -> studentService.getStudentFromDTO(studentDTO))
                .collect(Collectors.toSet());
        course.setStudents(students);
        return course;
    }

    public CourseDTO getCourseDTOFromCourse(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle(course.getTitle());
        courseDTO.setFree(course.isFree());
        courseDTO.setStudents(course.getStudents().stream()
                .map(student -> studentService.getStudentDTOFromStudent(student))
                .collect(Collectors.toSet()));
        return courseDTO;
    }

    public Boolean courseHasNonExistedStudent(Course course) {
        Set<Long> studentIds = course.getStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
        long existingStudentsCount = studentRepository.countByIdIn(studentIds);
        return existingStudentsCount != studentIds.size();
    }

    public Course updateCourseOrAddNew(Course course, Long id) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setTitle(course.getTitle());
                    existingCourse.setFree(course.isFree());
                    existingCourse.setStudents(course.getStudents());
                    return courseRepository.save(existingCourse);
                }).orElseGet(() -> courseRepository.save(course));
    }
}
