/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import semanticcohesion.data.attribute;

/**
 *
 * @author bhajoe
 */
public class FieldParser extends VoidVisitorAdapter {
    private ArrayList<attribute> attributes = new ArrayList<>();
    
    public FieldParser()
    {
        attributes.clear();
    }
    
    public ArrayList<attribute> getAttributes()
    {
        return this.attributes;
    }
    
    public void visit(FieldDeclaration n, Object arg) 
    {    
        for (VariableDeclarator att : n.getVariables())
        {
            //System.out.println("Attribute : "+att.toString()+" Type : "+n.getType());
            attribute attrib = new attribute(att.toString(), n.getType().toString());
            attributes.add(attrib);
        }
    }

    
}
