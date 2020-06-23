package de.oth.checkout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.io.FileUtils;


public class Checkout {

	private final String objectPath="./.jit/objects";
	private final String rootPath="./";
	

	
		
	public void setupCheckout(String hash) throws IOException {
		
		deleteAllFilesInRoot();
		
		
		checkout(hash,rootPath);
			
	}
	
	private void checkout(String hash,String currentPath) throws IOException {
		
		FileInputStream fis = new FileInputStream(new File(objectPath+"/"+hash));
		byte[] buffer = new byte[10];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[10];
		}
		fis.close();

		String content = sb.toString();
		
		//System.out.println(content);
		
	
		String lines[] = content.split(System.lineSeparator());
		
		for(int i=0; i<lines.length-1; i++)
		{
			//System.out.println(lines[i]);
			

			String[] command=lines[i].split(" ");
			
			if(command.length == 3 && (command[0].equals("File") || command[0].equals("Directory")))
			{	
				/*
				for(int j=0; j<command.length; j++)
				{
					System.out.println(command[j]);
					
				}
				*/
				
				if(command[0].equals("Directory")) {
					
					String newHash =command[1];
					String name=command[2];
					
					File newfile=new File(currentPath+name);
						 newfile.mkdir();
					checkout(newHash, currentPath+name+"/");
				}else if(command[0].equals("File")) {
					String newHash =command[1];
					String name=command[2];
					File newfile=new File(currentPath+name);
					newfile.createNewFile();
					
					
					FileInputStream classFis = new FileInputStream(new File(objectPath+"/"+newHash));
					byte[] classBuffer = new byte[10];
					StringBuilder classSb = new StringBuilder();
					while (classFis.read(classBuffer) != -1) {
						classSb.append(new String(classBuffer));
						classBuffer = new byte[10];
					}
					classFis.close();

					String ClassContent = classSb.toString();
					
					
					PrintWriter writer =new PrintWriter(newfile);
					writer.print(ClassContent);
					writer.close();
					
				}
			}
			
		}
		
		
	}
	
	private void deleteAllFilesInRoot() throws IOException {
		File f=new File(rootPath);
		
		File[] flist=f.listFiles();
		
		for(int i=0; i<flist.length; i++)
		{
			//System.out.println(flist[i].getName());
			if(flist[i].getName().equals(".jit"))
			{
				
			} else if(flist[i].getName().equals(".classpath")
					||flist[i].getName().equals(".project")
					||flist[i].getName().equals(".settings")
					||flist[i].getName().equals("pom.xml")
					||flist[i].getName().equals("src")
					||flist[i].getName().equals("target")
			) {
				
				//System.out.println(flist[i].getName()+" for the ide not deleted");
			} else {
				
				
					if(flist[i].isDirectory())
					{	
						FileUtils.deleteDirectory(flist[i]);
					}else {
						
						flist[i].delete();
					}
				
					
					
					
					
		
			}
		}
		
	}
	
	
	

}
