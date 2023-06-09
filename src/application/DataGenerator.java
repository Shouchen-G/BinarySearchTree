package application;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DataGenerator {
	private Filesystem file = new Filesystem();

	public DataGenerator() {
	}

	public void generateRandomPeople(int size) {
		ArrayList<String> names = file.loadNames();

		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TEXT files (*.txt)", "*.txt"));

			PrintStream stream = new PrintStream(fileChooser.showSaveDialog(null));
			for (int i = 0; i < size; i++) {
				int randomIndex = (int) (Math.random() * 499);
				String randomName = names.get(randomIndex);

				int age = ((int) (Math.random() * 100) + 1);
				String person = randomName + " " + age;
				stream.println(person);
			}
			stream.close();
		} catch (IndexOutOfBoundsException | FileNotFoundException e) {
			System.out.println("Input number should between 1-500");
		}
	}

	public void loadData(BinarySearchTree treeByFirstName, BinarySearchTree treebyLastName, BinarySearchTree treebyAge,
			ObservableList<String> dataList) {
		try {
			FileChooser fileChooser = new FileChooser();
			Scanner scan = new Scanner(fileChooser.showOpenDialog(null));
			while (scan.hasNext()) {
				String firstName = scan.next();
				String lastName = scan.next();
				int age = scan.nextInt();
				scan.nextLine();
				Person person = new Person(firstName, lastName, age);
				dataList.addAll(person.toString());
				Node<Person> firstNameNode = new Node<Person>(person);
				Node<Person> surnameNode = new Node<Person>(person);
				Node<Person> ageNode = new Node<Person>(person);
				treeByFirstName.insertByFirstName(firstNameNode);
				treebyLastName.insertBySurname(surnameNode);
				treebyAge.insertByAge(ageNode);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
