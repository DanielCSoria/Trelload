package model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Course implements Comparable<Course> {

    private final String name;
    private final Set<Student> subscribedStudents = new TreeSet<>();

    // Méthodes package private => la vue ne peut les utiliser
    Course(String name) {
        this.name = name;
    }

    boolean addStudent(Student student) {
        return subscribedStudents.add(student);
    }

    int nbStudents() {
        return subscribedStudents.size();
    }

    boolean removeStudent(Student student) {
        return subscribedStudents.remove(student);
    }

    boolean existsStudent(Student student) {
        return subscribedStudents.contains(student);
    }

    // Interface publique
    public final Set<Student> getStudents() {
        return Collections.unmodifiableSet(subscribedStudents);
    }

    public final String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return name != null ? name.equals(course.name) : course.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(Course o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
