package sandybox.auto.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.models.dto.StudentDTO;
import sandybox.auto.repository.StudentRepository;
import sandybox.auto.service.StudentService;

import java.util.List;

@RestController
@Tag(name = "Students", description = "API for managing students")
@RequestMapping("api/students")
public class StudentControllerAPI {

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    public StudentControllerAPI(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @Operation(summary = "Get all students", description = "Retrieve all students from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the students",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "404", description = "Students not found", content = @Content)
    })
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Operation(summary = "Get a student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Operation(summary = "Add a new student", description = "Create a new student in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public Student addStudent(@RequestBody StudentDTO studentDTO) {
        return studentRepository.save(studentService.getStudentFromDTO(studentDTO));
    }

    @Operation(summary = "Update a student", description = "Update details of an existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @PutMapping("/{id}")
    public Student replaceStudent(@RequestBody StudentDTO studentDTO, @PathVariable Long id) {
        Student student = studentService.getStudentFromDTO(studentDTO);
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(student.getName());
                    existingStudent.setSurname(student.getSurname());
                    existingStudent.setEmail(student.getEmail());
                    existingStudent.setCourse(student.getCourse());
                    return studentRepository.save(existingStudent);
                }).orElseGet(() -> studentRepository.save(student));
    }

    @Operation(summary = "Delete a student", description = "Delete a specific student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
