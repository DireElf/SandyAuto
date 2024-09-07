package sandy.auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandy.auto.models.Student;
import sandy.auto.repository.StudentRepository;
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public void save(Student student) {studentRepository.save(student);}
}
