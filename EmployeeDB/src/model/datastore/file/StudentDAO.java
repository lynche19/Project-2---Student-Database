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

import model.Student;
import model.IStudentDAO;

/**
 * @author Emma Lynch
 * @version 20151009
 * 
 */
public class StudentDAO implements IStudentDAO {

	protected String fileName = null;
	protected final List<Student> myList;

	public StudentDAO() {
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
	public void createRecord(Student student) {
		myList.add(student);
		writeList();
	}

	@Override
	public Student retrieveRecordById(int id) {
		for (Student student : myList) {
			if (student.getStudentId() == id) {
				return student;
			}
		}
		return null;
	}

	@Override
	public Student retrieveRecordByResHall(String resHall) {
		for (Student student : myList) {
			if (student.getResHall() == resHall) {
				return student;
			}
		}
		return null;
	}
	
	@Override
	public Student retrieveRecordByGradeLevel(String gradeLevel) {
		for (Student student : myList) {
			if (student.getGradeLevel() == gradeLevel) {
				return student;
			}
		}
		return null;
	}
	
	@Override
	public List<Student> retrieveAllRecords() {
		return myList;
	}

	@Override
	public void updateRecord(Student updatedStudent) {
		for (Student student : myList) {
			if (student.getStudentId() == updatedStudent.getStudentId()) {
				student.setLastName(updatedStudent.getLastName());
				student.setFirstName(updatedStudent.getFirstName());
				student.setResHall(updatedStudent.getResHall());
				student.setGradeLevel(updatedStudent.getGradeLevel());
				student.setDepartment(updatedStudent.getDepartment());
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(int id) {
		for (Student student : myList) {
			if (student.getStudentId() == id) {
				myList.remove(student);
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(Student student) {
		myList.remove(student);
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
				String resHall = data[3];
				String gradeLevel = data[4];
				String department = data[5];
				Student student = new Student(id, last, first, resHall, gradeLevel, department);
				myList.add(student);
			}
		} catch (IOException ioe) {
			System.out.println("Read file error with " + ioe.getMessage());
		}
	}

	protected void writeList() {
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (Student student : myList) {
				writer.write(String.format("%d,%s,%s,%s,%s,%s", student.getStudentId(), student.getLastName(),
						student.getFirstName(), student.getResHall(), student.getGradeLevel(), student.getDepartment()));
			}
		} catch (IOException ioe) {
			System.out.println("Write file error with " + ioe.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Student student : myList) {
			sb.append(String.format("%5d : %s, %s, %s, %s, %s", student.getStudentId(), student.getLastName(),
					student.getFirstName(), student.getResHall(), student.getGradeLevel(), student.getDepartment()));
		}

		return sb.toString();
	}
}
