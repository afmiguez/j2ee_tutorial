package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Degree;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateDegreeService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.DegreeServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

public class DegreeServiceTest {
	
	private DegreeServiceI dsi=new HibernateDegreeService();
	
	public DegreeServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    	HibernateUtil.getSessionFactory();
    }
    
    @AfterClass
    public static void tearDownClass() {
    	HibernateUtil.closeFactory();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

	@Test
	public void test() {
	
		int count=10;
		String name="name";
        for(int i=0;i<count;i++){
            this.dsi.createDegree(new Degree(i,name+i));
        }
        assertEquals(count,this.dsi.getDegrees().size());
        
        for(int i=0;i<count;i++){
        	this.dsi.createDegree(new Degree(i,name+i));
        }
        assertEquals(count,this.dsi.getDegrees().size());
        
        Degree test=new Degree(0,name+0);
        
        assertEquals(test, this.dsi.getDegreeByName(test.getName()));
        
        for(int i=0;i<count;i++){
            this.dsi.deleteDegree(this.dsi.getDegreeByName(name+i));
        }
        
        assertEquals(0,this.dsi.getDegrees().size());
	}

}
