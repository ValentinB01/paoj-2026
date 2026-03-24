package com.pao.laboratory04.service;

import com.pao.laboratory04.exception.StudentNotFoundException;
import com.pao.laboratory04.model.Student;
import com.pao.laboratory04.model.Subject;

import java.util.*;

public class StudentService {
    private List<Student> students;

    private StudentService() {
        this.students = new ArrayList<>();
    }

    private static class Holder {
        private static final StudentService INSTANCE = new StudentService();
    }

    public static StudentService getInstance() {
        return Holder.INSTANCE;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul cu numele '" + name + "' exista deja.");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul '" + name + "' nu a fost gasit.");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenți inregistrați.");
            return;
        }
        for (Student s : students) {
            System.out.println(s + " | Note: " + s.getGrades());
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu exista studenti inregistrati.");
            return;
        }
        List<Student> sortedList = new ArrayList<>(students);
        sortedList.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        System.out.println(" Top Studenți ");
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.println((i + 1) + ". " + sortedList.get(i));
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> averages = new HashMap<>();
        Map<Subject, List<Double>> allGrades = new HashMap<>();

        for (Student s : students) {
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                Subject subj = entry.getKey();
                Double grade = entry.getValue();

                allGrades.putIfAbsent(subj, new ArrayList<>());
                allGrades.get(subj).add(grade);
            }
        }

        for (Map.Entry<Subject, List<Double>> entry : allGrades.entrySet()) {
            double sum = 0;
            for (double g : entry.getValue()) {
                sum += g;
            }
            averages.put(entry.getKey(), sum / entry.getValue().size());
        }

        return averages;
    }
}