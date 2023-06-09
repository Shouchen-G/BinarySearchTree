package application;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TreeModel {
	private BinarySearchTree treeByFirstName;
	private BinarySearchTree treeByLastName;
	private BinarySearchTree treeByAge;
	private DataGenerator dataGenerator;
	private ObservableList<String> dataList;
	private ObservableList<String> resultList;

	public TreeModel() {
		this.dataGenerator = new DataGenerator();
		this.resultList = FXCollections.observableArrayList();
		this.dataList = FXCollections.observableArrayList();
		treeByFirstName = new BinarySearchTree();
		treeByLastName = new BinarySearchTree();
		treeByAge = new BinarySearchTree();
	}

	public DataGenerator getDataGenerator() {
		return dataGenerator;
	}

	public ObservableList<String> getResultList() {
		return resultList;
	}

	public BinarySearchTree getTreeByFirstName() {
		return treeByFirstName;
	}

	public BinarySearchTree getTreeByLastName() {
		return treeByLastName;
	}

	public BinarySearchTree getTreeByAge() {
		return treeByAge;
	}

	public ObservableList<String> getDataList() {
		return dataList;
	}

	public void buildTree() {
		this.dataGenerator.loadData(this.treeByFirstName, this.treeByLastName, this.treeByAge, this.dataList);

	}

	public void setTreeByFirstName(BinarySearchTree treeByFirstName) {
		this.treeByFirstName = treeByFirstName;
	}

	public void setTreeByLastName(BinarySearchTree treeByLastName) {
		this.treeByLastName = treeByLastName;
	}

	public void setTreeByAge(BinarySearchTree treeByAge) {
		this.treeByAge = treeByAge;
	}

	public void inOrderDepthFirst(Node<Person> root) {

		if (root != null) {
			inOrderDepthFirst(root.getLeft());
			this.resultList.add(root.getObject().toString());
			inOrderDepthFirst(root.getRight());
		}
	}

	public void preOrderDepthFirst(Node<Person> root, String label) {
		if (root != null) {
			this.resultList.add(label + root.getObject().toString());
			preOrderDepthFirst(root.getLeft(), "  " + label);
			preOrderDepthFirst(root.getRight(), "  " + label);
		}
	}

	public void postOrderDepthFirst(Node<Person> root) {
		if (root != null) {
			postOrderDepthFirst(root.getLeft());
			postOrderDepthFirst(root.getRight());
			this.resultList.add(root.getObject().toString());
		}
	}

	public void breadthFirst(Node<Person> root) {
		Queue<Node<Person>> todo = new ArrayDeque<>();
		todo.offer(root);
		while (!todo.isEmpty()) {
			Node<Person> node = todo.poll();
			this.resultList.add(node.getObject().toString());
			if (node.getLeft() != null) {
				todo.offer(node.getLeft());
			}
			if (node.getRight() != null) {
				todo.offer(node.getRight());
			}
		}
	}

	public Node<Person> findPersonNode(Node<Person> node, int id) {
		if (node != null) {
			if (node.getObject().getUniqueID() == id) {
				return node;
			} else {
				Node<Person> foundNode = findPersonNode(node.getLeft(), id);
				if (foundNode != null) {
					return foundNode;
				}
				return foundNode = findPersonNode(node.getRight(), id);
			}
		} else {
			return null;
		}
	}

	public void findPeopleByLaststname(Node<Person> node, String name) {
		if (node != null) {
			if ((node.getObject().getLastName().equalsIgnoreCase(name))) {
				resultList.add(node.getObject().toString());
			}
			findPeopleByLaststname(node.getLeft(), name);
			findPeopleByLaststname(node.getRight(), name);
		}
	}

	public void findPeopleByAge(Node<Person> node, int age) {
		if (node != null) {
			if ((node.getObject().getAge() == age)) {
				resultList.add(node.getObject().toString());
			}
			findPeopleByAge(node.getLeft(), age);
			findPeopleByAge(node.getRight(), age);
		}
	}

	public void fullNameLonger(Node<Person> root, int fullNameLength) {
		if (root != null) {
			if ((root.getObject().getFirstName().length() + root.getObject().getLastName().length()) > fullNameLength) {
				this.resultList.add(root.getObject().toString());
			}
			fullNameLonger(root.getRight(), fullNameLength);
			fullNameLonger(root.getLeft(), fullNameLength);
		}
	}

	public void fullNameShorter(Node<Person> root, int fullNameLength) {
		if (root != null) {
			if ((root.getObject().getFirstName().length() + root.getObject().getLastName().length()) < fullNameLength) {
				this.resultList.add(root.getObject().toString());
			}
			fullNameShorter(root.getRight(), fullNameLength);
			fullNameShorter(root.getLeft(), fullNameLength);
		}
	}

	public void SurnameLonger(Node<Person> root, int surnameLength) {
		if (root != null) {
			if (root.getObject().getLastName().length() > surnameLength) {
				this.resultList.add(root.getObject().toString());
			}
			SurnameLonger(root.getRight(), surnameLength);
			SurnameLonger(root.getLeft(), surnameLength);
		}
	}

	public void SurnameShorter(Node<Person> root, int surnameLength) {
		if (root != null) {
			if (root.getObject().getLastName().length() < surnameLength) {
				this.resultList.add(root.getObject().toString());
			}
			SurnameShorter(root.getRight(), surnameLength);
			SurnameShorter(root.getLeft(), surnameLength);
		}
	}

	public void SurnameShorterFirstName(Node<Person> root) {
		if (root != null) {
			if (root.getObject().getLastName().length() < root.getObject().getFirstName().length()) {
				this.resultList.add(root.getObject().toString());
			}
			SurnameShorterFirstName(root.getRight());
			SurnameShorterFirstName(root.getLeft());
		}
	}

	public void SurnameLongerFirstName(Node<Person> root) {
		if (root != null) {
			if (root.getObject().getLastName().length() > root.getObject().getFirstName().length()) {
				this.resultList.add(root.getObject().toString());
			}
			SurnameLongerFirstName(root.getRight());
			SurnameLongerFirstName(root.getLeft());
		}
	}

	public void drawNodeRecursive(Pane drawField, int x1, int y1, int x, int y, Node<Person> node) {
		Line line = new Line(x1, y1 + 5, x, y);
		drawField.getChildren().add(line);
		Circle circle = new Circle(x, y, 15, Paint.valueOf("blue"));
		drawField.getChildren().add(circle);
		Text txt = new Text(x - 3, y + 3, String.valueOf(node.getObject().getFirstName()));
		drawField.getChildren().add(txt);
		if (node.getLeft() != null)
			drawNodeRecursive(drawField, x, y, x - (30), y + 30, node.getLeft());
		if (node.getRight() != null)
			drawNodeRecursive(drawField, x, y, x + (30), y + 30, node.getRight());
	}

}
