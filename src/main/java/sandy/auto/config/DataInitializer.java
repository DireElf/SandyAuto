package sandy.auto.config;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sandy.auto.models.Course;
import sandy.auto.models.Student;
import sandy.auto.repository.CourseRepository;
import sandy.auto.repository.StudentRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, есть ли курс "No course" в базе данных
        if (!courseRepository.existsByTitle("No course")) {
            // Если курса нет, добавляем его
            Course noCourse = new Course();
            noCourse.setTitle("No course");
            noCourse.setFree(true);  // Пример: этот курс бесплатный
            courseRepository.save(noCourse);
            System.out.println("Course 'No course' added to the database.");
        }
        createCourses(10);
        createStudents(30);
    }

    private void createCourses(int number) {
        Faker faker = new Faker();
        for(int i = 0; i < number; i++) {
            Course course = new Course();
            course.setTitle(faker.programmingLanguage().name());
            course.setFree(true);
            courseRepository.save(course);
        }
    }

    private void createStudents(int number) {
        Faker faker = new Faker();
        List<Long> coursesIds = courseRepository.findAll().stream()
                .mapToLong(Course::getId).boxed().toList();
        for(int i = 0; i < number; i++) {
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
