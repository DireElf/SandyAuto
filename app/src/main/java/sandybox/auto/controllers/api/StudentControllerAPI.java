package sandybox.auto.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandybox.auto.models.Student;
import sandybox.auto.models.dto.StudentDTO;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;
import sandybox.auto.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Students", description = "API for managing students")
@RequestMapping("api/students")
public class StudentControllerAPI {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentService studentService;

    public StudentControllerAPI(StudentRepository studentRepository,
                                CourseRepository courseRepository,
                                StudentService studentService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Operation(summary = "Get all students", description = "Retrieve all students from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the students",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No students found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        List<StudentDTO> studentDTOList = studentList.stream()
                .map(studentService::getStudentDTOFromStudent)
                .collect(Collectors.toList());
        return ResponseEntity.ok(studentDTOList);
    }


    @Operation(summary = "Get a student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        StudentDTO studentDTO = studentService.getStudentDTOFromStudent(student);
        return ResponseEntity.ok(studentDTO);
    }

    @Operation(summary = "Add a new student", description = "Create a new student in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO studentDTO) {
        String courseTitle = studentDTO.getCourseName();
        if (courseTitle != null && !courseRepository.existsByTitle(courseTitle)) {
            return ResponseEntity.badRequest().body("Student has non-existent course");
        }
        if (courseTitle == null) {
            studentDTO.setCourseName("No course");
        }
        Student student = studentService.getStudentFromDTO(studentDTO);
        Student savedStudent = studentRepository.save(student);
        studentDTO.setId(savedStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }

    @Operation(summary = "Update a student", description = "Update details of an existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceStudent(@RequestBody StudentDTO studentDTO, @PathVariable Long id) {
        if (!courseRepository.existsByTitle(studentDTO.getCourseName())) {
            return ResponseEntity.badRequest().body("Student has non-existent course");
        }
        Student student = studentService.getStudentFromDTO(studentDTO);
        Student updatedStudent = studentService.updateStudentOrAddNew(student, id);
        studentDTO.setId(updatedStudent.getId());
        return ResponseEntity.ok(studentDTO);
    }

    @Operation(summary = "Delete a student", description = "Delete a specific student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
