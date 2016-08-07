/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import java.util.ArrayList;

/**
 *
 * @author AF
 */
public interface StudentServiceI {
    public Student createStudent(Student student);
    public ArrayList<Student> getStudents();
    public Student getStudentById(int id);
    public Student getStudentByStudentNumber(String studentNumber);
    public Student updateStudent(Student student);
    public Student deleteStudent(Student student);
    
}
