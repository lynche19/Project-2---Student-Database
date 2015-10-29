package model;

import java.util.List;

/**
 * @author Emma Lynch
 * @version 20151009
 *
 */
public interface IStudentDAO {

	void createRecord(Student student);

	Student retrieveRecordById(int id);
	
	Student retrieveRecordByResHall(String resHall);
	
	Student retrieveRecordByGradeLevel(String gradeLevel);

	List<Student> retrieveAllRecords();

	void updateRecord(Student updatedStudent);

	void deleteRecord(int id);

	void deleteRecord(Student student);

	String toString();

}