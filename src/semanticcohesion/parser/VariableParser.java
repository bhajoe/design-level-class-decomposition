/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

/**
 *
 * @author bhajoe
 */
public class VariableParser extends VoidVisitorAdapter
{
    public void visit(VariableDeclarationExpr n, Object arg)
    {      
        List <VariableDeclarator> myVars = n.getVars();
        for (VariableDeclarator vars: myVars)
        {
            System.out.println("Variable Name: "+vars.getId().getName());
            System.out.println("Variable Name: "+vars.toString());
        }
    }
}
