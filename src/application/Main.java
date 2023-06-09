package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends Application {
	private BinarySearchTree binarySearchTree;
	private TreeModel treeModel;
	private Label dataSize;
	private String currentItemSelected;
	private ObservableList<String> dataList;
	private ObservableList<String> resultList;

	@Override
	public void start(Stage primaryStage) {

		binarySearchTree = new BinarySearchTree();
		treeModel = new TreeModel();
		dataSize = new Label();
		dataList = treeModel.getDataList();
		resultList = treeModel.getResultList();

		Pane root = new VBox(5);
		root.getChildren().addAll(this.ButtonUI(), this.menuUI(), this.resultsUI());
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public VBox ButtonUI() {
		HBox buttonBox = new HBox(50);
		buttonBox.setStyle("-fx-background-color: CADETBLUE");

		Button dataGeneratorButton = new Button("Generate Data Set");
		Button loadDataButton = new Button("Load Data file");
		Button clearButton = new Button("CLEAR");
		dataGeneratorButton.setStyle("-fx-background-color: CADETBLUE;-fx-border-color: BLACK");
		loadDataButton.setStyle("-fx-background-color: CADETBLUE;-fx-border-color: BLACK");
		clearButton.setStyle("-fx-background-color: CADETBLUE;-fx-border-color: BLACK");
		buttonBox.getChildren().addAll(dataGeneratorButton, loadDataButton, clearButton);

		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton byFirstNameButton = new RadioButton("BST ordered by first name");
		RadioButton bySurnameButton = new RadioButton("BST ordered by surname");
		RadioButton byAgeButton = new RadioButton("BST ordered by age");
		byFirstNameButton.setToggleGroup(toggleGroup);
		bySurnameButton.setToggleGroup(toggleGroup);
		byAgeButton.setToggleGroup(toggleGroup);

		HBox searchMode = new HBox(20);
		searchMode.getChildren().addAll(byFirstNameButton, bySurnameButton, byAgeButton);
		VBox button = new VBox(5);
		button.getChildren().addAll(buttonBox, searchMode);

		dataGeneratorButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {

					int size = 0;
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Generate Random Dataset");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter an integer for data size:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						size = Integer.parseInt(result.get());
						treeModel.getDataGenerator().generateRandomPeople(size);
					}
				} catch (NullPointerException e) {
					System.out.println("No input and action");
				} catch (NumberFormatException n) {
					System.out.println("Please enter an integer");
				}
			}
		});

		loadDataButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				treeModel.buildTree();
				dataSize.setText(Integer.toString(dataList.size() ) + " items");
				resultList.clear();
				byFirstNameButton.setSelected(false);
				bySurnameButton.setSelected(false);
				byAgeButton.setSelected(false);

			}

		});

		byFirstNameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (byFirstNameButton.isSelected()) {
					binarySearchTree.setRoot(treeModel.getTreeByFirstName().getRoot());
				}
			}
		});

		bySurnameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (bySurnameButton.isSelected()) {
					binarySearchTree.setRoot(treeModel.getTreeByLastName().getRoot());
				}
			}
		});

		byAgeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (byAgeButton.isSelected()) {
					binarySearchTree.setRoot(treeModel.getTreeByAge().getRoot());
				}
			}
		});

		clearButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CLEAR");
				alert.setHeaderText(null);
				alert.setContentText("Do you want to initialise the window view?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					dataList.clear();
					resultList.clear();
					treeModel.getTreeByFirstName().setRoot(null);
					treeModel.getTreeByLastName().setRoot(null);
					treeModel.getTreeByAge().setRoot(null);
					binarySearchTree.setRoot(null);
					byFirstNameButton.setSelected(false);
					bySurnameButton.setSelected(false);
					byAgeButton.setSelected(false);
					dataSize.setText(Integer.toString(dataList.size()));
					if (dataList.size() == 0 && resultList.size() == 0) {
						dataList.add("Please load data file");
						resultList.add("No binary search tree display");
					}
				}

			}
		});

		return button;
	}

	public MenuBar menuUI() {
		MenuBar bar = new MenuBar();
		Menu print = new Menu("Print Trees");
		MenuItem inOrder = new MenuItem("In-order");
		MenuItem preOrder = new MenuItem("Pre-order");
		MenuItem postOrder = new MenuItem("Post-order");
		MenuItem breadthFirst = new MenuItem("Breadth-first");
		print.getItems().addAll(preOrder, inOrder, postOrder, breadthFirst);

		Menu filter = new Menu("Filter");
		MenuItem fullNameLength = new MenuItem("Full name length");
		MenuItem surnameLength = new MenuItem("Surname length");
		MenuItem surnameVsFirstName = new MenuItem("Surname Vs FirstName");
		filter.getItems().addAll(fullNameLength, surnameLength, surnameVsFirstName);

		Menu search = new Menu("Search");
		MenuItem findPeopleByage = new MenuItem("Find people by age");
		MenuItem findPeopleByLastName = new MenuItem("Find people by last name");
		search.getItems().addAll(findPeopleByage, findPeopleByLastName);

		Menu update = new Menu("Update Person's information");
		MenuItem updateName = new MenuItem("Update Person's name");
		MenuItem updateAge = new MenuItem("Update Person's age");
		update.getItems().addAll(updateName, updateAge);

		Menu bstOption = new Menu("BST Option");
		MenuItem checkBalance = new MenuItem("Is BST balanced?");
		MenuItem deleteNode = new MenuItem("Delete node in BST");
		MenuItem balanceTree = new MenuItem("Balance Tree");
		bstOption.getItems().addAll(deleteNode,checkBalance,balanceTree);

		bar.getMenus().addAll(print, filter, search, update, bstOption);
		
		balanceTree.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resultList.clear();
				Boolean isBalance = binarySearchTree.isBalanced(binarySearchTree.getRoot());
				if(isBalance != true) {
					Node<Person> root = binarySearchTree.buildBalancedTree(binarySearchTree.getRoot());
					binarySearchTree.setRoot(root);
					resultList.add("***Balanced Tree updated");
				}else {
					resultList.add("***This tree is already balanced");
				}
			}
		});

		deleteNode.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {

					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Disconnect person from BST");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter person's unique ID (e.g. 1001):");

					Optional<String> search = dialog.showAndWait();
					if (search.isPresent()) {
						resultList.clear();
						int iD = Integer.parseInt(search.get());
						Node<Person> foundNode = treeModel.findPersonNode(binarySearchTree.getRoot(), iD);
						treeModel.getTreeByFirstName().deleteNode(iD);
						treeModel.getTreeByLastName().deleteNode(iD);
						treeModel.getTreeByAge().deleteNode(iD);
						resultList.add("***Person disconnected from tree");
						resultList.add(foundNode.getObject().toString());
					}
				} catch (NumberFormatException n) {
					System.out.println("Please enter an integer");
				}
			}
		});

		checkBalance.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Boolean balanced = binarySearchTree.isBalanced(binarySearchTree.getRoot());
				resultList.clear();
				if (balanced) {
					resultList.add("This tree is balanced");
				} else {
					resultList.add("This tree is unbalanced");
				}

			}
		});

		updateName.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				try {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Update Person's information");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter person's unique ID (e.g. 1001):");

					Optional<String> search = dialog.showAndWait();
					if (search.isPresent()) {
						resultList.clear();
						int iD = Integer.parseInt(search.get());
						Node<Person> foundNode = treeModel.findPersonNode(binarySearchTree.getRoot(), iD);
						resultList.add(foundNode.getObject().toString());

						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Person Information");
						alert.setHeaderText("Found person's information: " + foundNode.getObject().toString());
						alert.setContentText("Do you want to update the name?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							Dialog<Pair<String, String>> updateName = new Dialog<>();
							dialog.setTitle("Update person's name");
							dialog.setHeaderText("Please enter the new updated First name and Surname.");
							dialog.setResizable(true);

							Label FirstName = new Label("First name: ");
							Label Surname = new Label("Surname: ");
							TextField FirstNameText = new TextField();
							TextField SurameText = new TextField();

							GridPane grid = new GridPane();
							grid.add(FirstName, 1, 1);
							grid.add(FirstNameText, 2, 1);
							grid.add(Surname, 1, 2);
							grid.add(SurameText, 2, 2);
							updateName.getDialogPane().setContent(grid);

							ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
							updateName.getDialogPane().getButtonTypes().add(buttonTypeOk);
							updateName.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
								@Override
								public Pair<String, String> call(ButtonType b) {

									if (b == buttonTypeOk) {

										return new Pair<String, String>(FirstNameText.getText(), SurameText.getText());
									}
									return null;
								}
							});

							Optional<Pair<String, String>> update = updateName.showAndWait();

							if (update.isPresent()) {
								foundNode.getObject().setFirstName(update.get().getKey());
								foundNode.getObject().setLastName(update.get().getValue());
								resultList.add("***Information updated");
								resultList.add(foundNode.getObject().toString());
								treeModel.getTreeByFirstName().deleteNode(iD);
								treeModel.getTreeByLastName().deleteNode(iD);
								treeModel.getTreeByAge().deleteNode(iD);
								treeModel.getTreeByFirstName().insertByFirstName(foundNode);
								treeModel.getTreeByLastName().insertBySurname(foundNode);
								treeModel.getTreeByAge().insertByAge(foundNode);
							}

						}
					}
				} catch (NumberFormatException n) {
					System.out.println("Please enter an integer");
				} catch (NullPointerException e) {
					System.out.println("Please select BST toggle button");
				}
			}
		});

		updateAge.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Update Person's information");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter person's unique ID (e.g. 1001):");

					Optional<String> search = dialog.showAndWait();
					if (search.isPresent()) {
						resultList.clear();
						int iD = Integer.parseInt(search.get());
						Node<Person> foundNode = treeModel.findPersonNode(binarySearchTree.getRoot(), iD);
						resultList.add(foundNode.getObject().toString());

						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Person Information");
						alert.setHeaderText("Found person's information: " + foundNode.getObject().toString());
						alert.setContentText("Do you want to update the age?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							dialog.setTitle("Update Person's age");
							dialog.setHeaderText(null);
							dialog.setContentText("Please enter new age:");

							Optional<String> updateAge = dialog.showAndWait();
							if (updateAge.isPresent()) {
								int newAge = Integer.parseInt(updateAge.get());
								foundNode.getObject().setAge(newAge);
								resultList.add("***Information updated");
								resultList.add(foundNode.getObject().toString());
								treeModel.getTreeByFirstName().deleteNode(iD);
								treeModel.getTreeByLastName().deleteNode(iD);
								treeModel.getTreeByAge().deleteNode(iD);
								treeModel.getTreeByFirstName().insertByFirstName(foundNode);
								treeModel.getTreeByLastName().insertBySurname(foundNode);
								treeModel.getTreeByAge().insertByAge(foundNode);
							}
						}
					}
				} catch (NumberFormatException n) {
					System.out.println("Please enter an integer");
				} catch (NullPointerException e) {
					System.out.println("Please select BST toggle button");
				}
			}

		});

		findPeopleByage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Find people with same age");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter an integer for age between 1-100:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						int age = Integer.parseInt(result.get());
						resultList.clear();
						treeModel.findPeopleByAge(binarySearchTree.getRoot(), age);
					}
				} catch (NumberFormatException n) {
					System.out.println("Please enter an integer");
				}
			}
		});

		findPeopleByLastName.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Find people with same Last Name");
					dialog.setHeaderText(null);
					dialog.setContentText("Please enter the Last Name:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						resultList.clear();
						treeModel.findPeopleByLaststname(binarySearchTree.getRoot(), result.get());
					}
				} catch (NumberFormatException n) {
					System.out.println("Please enter a string");
				}
			}
		});

		surnameVsFirstName.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Find people with surname longer/shorter than first name");
				alert.setHeaderText(null);
				alert.setContentText("Choose your option.");

				ButtonType surnameLongerFirst = new ButtonType("Surname longer than first name");
				ButtonType surnameShorterFirst = new ButtonType("Surname shorter than first name");
				ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(surnameLongerFirst, surnameShorterFirst, buttonTypeCancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == surnameLongerFirst) {
					resultList.clear();
					treeModel.SurnameLongerFirstName(binarySearchTree.getRoot());
				} else if (result.get() == surnameShorterFirst) {
					resultList.clear();
					treeModel.SurnameShorterFirstName(binarySearchTree.getRoot());
				} else {

				}
			}
		});

		surnameLength.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					Dialog<Pair<Integer, String>> dialog = new Dialog<>();
					dialog.setResizable(true);
					dialog.setTitle("Find people with longer/shorter surname length");
					dialog.setHeaderText("Please enter the surname length and select longer/shorter mode");
					DialogPane dialogPane = dialog.getDialogPane();
					dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
					dialogPane.setMinWidth(400);
					TextField textField = new TextField("Please enter an integer");

					ObservableList<String> options = FXCollections.observableArrayList("Longer", "Shorter");
					ComboBox<String> comboBox = new ComboBox<>(options);

					dialogPane.setContent(new VBox(8, textField, comboBox));
					Platform.runLater(textField::requestFocus);
					dialog.setResultConverter(new Callback<ButtonType, Pair<Integer, String>>() {
						@Override
						public Pair<Integer, String> call(ButtonType dialogButton) {

							if (dialogButton == ButtonType.OK) {
								int length = Integer.parseInt(textField.getText());
								return new Pair<Integer, String>(length, comboBox.getValue());
							}

							return null;
						}
					});
					Optional<Pair<Integer, String>> result = dialog.showAndWait();
					if (result.isPresent()) {
						if (result.get().getValue().equals("Longer")) {
							resultList.clear();
							treeModel.SurnameLonger(binarySearchTree.getRoot(), result.get().getKey());
							if (resultList.size() == 0) {
								resultList.add("People not found with Surname longer than the input value");
							}
						} else {
							resultList.clear();
							treeModel.SurnameShorter(binarySearchTree.getRoot(), result.get().getKey());
							if (resultList.size() == 0) {
								resultList.add("People not found with Surname shorter than the input value");
							}
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter an integer");
				}

			}
		});

		fullNameLength.setOnAction(new EventHandler<ActionEvent>() {
			/*
			 * Reference:https://stackoverflow.com/questions/31556373/javafx-dialog-with-2-
			 * input-fields
			 */
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Dialog<Pair<Integer, String>> dialog = new Dialog<>();
					dialog.setResizable(true);
					dialog.setTitle("Find people with longer/shorter name length");
					dialog.setHeaderText("Please enter the name length and select longer/shorter mode");
					DialogPane dialogPane = dialog.getDialogPane();
					dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
					dialogPane.setMinWidth(400);
					TextField textField = new TextField("Please enter an integer");

					ObservableList<String> options = FXCollections.observableArrayList("Longer", "Shorter");
					ComboBox<String> comboBox = new ComboBox<>(options);

					dialogPane.setContent(new VBox(8, textField, comboBox));
					Platform.runLater(textField::requestFocus);
					dialog.setResultConverter(new Callback<ButtonType, Pair<Integer, String>>() {
						@Override
						public Pair<Integer, String> call(ButtonType dialogButton) {

							if (dialogButton == ButtonType.OK) {
								int length = Integer.parseInt(textField.getText());
								return new Pair<Integer, String>(length, comboBox.getValue());
							}

							return null;
						}
					});
					Optional<Pair<Integer, String>> result = dialog.showAndWait();
					if (result.isPresent()) {
						if (result.get().getValue().equals("Longer")) {
							resultList.clear();
							treeModel.fullNameLonger(binarySearchTree.getRoot(), result.get().getKey());
							if (resultList.size() == 0) {
								resultList.add("People not found with full-name longer than the input value");
							}
						} else {
							resultList.clear();
							treeModel.fullNameShorter(binarySearchTree.getRoot(), result.get().getKey());
							if (resultList.size() == 0) {
								resultList.add("People not found with full-name shorter than the input value");
							}
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter an integer");
				}

			}
		});

		inOrder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resultList.clear();
				resultList.add("***Printing sorted tree (in-order depth-first):");
				treeModel.inOrderDepthFirst(binarySearchTree.getRoot());
			}
		});

		preOrder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resultList.clear();
				resultList.add("***Printing sorted tree (pre-order depth-first):");
				treeModel.preOrderDepthFirst(binarySearchTree.getRoot(), "-");
			}
		});

		postOrder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resultList.clear();
				resultList.add("***Printing sorted tree (post-order depth-first):");
				treeModel.postOrderDepthFirst(binarySearchTree.getRoot());
			}
		});

		breadthFirst.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				resultList.clear();
				resultList.add("***Printing sorted tree (breadth-first):");
				treeModel.breadthFirst(binarySearchTree.getRoot());
			}
		});

		return bar;
	}

	public HBox resultsUI() {
		HBox view = new HBox(20);
		view.setAlignment(Pos.CENTER);
		ListView<String> resultsView = new ListView<String>();
		resultsView.setItems(resultList);
		resultsView.setPrefWidth(300);
		ListView<String> dataView = new ListView<String>();
		dataView.setItems(dataList);
		dataView.setPrefWidth(300);
		Label dataViewHeader = new Label(
				"Imported data set, loading data will stack." + "\nClick CLEAR button to initialise the window view");
		dataViewHeader.setFont(new Font("Arial", 12));
		dataViewHeader.setStyle("-fx-text-fill: TEAL");

		dataSize.setText(Integer.toString(dataList.size() ) + " items");
		dataSize.setStyle("-fx-text-fill: TEAL");
		dataList.add("Please load data file");
		resultList.add("No binary search tree display");
		VBox vBox = new VBox();
		vBox.getChildren().addAll(dataViewHeader, dataView, dataSize);

		view.getChildren().addAll(vBox, resultsView);

		/*
		 * Reference:https://stackoverflow.com/questions/53028313/how-can-i-change-the-
		 * background-color-of-only-the-first-cell-in-listview-in-java
		 */
		resultsView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							// There is no item to display in this cell, so leave it empty
							setGraphic(null);

							// Clear the style from the cell
							setStyle(null);
							setText(null);
						} else {
							if (item.startsWith("***")) {
								setStyle("-fx-background-color: CADETBLUE; -fx-text-fill: yellow");
							}
							setText(item);
						}
					}
				};
			}
		});

		resultsView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent click) {

				if (click.getClickCount() == 2) {
					// Use ListView's getSelected Item
					currentItemSelected = resultsView.getSelectionModel().getSelectedItem();
					System.out.println(currentItemSelected);
				}
			}
		});

		dataView.setCellFactory(list -> {
			ListCell<String> cell = new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
						setStyle(null);
					} else {
						if (item.equalsIgnoreCase(list.getItems().get(0))) {
							setStyle("-fx-background-color: CADETBLUE; -fx-text-fill: yellow");
						}
						setText(item);
					}
				}
			};
			return cell;
		});
		return view;

	}

	public static void main(String[] args) {
		launch(args);
	}
}
