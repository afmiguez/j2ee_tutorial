/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.StudentServiceI;
import java.util.ArrayList;

/**
 *
 * @author AF
 */
public class MemoryStudentService implements StudentServiceI{
    
    private final ArrayList<Student> students=new ArrayList<>();

    @Override
    public Student createStudent(Student student) {
        for(Student s:this.students){
            if(s.equals(student)){
                return s;
            }
        }
        this.students.add(student);
        return student;
    }

    @Override
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    @Override
    public Student getStudentById(int id) {
        for(Student s:this.students){
            if(s.getId()==id){
                return s;
            }
        }
        return null;
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        for(Student s:this.students){
            if(s.getStudentNumber().equals(studentNumber)){
                return s;
            }
        }
        return null;
    }

    @Override
    public Student updateStudent(Student student) {
        for(Student s:this.students){
            if(s.getStudentNumber().equals(student.getStudentNumber())){
                int i=this.students.indexOf(s);
                this.students.remove(s);
                this.students.add(i, student);
                return student;
            }
        }
        return null;
    }

    @Override
    public Student deleteStudent(Student student) {
    	System.out.println("testStudent");
        if(this.students.remove(student)){
            return student;
        }
        return null;
    }   
}
