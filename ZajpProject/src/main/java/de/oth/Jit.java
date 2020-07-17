package de.oth;

import de.oth.checkout.Checkout;
import de.oth.repository.Repository;

/**
 * 
 * @author Dimitri
 *
 */

public class Jit {

	/**
	 * 
	 * main program starts here
	 * 
	 * @param args the arguments that the user give the programm
	 */
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
			} else if (command.equalsIgnoreCase("commit")) {

				commit(args[1]);

			} else if (command.equalsIgnoreCase("checkout")) {

				checkout(args[1]);

			} else if (command.equalsIgnoreCase("staging")) {

				staging();

			} else if (command.equalsIgnoreCase("commits")) {

				commits();
			} else if (command.equalsIgnoreCase("help")) {

				help();

			} else {
				System.out.println(command + " is not a valid command!");
				return;
			}

		}

	}

	/**
	 * 
	 * print all commands on the console
	 * 
	 */
	private static void help() {

		System.out.println(" \"init\" 				: initializing your workspace in the current Directory"
				+ System.lineSeparator());
		System.out.println(
				" \"add <path>\" 		 		: add the given file to the staging area" + System.lineSeparator());
		System.out.println(
				" \"remove <path>\"		 	: remove the given file from the staging area" + System.lineSeparator());
		System.out.println(
				" \"staging\" 			 	: print the current staging area on the console" + System.lineSeparator());
		System.out.println(
				" \"commit <message>\"	 		: commit the current staging area with a massage for you that help you to find it later"
						+ System.lineSeparator());
		System.out.println(
				" \"commits\" 			 	: print all your commits on the console with the massage and the right hash code"
						+ System.lineSeparator());
		System.out.println(
				" \"checkout <hash>\" 	 		: recreate your earlier commited files with the help of the given hash code"
						+ System.lineSeparator());

	}

	/**
	 * 
	 * print all commits that the user did on the console with the commit massage
	 * and the hash code
	 * 
	 */
	private static void commits() {

		Checkout c = new Checkout();
		c.printAllCommits();
	}

	/**
	 * 
	 * delete all files in the working Directory and recreate with the given hash
	 * code like the working Directory was at the time that this commit was done
	 * 
	 * @param hash the hashcode for the time that one commits was done
	 */
	private static void checkout(String hash) {
		System.out.println("checkout started");
		Repository r = new Repository();

		if (r.checkout(hash)) {

			System.out.println("checkout done");

		} else {
			System.out.println("checkout couldn't be done because a error has occurred");

		}

	}

	/**
	 * 
	 * save the current staging area with a massage
	 * 
	 * @param commitMassage the massage that the user give to identify it later
	 */
	private static void commit(String commitMassage) {
		System.out.println("commit started");
		Repository r = new Repository();
		if (r.commit(commitMassage)) {
			System.out.println("commit done");

		} else {

			System.out.println("commit couldn't be done because a error has occurred");

		}

	}

	/**
	 * 
	 * initialsie the current Directory as the workspace, must be done before
	 * add/commit/checkout commands
	 * 
	 */
	private static void init() {

		System.out.println("init started");

		Repository r = new Repository();

		r.init();

		System.out.println("init done");

	}

	/**
	 * 
	 * add a file to the staging area
	 * 
	 * @param dataPath the path to the file that the user want to add to the staging
	 *                 area
	 */
	private static void add(String dataPath) {
		System.out.println("add started");
		Repository r = new Repository();

		if (r.add(dataPath)) {

			System.out.println("add done");

		} else {

			System.out.println("add couldn't be done because a error has occurred");

		}

	}

	/**
	 * 
	 * remove a file from the staging area
	 * 
	 * @param dataPath the path to the file that the user want to remove from the
	 *                 staging area
	 */
	private static void remove(String dataPath) {
		System.out.println("remove started");

		Repository r = new Repository();

		if (r.remove(dataPath)) {

			System.out.println("remove done");

		} else {

			System.out.println("add couldn't be done because a error has occurred");

		}

	}

	/**
	 * print the current staging area on the console
	 */
	private static void staging() {

		Repository r = new Repository();
		r.printCurrentStaging();
	}

}
