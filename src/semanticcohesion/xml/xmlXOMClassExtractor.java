/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import nu.xom.Builder;
import nu.xom.Comment;
import nu.xom.DocType;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ParsingException;
import nu.xom.ProcessingInstruction;
import nu.xom.Text;
import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;

/**
 *
 * @author Bayu Priyambadha
 */
public class xmlXOMClassExtractor {
    private HashMap<String, class_model> classes;
    private class_model temp_class;
    private attribute temp_attribute;
    private method temp_method;
    private methodParameter temp_parameter;

    public xmlXOMClassExtractor() {
        this.classes = new HashMap<String, class_model>();
    }
    
    public HashMap<String, class_model> getClasses() {
        return classes;
    }
    
    public void parse(File[] XMLFiles) throws ParsingException, IOException
    {
        for (File fl : XMLFiles)
        {
            Builder builder = new Builder();
            try {
              Document doc = builder.build(fl);
              Element root = doc.getRootElement();
              listChildren(root, 0, 0, "");      
            }
            // indicates a well-formedness error
            catch (IOException ex) { 
              System.out.println(ex);
            }
          }
    }
    
    public void listChildren(Node current, int depth, int line, String parent) {
   
    String data = "";
    String tag_name = "";
    if (current instanceof Element) {
        Element temp = (Element) current;
        
        tag_name = temp.getQualifiedName();
        //Get the class
        //Project|Models|Package|ModelChildren|Package|ModelChildren|Package|ModelChildren|
        //String classRoot = "|Project|Models|Package|ModelChildren|Package|ModelChildren|Package|ModelChildren";
        //String classRoot = "|Project|Models|Package|ModelChildren|Package|ModelChildren";
        
        String classRoot = "|Project|Models";
        getClass(classRoot, temp, parent, false, false);
        
        //getClass(classRoot, temp, parent, false, false);
        
        //Get the inner class
        //String innerClassRoot = "|Project|Models|Package|ModelChildren|Package|ModelChildren|Class|ModelChildren"; 
        //getClass(innerClassRoot, temp, parent, false, false);
        
        //String IC1 = "|Project|Models|Package|ModelChildren|Package|ModelChildren|Class|ModelChildren|Class|ModelChildren"; 
        //getClass(IC1, temp, parent, false, false);
        
        //String IC2 = "|Project|Models|Package|ModelChildren|Package|ModelChildren|Class|ModelChildren|Class|ModelChildren|Class|ModelChildren"; 
        //getClass(IC2, temp, parent, false, false);
        
        //Get the class inner package
        //String innerPackageClassRoot = "|Project|Models|Package|ModelChildren|Package|ModelChildren|Package|ModelChildren|Package"; 
        //getClass(innerPackageClassRoot, temp, parent, false, false);
                
        //Get the class relation
        parent = parent+"|"+tag_name;
        for (int i = 0; i < current.getChildCount(); i++) {
            listChildren(current.getChild(i), depth+1, line+i, parent);
        }
        
    }
    else if (current instanceof ProcessingInstruction) {
        //ProcessingInstruction temp = (ProcessingInstruction) current;
        //data = ":) " + temp.getTarget();   
    }
    else if (current instanceof DocType) {
        //DocType temp = (DocType) current;
        //data = ":& " + temp.getRootElementName();   
    }
    else if (current instanceof Text || current instanceof Comment) {
        //String value = current.getValue();
        //value = value.replace('\n', ' ').trim();
        //if (value.length() <= 20) data = ":+ " + value;
        //else data = ":# " + current.getValue().substring(0, 17) + "...";   
    }
    
  }
  
