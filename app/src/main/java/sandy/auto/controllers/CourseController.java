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
import sandy.auto.models.Student;
import sandy.auto.service.CourseService;
import sandy.auto.service.StudentService;

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
        // Fetch all courses (no pagination for course tabs)
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);  // List of courses

        // Handle active tab (if provided)
        Long activeCourseId = activeTab != null ? activeTab : (!courses.isEmpty() ? courses.get(0).getId() : null);
        model.addAttribute("activeTab", activeCourseId);

        if (activeCourseId != null) {
            // Fetch students for the active course with pagination
            Page<Student> studentsPage = studentService.findByCourseId(activeCourseId, PageRequest.of(studentPage, 5));
            model.addAttribute("studentsPage", studentsPage); // Student pagination info
            model.addAttribute("students", studentsPage.getContent()); // List of students for active course
        } else {
            // Если студентов нет, передаем пустую страницу
            model.addAttribute("studentsPage", Page.empty());
            model.addAttribute("students", List.of());
        }

        return "courses";
    }

    @PostMapping("/add")
    public String addCourse(@RequestParam String title, @RequestParam(defaultValue = "false") boolean isFree) {
        Course course = new Course(title, isFree);
        courseService.save(course);
        return "redirect:/courses";
    }
}
