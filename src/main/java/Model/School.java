package model;



import java.util.*;

public class School extends Observable {

    public enum NotificationKind {
        INIT,
        STUDENT_ADDED,
        STUDENT_ADDED_TO_COURSE,
        STUDENT_REMOVED_FROM_COURSE
    }

    public static final int MAX_STUDENTS_PER_COURSE = 5,
            MAX_COURSES_PER_STUDENT = 2;

    private final Set<Student> students = new TreeSet<>();
    private final Set<Course> courses = new TreeSet<>();

    public School() {
        initData();
    }

    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(courses);
    }

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public boolean existsStudent(Student student) {
        return students.contains(student);
    }

    public boolean studentCanBeAdded(Student student, Course course) {
        return !(isCourseComplete(course)
                || isStudentComplete(student)
                || course.existsStudent(student));
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    public boolean createStudentAndAddToCourse(Student student, Course course) {
        if (isCourseComplete(course) || students.contains(student)) return false;
        students.add(student);
        notifyObservers(NotificationKind.STUDENT_ADDED);
        return addStudentToCourse(student, course);
    }

    public boolean addStudentToCourse(Student student, Course course) {
        if (!studentCanBeAdded(student, course)) return false;
        course.addStudent(student);
        notifyObservers(NotificationKind.STUDENT_ADDED_TO_COURSE);
        return true;
    }

    public boolean removeStudentFromCourse(Student student, Course course) {
        boolean ok = course.removeStudent(student);
        if (ok)
            notifyObservers(NotificationKind.STUDENT_REMOVED_FROM_COURSE);
        return ok;
    }

    public boolean isCourseComplete(Course course) {
        return course.nbStudents() >= MAX_STUDENTS_PER_COURSE;
    }

    private boolean isStudentComplete(Student student) {
        int nbcours = 0;
        for (Course c : getCourses()) {
            if (c.existsStudent(student)) ++nbcours;
        }
        return nbcours >= MAX_COURSES_PER_STUDENT;
    }

    private void initData() {
        Student delphine = new Student("Delphine"),
                caro = new Student("Caroline"),
                eddy = new Student("Eddy"),
                mohamed = new Student("Mohamed"),
                bernard = new Student("Bernard"),
                amelie = new Student("Amélie");

        students.add(delphine);
        students.add(caro);
        students.add(eddy);
        students.add(mohamed);
        students.add(bernard);
        students.add(amelie);

        Course anc3 = new Course("ANC3"),
                prwb = new Course("PRWB"),
                pro2 = new Course("PRO2");

        courses.add(anc3);
        courses.add(prwb);
        courses.add(pro2);

        anc3.addStudent(delphine);
        anc3.addStudent(amelie);
        anc3.addStudent(bernard);

        pro2.addStudent(mohamed);
        pro2.addStudent(caro);
        pro2.addStudent(delphine);
        pro2.addStudent(bernard);
        pro2.addStudent(eddy);

        prwb.addStudent(amelie);
        prwb.addStudent(eddy);
        prwb.addStudent(caro);

    }
}
