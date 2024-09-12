package sandybox.auto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;
import sandybox.auto.utils.DataUtils;

import static sandybox.auto.utils.DataUtils.*;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private DataUtils dataUtils;

    @Override
    public void run(String... args) throws Exception {
        dataUtils.createDefaultCourse();
        dataUtils.addRandomCourses(5);
        dataUtils.addRandomStudents(5);
    }
}
