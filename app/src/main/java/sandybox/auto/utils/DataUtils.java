package sandybox.auto.utils;

import com.github.javafaker.Faker;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

public class DataUtils {

    public static void createDefaultCourse(CourseRepository courseRepository) {
        if (!courseRepository.existsByTitle("No course")) {
            Course noCourse = new Course();
            noCourse.setTitle("No course");
            noCourse.setFree(true);
            courseRepository.save(noCourse);
        }
    }

    public static void addRandomCourses(CourseRepository courseRepository, int number) {
        Faker faker = new Faker();
        Set<String> titleSet = new HashSet<>();
        List<Course> coursesToSave = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            String title = faker.educator().course();
            while (titleSet.contains(title) || courseRepository.existsByTitle(title)) {
                title = faker.educator().course();
            }
            titleSet.add(title);
            Course course = new Course();
            course.setTitle(title);
            course.setFree(true);
            coursesToSave.add(course);
        }
        courseRepository.saveAll(coursesToSave);
    }


    public static void addRandomStudents(CourseRepository courseRepository,
                                         StudentRepository studentRepository,
                                         int number) {
        Faker faker = new Faker();
        Random random = new Random();
        List<Long> courseIds = courseRepository.findAll().stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        List<Student> studentsToSave = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            String email = faker.internet().emailAddress();
            while (studentRepository.existsByEmail(email)) {
                email = faker.internet().emailAddress();
            }
            Student student = new Student();
            student.setName(faker.name().firstName());
            student.setSurname(faker.name().lastName());
            student.setEmail(email);
            Long courseId = courseIds.get(random.nextInt(courseIds.size()));
            student.setCourse(courseRepository.findById(courseId).orElse(null));
            studentsToSave.add(student);
        }
        studentRepository.saveAll(studentsToSave);
    }
}
