package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

/**
 * Created by Nicholas Tahani on 11/10/2017.
 */
public class TestStudent {

	private IStudent student;
	private IAdmin admin;
	private IInstructor instructor;

	@Before
	public void setup() {
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();
	}

	/**
	 * register for class that is not created by the admin
	 */

	@Test
	public void testRegisteredFor1() {
		this.student = new Student();
		this.admin = new Admin();

		this.student.registerForClass("Student", "Test", 2017);

		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}

	/**
	 * class exists, but the capacity is reached (can we add third student to a
	 * calss with capacity 2)
	 */
	@Test
	public void testRegisteredFor2() {
		this.student = new Student();
		this.admin = new Admin();

		this.admin.createClass("Test", 2017, "Instructor", 2);

		// add two students for this class
		this.student.registerForClass("Student1", "Test", 2017);
		this.student.registerForClass("Student2", "Test", 2017);

		// now add a new student
		this.student.registerForClass("Student", "Test", 2017);

		assertTrue(this.admin.getClassCapacity("Test", 2017) == 3);
	}

	/**
	 * student drops class that is registered for admin has created the course;
	 * student register and then drop the class
	 */
	@Test
	public void testdropClass1() {
		this.student = new Student();
		this.admin = new Admin();

		// create a class
		this.admin.createClass("Test", 2017, "Instructor", 2);

		// register the student
		this.student.registerForClass("Student", "Test", 2017);

		//
		this.student.dropClass("Student", "Test", 2017);

		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}

	/**
	 * the student is registered and the class has not ended (how do we know a class
	 * has ended?) class is not created, but student can drop from the class!
	 */
	@Test
	public void testdropClass2() {
		this.student = new Student();
		this.admin = new Admin();

		this.student.dropClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}

	/**
	 * the student is registered and the class has not ended
	 */
	@Test
	public void testdropClass3() {
		this.student.dropClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}

	/**
	 * provided homework exists, student is registered and the class is taught in
	 * the current year
	 */
	@Test
	public void testSubmitHomeowrk1() {
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();

		// admin create a class
		this.admin.createClass("Test", 2017, "Instructor", 2);

		// instructor add homework
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		// student registers for the course
		this.student.registerForClass("Student", "Test", 2017);
		
		this.student.submitHomework("Student", "homework1", "homework1 solution", "Test", 2017);

		// this is normal operation; check if the normal operation works properly
		assertTrue(this.student.hasSubmitted("Student", "homework1", "Test", 2017));
	}

	/**
	 * provided homework exists, student is registered and the class is not taught in
	 * the current year
	 */
	@Test
	public void testSubmitHomeowrk2() {
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();

		// admin created a class in the previous year
		this.admin.createClass("Test", 2016, "Instructor", 2);

		// instructor add homework
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		// student registers for the course
		this.student.registerForClass("Student", "Test", 2017);
		
		this.student.submitHomework("Student", "homework1", "homework1 solution", "Test", 2017);

		// this is normal operation; check if the normal operation works properly
		assertTrue(this.student.hasSubmitted("Student", "homework1", "Test", 2017));
	}

	/**
	 *  homework does not exist, student is not registered and the 
	 *  class is not taught, but student can submit homework!!
	 */
	@Test
	public void testSubmitHomeowrk3() {
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();

		// admin create a class
		//this.admin.createClass("Test", 2017, "Instructor", 2);

		// instructor add homework
		//this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		// student registers for the course
		//this.student.registerForClass("Student", "Test", 2017);
		
		this.student.submitHomework("Student", "homework1", "homework1 solution", "Test", 2017);

		// this should never happen
		assertFalse(this.student.hasSubmitted("Student", "homework1", "Test", 2017));
	}
	
	/**
	 *  homework does exist, student is not registered and the 
	 *  class is taught, but student can submit homework!!
	 */
	@Test
	public void testSubmitHomeowrk4() {
		this.student = new Student();
		this.admin = new Admin();
		this.instructor = new Instructor();

		// admin create a class
		this.admin.createClass("Test", 2017, "Instructor", 2);

		// instructor add homework
		this.instructor.addHomework("Instructor", "Test", 2017, "homework1");

		// student registers for the course
		//this.student.registerForClass("Student", "Test", 2017);
		
		this.student.submitHomework("Student", "homework1", "homework1 solution", "Test", 2017);

		// this should never happen
		assertFalse(this.student.hasSubmitted("Student", "homework1", "Test", 2017));
	}
}
