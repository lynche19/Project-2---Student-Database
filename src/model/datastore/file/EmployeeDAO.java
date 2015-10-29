package model.datastore.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Employee;
import model.IEmployeeDAO;

/**
 * @author John Phillips
 * @version 20151009
 * 
 */
public class EmployeeDAO implements IEmployeeDAO {

	protected String fileName = null;
	protected final List<Employee> myList;

	public EmployeeDAO() {
		Properties props = new Properties();
		FileInputStream fis = null;

		// read the properties file
		try {
			fis = new FileInputStream("res/file/db.properties");
			props.load(fis);
			this.fileName = props.getProperty("DB_FILENAME");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.myList = new ArrayList<>();
		try {
			Files.createFile(Paths.get(fileName));
		} catch (FileAlreadyExistsException fae) {
			;
		} catch (IOException ioe) {
			System.out.println("Create file error with " + ioe.getMessage());
		}
		readList();
	}

	@Override
	public void createRecord(Employee employee) {
		myList.add(employee);
		writeList();
	}

	@Override
	public Employee retrieveRecordById(int id) {
		for (Employee employee : myList) {
			if (employee.getEmpId() == id) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public List<Employee> retrieveAllRecords() {
		return myList;
	}

	@Override
	public void updateRecord(Employee updatedEmployee) {
		for (Employee employee : myList) {
			if (employee.getEmpId() == updatedEmployee.getEmpId()) {
				employee.setLastName(updatedEmployee.getLastName());
				employee.setFirstName(updatedEmployee.getFirstName());
				employee.setHomePhone(updatedEmployee.getHomePhone());
				employee.setSalary(updatedEmployee.getSalary());
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(int id) {
		for (Employee employee : myList) {
			if (employee.getEmpId() == id) {
				myList.remove(employee);
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(Employee employee) {
		myList.remove(employee);
		writeList();
	}

	protected void readList() {
		Path path = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int id = Integer.parseInt(data[0]);
				String last = data[1];
				String first = data[2];
				String homePhone = data[3];
				double salary = Double.parseDouble(data[4]);
				Employee employee = new Employee(id, last, first, homePhone, salary);
				myList.add(employee);
			}
		} catch (IOException ioe) {
			System.out.println("Read file error with " + ioe.getMessage());
		}
	}

	protected void writeList() {
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (Employee employee : myList) {
				writer.write(String.format("%d,%s,%s,%s,%.2f\n", employee.getEmpId(), employee.getLastName(),
						employee.getFirstName(), employee.getHomePhone(), employee.getSalary()));
			}
		} catch (IOException ioe) {
			System.out.println("Write file error with " + ioe.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Employee employee : myList) {
			sb.append(String.format("%5d : %s, %s, %s, %.2f\n", employee.getEmpId(), employee.getLastName(),
					employee.getFirstName(), employee.getHomePhone(), employee.getSalary()));
		}

		return sb.toString();
	}
}
