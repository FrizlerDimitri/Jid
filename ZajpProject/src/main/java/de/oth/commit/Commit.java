package de.oth.commit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.oth.hashing.Hashable;
import de.oth.staging.Node;
import de.oth.staging.Tree;

/**
 * 
 * @author Dimitri
 *
 */

public class Commit extends Hashable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -651696428928779157L;

	private String commitment;
	private final File objectDir;
	private Tree treeToCommit;

	public Commit(String commitment, File objectDir, Tree treeToCommit) {
		this.commitment = commitment;
		this.objectDir = objectDir;
		this.treeToCommit = treeToCommit;
	}



	public void makeCommit(Node n) {

		Node currentNode = n;

		String CurrentName = currentNode.SHA1HashValue();

		try {
			PrintWriter writer = new PrintWriter(objectDir.getPath() + "/" + CurrentName);

			if (currentNode.equals(treeToCommit.getRootNode())) {
				writer.println("Commit " + commitment);
			} else if (currentNode.getData().isDirectory()) {

				writer.println("Directory");

			}

			if (currentNode.getData().isDirectory()) {
				for (int i = 0; i < currentNode.getChildNodes().size(); i++) {

					String filetyp = "";
					if (currentNode.getChildNodes().get(i).getData().isDirectory()) {
						filetyp = "Directory ";
					} else {

						filetyp = "File ";
					}

					writer.println(filetyp + currentNode.getChildNodes().get(i).SHA1HashValue() + " "
							+ currentNode.getChildNodes().get(i).getData().getName());

					makeCommit(currentNode.getChildNodes().get(i));

				}
			} else

			{
				Path source = Paths.get(currentNode.getData().getPath());

				FileOutputStream out = new FileOutputStream(objectDir.getPath() + "/" + CurrentName);
				Files.copy(source, out);

			}

			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

}
