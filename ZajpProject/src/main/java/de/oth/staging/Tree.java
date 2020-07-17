package de.oth.staging;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Dimitri
 *
 */
public class Tree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3015711689497306391L;

	public Node rootNode;
	private final String rootPath = "./";

	public Tree() {

		this.rootNode = new Node(new File(rootPath));

	}

	/**
	 * 
	 * @return the root Node of the Tree
	 */
	public Node getRootNode() {
		return rootNode;
	}

	/**
	 * 
	 * @param file that that the user would like to add
	 */
	public void add(File file) {

		Node parentNode = rootNode;
		List<File> filelist = FileStructureToList(file);

		for (int i = 0; i < filelist.size(); i++) {

			Node newChildNode = new Node(filelist.get(i));

			boolean inlist = false;
			for (int j = 0; j < parentNode.getChildNodes().size(); j++) {
				if (parentNode.getChildNodes().get(j).equals(newChildNode)) {

					newChildNode.setParentNode(parentNode.getChildNodes().get(j));
					parentNode = parentNode.getChildNodes().get(j);
					inlist = true;
				}

			}

			if (!inlist) {
				newChildNode.setParentNode(parentNode);
				parentNode.addChild(newChildNode);
				Collections.sort(parentNode.getChildNodes());
				parentNode = newChildNode;
				// parentNode.getChildNodes().sort();

			}

		}

	}

	/**
	 * 
	 * create from a given file a list of files for every subdirectory
	 * 
	 * @param f the whole link to the file
	 * @return the list of all files with there subdirectories
	 */
	private List<File> FileStructureToList(File f) {

		List<File> filelist = new ArrayList<File>();

		while (f != null) {

			filelist.add(f);

			f = f.getParentFile();

		}
		Collections.reverse(filelist);
		return filelist;

	}

	/**
	 * 
	 * remove a file from the Tree and delete all empty folders
	 * 
	 * @param f the file that sould be removed
	 */
	public void remove(File f) {

		List<File> filelist = FileStructureToList(f);
		Node currenNode = rootNode;

		boolean inStagingArea = false;

		for (int i = 0; i < filelist.size(); i++) {

			for (int j = 0; j < currenNode.getChildNodes().size(); j++) {

				if (currenNode.getChildNodes().get(j).getData().equals(filelist.get(i)) && i != (filelist.size() - 1)) {
					currenNode = currenNode.getChildNodes().get(j);
				} else if (currenNode.getChildNodes().get(j).getData().equals(filelist.get(i))
						&& i == (filelist.size() - 1)) {

					currenNode.getChildNodes().remove(j);

					inStagingArea = true;
					// clean empty dirs

					while (!currenNode.equals(rootNode)) {
						if (currenNode.getChildNodes() == null || currenNode.getChildNodes().size() == 0) {

							Node parentNode = currenNode.getParentNode();

							parentNode.getChildNodes().remove(currenNode);

							currenNode = parentNode;
						} else {

							currenNode = currenNode.getParentNode();

						}
					}

				}

			}

		}

		if (!inStagingArea) {
			System.out.println("File not in staging area");
		}

	}

	/**
	 * Setup the Tree to print the current staging area on console
	 */
	public void setupPrintStages() {

		System.out.println("+--  " + rootNode.getData().getName() /*+"   Hash Code : " +rootNode.SHA1HashValue()*/ );
		printStages(rootNode.getChildNodes(), "");
	}

	/**
	 * 
	 * print recursively the Staging Tree
	 * @param childs all sub files and directories in the current Node that should print aswell
	 * @param s  String for the whitespaces
	 */
	private void printStages(List<Node> childs, String s) {

		if (childs == null || childs.size() == 0) {
			return;
		}

		s += "     ";

		for (int i = 0; i < childs.size(); i++) {

			System.out.println(s + "+--  "
					+ childs.get(i).getData().getName() /*+"   Hash Code : "+childs.get(i).SHA1HashValue()*/ );
			printStages(childs.get(i).getChildNodes(), s);
		}

	}

}
