/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;

/**
 *
 * @author bhajoe
 */
public class xmlClassParser {
    private ArrayList<class_model> classes;

    public ArrayList<class_model> getClasses() {
        return classes;
    }
    
    public xmlClassParser() {
        this.classes = new ArrayList<class_model>();
    }
    
    public void parse(File[] XMLFiles) throws ParserConfigurationException, SAXException, IOException
    {
        for (File fl : XMLFiles)
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fl);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Model");
            int index_class = -1;
            for (int i = 0; i < nList.getLength(); i++)
            {
                //Find class
                if (nList.item(i).getAttributes().getNamedItem("modelType").getTextContent().equals("Class"))
                {
                    //System.out.println("index class : "+index_class);
                    //System.out.println("Class : "+nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    class_model cls = new class_model();
                    cls.setName(nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    this.classes.add(cls);
                    index_class++;
                }
                //Find class attributes
                if (nList.item(i).getAttributes().getNamedItem("modelType").getTextContent().equals("Attribute"))
                {
                    attribute att = new attribute();
                    //System.out.print(" Attribute : "+nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    att.setName(nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    for (int ai = 0; ai < nList.item(i).getChildNodes().item(1).getChildNodes().getLength(); ai++)
                    {
                        if (nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getNodeName().equals("TextModelProperty"))
                        {
                            //System.out.println(nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getAttributes().getNamedItem("name").getTextContent());
                            if(nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getAttributes().getNamedItem("name").getTextContent().equals("type"));
                            {
                                for (int x =0;x<nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().getLength();x++)
                                {
                                    //System.out.println("INI : "+nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getNodeName());
                                    if (nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getNodeName().equals("StringValue"))
                                    {
                                        if (nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("value").getTextContent().equals(""))
                                        {
                                            //System.out.println(" Id : "+nList.item(2).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("Id").getTextContent());
                                        } else
                                        {
                                            //System.out.println("attribut nm : "+att.getName());
                                            //System.out.println(" -> Type v : "+nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("value").getTextContent());
                                            att.setType(nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("value").getTextContent());
                                        }
                                    } else if (nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getNodeName().equals("ModelRef"))
                                    {
                                        if (!nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("id").getTextContent().equals(""))
                                        {
                                            Node nd = getModelById(nList.item(i).getChildNodes().item(1).getChildNodes().item(ai).getChildNodes().item(x).getAttributes().getNamedItem("id").getTextContent(),doc);
                                            //System.out.println(" -> Type : "+nd.getAttributes().getNamedItem("name").getTextContent());
                                            att.setType(nd.getAttributes().getNamedItem("name").getTextContent());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.classes.get(index_class).addAttribute(att);
                }
                if (nList.item(i).getAttributes().getNamedItem("modelType").getTextContent().equals("Operation"))
                {
                    method mtd = new method();
                    //System.out.println(" Operation : "+nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    mtd.setName(nList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    for (int p = 0; p<nList.item(i).getChildNodes().getLength(); p++)
                    {
                        Node returnTypeNode = nList.item(i).getChildNodes().item(p);
                        //Find return value method
                        for (int iRT = 0; iRT<returnTypeNode.getChildNodes().getLength(); iRT++)
                        {
                            Node TMP_RT = returnTypeNode.getChildNodes().item(iRT);
                            if (TMP_RT.getNodeName().equals("TextModelProperty"))
                            {
                                if (TMP_RT.getAttributes().getNamedItem("name").getTextContent().equals("returnType"))
                                {
                                    if (TMP_RT.getChildNodes().getLength() > 1)
                                    {
                                        if (TMP_RT.getChildNodes().item(1).getNodeName().equals("ModelRef"))
                                        {
                                            String idRT = TMP_RT.getChildNodes().item(1).getAttributes().getNamedItem("id").getTextContent();
                                            Node ReturnTypeModel = getModelById(idRT, doc);
                                            if (ReturnTypeModel.getAttributes().getNamedItem("modelType").getTextContent().equals("DataType"))
                                            {
                                                mtd.setReturnType(ReturnTypeModel.getAttributes().getNamedItem("name").getTextContent());
                                            } 
                                        } else if (TMP_RT.getChildNodes().item(1).getNodeName().equals("StringValue"))
                                        {
                                            mtd.setReturnType(TMP_RT.getChildNodes().item(1).getAttributes().getNamedItem("value").getTextContent());
                                        }
                                    } else
                                    {
                                        mtd.setReturnType("none");
                                    }
                                } 
                            }
                        }
                        //Find method's parameters
                        if (nList.item(i).getChildNodes().item(p).getNodeName().equals("ChildModels"))
                        {
                            for (int tp=0;tp<nList.item(i).getChildNodes().item(p).getChildNodes().getLength();tp++)
                            {
                                if (nList.item(i).getChildNodes().item(p).getChildNodes().item(tp).getNodeName().equals("Model"))
                                {
                                    if (nList.item(i).getChildNodes().item(p).getChildNodes().item(tp).getAttributes().getNamedItem("modelType").getTextContent().equals("Parameter"))
                                    {
                                        methodParameter nmp = new methodParameter();
                                        //System.out.println("Parameter : "+nList.item(i).getChildNodes().item(p).getChildNodes().item(tp).getAttributes().getNamedItem("name").getTextContent());
                                        nmp.setName(nList.item(i).getChildNodes().item(p).getChildNodes().item(tp).getAttributes().getNamedItem("name").getTextContent());
                                        Node ModelProperties = nList.item(i).getChildNodes().item(p).getChildNodes().item(tp);
                                        for (int z = 0;z<ModelProperties.getChildNodes().item(1).getChildNodes().getLength();z++)
                                        {
                                            if (ModelProperties.getChildNodes().item(1).getChildNodes().item(z).getNodeName().equals("TextModelProperty"))
                                            {
                                                Node TextModelProperty = ModelProperties.getChildNodes().item(1).getChildNodes().item(z);
                                                for (int w = 0;w<TextModelProperty.getChildNodes().getLength();w++)
                                                {
                                                    if (TextModelProperty.getChildNodes().item(w).getNodeName().equals("ModelRef"))
                                                    {
                                                        String idType = TextModelProperty.getChildNodes().item(w).getAttributes().getNamedItem("id").getTextContent();
                                                        Node TypeParam = getModelById(idType, doc);
                                                        if (TypeParam.getAttributes().getNamedItem("modelType").getTextContent().equals("DataType"))
                                                        {
                                                            nmp.setType(TypeParam.getAttributes().getNamedItem("name").getTextContent());
                                                            //System.out.println("    Type nya "+TypeParam.getAttributes().getNamedItem("name").getTextContent());
                                                        } 
                                                    } else if (TextModelProperty.getChildNodes().item(w).getNodeName().equals("StringValue"))
                                                    {
                                                        String strType = TextModelProperty.getChildNodes().item(w).getAttributes().getNamedItem("value").getTextContent();
                                                        //System.out.println("    Type nya "+strType);
                                                        nmp.setType(strType);
                                                    }
                                                }
                                            }
                                        }
                                        mtd.addParameter(nmp);
                                    }          
                                }
                            }
                        }
                    }
                    this.classes.get(index_class).addMethod(mtd);
                }
            }
            //System.out.println("-------------------------------");
        }
    }

    public Node getModelById(String id, Document doc)
    {
        NodeList nList = doc.getElementsByTagName("Model");
        for (int i = 0; i < nList.getLength() ; i++)
        {
            if (nList.item(i).getAttributes().getNamedItem("id").getTextContent().equals(id))
                return nList.item(i);
        }
        return null;
    }    

}


