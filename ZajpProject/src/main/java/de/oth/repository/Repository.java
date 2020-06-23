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


public class Repository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6991793348956107124L;
	
	private final String jidPath="./.jit";
	private final String objectPath = "./.jit/objects";
	private final String stagingPath = "./.jit/staging";
	
	private final String SerielPath= "./.jit/staging/staging.ser";
	

	// methodes

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

	public void add(String dataPath) {
		
		

		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return;
		}

		if (!validDatapath(dataPath)) {
			System.out.println("Not a valid datapath");
			return;
		}

		File f = new File(dataPath);
		File staging = new File(SerielPath);

		Tree t=new Tree();
		
		if(staging.exists()) {
		
			try {
				t=loadTree();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		t.add(f);
		
		try {
			saveTree(t);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("current staging area:");
		t.printstages();
		
		
	}

	
	public void remove(String dataPath) {
		
		
		
		
		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return;
		}
		
		File f= new File(dataPath);
		
		
		Tree t = new Tree();

		try {
			t=loadTree();

			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.remove(f);
		try {
			saveTree(t);
			
			t.printstages();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
		
	public void commit(String commit) {
		
		if (!allreadyinitialized()) {
			System.out.println("Not instinitialized");
			return;
		}
		
		
		if(!(new File(SerielPath).exists())) {
			
			System.out.println("Nothing in the Staging Area");
			
		}
		
		
		try {
			Tree t=loadTree();
			
			Commit c=new Commit(commit, new File(objectPath), t);
			c.makeCommit(t.getRootNode());
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	
	private boolean allreadyinitialized() {

		if (new File(jidPath).exists() && new File(objectPath).exists() && new File(stagingPath).exists()) {
			return true;
		} else {

			return false;
		}

	}

	private boolean validDatapath(String dataPath) {

		

		File datafile = new File(dataPath);

		if (datafile.exists()) {
			return true;
		}

		return false;

	}

	
	
	
	private void saveTree(Tree t) throws IOException {

		FileOutputStream fos = new FileOutputStream(SerielPath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(t);

		oos.close();

	}
	

	
	
	private Tree loadTree() throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(SerielPath);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Tree t = (Tree) ois.readObject();
		ois.close();
		return t;

	}

	public void checkout(String hash) {
		
		Checkout c=new Checkout();
		try {
			c.setupCheckout(hash);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
