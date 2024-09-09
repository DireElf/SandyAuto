package sandybox.auto.models.dto;

public class StudentDTO {
    private String name;
    private String surname;
    private String email;
    private String courseName;

    public StudentDTO(String name, String surname, String email, String courseName) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.courseName = courseName;
    }

    public StudentDTO() {
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
}