    private void getClass(String classRoot,Element temp,String parent, boolean constructor, boolean gettersetter)
    {
        String data;
        String tag_name = temp.getQualifiedName();
            if (parent.equals(classRoot))
            {
                if (tag_name.equals("Class")) 
                {
                    data = temp.getAttributeValue("Name");
                    //System.out.println(data);
                    if (!(this.temp_method == null))
                    {
                        this.temp_class.addMethod(this.temp_method);
                        //System.out.println("Masuk "+this.temp_class.getName());
                        this.classes.put(this.temp_class.getName(),this.temp_class);
                        this.temp_method = null;
                        this.temp_class = null;
                    } else if (!(this.temp_class == null))
                    {
                        //System.out.println("Masuk juga "+this.temp_class.getName());
                        this.classes.put(this.temp_class.getName(),this.temp_class);
                        this.temp_class = null;
                    }
                    
                    this.temp_class = new class_model();
                    this.temp_class.setName(data);
                    
                    //System.out.println(data);
                }
            }
            //Get the class's Attribute
            if (parent.equals(classRoot+"|Class|ModelChildren"))
            {
                if (tag_name.equals("Attribute")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_attribute = new attribute();
                    this.temp_attribute.setName(data);
                    String temp_type = temp.getAttributeValue("Type");
                    //System.out.println(temp.getAttributeValue("Type"));
                    if (temp_type != null)
                    {
                        //System.out.println(temp.getAttributeValue("Type"));
                        this.temp_attribute.setType(temp.getAttributeValue("Type"));
                        this.temp_class.addAttribute(this.temp_attribute);
                        this.temp_attribute = null;
                    }
                    //System.out.println(data);
                }
            }
            //Get the class's Attribute's type
            if (parent.equals(classRoot+"|Class|ModelChildren|Attribute|Type"))
            {
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_attribute.setType(data);
                    //System.out.println(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = "Attribute Type "+tag_name+": "+temp.getAttributeValue("Name");
                    this.temp_attribute.setType(data);
                    //System.out.println(data);
                }
                this.temp_class.addAttribute(this.temp_attribute);
                this.temp_attribute = null;
            }
            //Get the class operation, parameters and types
            //Operation
            if (parent.equals(classRoot+"|Class|ModelChildren"))
            {
                if (tag_name.equals("Operation")) 
                {
                    data = temp.getAttributeValue("Name");
                    if (!(this.temp_method == null))
                    {
                        this.temp_class.addMethod(this.temp_method);
                        this.temp_method = null;
                    }
                    
                    //If Constructor is included
                    //if ((!isGetter(data) || (!isSetter(data))) && ((constructor && (data.equals(this.temp_class.getName()))) || (!data.equals(this.temp_class.getName()))))
                    /*if (((!isGetter(data)) && (!isSetter(data))) && (!data.equals(this.temp_class.getName())))
                    {
                        this.temp_method = new method();
                        this.temp_method.setName(data);
                    } else
                    {
                        System.out.println(data + " getter/setter/constructor");
                    } */
                    
                    if (((!isGetter(data)) && (!isSetter(data))))
                    {
                        this.temp_method = new method();
                        this.temp_method.setName(data);
                    } else
                    {
                        System.out.println(data + " getter/setter");
                    } 
                    
                    //If getter and setter is included
                    /*if ((gettersetter && isGetter(data)) || ((gettersetter && isSetter(data))))
                    {
                        this.temp_method = new method();
                        this.temp_method.setName(data);
                    }*/
                        
                    //System.out.println(data);
                }
            }
            //Return Type
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ReturnType"))
            {
                data = "";
                if (this.temp_method != null)
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_method.setReturnType(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_method.setReturnType(data);
                }
                //System.out.println(data);
            }
            //Parameter
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ModelChildren"))
            {
                if (tag_name.equals("Parameter")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_parameter = new methodParameter();
                    this.temp_parameter.setName(data);
                    //System.out.println(data);
                }
            }
            //Parameter Type
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ModelChildren|Parameter|Type"))
            {
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_parameter.setType(data);
                    //System.out.println(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = "Parameter Type "+tag_name+": "+temp.getAttributeValue("Name");
                    this.temp_parameter.setType(data);
                    //System.out.println(data);
                }
                if (this.temp_method != null)
                {
                    this.temp_method.addParameter(this.temp_parameter);
                }
                this.temp_parameter = null;
            }
    }
    
    public boolean isGetter(String methodName) 
    {
        return methodName.matches("^get[A-Z].*");
    }
    
    public boolean isSetter(String methodName) 
    {
        return methodName.matches("^set[A-Z].*");
    }
}
