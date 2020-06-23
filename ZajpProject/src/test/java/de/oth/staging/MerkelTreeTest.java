package de.oth.staging;

import java.io.File;
import java.io.IOException;

import de.oth.commit.Commit;

import org.junit.Test;

import de.oth.checkout.Checkout;

public class MerkelTreeTest {

	@Test
	public void test() {

		Tree t = new Tree();
		System.out.println("add srcTest/main/de/A.java");

		t.add(new File("srcTest/main/de/A.java"));

		t.printstages();

		System.out.println("add srcTest/main/de/B.java ");

		t.add(new File("srcTest/main/de/B.java"));

		t.printstages();

		System.out.println("add srcTest/main/de/oth/C.java");
		t.add(new File("srcTest/main/de/oth/C.java"));
		t.printstages();
	
		
		
		
		
		

		/*
		 * System.out.println("remove srcTest/main/de/A.java");
		 * 
		 * t.remove(new File("srcTest/main/de/A.java")); t.printstages();
		 */

		/*
		 * System.out.println("remove srcTest/main/de/B.java");
		 * 
		 * t.remove(new File("srcTest/main/de/B.java")); t.printstages();
		 */

		/*
		 * System.out.println("remove srcTest/main/de/oth/C.java");
		 * 
		 * t.remove(new File("srcTest/main/de/oth/C.java")); t.printstages();
		 */

		
		
		
		  Commit c=new Commit("Das ist ein commitment", new File("./.jit/objects") , t); 
		  c.makeCommit(t.getRootNode());
		 
		
		  
		  Checkout checkout= new Checkout();
		  try {
			checkout.setupCheckout("123b0ecd3bed6b88ff8ffdc94ec320b9399c435a");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		 

	}

}
