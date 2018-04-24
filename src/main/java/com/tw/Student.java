package com.tw;

import java.io.Serializable;
import java.util.Map;

public class Student implements Serializable{
    public static final String SEPARATOR = "|";
    private String name;
    private int id;
    private int math;
    private int chinese;
    private int english;
    private int programming;
    private double average;
    private int summary;

    public Student() {
    }

    public void add(String name, int id, Map<String,Integer> map) {
        this.name = name;
        this.id = id;
        this.math = map.get("数学") == null ? 0 : map.get("数学");
        this.chinese = map.get("语文") == null ? 0 : map.get("语文");
        this.english = map.get("英语") == null ? 0 : map.get("英语");
        this.programming = map.get("编程") == null ? 0 : map.get("编程");
        setSummary();
        setAverage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAverage() {
        average = getSummary() / (double)4;
    }

    public double getAverage() {
        return average;
    }

    public void setSummary() {
        summary = math + chinese + english + programming;
    }

    public int getSummary() {
        return summary;
    }

    public String printStudentInfo() {
        return name + SEPARATOR + math + SEPARATOR + chinese + SEPARATOR + english + SEPARATOR + programming + SEPARATOR + average + SEPARATOR + summary;
    }

}
