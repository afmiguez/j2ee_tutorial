package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces;

import java.util.ArrayList;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Degree;

public interface DegreeServiceI {
	public Degree createDegree(Degree degree);
    public ArrayList<Degree> getDegrees ();
    public Degree getDegreeById(int id);
    public Degree getDegreeByName(String name);
    public Degree updateDegree(Degree degree);
    public Degree deleteDegree(Degree degree);

}
