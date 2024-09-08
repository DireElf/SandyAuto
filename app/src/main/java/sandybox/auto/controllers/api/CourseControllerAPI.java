package sandybox.auto.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import sandybox.auto.models.Course;
import sandybox.auto.repository.CourseRepository;

import java.util.List;

@RestController
@Tag(name = "Courses", description = "API for managing courses")
@RequestMapping("api/courses")
public class CourseControllerAPI {

    private final CourseRepository courseRepository;

    public CourseControllerAPI(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Operation(summary = "Get all courses", description = "Retrieve all courses from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the courses",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "404", description = "Courses not found", content = @Content)
    })
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Operation(summary = "Get a course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the course",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Operation(summary = "Add a new course", description = "Create a new course in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @Operation(summary = "Update a course", description = "Update details of an existing course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Course.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @PutMapping("/{id}")
    public Course replaceCourse(@RequestBody Course course, @PathVariable Long id) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setTitle(course.getTitle());
                    existingCourse.setFree(course.isFree());
                    existingCourse.setStudents(course.getStudents());
                    return courseRepository.save(existingCourse);
                }).orElseGet(() -> courseRepository.save(course));
    }

    @Operation(summary = "Delete a course", description = "Delete a specific course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteCoursesById(@PathVariable Long id) {
        if (id == 1L) throw new RuntimeException("Course 'No course' cannot be deleted");
        courseRepository.deleteById(id);
    }
}
