/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

/**
 *
 * @author bayu
 */
public class NodeLeaf extends xmlnode{

    @Override
    public void printNode() {
        if (this.getName().equals("Class"))
            NodeTree.setPaths(this.getParent().getPath());
    }
    
     public xmlnode searchObj(String obj) {
        if (this.getName().equals(obj))
            return this;
        else
            return null;
    }
    
}
