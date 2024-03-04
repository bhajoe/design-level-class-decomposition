/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;

/**
 *
 * @author bhajoe
 */
public class MethodParser extends VoidVisitorAdapter {
    private ArrayList<method> methods = new ArrayList<>();

    public MethodParser() {
        methods.clear();
    }
    
    public ArrayList<method> getMethods()
    {
        return this.methods;
    }
 
    public void visit(MethodDeclaration n, Object arg) {
        ArrayList<methodParameter> params = new ArrayList<methodParameter>();
        if (n.getParameters() != null)
        {
            for(Parameter p : n.getParameters())
            {
                methodParameter mp = new methodParameter(p.toString(), p.getType().toString());
                params.add(mp);
            }
        }
        method m = new method(n.getName(), params);
        methods.add(m);
    }

        
}
