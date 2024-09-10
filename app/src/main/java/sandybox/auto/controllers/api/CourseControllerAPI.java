package sandybox.auto.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandybox.auto.models.Course;
import sandybox.auto.models.dto.CourseDTO;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Courses", description = "API for managing courses")
@RequestMapping("api/courses")
public class CourseControllerAPI {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public CourseControllerAPI(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @Operation(summary = "Get all courses", description = "Retrieve all courses from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the courses",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Courses not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDTO> courseDTOList = courseList.stream()
                .map(courseService::getCourseDTOFromCourse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDTOList);
    }

    @Operation(summary = "Get a course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the course",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return courseService.getCourseDTOFromCourse(course);
    }

    @Operation(summary = "Add a new course", description = "Create a new course in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseService.getCourseFromDTO(courseDTO);
        if (courseService.courseHasNonExistedStudent(course)) {
            return ResponseEntity.badRequest().body("Course contains non-existing student(s)");
        }
        Course savedCourse = courseRepository.save(course);
        CourseDTO savedCourseDTO = courseService.getCourseDTOFromCourse(savedCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseDTO);
    }

    @Operation(summary = "Update a course", description = "Update details of an existing course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long id) {
        if (id == 1L && !courseDTO.getTitle().equals("No course")) {
            return ResponseEntity.badRequest().body("Title of the first course cannot be changed");
        }
        Course course = courseService.getCourseFromDTO(courseDTO);
        if (courseService.courseHasNonExistedStudent(course)) {
            return ResponseEntity.badRequest().body("Course contains non-existing student(s)");
        }
        Course updatedCourse = courseService.updateCourseOrAddNew(course, id);
        CourseDTO updatedCourseDTO = courseService.getCourseDTOFromCourse(updatedCourse);
        return ResponseEntity.ok(updatedCourseDTO);
    }


    @Operation(summary = "Delete a course", description = "Delete a specific course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoursesById(@PathVariable Long id) {
        if (id == 1L) {
            return ResponseEntity.badRequest().body("Course 'No course' cannot be deleted");
        }
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
