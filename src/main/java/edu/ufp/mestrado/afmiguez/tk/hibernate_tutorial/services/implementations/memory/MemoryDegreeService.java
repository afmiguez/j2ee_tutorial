package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory;

import java.util.ArrayList;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Degree;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.DegreeServiceI;

public class MemoryDegreeService implements DegreeServiceI {
	private final ArrayList<Degree> degrees = new ArrayList<>();

	@Override
	public Degree createDegree(Degree degree) {
		for (Degree d : this.degrees) {
			if (d.equals(degree)) {
				return d;
			}
		}
		
		MemoryCourseService mcs=new MemoryCourseService();
		
		for (Course c : degree.getCourses()) {
			mcs.createCourse(c);
			System.out.println(mcs.getCourses().size());
		}
		this.degrees.add(degree);
		
		
		return degree;
	}

	@Override
	public ArrayList<Degree> getDegrees() {
		return degrees;
	}

	@Override
	public Degree getDegreeById(int id) {
		for (Degree d : this.degrees) {
			if (d.getId() == id) {
				return d;
			}
		}
		return null;
	}

	@Override
	public Degree getDegreeByName(String name) {
		for (Degree d : this.degrees) {
			if (d.equals(name)) {
				return d;
			}
		}
		return null;
	}

	@Override
	public Degree updateDegree(Degree degree) {
		for (Degree d : this.degrees) {
			if (d.getName().equals(degree.getName())) {
				int i = this.degrees.indexOf(d);
				this.degrees.remove(d);
				this.degrees.add(i, degree);
				return degree;
			}
		}
		return null;
	}

	@Override
	public Degree deleteDegree(Degree degree) {
		System.out.println("teste");
		if (this.degrees.remove(degree)) {
			return degree;
		}
		return null;
	}

}
