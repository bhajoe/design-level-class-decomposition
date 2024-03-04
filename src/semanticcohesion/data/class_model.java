/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.data;

import java.util.ArrayList;

/**
 *
 * @author bhajoe
 */
public class class_model {
    private String name;
    private ArrayList<attribute> attributes;
    private int PublicAttr;
    private int PrivateAttr;
    private int ProtectedAttr;
    private ArrayList<method> methods;
    private int PublicMethod;
    private int PrivateMethod;
    private int ProtectedMethod;
    private int NumOfRealization;
    private int NumOfGeneralization;
    private int[][] DATmatrix;

    public class_model() {
        this.attributes = new ArrayList<attribute>();
        this.methods = new ArrayList<method>();
        this.PublicAttr = 0;
        this.PrivateAttr = 0;
        this.ProtectedAttr = 0;
        this.PublicMethod = 0;
        this.PrivateMethod = 0;
        this.ProtectedMethod = 0;
        this.NumOfRealization = 0;
        this.NumOfGeneralization = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<attribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<method> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<method> methods) {
        this.methods = methods;
    }
    
    public void addAttribute(attribute attr)
    {
        this.attributes.add(attr);
        if (attr.getVisibility().toLowerCase().equals("private"))
        {
            this.PrivateAttr += 1;
        } else if (attr.getVisibility().toLowerCase().equals("public"))
        {
            this.PublicAttr += 1;
        } else if (attr.getVisibility().toLowerCase().equals("protected"))
        {
            this.ProtectedAttr += 1;
        }
    }
    
    public attribute getAttributeById(int id)
    {
        return this.attributes.get(id);
    }
    
    public void addMethod(method mtd)
    {
        this.methods.add(mtd);
        //System.out.println(mtd.getVisibility());
        if (mtd.getVisibility().toLowerCase().equals("private"))
        {
            this.PrivateMethod += 1;
        } else if (mtd.getVisibility().toLowerCase().equals("public"))
        {
            this.PublicMethod += 1;
        } else if (mtd.getVisibility().toLowerCase().equals("protected"))
        {
            this.ProtectedMethod += 1;
        }
    }
    
    public method getMethodById(int mtd)
    {
        return this.methods.get(mtd);
    }

    public int[][] getDATmatrix() {
        return DATmatrix;
    }

    public void setDATmatrix(int[][] DATmatrix) {
        this.DATmatrix = DATmatrix;
    }

    public int getPublicAttr() {
        return PublicAttr;
    }

    public int getPrivateAttr() {
        return PrivateAttr;
    }

    public int getProtectedAttr() {
        return ProtectedAttr;
    }

    public int getPublicMethod() {
        return PublicMethod;
    }

    public int getPrivateMethod() {
        return PrivateMethod;
    }

    public int getProtectedMethod() {
        return ProtectedMethod;
    }

    public int getNumOfRealization() {
        return NumOfRealization;
    }

    public void addNumOfRealization() {
        this.NumOfRealization += 1;
    }

    public int getNumOfGeneralization() {
        return NumOfGeneralization;
    }

    public void addNumOfGeneralization() {
        this.NumOfGeneralization += 1;
    }
    
    
    
    
}
