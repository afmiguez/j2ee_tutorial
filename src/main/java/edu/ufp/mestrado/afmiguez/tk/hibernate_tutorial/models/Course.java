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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author AF
 */
@Entity
@Table(name = "Course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_SEQ")
	@SequenceGenerator(name = "COURSE_SEQ", sequenceName = "SEQUENCE_COURSES", allocationSize = 1)
	@Column(name = "id")
	private int id;
	@Column(name = "name", unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "degree_id")
	private Degree degree;

	@ManyToMany
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = {
			@JoinColumn(name = "student_id") })
	private List<Student> students = new ArrayList<>();

	public Course(String name) {
		this.name = name;
	}

	public Course() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void addStudent(Student student) {
		for (Student s : this.students) {
			if (s.getStudentNumber().equals(student.getStudentNumber())) {
				return;
			}
		}
		this.students.add(student);
		return;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.name);
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
		final Course other = (Course) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", degree=" + degree.getName() + ", students=" + students + "]";
	}

}
