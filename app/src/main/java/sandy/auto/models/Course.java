package sandy.auto.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isFree;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    // Constructors
    public Course() {
    }

    public Course(String title, boolean isFree) {
        this.title = title;
        this.isFree = isFree;
    }

    // Getters and Setters
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    // Methods to manage bidirectional relationship
    public void addStudent(Student student) {
        this.students.add(student);
        student.setCourse(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setCourse(null);
    }
}

