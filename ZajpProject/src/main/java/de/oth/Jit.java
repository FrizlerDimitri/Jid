package de.oth;

import de.oth.repository.Repository;

/**
 * 
 * @author Dimitri
 *
 */

public class Jit {
	
	
	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Too few arguments");

		} else if (args.length > 2) {
			System.out.println("Too many arguments");
		} else {

			String command = args[0];

			if (command.equalsIgnoreCase("init")) {
				init();

			} else if (command.equalsIgnoreCase("add")) {
				
				  add(args[1]);
				 

			} else if (command.equalsIgnoreCase("remove")) {
				
				 remove(args[1]);
			} else if(command.equalsIgnoreCase("commit"))
			{
				
				commit(args[1]);
				
			} else if(command.equalsIgnoreCase("checkout")) {
				
				checkout(args[1]);
				
			} else {
				System.out.println(command + " is not a valid command!");
				return;
			}

		}

	}

	
	private static void checkout(String hash) {
		System.out.println("checkout started");
		Repository r=new Repository();
		r.checkout(hash);
		System.out.println("checkout done");
		
		
		
	}


	private static void commit(String commitMassage) {
		System.out.println("commit started");
		Repository r=new Repository();
		r.commit(commitMassage);
		System.out.println("commit done");
	}

	private static void init() {

		System.out.println("init started");

		Repository r = new Repository();

		r.init();

		System.out.println("init done");

	}
		
	private static void add(String dataPath) {
		System.out.println("add started");
		Repository r = new Repository();
		r.add(dataPath);
		System.out.println("add done");

	}
	
	private static void remove(String dataPath) {
		System.out.println("remove started");
		
		Repository r = new Repository();

		r.remove(dataPath);
		
		System.out.println("remove done");

	}
	
	

}
