package application;

public class Person {
	private String firstName;
	private String lastName;
	private int age;
	private int uniqueID;
	public static int idAuto = 1001;

	public Person(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.uniqueID = idAuto++;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return uniqueID + " " + firstName + " " + lastName + " " + age;
	}

}
