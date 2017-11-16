package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nicholas Tahani on 7/11/2017.
 */
public class TestInstructor {

	private IInstructor instructor;
	private IStudent student;
	private IAdmin admin;

	@Before
	public void setup() {
		this.admin = new Admin();
		this.instructor = new Instructor();
		this.student = new Student();
	}

	/**
	 * homework must not exist because it is not assigned
	 */
	@Test
	public void testAddHomework1() {
		assertFalse(this.instructor.homeworkExists("Test", 2017, "homework1"));
	}

	/**
	 * is this instructor assigned to class "Test" that added the homework homework1
	 */
	@Test
	public void testAddHomework2() {
		this.instructor = new Instructor();

		// instructor adds homework
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		assertEquals("Instructor", this.admin.getClassInstructor("Test", 2017));

	}

	@Test
	public void testAddHomework3() {
		this.instructor = new Instructor();
		this.admin = new Admin();
		this.student = new Student();

		// admin creates a class
		this.admin.createClass("Test", 2017, "Instructor", 25);

		// instructor adds a homework
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		// student register for a course and submit a homework
		this.student.registerForClass("StudentName", "Test", 2017);
		this.student.submitHomework("StudentName", "homework1", "hw1 solution", "Test", 2017);

		//
		assertTrue(this.instructor.homeworkExists("Test", 2017, "homework1"));

	}

	/**
	 * normal assigning grade
	 */
	@Test
	public void testAssignGrade1() {
		this.instructor = new Instructor();
		this.admin = new Admin();
		this.student = new Student();

		this.admin.createClass("ClassName", 2017, "Instructor", 20);

		this.student.registerForClass("StudentName", "ClassName", 2017);
		this.instructor.addHomework("Instructor", "ClassName", 2017, "homework1");

		this.student.submitHomework("StudentName", "homework1", "hw1 solution", "ClassName", 2017);
		this.instructor.assignGrade("Instructor", "ClassName", 2017, "homework1", "StudentName", 100);
		assertEquals(new Integer(100), this.instructor.getGrade("ClassName", 2017, "homework1", "StudentName"));
	}
	
	/**
	 * this instructor has been assigned to this class
	 */
	@Test
	public void testAssignGrade2() {
		this.instructor = new Instructor();
		this.admin = new Admin();
		this.student = new Student();

		this.admin.createClass("Test", 2017, "Instructor", 20);

		this.student.registerForClass("StudentName", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		this.student.submitHomework("StudentName", "homework1", "hw1 solution", "Test", 2017);
		
		// different instructor assigns the grade!
		this.instructor.assignGrade("Instructor1", "Test", 2017, "homework1", "StudentName", 100);
		assertEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "homework1", "StudentName"));

		assertEquals("Instructor1", this.admin.getClassInstructor("Test", 2017));
	}
	
	/**
	 * the homework has been assigned 
	 */
	@Test
	public void testAssignGrade3() {
		this.instructor = new Instructor();
		this.admin = new Admin();
		this.student = new Student();

		this.admin.createClass("Test", 2017, "Instructor", 20);

		this.student.registerForClass("StudentName", "Test", 2017);
		
		//homework was not assigned
		this.student.submitHomework("StudentName", "homework1", "hw1 solution", "Test", 2017);
		
		//instructor assign a grade
		this.instructor.assignGrade("Instructor", "Test", 2017, "homework1", "StudentName", 100);

		assertTrue(this.instructor.homeworkExists("Test", 2017, "homework1"));
	}
	
	/**
	 * the student has submitted this homework
	 */
	
	@Test
	public void testAssignGrade4() {
		this.instructor = new Instructor();
		this.admin = new Admin();
		this.student = new Student();

		this.admin.createClass("Test", 2017, "Instructor", 20);
		
		this.student.registerForClass("StudentName", "Test", 2017);

		//instructor assign a grade; note student didn't submit the homework
		this.instructor.assignGrade("Instructor", "Test", 2017, "homework1", "StudentName", 100);
		
		assertEquals(new Integer(100), this.instructor.getGrade("Test", 2017, "homework1", "StudentName"));

	}

}
