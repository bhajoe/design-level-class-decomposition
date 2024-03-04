/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author bayu
 */
public abstract class xmlnode {
    private String nameTag;
    private String name;
    private String value;
    private xmlnode parent;
    private static Set<String> paths = new TreeSet<String>();
    
    abstract public void printNode();
    
    
    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public xmlnode getParent() {
        return parent;
    }

    public void setParent(xmlnode parent) {
        this.parent = parent;
    }
    
    public String getPath()
    {
        String pt = "";
        if (this.parent != null) {
            pt= this.parent.getPath()+"|"+this.name;
        } else {
            pt = this.name;
        }
        return pt;
    }

    public static Set<String> getPaths() {
        return paths;
    }

    public static void setPaths(String path) {
        xmlnode.paths.add(path);
    }
    
    
    
    
    
}
