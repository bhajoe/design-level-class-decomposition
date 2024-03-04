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
import java.util.List;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;

/**
 *
 * @author Bayu Priyambadha
 */
public class xmlXOMClassExtractor_DFS {
    private HashMap<String, class_model> classes;
    private class_model temp_class;
    private attribute temp_attribute;
    private method temp_method;
    private methodParameter temp_parameter;
    private static ArrayList<QueryNode> HashQuery = new ArrayList<QueryNode>();
    static counter ct;
    private static class_model cm;

    public xmlXOMClassExtractor_DFS() {
        this.classes = new HashMap<String, class_model>();
    }
    
    public HashMap<String, class_model> getClasses() {
        return classes;
    }
    
    public void parse(File[] XMLFiles) throws ParsingException, IOException, CloneNotSupportedException
    {
        for (File fl : XMLFiles)
        {
            this.ct = new counter();
            Builder builder = new Builder();
            Node xmlRoot = null;
            try {
                Document doc = builder.build(fl);
                Element root = doc.getRootElement();
                xmlRoot = new Node("Root", "", "", "", "");
                xmlRoot.setParent(xmlRoot);
                listChildren(root, xmlRoot);      
            }
            // indicates a well-formedness error
            catch (ParsingException ex) { 
                System.out.println(ex.getMessage());
            }  
            catch (IOException ex) { 
                System.out.println(ex);
            }           
            
            QueryNode qClass = new QueryNode("Class",0);
            QueryNode qRealization = new QueryNode("Realization",1);
            QueryNode qGeneratlization = new QueryNode("Generalization",1);
            QueryNode qAttribute = new QueryNode("Attribute",1);
            QueryNode qType = new QueryNode("Type",2);
            QueryNode qAttDataType = new QueryNode("DataType",3);
            QueryNode qAttClass = new QueryNode("Class",3);
            QueryNode qOperation = new QueryNode("Operation",1);
            QueryNode qReturnType = new QueryNode("ReturnType",2);
            QueryNode qDataType = new QueryNode("DataType",3);
            QueryNode qRTClass = new QueryNode("ClassType",3);
            QueryNode qParameter = new QueryNode("Parameter",2);
            QueryNode qParameterType = new QueryNode("Type",3);
            QueryNode qTypeClass = new QueryNode("ClassType",4);
            QueryNode qTypeDataType = new QueryNode("DataType",4);
            
            qParameterType.addneighbours(qTypeClass);
            qParameterType.addneighbours(qTypeDataType);
            
            qParameter.addneighbours(qParameterType);
            
            qReturnType.addneighbours(qDataType);
            qReturnType.addneighbours(qRTClass);
            
            qOperation.addneighbours(qReturnType);
            qOperation.addneighbours(qParameter);
            
            qType.addneighbours(qAttDataType);
            qType.addneighbours(qAttClass);
            
            qAttribute.addneighbours(qType);
            qClass.addneighbours(qAttribute);
            qClass.addneighbours(qOperation);
            
            qClass.addneighbours(qRealization);
            qClass.addneighbours(qGeneratlization);
                                    
            //System.out.println("The DFS traversal of the graph using recursion ");
            long startTime = System.currentTimeMillis();
            dfs(xmlRoot,qClass,0,0,"");
            long endTime = System.currentTimeMillis();
            float duration = (float) (endTime - startTime)/1000;
            //System.out.println("Duration: "+duration+" second");
            
            //System.out.println("Number of class :"+ct.getNumber());
    }
}
        
        public static void listChildren(nu.xom.Node current, Node Parent) 
        {
            String data = "";
            String value = "";
            String visibility = "";
            String type = "";
            String returntype = "";
            if (current instanceof Element) {
                Element temp = (Element) current;
                data = temp.getQualifiedName();   
                value = temp.getAttributeValue("Name");
                visibility = temp.getAttributeValue("Visibility");
                returntype = temp.getAttributeValue("ReturnType");
                if (visibility==null)
                    visibility = "";
                if (value==null)
                    value = temp.getAttributeValue("Type");
                type = temp.getAttributeValue("Type");
                //System.out.println(value+", tipe "+type);
                if (type==null)
                    type = null;
                if (returntype==null)
                    returntype="";
                Node nt;
                if (current.getChildCount() > 0)
                {
                    nt = new Node(data, value, type, visibility,returntype);
                    for (int i = 0; i < current.getChildCount(); i++) 
                    {
                        listChildren(current.getChild(i), nt);
                    }
                } else
                {
                    nt = new Node(data, value, type, visibility,returntype);
                }
                //set as a child to the current object
                //System.out.println("Parent : "+Parent.getName()+" Child : "+nt.getName());
                Parent.addneighbours(nt);
                if (Parent.getName().equals("ReturnType") && nt.getName().equals("Class"))
                {
                    nt.setName("ClassType");
                    //System.out.println("Ini dia: "+nt.getName()+" > "+nt.getValue());
                }
                
                nt.setParent(Parent);
                Parent.addChild(nt);
            }
        }
    
