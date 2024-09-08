package sandybox.auto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

import static sandybox.auto.utils.DataUtils.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        createDefaultCourse(courseRepository);
        addRandomCourses(courseRepository, 5);
        addRandomStudents(courseRepository,studentRepository, 5);
    }
}
