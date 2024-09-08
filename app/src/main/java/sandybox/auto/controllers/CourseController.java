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
    public String listCourses(Model model, @RequestParam(required = false) Long activeTab) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("activeTab",
                activeTab != null ? activeTab : courses.get(0).getId());

        return "courses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam(defaultValue = "false") boolean isFree) {
        Course course = new Course(title, isFree);
        courseService.save(course);
        return "redirect:/courses";
    }
}
