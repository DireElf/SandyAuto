package sandybox.auto.models.dto;

import javax.validation.constraints.Pattern;

public class StudentDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Invalid date format. Expected dd.MM.yyyy")
    private String birthday;

    private String gender;
    private String courseName;

    public StudentDTO(Long id, String name, String surname, String email, String birthday, String gender, String courseName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.courseName = courseName;
    }

    public StudentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
