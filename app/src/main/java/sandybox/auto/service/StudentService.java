package sandybox.auto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandybox.auto.models.Student;
import sandybox.auto.models.dto.StudentDTO;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public void save(Student student) {studentRepository.save(student);}

    public void removeAll() {
        studentRepository.deleteAll();
    }

    public Page<Student> findByCourseId(Long courseId, PageRequest pageRequest) {
        return studentRepository.findByCourseId(courseId, pageRequest);
    }

    public Student getStudentFromDTO(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setEmail(studentDTO.getEmail());
        student.setCourse(
                courseRepository.findByTitle(studentDTO.getCourseName())
        );
        return student;
    }

    public StudentDTO getStudentDTOFromStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setCourseName(student.getCourse().getTitle());
        return studentDTO;
    }
}
