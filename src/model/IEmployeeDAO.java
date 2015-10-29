package model;

import java.util.List;

/**
 * @author John Phillips
 * @version 20151009
 *
 */
public interface IEmployeeDAO {

	void createRecord(Employee employee);

	Employee retrieveRecordById(int id);

	List<Employee> retrieveAllRecords();

	void updateRecord(Employee updatedEmployee);

	void deleteRecord(int id);

	void deleteRecord(Employee employee);

	String toString();

}