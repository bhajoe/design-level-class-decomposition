/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.xml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bayu
 */
public class Node implements Cloneable
	{
                String Name;
		String value;
                String type;
                String returntype;
                String visibility;
                String ID;
		boolean visited;
                Node Parent;
		List<Node> neighbours;
                List<Node> childs;
                
 
                Node()
                {
                    
                }
		
                /*Node(String nm, String val, String vis, String returntype)
		{
			this.Name = nm;
                        this.value = val;
                        this.visibility = vis;
                        this.ID = ID;
			this.neighbours=new ArrayList<>();
                        this.returntype = returntype;
 		}*/
                
                Node(String nm, String val, String type, String vis, String returntype)
		{
			this.Name = nm;
                        this.value = val;
                        this.visibility = vis;
                        this.ID = ID;
			this.neighbours=new ArrayList<>();
                        this.type = type;
                        this.returntype = returntype;
 		}

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getReturntype() {
                    return returntype;
                }

                public void setReturntype(String returntype) {
                    this.returntype = returntype;
                }
                
                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
                
                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getVisibility() {
                    return visibility;
                }

                public void setVisibility(String visibility) {
                    this.visibility = visibility;
                }
                
                public void addneighbours(Node neighbourNode)
		{
			this.neighbours.add(neighbourNode);
		}

                public List<Node> getNeighbours() {
			return neighbours;
		}

                public void setNeighbours(List<Node> neighbours) {
			this.neighbours = neighbours;
		}
                
                public void addChild(Node child)
		{
			this.neighbours.add(child);
		}

                public List<Node> getChilds() {
                    return childs;
                }

                public void setChilds(List<Node> childs) {
                    this.childs = childs;
                }



                public Node getParent() {
                    return Parent;
                }

                public void setParent(Node Parent) {
                    this.Parent = Parent;
                }
                
                public Object clone() throws CloneNotSupportedException
                {
                    return super.clone();
                }
                
                public void print()
                {
                    System.out.println("----");
                    System.out.println("Name :"+this.Name);
                    System.out.println("Value :"+this.value);
                    System.out.println("Type :"+this.type);
                    System.out.println("----");
                }
            
                
	}
