package sandybox.auto.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.models.enums.Gender;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;
import sandybox.auto.service.CourseService;
import sandybox.auto.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sandybox.auto.utils.DataUtils;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DataUtils dataUtils;

    @GetMapping()
    public String listStudents(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Student> studentsPage = studentService.findAll(PageRequest.of(page, 10));
        List<Course> courses = courseService.findAll();

        model.addAttribute("students", studentsPage.getContent());
        model.addAttribute("courses", courses);
        model.addAttribute("page", studentsPage);

        return "students";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String email,
                             @RequestParam Long courseId,
                             @RequestParam String birthday,
                             @RequestParam(required = false) String gender) {
        Course course = courseService.findById(courseId);
        LocalDate birthDate = dataUtils.parseDate(birthday);
        Gender studentGender = gender == null ? Gender.UNKNOWN : Gender.valueOf(gender.toUpperCase());
        Student student = new Student(name, surname, email);
        student.setBirthday(birthDate);
        student.setGender(studentGender);
        student.setCourse(course);
        studentService.save(student);
        return "redirect:/students";
    }

    @PostMapping("/add-random")
    public String addRandom() {
        dataUtils.addRandomStudents(1);
        return "redirect:/students";
    }

    @GetMapping("/remove-all")
    public String removeAllStudents() {
        studentService.removeAll();
        return "redirect:/students";
    }
}
