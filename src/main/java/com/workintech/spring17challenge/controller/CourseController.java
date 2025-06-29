package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.model.LowCourseGpa;
import com.workintech.spring17challenge.model.MediumCourseGpa;
import com.workintech.spring17challenge.model.HighCourseGpa;
import com.workintech.spring17challenge.exceptions.CourseAlreadyExistsException;
import com.workintech.spring17challenge.exceptions.InvalidCourseCreditException;
import com.workintech.spring17challenge.exceptions.CourseNotFoundException;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {
    private final List<Course> courses;
    private final LowCourseGpa lowCourseGpa;
    private final MediumCourseGpa mediumCourseGpa;
    private final HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.courses = new ArrayList<>();
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init() {
        courses.clear();
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courses;
    }

    @GetMapping("/courses/{name}")
    public Course getCourseByName(@PathVariable String name) {
        return courses.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course with name '" + name + "' not found."));
    }

    @PostMapping("/courses")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public CourseResponse addCourse(@RequestBody Course course) {
        // Null check
        if (course.getName() == null || course.getCredit() == null || course.getGrade() == null) {
            throw new InvalidCourseCreditException("Course name, credit and grade cannot be null.");
        }
        
        if (courses.stream().anyMatch(c -> c.getName().equalsIgnoreCase(course.getName()))) {
            throw new CourseAlreadyExistsException("Course with name '" + course.getName() + "' already exists.");
        }
        if (course.getCredit() < 0 || course.getCredit() > 4) {
            throw new InvalidCourseCreditException("Course credit must be between 0 and 4.");
        }
        courses.add(course);
        int totalGpa = calculateTotalGpa(course);
        return new CourseResponse(course, totalGpa);
    }

    @PutMapping("/courses/{id}")
    public CourseResponse updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        Course existingCourse = courses.stream()
                .filter(c -> c.getId() != null && c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found."));
        
        if (updatedCourse.getCredit() == null || updatedCourse.getCredit() < 0 || updatedCourse.getCredit() > 4) {
            throw new InvalidCourseCreditException("Course credit must be between 0 and 4.");
        }
        
        // Update the existing course
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setCredit(updatedCourse.getCredit());
        existingCourse.setGrade(updatedCourse.getGrade());
        
        int totalGpa = calculateTotalGpa(existingCourse);
        return new CourseResponse(existingCourse, totalGpa);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable int id) {
        Course courseToDelete = courses.stream()
                .filter(c -> c.getId() != null && c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found."));
        
        courses.remove(courseToDelete);
    }

    @DeleteMapping("/courses/reset")
    public void resetCourses() {
        courses.clear();
    }

    private int calculateTotalGpa(Course course) {
        if (course.getCredit() == null || course.getGrade() == null || course.getGrade().getCoefficient() == null) {
            return 0;
        }
        int credit = course.getCredit();
        int coefficient = course.getGrade().getCoefficient();
        if (credit <= 2) {
            return coefficient * credit * lowCourseGpa.getGpa();
        } else if (credit == 3) {
            return coefficient * credit * mediumCourseGpa.getGpa();
        } else if (credit == 4) {
            return coefficient * credit * highCourseGpa.getGpa();
        }
        return 0;
    }

    public static class CourseResponse {
        private Course course;
        private int totalGpa;
        public CourseResponse(Course course, int totalGpa) {
            this.course = course;
            this.totalGpa = totalGpa;
        }
        public Course getCourse() { return course; }
        public int getTotalGpa() { return totalGpa; }
    }
} 