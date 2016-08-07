/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;

import java.util.ArrayList;

/**
 *
 * @author AF
 */
public interface CourseServiceI {
    public Course createCourse(Course course);
    public ArrayList<Course> getCourses();
    public Course getCourseById(int id);
    public Course getCourseByName(String name);
    public Course updateCourse(Course course);
    public Course deleteCourse(Course course);
    public ArrayList<Student> getStudentsByCourse(String name);
}