    public void dfs(Node node, QueryNode qs, int dept, int classDept, String className) throws CloneNotSupportedException
	{
            String space = "";
            String nameOfClass = "";
            
            for (int i = 0; i<=dept; i++)
                space += "   ";
            
            //System.out.println(qs.getName()+" = "+node.getName());
            //System.out.println(qs.getRootNode().getName());
            //System.out.println(qs.level+"|"+qs.getNeighbours().size());
            
            if (qs.getName().equals(node.Name))
            {
                //System.out.println("  *"+space+node.Name + " | "+ node.value + " | "+node.getParent().Name);
                //System.out.println(space+node.Name+ " | "+ node.value + " | " + node.type +" | " + node.getParent().getName() + "| child of query :"+qs.getNeighbours().size());
                List<Node> neighbours=node.getNeighbours();
                
                if (node.Name.equals("Class") && (node.getParent().getName().equals("Models") || node.getParent().getName().equals("ModelChildren")))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    
                    this.temp_class = new class_model();
                    this.temp_class.setName(node.value);
                    this.classes.put(node.value, this.temp_class);
                    className = node.value;
                    //className = nameOfClass;
                    
                    ct.Counting();
                }
                
                if (node.Name.equals("Generalization"))
                {
                    this.temp_class.addNumOfGeneralization();
                }
                
                if (node.Name.equals("Realization"))
                {
                    this.temp_class.addNumOfRealization();
                }
                
                if (node.Name.equals("Attribute"))
                {
                    //node.print();
                    this.temp_attribute = new attribute();
                    this.temp_attribute.setName(node.value);
                    this.temp_attribute.setVisibility(node.visibility);                    
                    if (node.type != null)
                    {
                        //System.out.println("Name :"+node.value);
                        //System.out.println("Type : "+node.type);
                        this.temp_attribute.setType(node.type);
                        this.classes.get(className).addAttribute(this.temp_attribute);
                        this.temp_attribute = null;
                    }

                }
                
                if (node.Name.equals("DataType") && node.getParent().getName().equals("Type") && node.getParent().getParent().getName().equals("Attribute"))
                {
                    if (this.temp_attribute != null)
                    {
                        //System.out.println(this.temp_attribute);
                        this.temp_attribute.setType(node.value);
                        this.classes.get(className).addAttribute(this.temp_attribute);
                        this.temp_attribute = null;
                    }
                }
                
                if (node.Name.equals("Operation"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    /*if (this.temp_method != null)
                    {
                        System.out.println(this.classes.get(className).getName()+"<--");
                        this.classes.get(className).addMethod(this.temp_method);
                        this.temp_method = null;
                    }*/
                    this.temp_method = new method();
                    this.temp_method.setName(node.value);
                    this.temp_method.setVisibility(node.visibility);
                    //System.out.println(node.getReturntype());
                    this.temp_method.setReturnType(node.getReturntype());
                    this.classes.get(className).addMethod(this.temp_method);

                }
                
                if (node.Name.equals("DataType") && node.getParent().getName().equals("ReturnType") && node.getParent().getParent().getName().equals("Operation"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    //System.out.println("Masuk");
                    this.temp_method.setReturnType(node.value);
                }
                
                if (node.Name.equals("ClassType") && node.getParent().getName().equals("ReturnType") && node.getParent().getParent().getName().equals("Operation"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    //System.out.println("Masuk");
                    this.temp_method.setReturnType(node.value);
                }
                
                /*if (node.getParent().getName().equals("ReturnType") && node.getParent().getParent().getName().equals("Operation"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    System.out.println("Class Type : "+node.value);
                    this.temp_method.setReturnType(node.value);
                }*/
                                
                
                if (node.Name.equals("Parameter"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    this.temp_parameter = new methodParameter();
                    this.temp_parameter.setName(node.value);
                    if (node.type != null)
                    {
                        this.temp_parameter.setType(node.type);
                        this.temp_method.addParameter(this.temp_parameter);
                    }
                }
                
                if (node.Name.equals("DataType") && node.getParent().getName().equals("Type") && node.getParent().getParent().getName().equals("Parameter"))
                {
                    //System.out.println(space+node.Name+ " | "+ node.value);
                    this.temp_parameter.setType(node.value);
                    this.temp_method.addParameter(this.temp_parameter);
                }
                
                for (int i = 0; i < neighbours.size(); i++) {
                    Node n=neighbours.get(i);
                    QueryNode qnn = qs.getRootNode();
                    //if (n.getName().equals(qnn.getName()))
                    //{
                    //System.out.println(className);
                        dfs(n,qnn,dept+1,classDept,className);
                    //}
                    if(n!=null && !n.visited)
                    {
                        //Node nNode = (Node) n.clone();
                        //dfs(nNode,qs,dept+1,classDept);
                        for (int j = 0; j < qs.neighbours.size(); j++)
                        {
                           QueryNode qN = qs.neighbours.get(j);
                           dfs(n,qN,dept+1,classDept,className);
                        }
                    } 
                }
                node.visited=true;
                
                
            } else
            {
                //System.out.println(space+qs.Name+" -->> "+node.Name);
                List<Node> neighbours=node.getNeighbours();
                //node.visited=true;

                for (int i = 0; i < neighbours.size(); i++) {
                    Node n=neighbours.get(i);
                    if(n!=null && !n.visited)
                    {
                        //Node nNode = (Node) n.clone();
                        dfs(n,qs,dept+1,classDept,className);
                    }
                }
            }
	}
    
    private void getClass(Element temp,String parent, boolean constructor, boolean gettersetter)
    {
        String data;
        String tag_name = temp.getQualifiedName();
            
                if (tag_name.equals("Class")) 
                {
                    data = temp.getAttributeValue("Name");
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
            
            
                if (tag_name.equals("Attribute")) 
                {
                    data = temp.getAttributeValue("Name");
                    String vis = temp.getAttributeValue("Visibility");
                    this.temp_attribute = new attribute();
                    this.temp_attribute.setName(data);
                    this.temp_attribute.setVisibility(vis); // Additional ver. 1.1
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
            
            //Get the class's Attribute's type
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
            
            //Get the class operation, parameters and types
            //Operation
            
                if (tag_name.equals("Operation")) 
                {
                    data = temp.getAttributeValue("Name");
                    String vis_method = temp.getAttributeValue("Visibility");
                    System.out.println(vis_method);
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
                    
                    //if (((!isGetter(data)) && (!isSetter(data)))) //Getter-Setter
                    //{
                        //if (!isDelegation(data)) //Delegation
                        //{
                            //if (!data.equals(this.temp_class.getName())) //Constructor
                            //{
                                this.temp_method = new method();
                                this.temp_method.setName(data);
                                this.temp_method.setVisibility(vis_method);
                            //} else
                            //{
                            //    System.out.println(data + " constructor");
                            //}
                        //} else
                        //{
                        //    System.out.println(data + " delegation");
                        //}
                    //} else
                    //{
                    //    System.out.println(data + " constructor");
                    //}
                    //If getter and setter is included
                    /*if ((gettersetter && isGetter(data)) || ((gettersetter && isSetter(data))))
                    {
                        this.temp_method = new method();
                        this.temp_method.setName(data);
                    }*/
                        
                    //System.out.println(data);
                }
            
            //Return Type
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
            
            //Parameter
                if (tag_name.equals("Parameter")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_parameter = new methodParameter();
                    this.temp_parameter.setName(data);
                    //System.out.println(data);
                }
            
            //Parameter Type
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
    
    private void getClass(String classRoot,Element temp,String parent)
    {
        String data;
        String tag_name = temp.getQualifiedName();
            if (parent.equals(classRoot))
            {
                if (tag_name.equals("Class")) 
                {
                    data = temp.getAttributeValue("Name");
                    if (!(this.temp_method == null))
                    {
                        this.temp_class.addMethod(this.temp_method);
                        this.classes.put(this.temp_class.getName(),this.temp_class);
                        this.temp_method = null;
                        this.temp_class = null;
                    } 
                    
                    this.temp_class = new class_model();
                    this.temp_class.setName(data);
                    
                    System.out.println(data);
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
                    System.out.println(data);
                }
            }
            //Get the class's Attribute's type
            if (parent.equals(classRoot+"|Class|ModelChildren|Attribute|Type"))
            {
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_attribute.setType(data);
                    System.out.println(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = "Attribute Type "+tag_name+": "+temp.getAttributeValue("Name");
                    this.temp_attribute.setType(data);
                    System.out.println(data);
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
                    this.temp_method = new method();
                    this.temp_method.setName(data);
                    System.out.println(data);
                }
            }
            //Return Type
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ReturnType"))
            {
                data = "";
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_method.setReturnType(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_method.setReturnType(data);
                }
                System.out.println(data);
            }
            //Parameter
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ModelChildren"))
            {
                if (tag_name.equals("Parameter")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_parameter = new methodParameter();
                    this.temp_parameter.setName(data);
                    System.out.println(data);
                }
            }
            //Parameter Type
            if (parent.equals(classRoot+"|Class|ModelChildren|Operation|ModelChildren|Parameter|Type"))
            {
                if (tag_name.equals("DataType")) 
                {
                    data = temp.getAttributeValue("Name");
                    this.temp_parameter.setType(data);
                    System.out.println(data);
                } else if (tag_name.equals("Class")) 
                {
                    data = "Parameter Type "+tag_name+": "+temp.getAttributeValue("Name");
                    this.temp_parameter.setType(data);
                    System.out.println(data);
                }
                this.temp_method.addParameter(this.temp_parameter);
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
    
    public boolean isDelegation(String methodName)
    {
        return methodName.matches("^is[A-Z].*");
    }
    
    static class counter
        {
            private int num;
            
            counter()
            {
                this.num = 0;
            }
            
            public void Counting()
            {
                this.num += 1;
            }
            
            public int getNumber()
            {
                return this.num;
            }
        }
}
