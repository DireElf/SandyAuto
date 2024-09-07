package sandy.auto.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import sandy.auto.models.Course;
import sandy.auto.models.Student;
import sandy.auto.service.CourseService;
import sandy.auto.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/students")
    public String listStudents(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Student> studentsPage = studentService.findAll(PageRequest.of(page, 10));
        List<Course> courses = courseService.findAll();

        model.addAttribute("students", studentsPage.getContent()); // Список студентов на текущей странице
        model.addAttribute("courses", courses); // Курсы для выпадающего списка
        model.addAttribute("page", studentsPage); // Объект страницы для работы с пагинацией

        return "students";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String email,
                             @RequestParam Long courseId) {
        Course course = courseService.findById(courseId);
        Student student = new Student(name, surname, email);
        student.setCourse(course); // Связываем студента с курсом
        studentService.save(student);
        return "redirect:/students"; // После добавления студента перенаправляем на список
    }
}
