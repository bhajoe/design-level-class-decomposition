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
public class QueryNode
	{
                private String Name;
                int level;
		static int visited;
                QueryNode parent;
		List<QueryNode> neighbours;
                static int QueryNumber;
 
		QueryNode(String nm, int lvl)
		{
			this.Name = nm;
                        this.level = lvl;
                	this.neighbours=new ArrayList<>();
                        QueryNumber++;
 		}

                public void addneighbours(QueryNode neighbourNode)
		{
                    if (neighbourNode != null)
                    {
			this.neighbours.add(neighbourNode);
                        neighbourNode.setParent(this);
                    }
		}

                public List<QueryNode> getNeighbours() {
			return neighbours;
		}

                public void setNeighbours(List<QueryNode> neighbours) {
			this.neighbours = neighbours;
		}

                public QueryNode getParent() {
                    return this.parent;
                }

                public void setParent(QueryNode parent) {
                    this.parent = parent;
                }
                
                public String getName()
                {
                    visited++;
                    return this.Name;
                }
                
                public boolean isAllVisited()
                {
                    if (visited==QueryNumber)
                        return true;
                    else
                        return false;
                }
                
                public QueryNode getRootNode()
                {
                    if (level == 0)
                    {
                        return this;
                    } else
                    {
                        return getParent().getRootNode();
                    }
                }
                               
	}
