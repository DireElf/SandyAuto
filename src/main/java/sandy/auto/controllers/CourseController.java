package sandy.auto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sandy.auto.models.Course;
import sandy.auto.service.CourseService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listCourses(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) Long activeTab) {
        Page<Course> coursesPage = courseService.findAll(PageRequest.of(page, 10));
        List<Course> courses = coursesPage.getContent();

        model.addAttribute("courses", courses);  // Список курсов
        model.addAttribute("page", coursesPage); // Информация для пагинации

        // Передаем активную вкладку (если передана)
        model.addAttribute("activeTab",
                activeTab != null ? activeTab : courses.isEmpty() ? null : courses.get(0).getId());

        return "courses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam(defaultValue = "false") boolean isFree) {
        Course course = new Course(title, isFree);
        courseService.save(course);
        return "redirect:/courses";
    }
}
