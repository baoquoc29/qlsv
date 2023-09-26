package com.example.qlsv;

public class Students {
    private String studentId;
    private String name;
    private String studentClass;
    private String major;

    public Students(String studentId, String name, String studentClass, String major) {
        this.studentId = studentId;
        this.name = name;
        this.studentClass = studentClass;
        this.major = major;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return studentId + " " + name + " " + studentClass + " " + major;
    }
}

