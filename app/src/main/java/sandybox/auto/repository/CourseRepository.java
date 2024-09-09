package sandybox.auto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sandybox.auto.models.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitle(String noCourse);

    Course findByTitle(String name);
}
