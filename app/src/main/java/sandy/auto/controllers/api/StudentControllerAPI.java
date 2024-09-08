package sandy.auto.controllers.api;

import org.springframework.web.bind.annotation.*;
import sandy.auto.models.Student;
import sandy.auto.repository.StudentRepository;

import java.util.List;

@RestController
public class StudentControllerAPI {
    private final StudentRepository studentRepository;

    public StudentControllerAPI(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("api/students")
    List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("api/students/{id}")
    Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("api/students")
    Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("api/students/{id}")
    Student replaceStudent(@RequestBody Student student, @PathVariable Long id) {
        return studentRepository.findById(id)
                .map(s -> {
                    s.setName(student.getName());
                    s.setSurname(student.getSurname());
                    s.setEmail(student.getEmail());
                    s.setGender(student.getGender());
                    s.setBirthday(student.getBirthday());
                    return studentRepository.save(s);
                }).orElseGet(() -> studentRepository.save(student));
    }

    @DeleteMapping("api/students/{id}")
    void deleteStudentById(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
