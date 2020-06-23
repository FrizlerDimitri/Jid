package de.oth.staging;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.oth.hashing.Hashable;

public class Node extends Hashable implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6013412496891695185L;
	private File data;
	private Node parentNode;
	private List<Node> childNodes;
	
	
		
	public Node(File data) {
		
		this.data=data;
		this.childNodes=new ArrayList<Node>();
		
	}



	public File getData() {
		return data;
	}
	
	public void setData(File data) {
		this.data = data;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public List<Node> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Node> childNodes) {
		this.childNodes = childNodes;
	}


	public String SHA1HashValue() {
		Node thisNode=this;
		if (thisNode.getChildNodes()==null||this.getChildNodes().size()==0) {
			
			return dataToHash(this.getData()); 
			
		}
		
		
		if(thisNode.childNodes.size()==2)
		{
			
			return dataToHash(thisNode.getChildNodes().get(0).SHA1HashValue()+thisNode.getChildNodes().get(1).SHA1HashValue());
			
		} else if(thisNode.childNodes.size()==1) {
			
			return dataToHash(thisNode.getChildNodes().get(0).SHA1HashValue()+thisNode.getChildNodes().get(0).SHA1HashValue());
		}else {	
		
		
		
		List <String> hashlist= new ArrayList<String>();
		
		for(int i=0; i<thisNode.getChildNodes().size(); i++) {
			
			hashlist.add(thisNode.getChildNodes().get(i).SHA1HashValue());
			
		}
		
		return SHA1HashValue2(hashlist);
		
		}
		
	}
	
	
	private String SHA1HashValue2(List <String> hashlist ) {
		
		
		if(hashlist.size()==1)
		{
			return hashlist.get(0);
		}
		
		
		List<String> updatedHashlist=new ArrayList<String>();
		
		int dataleft=hashlist.size();
		int i=0;
		for (; dataleft>1; dataleft-=2)
			
		{
			String left=hashlist.get(i++);
			String right=hashlist.get(i++);
			String newHash =dataToHash(left+right);
			
			updatedHashlist.add(newHash);
		}
		
		if(dataleft==1) {
			
			String left=hashlist.get(i);
			String newHash=dataToHash(left+left);
			
			updatedHashlist.add(newHash);
		}
		
		
		return SHA1HashValue2(updatedHashlist);
		
		
	}
	

		
	public void addChild(Node newChild) {
		
		childNodes.add(newChild);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj.getClass() != this.getClass())
		{
			return false;
		}
		
		Node thisNode=this;
		Node otherNode=(Node)obj;
		
		if(thisNode.getData().equals(otherNode.getData()))
		{
			return true;
		}
		
		return false;
	}
	
	
	

}
