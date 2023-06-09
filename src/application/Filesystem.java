package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Filesystem {

	public ArrayList<String> loadNames() {
		ArrayList<String> names = new ArrayList<>();
		try {
			Scanner scan = new Scanner(new File("names.txt"));
			while (scan.hasNext()) {
				String name = scan.nextLine().trim();
				names.add(name);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return names;
	}
}
