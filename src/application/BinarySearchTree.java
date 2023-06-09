package application;

import java.util.Vector;

public class BinarySearchTree {
	private Node<Person> root;

	public BinarySearchTree() {

	}

	public Node<Person> getRoot() {
		return root;
	}

	public void setRoot(Node<Person> root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void insertByFirstName(Node<Person> value) {
		if (isEmpty()) {
			root = value;
		} else {
			insertByFirstName(root, value);
		}
	}

	public void insertByFirstName(Node<Person> root, Node<Person> value) {
		if ((value.getObject().getFirstName().compareTo(root.getObject().getFirstName())) <= 0) {
			if (root.getLeft() == null) {
				root.setLeft(value);
			} else {
				insertByFirstName(root.getLeft(), value);
			}
		} else {
			if (root.getRight() == null) {
				root.setRight(value);
			} else {
				insertByFirstName(root.getRight(), value);
			}
		}
	}

	public void insertBySurname(Node<Person> value) {
		if (isEmpty()) {
			root = value;
		} else {
			insertBySurname(root, value);
		}
	}

	public void insertBySurname(Node<Person> root, Node<Person> value) {

		if (value.getObject().getLastName().compareTo(root.getObject().getLastName()) <= 0) {
			if (root.getLeft() == null) {
				root.setLeft(value);
			} else {
				insertBySurname(root.getLeft(), value);
			}
		} else {
			if (root.getRight() == null) {
				root.setRight(value);
			} else {
				insertBySurname(root.getRight(), value);
			}
		}
	}

	public void insertByAge(Node<Person> value) {
		if (isEmpty()) {
			root = value;
		} else {
			insertByAge(root, value);
		}
	}

	public void insertByAge(Node<Person> root, Node<Person> value) {
		if (value.getObject().getAge() < root.getObject().getAge()) {
			if (root.getLeft() == null) {
				root.setLeft(value);
			} else {
				insertByAge(root.getLeft(), value);
			}
		} else {
			if (root.getRight() == null) {
				root.setRight(value);
			} else {
				insertByAge(root.getRight(), value);
			}
		}
	}
	
	public void deleteNode(int id) {
		if (root != null) {
			if (root.getObject().getUniqueID() == id) {
				root = null;
			} else {
				deleteNodeRec(root, id);
			}
		}
	}

	public void deleteNodeRec(Node<Person> node, int id) {

		if (node.getLeft() != null && node.getLeft().getObject().getUniqueID() == id) {
			node.setLeft(null);
			return;
		}

		if (node.getRight() != null && node.getRight().getObject().getUniqueID() == id) {
			node.setRight(null);
			;
			return;
		}

		if (node.getLeft() != null) {
			deleteNodeRec(node.getLeft(), id);
		}

		if (node.getRight() != null) {
			deleteNodeRec(node.getRight(), id);
		}
	}

	/*
	 * Reference: https://stackoverflow.com/questions/742844/how-to-determine-if-binary-tree-is-balanced
	 */
	public int dfHeight(Node<Person> root) {
		if (root == null) {
			return 0;
		}
		int left = dfHeight(root.getLeft());
		if (left == -1) {
			return -1;
		}
		int right = dfHeight(root.getRight());
		if (left == -1) {

			return -1;
		}
		if (Math.abs(left - right) > 1) {

			return -1;
		} else {
			return Math.max(left, right) + 1;
		}
	}

	public boolean isBalanced(Node<Person> root) {
		return dfHeight(root) != -1;
	}

	/*
	 * Reference:https://www.geeksforgeeks.org/convert-normal-bst-balanced-bst/
	 */
	  public Node<Person> buildBalancedTree(Node<Person> root)
	    {
	        // Store nodes of given BST in sorted order
	        Vector<Node<Person>> nodes = new Vector<Node<Person>>();
	        storeBSTNodes(root, nodes);
	 
	        // Constructs BST from nodes[]
	        int n = nodes.size();
	        return buildTreeUtil(nodes, 0, n - 1);
	    }
	  public void storeBSTNodes(Node<Person> root, Vector<Node<Person>> nodes)
	    {
	        // Base case
	        if (root == null)
	            return;
	 
	        // Store nodes in Inorder (which is sorted
	        // order for BST)
	        storeBSTNodes(root.getLeft(), nodes);
	        nodes.add(root);
	        storeBSTNodes(root.getRight(), nodes);
	    }
	 
	    /* Recursive function to construct binary tree */
	  public Node<Person> buildTreeUtil(Vector<Node<Person>> nodes, int start,
	            int end)
	    {
	        // base case
	        if (start > end)
	            return null;
	 
	        /* Get the middle element and make it root */
	        int mid = (start + end) / 2;
	        Node<Person> node = nodes.get(mid);
	 
	        /* Using index in Inorder traversal, construct
	           left and right subtress */
	        node.setLeft(buildTreeUtil(nodes, start, mid - 1));
	        node.setRight(buildTreeUtil(nodes, mid + 1, end));
	 
	        return node;
	    }

	@Override
	public String toString() {
		return this.root.getObject().toString();
	}

}
