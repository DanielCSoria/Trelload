package ctrl;
import model.Course;
import model.School;
import model.Student;

import java.util.Collections;
import java.util.Set;
public class Ctrl {

    private final School school;

    public Ctrl(School school) {
        this.school = school;
    }

    public void addStudentToCourse(Student student, Course course) {
        if (student != null && course != null)
            school.addStudentToCourse(student, course);
    }

    public void removeStudentFromCourse(Student student, Course course) {
        if (student != null && course != null)
            school.removeStudentFromCourse(student, course);
    }

    public void createStudentAndAddToCourse(String student, Course course) {
        if (student != null && course != null)
            school.createStudentAndAddToCourse(new Student(student), course);
    }

    public boolean btSubscribeHasToBeDisabled(Student student, Course course) {
        return (course == null
                || student == null
                || !school.studentCanBeAdded(student, course));
    }

    public boolean btNewStudentHasToBeDisabled(String student, Course course) {
        if (course == null
                || student == null
                || student.isEmpty()
                || student.isBlank()
                || school.isCourseComplete(course)) return true;
        return school.existsStudent(new Student(student));
    }
}
