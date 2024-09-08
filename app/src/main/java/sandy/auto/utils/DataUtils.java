package sandy.auto.utils;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandy.auto.models.Course;
import sandy.auto.models.Student;
import sandy.auto.repository.CourseRepository;
import sandy.auto.repository.StudentRepository;

import java.util.List;
import java.util.Random;

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
            course.setTitle(faker.programmingLanguage().name());
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
                .mapToLong(Course::getId).boxed().toList();
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
