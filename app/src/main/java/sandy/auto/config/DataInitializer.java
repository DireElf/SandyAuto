package sandy.auto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sandy.auto.repository.CourseRepository;
import sandy.auto.repository.StudentRepository;

import static sandy.auto.utils.DataUtils.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        createDefaultCourse(courseRepository);
        addRandomCourses(courseRepository, 25);
        addRandomStudents(courseRepository,studentRepository, 300);
    }
}
