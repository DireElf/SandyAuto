package sandybox.auto.models.dto;

import sandybox.auto.models.Student;

import java.util.List;

public class CourseDTO {
    private String title;
    private boolean isFree;
    private List<Student> students;

    public CourseDTO(String title, boolean isFree, List<Student> students) {
        this.title = title;
        this.isFree = isFree;
        this.students = students;
    }

    public CourseDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
