package de.oth.checkout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author Dimitri
 *
 */
public class Checkout {

	private final String objectPath = "./.jit/objects";
	private final String rootPath = "./";

	
	
	/**
	 * 
	 * @param hash of the work space
	 * @throws IOException
	 */
	public void setupCheckout(String hash) throws IOException {

		if (!validCommitHash(hash)) {

			System.out.println("Not a valid hashcode!");
			return;
		}
		
		deleteAllFilesInRoot();
		checkout(hash, rootPath);
		

	}

	
	
	/**
	 * 
	 * @param hash current hash/name of file in objects 
	 * @param currentPath Path String of the current File
	 * @throws IOException
	 */
	private void checkout(String hash, String currentPath) throws IOException {
		
		FileInputStream fis = new FileInputStream(new File(objectPath + "/" + hash));
		byte[] buffer = new byte[10];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[10];
		}
		fis.close();

		String content = sb.toString();

		String lines[] = content.split(System.lineSeparator());

		for (int i = 0; i < lines.length; i++) {

			String[] command = lines[i].split(" ");

			if (i != 0 && (command[0].equals("File") || command[0].equals("Directory"))) {

				String newHash = command[1];
				String name = "";
				for (int j = 2; j < command.length; j++) {
					name += command[j];

					if (j != command.length - 1)
						name += " ";
				}

				if (command[0].equals("Directory")) {

					File newfile = new File(currentPath + name);
					newfile.mkdir();
					checkout(newHash, currentPath + name + "/");
				} else if (command[0].equals("File")) {

					File newfile = new File(currentPath + name);

					Path source = Paths.get(objectPath + "/" + newHash);

					FileOutputStream out = new FileOutputStream(newfile);
					Files.copy(source, out);

				}
			}

		}

	}

	/**
	 * 
	 * delete all files execpt the hidden .jit Dir 
	 * 
	 * @throws IOException
	 */
	private void deleteAllFilesInRoot() throws IOException {
		File f = new File(rootPath);

		File[] flist = f.listFiles();

		for (int i = 0; i < flist.length; i++) {

			if (flist[i].getName().equals(".jit")) {

			} else if (flist[i].getName().equals(".classpath") || flist[i].getName().equals(".project")
					|| flist[i].getName().equals(".settings") || flist[i].getName().equals("pom.xml")
					|| flist[i].getName().equals("src") || flist[i].getName().equals("target")) {

			} else {

				if (flist[i].isDirectory()) {
					
				
					FileUtils.deleteDirectory(flist[i]);
				
					
				} else {

					flist[i].delete();
				}

			}
		}

	}

	
	/**
	 * 
	 * @param hash that the user has given the programm
	 * @return true if the given hash is a commit hash
	 */
	private boolean validCommitHash(String hash) {
		File hashFile = new File(objectPath + "/" + hash);

		if (!hashFile.exists()) {
			return false;
		}

		String content = "";

		FileInputStream fis;
		try {
			fis = new FileInputStream(hashFile);

			byte[] buffer = new byte[10];
			StringBuilder sb = new StringBuilder();
			while (fis.read(buffer) != -1) {

				sb.append(new String(buffer));
				buffer = new byte[10];
			}

			content = sb.toString();
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] lines = content.split(System.lineSeparator());
		String[] firstLine = lines[0].split(" ");

		if (firstLine[0].equals("Commit")) {

			return true;
		}

		return false;

	}

	
	/**
	 * 	print all commits with the hash code on the console
	 */
	public void printAllCommits() {
		
		File objectDir=new File(objectPath);
		
		File[] allObjects= objectDir.listFiles();
		
		boolean atleastOneCommitsExist=false;
		
		
		
		for(int i=0; i<allObjects.length; i++)
		{
			String fileName=allObjects[i].getName();
			
			
			if(validCommitHash(fileName))
			{
				
				String content="";
				FileInputStream fis;
				try {
					fis = new FileInputStream(allObjects[i]);

					byte[] buffer = new byte[10];
					StringBuilder sb = new StringBuilder();
					while (fis.read(buffer) != -1) {

						sb.append(new String(buffer));
						buffer = new byte[10];
					}

					 content = sb.toString();
					fis.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String[] lines = content.split(System.lineSeparator());
				String[] firstLine = lines[0].split(" ");
				String commitMassage="";
				
				for(int j=1; j<firstLine.length; j++)
				{
					if(j==1)
					{
						commitMassage+="\"";
					}
					
					commitMassage+=firstLine[j]+" ";
					
					
					if(j==firstLine.length-1)
					{
						commitMassage+="\"";
					}
					
				}
				
				System.out.println("Commit : "+commitMassage+" Hashcode/Filename : "+allObjects[i].getName());
				atleastOneCommitsExist=true;
			}
					
		}
		
		if(!atleastOneCommitsExist)
		{
			System.out.println("No files commited yet");
		}
		
	}
	
	
	
	
	
	
}
