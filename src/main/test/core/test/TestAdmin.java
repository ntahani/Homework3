package core.test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Nicholas Tahani
 * Since 11/7/2017.
 * Admin class specification imposes constraints:
 * 1. className/year pair must be unique
 * 2. no instructor can be assigned to more than two courses in a year
 */
public class TestAdmin {
    private IStudent student;
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    /**
     * create a class and check if it exists
     */
    @Test
    public void testMakeClass() {
        this.admin = new Admin();

        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    
    /**
     * check if a class exists even if it is not created by admin
     */
    @Test
    public void testMakeClass2() {
        this.admin = new Admin();

        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    /**
     * test if changeCapacity() works!
     */
    @Test
    public void testCapacity() {
        this.admin = new Admin();

        // set capacity to 15
        this.admin.createClass("Test", 2016, "Instructor", 15);
        
        //is capacity 15?
        assertEquals(15, this.admin.getClassCapacity("Test", 2016));
    }
    
    /**
     * test the changeCapacity() with new capacity
     */
    @Test
    public void testCapacity2() {
        this.admin = new Admin();

        this.admin.createClass("Test", 2016, "Instructor", 15);
        this.admin.changeCapacity("Test", 2016, 5);
        assertEquals(5, this.admin.getClassCapacity("Test", 2016));

    }
    
    /**
     * must be at least equal to the # of students
     */
    @Test
    public void testChangeCapacity() {
        this.student = new Student();
        this.admin = new Admin();
        // set capacity to 3
        this.admin.createClass("Test", 2017, "Instructor", 3);

        // register 4 students
        this.student.registerForClass("John", "Test", 2017);
        this.student.registerForClass("Jane", "Test", 2017);
        this.student.registerForClass("Joe", "Test", 2017);
        this.student.registerForClass("Jim", "Test", 2017);

        assertTrue(this.admin.getClassCapacity("Test", 2017) >= 4);

    }
    
    /**
     * is class instructor correct
     */
    @Test
    public void testGetClassInstructor() {
        this.admin = new Admin();

        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));

    }
    
    /**
     * can we assign an instructor to more than two classes per year?
     */
    @Test
    public void testNumberOfCoursesPerInstructor() {
        this.admin = new Admin();

    	int year = 2017;
        this.admin.createClass("Test1", year, "Instructor", 15);
        this.admin.createClass("Test2", year, "Instructor", 15);
        this.admin.createClass("Test3", year, "Instructor", 15);
        
        int count =0;
        String [] classes = {"Test1", "Test2", "Test3"};
        for (String c : classes) {
        	if ("Instructor".equalsIgnoreCase(this.admin.getClassInstructor(c, year))) {
        		count++;
        	}
        }
        
        assertEquals(2, count);
    }
    
    /**
     * test for class uniqueness
     */
    @Test
    public void testclassUniqueness() {
        this.admin = new Admin();
    	int year = 2017;

        this.admin.createClass("Test", year, "Instructor", 15);
        this.admin.createClass("Test", year, "Instructor1", 50);

        String instructor = this.admin.getClassInstructor("Test", year);
        String instructor1 = this.admin.getClassInstructor("Test", year);
        
        int cap1 = this.admin.getClassCapacity("Test", year);
        int cap2 = this.admin.getClassCapacity("Test", year);
        
        System.out.println(instructor + " " + instructor1 + " " + cap1 + " " + cap2);
        assertEquals("Instructor", instructor1);
        
    }
}
