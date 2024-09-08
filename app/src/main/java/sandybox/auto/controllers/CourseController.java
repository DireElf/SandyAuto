package sandybox.auto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.service.CourseService;
import sandybox.auto.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listCourses(Model model,
                              @RequestParam(required = false) Long activeTab,
                              @RequestParam(defaultValue = "0") int studentPage) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);

        Long activeCourseId = activeTab != null ? activeTab : courses.get(0).getId();
        model.addAttribute("activeTab", activeCourseId);

        Page<Student> studentsPage = studentService
                .findByCourseId(activeCourseId, PageRequest.of(studentPage, 10));
        model.addAttribute("studentsPage", studentsPage);
        model.addAttribute("students", studentsPage.getContent());
        return "courses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam(defaultValue = "false") boolean isFree) {
        Course course = new Course(title, isFree);
        courseService.save(course);
        return "redirect:/courses";
    }
}
