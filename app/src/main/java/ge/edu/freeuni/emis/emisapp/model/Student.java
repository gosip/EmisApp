package ge.edu.freeuni.emis.emisapp.model;

import android.graphics.Bitmap;

/**
 * Created by giorgi on 7/7/15.
 */
public class Student {
    // profile picture
    private Bitmap profileImg;
    // major specialty, for instance computer science
    private String major;
    // expected degree - Masters, Bachelor...
    private String expectedDegree;
    private int currSemester;
    private String gender;
    private String nationality;
    // birth date saved as a string in format like: Y-M-D 1994-09-07
    private String birthDate;
    // active or not
    private String status;
    private String schoolName;
    private int numCredits;
    private double GPA;
    private String phoneNumber;

    // not overriding constructor, using setters and chaining instead
    public Student setProfileImg(Bitmap profileImg) {
        this.profileImg = profileImg;
        // for chaining
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
    public Student setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    // public getters
    public Bitmap getProfileImg() {
        return profileImg;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
