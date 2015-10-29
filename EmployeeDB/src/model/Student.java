package model;

/**
 * The Student class represents a single student.
 * 
 * @author Emma Lynch
 * @version 20151015
 *
 */
public class Student {

	private int studentId;
	private String lastName;
	private String firstName;
	private String resHall;
	private String gradeLevel;
	private String department;

	public Student() {
		studentId = 0;
		lastName = "";
		firstName = "";
		resHall = "";
		gradeLevel = "";
		department = "";
	}

	public Student(int studentId, String lastName, String firstName, String resHall, String gradeLevel, String department) {
		this.studentId = studentId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.resHall = resHall;
		this.gradeLevel = gradeLevel;
		this.department = department;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getResHall() {
		return resHall;
	}

	public void setResHall(String resHall) {
		this.resHall = resHall;
	}

	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return String.format("%5d : %s, %s, %s, %s, %s", this.getStudentId(), this.getLastName(),
				this.getFirstName(), this.getResHall(), this.getGradeLevel(), this.getDepartment());
	}
}