package de.oth.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.oth.checkout.Checkout;
import de.oth.commit.Commit;
import de.oth.staging.Tree;

/**
 * 
 * @author Dimitri
 *
 */
public class Repository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6991793348956107124L;

	private final String jidPath = "./.jit";
	private final String objectPath = "./.jit/objects";
	private final String stagingPath = "./.jit/staging";

	private final String SerielPath = "./.jit/staging/staging.ser";

	public void init() {

		if (!allreadyinitialized()) {

			File jid = new File(jidPath);
			File objects = new File(objectPath);
			File staging = new File(stagingPath);

			if (jid.mkdir()) {
				System.out.println(jidPath + " Directory  created");
			} else {
				System.out.println(jidPath + " Directory existed already");
			}

			if (objects.mkdir()) {
				System.out.println(objectPath + " Directory  created");
			} else {
				System.out.println(objectPath + " Directory existed already");
			}

			if (staging.mkdir()) {
				System.out.println(stagingPath + " Directory  created");
			} else {
				System.out.println(stagingPath + " Directory existed already");
			}
			
			
		}

		
	}

	/**
	 * 
	 * @param dataPath data Path string that the user would like to add to the
	 *                 staging area
	 */
	public boolean add(String dataPath) {

		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return false;
		}

		if (!validDatapath(dataPath)) {
			System.out.println("Not a valid datapath");
			return false;
		}

		File f = new File(dataPath);
		File staging = new File(SerielPath);

		Tree t = new Tree();

		if (staging.exists()) {

			try {
				t = loadTree();
			} catch (ClassNotFoundException e) {
				
				//e.printStackTrace();
				return false;
			} catch (IOException e) {
				//e.printStackTrace();
				return false;
			}
		}

		t.add(f);

		try {
			saveTree(t);
		} catch (IOException e) {
			
			//e.printStackTrace();
			return false;
		}

		System.out.println("current staging area:");
		t.setupPrintStages();

		return true;
	}

	
	
	/**
	 * 
	 * 
	 * @param dataPath
	 * @return true if method worked correctly
	 */
	public boolean remove(String dataPath) {

		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return false;
		}

		File f = new File(dataPath);

		Tree t = new Tree();

		try {
			t = loadTree();

		} catch (ClassNotFoundException e) {
			
			//e.printStackTrace();
			return false;
			
		} catch (IOException e) {
	
			//e.printStackTrace();
			return false;
		}

		t.remove(f);
		try {
			saveTree(t);

			t.setupPrintStages();

		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}

		return true;
	}

	
	/**
	 * 
	 * @param commit massage that the user made
	 * @return true if function worked properly
	 */
	public boolean commit(String commit) {

		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return false;
		}

		if (!(new File(SerielPath).exists())) {

			System.out.println("Nothing in the Staging Area");

		}

		
		try {
			Tree t = loadTree();

			Commit c = new Commit(commit, new File(objectPath), t);
			c.makeCommit(t.getRootNode());

		} catch (ClassNotFoundException e) {
			
			//e.printStackTrace();

			return false;
		} catch (IOException e) {
			//e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * 
	 * @return true if initialized or false if the user didn't
	 */
	private boolean allreadyinitialized() {

		if (new File(jidPath).exists() && new File(objectPath).exists() && new File(stagingPath).exists()) {

			return true;

		} else {

			return false;
		}

	}

	/**
	 * 
	 * @param dataPath String of data path
	 * @return return true if the user given a data path that exist
	 */
	private boolean validDatapath(String dataPath) {

		File datafile = new File(dataPath);

		if (datafile.exists()) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * save the Tree Object with serialization in .jit/staging/staging.ser
	 * 
	 * @param t the Tree that sould be saved/
	 * @throws IOException
	 */
	private void saveTree(Tree t) throws IOException {

		FileOutputStream fos = new FileOutputStream(SerielPath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(t);

		oos.close();

	}

	/**
	 * 
	 * load/deserialize Tree from .jit/staging/staging.ser
	 * 
	 * @return the loaded Tree
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Tree loadTree() throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(SerielPath);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Tree t = (Tree) ois.readObject();
		ois.close();
		return t;

	}

	
	
	
	public boolean checkout(String hash) {

		Checkout c = new Checkout();
		try {
			c.setupCheckout(hash);
			File staging = new File(SerielPath);
			staging.delete();
			
		} catch (IOException e) {
			
			//e.printStackTrace();
			return false;
			
		}
		
		return true;

	}

	/**
	 * print current Staging area on the console
	 */
	public void printCurrentStaging() {

		if (!new File(SerielPath).exists()) {

			System.out.println("Staging area does't exist yet");
			return;
		}

		Tree t = new Tree();
		try {
			
			t = loadTree();

		} catch (ClassNotFoundException | IOException e) {
			
			//e.printStackTrace();
		}

		t.setupPrintStages();
	}
	
}
