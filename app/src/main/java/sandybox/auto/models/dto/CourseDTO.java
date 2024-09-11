package sandybox.auto.models.dto;

import java.util.Set;

public class CourseDTO {
    private Long id;
    private String title;
    private boolean isFree;
    private Set<StudentDTO> students;

    public CourseDTO(Long id, String title, boolean isFree, Set<StudentDTO> students) {
        this.id = id;
        this.title = title;
        this.isFree = isFree;
        this.students = students;
    }

    public CourseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
}
