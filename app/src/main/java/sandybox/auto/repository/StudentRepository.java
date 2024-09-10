package sandybox.auto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sandybox.auto.models.Student;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByCourseId(Long courseId, PageRequest pageRequest);
    List<Student> findByCourseTitle(String title);
    Boolean existsByEmail(String email);
    long countByIdIn(Set<Long> studentIds);
}
