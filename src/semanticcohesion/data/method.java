/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bhajoe
 */
public class method {
    private String name;
    private ArrayList<methodParameter> parameters;
    private HashMap<String, String> hash_parameters;
    private String returnType;
    private String visibility;
    
    public void setReturnType(String type)
    {
        this.returnType = type;
    }
    
    public String getReturnType()
    {
        return this.returnType;
    }

    public method() {
        this.parameters = new ArrayList<methodParameter>();
        this.hash_parameters = new HashMap<String, String>();
    }
    public method(String name, ArrayList<methodParameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<methodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<methodParameter> parameters) {
        this.parameters = parameters;
    }
    
    public void addParameter(methodParameter mtp)
    {
        this.parameters.add(mtp);
        this.hash_parameters.put(mtp.getType(), mtp.getName());
    }
    
    public methodParameter getParameterById(int id)
    {
        return this.parameters.get(id);
    }
    
    public boolean isTypeisExist(String type)
    {
        if (this.hash_parameters.containsKey(type))
            return true;
        else
            return false;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    

}
