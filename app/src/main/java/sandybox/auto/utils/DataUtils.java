package sandybox.auto.utils;

import com.github.javafaker.Faker;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

import java.util.List;
import java.util.Random;
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
        for(int i = 1; i <= number; i++) {
            Course course = new Course();
            course.setTitle(faker.educator().course());
            course.setFree(true);
            courseRepository.save(course);
        }
    }

    public static void addRandomStudents(CourseRepository courseRepository,
                                         StudentRepository studentRepository,
                                         int number)
    {
        Faker faker = new Faker();
        List<Long> coursesIds = courseRepository.findAll().stream()
                .mapToLong(Course::getId).boxed().collect(Collectors.toList());
        for(int i = 1; i <= number; i++) {
            Student student = new Student();
            student.setName(faker.name().firstName());
            student.setSurname(faker.name().lastName());
            student.setEmail(faker.internet().emailAddress());
            Random random = new Random();
            Long courseId = coursesIds.get(random.nextInt(coursesIds.size()));
            student.setCourse(courseRepository.findById(courseId).orElse(null));
            studentRepository.save(student);
        }
    }
}
