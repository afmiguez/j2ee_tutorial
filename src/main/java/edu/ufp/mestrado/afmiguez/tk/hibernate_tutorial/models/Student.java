/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author AF
 */
@Entity
@Table(name = "Student")
public class Student extends Person {

	@Column(name = "student_number", unique = true)
	private String studentNumber;

	@ManyToMany
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = {
			@JoinColumn(name = "course_id") })
	private List<Course> courses = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "degree_id")
	private Degree degree;

	public Student(String firstName, String lastName, String studentNumber) {
		super(firstName, lastName);
		this.studentNumber = studentNumber;
	}

	public Student() {
	}

	public int getId() {
		return super.getId();
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.studentNumber);
		hash = 89 * hash + Objects.hashCode(this.courses);
		hash = 89 * hash + Objects.hashCode(this.degree);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Student other = (Student) obj;
		if (!Objects.equals(this.studentNumber, other.studentNumber)) {
			return false;
		}
		if (!Objects.equals(this.courses, other.courses)) {
			return false;
		}
		if (!Objects.equals(this.degree, other.degree)) {
			return false;
		}
		return true;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "Student [studentNumber=" + studentNumber + ", courses=" + this.coursesString() + ", degree="
				+ degree.getName() + "]";
	}

	private String coursesString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		int count = this.courses.size();
		int i = 0;
		for (Course c : this.courses) {

			sb.append(c.getName());
			if (i < count - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append("]");

		return sb.toString();
	}

	public void addCourse(Course course) {
		if (course == null) {
			return;
		}
		for (Course c : this.courses) {
			if (c.getName().equals(course.getName())) {
				return;
			}
		}
		course.addStudent(this);
		this.courses.add(course);
		return;

	}

}
