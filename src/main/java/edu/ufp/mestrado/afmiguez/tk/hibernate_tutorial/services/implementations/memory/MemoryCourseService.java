/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.CourseServiceI;
import java.util.ArrayList;


/**
 *
 * @author AF
 */
public class MemoryCourseService implements CourseServiceI {

	private final ArrayList<Course> courses = new ArrayList<>();

	@Override
	public Course createCourse(Course course) {
		for (Course c : this.courses) {
			if (c.equals(course)) {
				return c;
			}
		}
		this.courses.add(course);
		return course;
	}

	@Override
	public ArrayList<Course> getCourses() {
		return this.courses;
	}

	@Override
	public Course getCourseById(int id) {
		for (Course c : this.courses) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Course getCourseByName(String name) {
		for (Course c : this.courses) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Course updateCourse(Course course) {
		for (Course c : this.courses) {
			if (c.getName().equals(course.getName())) {
				int i = this.courses.indexOf(c);
				this.courses.remove(c);
				this.courses.add(i, course);
				return course;
			}
		}
		return null;
	}

	@Override
	public Course deleteCourse(Course course) {
		System.out.println("testeCourse");
		if (this.courses.remove(course)) {
			return course;
		}
		return null;
	}

	@Override
	public ArrayList<Student> getStudentsByCourse(String name) {
		return (ArrayList<Student>) getCourseByName(name).getStudents();		
	}

}
