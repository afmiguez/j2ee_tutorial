/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author AF
 */
@Entity
@Table(name = "Degree")
public class Degree {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEGREE_SEQ")
	@SequenceGenerator(name = "DEGREE_SEQ", sequenceName = "SEQUENCE_DEGREES", allocationSize = 1)
	@Column(name = "id")
	private int id;

	@Column(name = "name", unique = true)
	private String name;

	@OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
	private List<Course> courses = new ArrayList<>();

	// @OneToMany(mappedBy = "degree",cascade=CascadeType.ALL)
	// private Set<Student> students=new HashSet<>();

	public Degree(String name) {
		this.name = name;
	}

	public Degree() {
	}

	public Degree(int id, String name) {
		this.id = id;
		this.name = name;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	// public Set<Student> getStudents() {
	// return students;
	// }
	//
	// public void setStudents(Set<Student> students) {
	// this.students = students;
	// }

	public void addCourse(Course course) {
		for (Course c : this.courses) {
			if (c.getName().equals(course.getName())) {
				return;
			}
		}
		course.setDegree(this);
		this.courses.add(course);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + Objects.hashCode(this.name);
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
		final Degree other = (Degree) obj;

		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Degree [id=" + id + ", name=" + name + ", courses=" + courses + "]";
	}

}
