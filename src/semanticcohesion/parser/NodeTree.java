/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import java.util.ArrayList;

/**
 *
 * @author bayu
 */
public class NodeTree extends xmlnode{
  
    private ArrayList<xmlnode> childs = new ArrayList<>();
    
    @Override
    public void printNode() {
        
        if (this.getName().equals("Class"))
            this.setPaths(this.getParent().getPath());
        for (xmlnode n : this.childs)
        {
            n.printNode();
        }
    }
    
    public void addChild(xmlnode xnode)
    {
        this.childs.add(xnode);
    }
    
    public void removeChild(xmlnode xnode)
    {
        this.childs.remove(xnode);
    }
    
    public xmlnode getChild(xmlnode xnode)
    {
        xmlnode x = null;
        if (this.childs.contains(xnode))
        {
            x = this.childs.get(this.childs.indexOf(xnode));
        }
        return x;
    }
    
    public ArrayList<xmlnode> getChilds()
    {
        return this.childs;
    }
       
    
}
