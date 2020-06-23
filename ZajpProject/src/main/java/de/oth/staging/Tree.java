package de.oth.staging;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public Node getRootNode() {
		return rootNode;
	}

	public void add(File f) {

		Node parentNode = rootNode;
		List<File> filelist = FileStructureToList(f);

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

			if (inlist == false) {
				newChildNode.setParentNode(parentNode);
				parentNode.addChild(newChildNode);
				parentNode = newChildNode;
			}

		}

	}

	private List<File> FileStructureToList(File f) {

		List<File> filelist = new ArrayList<File>();

		while (f != null) {

			filelist.add(f);

			f = f.getParentFile();

		}
		Collections.reverse(filelist);
		return filelist;

	}

	

	
	
	
	public void remove(File f) {
		
		
	//Node root = rootNode;
	List<File> filelist = FileStructureToList(f);
	Node currenNode=rootNode;
	
	for(int i=0; i<filelist.size(); i++)
	{
		
		for(int j=0; j<currenNode.getChildNodes().size(); j++)
		{
			
			if(currenNode.getChildNodes().get(j).getData().equals(filelist.get(i)) && i != (filelist.size()-1))
			{
				currenNode=currenNode.getChildNodes().get(j);
			} else if(currenNode.getChildNodes().get(j).getData().equals(filelist.get(i)) && i == (filelist.size()-1)) {
				
				currenNode.getChildNodes().remove(j);
				
				
				// clean empty dirs
				
				while(!currenNode.equals(rootNode))
				{
					if(currenNode.getChildNodes() == null || currenNode.getChildNodes().size()==0 )
					{
						
						Node parentNode=currenNode.getParentNode();
						
						
						parentNode.getChildNodes().remove(currenNode);
					
						currenNode=parentNode;
					} else {
						
					  currenNode=currenNode.getParentNode();	
						
					}
				}
				
				
				
				
				
			}
			
		}
		
	}
	

		
	}
	
	
	
	
	public void printstages() {

		System.out.println("+--  " + rootNode.getData().getName()+"   Hash Code : "+rootNode.SHA1HashValue());
		printIt(rootNode.getChildNodes(), "");
	}

	
	private void printIt(List<Node> childs, String s) {

		if (childs == null || childs.size() == 0) {
			return;
		}

		s += "     ";

		for (int i = 0; i < childs.size(); i++) {

			System.out.println(s + "+--  " + childs.get(i).getData().getName()+"   Hash Code : "+childs.get(i).SHA1HashValue());
			printIt(childs.get(i).getChildNodes(), s);
		}

	}

}
