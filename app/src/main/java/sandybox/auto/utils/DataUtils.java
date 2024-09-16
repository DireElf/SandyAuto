package sandybox.auto.utils;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandybox.auto.models.Course;
import sandybox.auto.models.Student;
import sandybox.auto.models.enums.Gender;
import sandybox.auto.repository.CourseRepository;
import sandybox.auto.repository.StudentRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class DataUtils {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void createDefaultCourse() {
        if (!courseRepository.existsByTitle("No course")) {
            Course noCourse = new Course();
            noCourse.setTitle("No course");
            noCourse.setFree(true);
            courseRepository.save(noCourse);
        }
    }

    public void addRandomCourses(int number) {
        Faker faker = new Faker();
        Set<String> titleSet = new HashSet<>();
        List<Course> coursesToSave = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            String title = faker.educator().course();
            while (titleSet.contains(title) || courseRepository.existsByTitle(title)) {
                title = faker.educator().course();
            }
            titleSet.add(title);
            Course course = new Course();
            course.setTitle(title);
            course.setFree(true);
            coursesToSave.add(course);
        }
        courseRepository.saveAll(coursesToSave);
    }


    public void addRandomStudents(int number) {
        Faker faker = new Faker();
        Random random = new Random();
        List<Long> courseIds = courseRepository.findAll().stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        List<Student> studentsToSave = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            String email = faker.internet().emailAddress();
            while (studentRepository.existsByEmail(email)) {
                email = faker.internet().emailAddress();
            }
            Student student = new Student();
            student.setName(faker.name().firstName());
            student.setSurname(faker.name().lastName());
            student.setEmail(email);
            Long courseId = courseIds.get(random.nextInt(courseIds.size()));
            Gender[] genders = Gender.values();
            Gender gender = genders[random.nextInt(3)];
            student.setGender(gender);
            student.setBirthday(getRandomBirthday(faker));
            student.setCourse(courseRepository.findById(courseId).orElse(null));
            studentsToSave.add(student);
        }
        studentRepository.saveAll(studentsToSave);
    }

    public LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка преобразования даты: " + e.getMessage());
        }
        return date;
    }

    public String formatDate(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private LocalDate getRandomBirthday(Faker faker) {
        Date birthday = faker.date().birthday();
        return birthday.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
