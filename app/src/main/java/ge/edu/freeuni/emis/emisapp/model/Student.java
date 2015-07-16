package ge.edu.freeuni.emis.emisapp.model;

import android.graphics.Bitmap;

/**
 * Created by giorgi on 7/7/15.
 */
public class Student {
    private String studentName;
    // major specialty, for instance computer science
    private String major;
    // expected degree - Masters, Bachelor...
    private String expectedDegree;
    private int currSemester;
    private String gender;
    private String nationality;
    // birth date saved as a string in format like: Y-M-D 1994-09-07
    private String birthDate;
    private String address;
    // active or not
    private String status;
    private String schoolName;
    private int numCredits;
    private double GPA;

    // not overriding constructor, using setters and chaining instead
    public Student setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }
    public Student setMajor(String major) {
        this.major = major;
        return this;
    }
    public Student setExpectedDegree(String expectedDegree) {
        this.expectedDegree = expectedDegree;
        return this;
    }
    public Student setCurrSemester(int currSemester) {
        this.currSemester = currSemester;
        return this;
    }
    public Student setGender(String gender) {
        this.gender = gender;
        return this;
    }
    public Student setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }
    public Student setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }
    public Student setAddress(String address) {
        this.address = address;
        return this;
    }
    public Student setStatus(String status) {
        this.status = status;
        return this;
    }
    public Student setSchoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }
    public Student setNumCredits(int numCredits) {
        this.numCredits = numCredits;
        return this;
    }
    public Student setGPA(double GPA) {
        this.GPA = GPA;
        return this;
    }

    // public getters
    public String getStudentName() {
        return studentName;
    }
    public String getMajor() {
        return major;
    }
    public String getExpectedDegree() {
        return expectedDegree;
    }
    public int getCurrSemester() {
        return currSemester;
    }
    public String getGender() {
        return gender;
    }
    public String getNationality() {
        return nationality;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getAddress() {
        return address;
    }
    public String getStatus() {
        return status;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public int getNumCredits() {
        return numCredits;
    }
    public double getGPA() {
        return GPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (currSemester != student.currSemester) return false;
        if (numCredits != student.numCredits) return false;
        if (Double.compare(student.GPA, GPA) != 0) return false;
        if (studentName != null ? !studentName.equals(student.studentName) : student.studentName != null)
            return false;
        if (major != null ? !major.equals(student.major) : student.major != null) return false;
        if (expectedDegree != null ? !expectedDegree.equals(student.expectedDegree) : student.expectedDegree != null)
            return false;
        if (gender != null ? !gender.equals(student.gender) : student.gender != null) return false;
        if (nationality != null ? !nationality.equals(student.nationality) : student.nationality != null)
            return false;
        if (birthDate != null ? !birthDate.equals(student.birthDate) : student.birthDate != null)
            return false;
        if (address != null ? !address.equals(student.address) : student.address != null)
            return false;
        if (status != null ? !status.equals(student.status) : student.status != null) return false;
        return !(schoolName != null ? !schoolName.equals(student.schoolName) : student.schoolName != null);

    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", major='" + major + '\'' +
                ", expectedDegree='" + expectedDegree + '\'' +
                ", currSemester=" + currSemester +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", numCredits=" + numCredits +
                ", GPA=" + GPA +
                '}';
    }
}
