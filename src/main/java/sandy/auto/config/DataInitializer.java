package sandy.auto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sandy.auto.models.Course;
import sandy.auto.repository.CourseRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

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
    }
}
