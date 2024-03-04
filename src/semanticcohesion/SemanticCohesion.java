/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion;

import semanticcohesion.d3c2.DATSemanticMatrix;
import japa.parser.ParseException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import nu.xom.ParsingException;
import org.xml.sax.SAXException;
import semanticcohesion.d3c2.GBCMMatrix;
import semanticcohesion.data.class_model;
import semanticcohesion.dijkmanSemS.dijkmanSemantic;
import semanticcohesion.parser.MyParser;
import semanticcohesion.xml.xmlClassParser;
import semanticcohesion.xml.xmlXOMClassExtractor;

/**
 *
 * @author bhajoe
 */
public class SemanticCohesion {

        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        //SCForm scf = new SCForm();
        //scf.show();
        HashMap<String, class_model> classes;
        try {
            // TODO add your handling code here:
            
            //xmlClassParser xmlP = new xmlClassParser();
            xmlXOMClassExtractor xmlP = new xmlXOMClassExtractor();
            File[] XMLFiles = getFiles(args[0], ".xml");
            xmlP.parse(XMLFiles);
            classes = xmlP.getClasses();
            
            GBCMMatrix mar = new GBCMMatrix();
            mar.setSynWeight(0.5);
            mar.setSemWeight(0.5);
            classes.values().stream().map((cls) -> {
                try {
                    //for (attribute att : cls.getAttributes())
                    //System.out.println(att.getName()+"*->"+att.getType());
                    mar.setCclass(cls,0.5,0.5,true);
                } catch (IOException ex) {
                    Logger.getLogger(SemanticCohesion.class.getName()).log(Level.SEVERE, null, ex);
                }
                return cls;
            }).map((cls) -> {
                System.out.print(cls.getName());
                return cls;
            }).forEachOrdered((_item) -> {
                System.out.print("|"+mar.getMAR());
                System.out.print("|"+mar.getMMR());
                System.out.print("|"+mar.getAAR());
                System.out.print("|"+mar.getCapacity());
                System.out.println("|"+mar.calculateCohesion());
            });
        } catch (IOException | ParsingException ex) {
            Logger.getLogger(SCForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void countD3C2fromXML(String path_folder) throws ParserConfigurationException, SAXException, IOException
    {
        xmlClassParser xmlP = new xmlClassParser();
        File[] XMLFiles = getFiles(path_folder, ".xml");
        xmlP.parse(XMLFiles);
        ArrayList<class_model> classes = xmlP.getClasses();
        DATSemanticMatrix matrix = new DATSemanticMatrix();
        matrix.setThreshold(0.5,0.5);
        for (class_model cls : classes)
        {
            
            //for (attribute att : cls.getAttributes())
                //System.out.println(att.getName()+"*->"+att.getType());
            matrix.setCclass(cls);
            System.out.print(cls.getName());
            System.out.print("|"+matrix.getMMAC(0)); // MMAC
            System.out.print("|"+matrix.getAAC(0)); // ACC
            System.out.print("|"+matrix.getAMC(0)); //AMC
            System.out.print("|"+matrix.getd3c2(0)); //D3C2
            System.out.print("|"+matrix.getMMAC(1)); //MMAC
            System.out.print("|"+matrix.getAAC(1)); //ACC
            System.out.print("|"+matrix.getAMC(1)); //AMC
            System.out.println("|"+matrix.getd3c2(1)); //D3C2
            
            /*System.out.println("DAT Matrix :");
            for (int i = 0; i<cls.getMethods().size(); i++)
            {
                //System.out.print(cls.getMethods().get(i).getName()+"    ");
                for (int j = 0; j<cls.getAttributes().size(); j++)
                {
                    System.out.print("("+cls.getAttributes().get(j).getName()+") "+cls.getAttributes().get(j).getType()+"->"+matrix.getDATMatrix()[i][j]+"   ");
                }
                System.out.println(">>"+cls.getMethods().get(i).getName());
            }
            
            
            System.out.println("Semantic DAT Matrix :");
            for (int i = 0; i<cls.getMethods().size(); i++)
            {
                //System.out.print(cls.getMethods().get(i).getName()+"    ");
                for (int j = 0; j<cls.getAttributes().size(); j++)
                {
                    System.out.print("("+cls.getAttributes().get(j).getName()+") "+cls.getAttributes().get(j).getType()+"->"+matrix.getSemanticDATMatrix()[i][j]+"   ");
                }
                System.out.println(">>"+cls.getMethods().get(i).getName());
            }*/
            //System.out.println("--------------------------");
        }
    }
    
    private static void countD3C2(String path_folder) throws ParseException, IOException
    {
        //"E:\\My Research\\Semantic Cohesion 2016\\Dataset\\jdraw\\jdraw\\data"
        String path = path_folder;
        File[] fl = getFiles(path,".java");
        
        for (File file : fl)
        {
            System.out.println("File :"+file.getName());
            MyParser mp = new MyParser(path+"\\"+file.getName());
            mp.parse();
            
            System.out.print("  ");
            for (int x = 0; x < mp.getAttributes().size(); x++)
                System.out.print("    a"+x);
            System.out.println("");
            
            for (int i = 0; i < mp.getMethods().size(); i++)
            {
                System.out.print("m"+i);
                for (int j = 0; j < mp.getAttributes().size(); j++)
                {
                    int value = 0;
                    for (int p = 0; p < mp.getMethods().get(i).getParameters().size(); p++)
                    {
                        if (mp.getMethods().get(i).getParameters().get(p).getType().equals(mp.getAttributes().get(j).getType()))
                            value = 1;
                    }
                    System.out.print("     "+value);
                }
                System.out.println("");
            }
            System.out.println("\nMethod : ");
            for (int i = 0; i < mp.getMethods().size(); i++)
            {
                System.out.println("m"+i+" : "+mp.getMethods().get(i).getName());
            }
            System.out.println("\nAttribute : ");
            for (int j = 0; j < mp.getAttributes().size(); j++)
            {
                System.out.println("a"+j+" : "+mp.getAttributes().get(j).getType());
            }
        }
    }
    
    private static void countSemanticLabel(String path_folder) throws IOException, ParseException
    {
        dijkmanSemantic ds = new dijkmanSemantic();
        //"E:\\My Research\\Semantic Cohesion 2016\\Dataset\\jdraw\\jdraw\\data"
        String path = path_folder;
        File[] fl = getFiles(path,".java");
        
        for (File file : fl)
        {
            //write to txt
            File file_w = new File(path+"\\"+file.getName()+".txt");
            // if file doesnt exists, then create it
            if (!file_w.exists()) {
                file_w.createNewFile();
            }
            FileWriter fw = new FileWriter(file_w.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            System.out.println("File :"+file.getName());
            MyParser mp = new MyParser(path+"\\"+file.getName());
            mp.parse();
            for (int i = 0; i < mp.getMethods().size(); i++)
            {
                for (int j = 0; j < mp.getAttributes().size(); j++)
                {
                    ds.setL1(mp.getAttributes().get(j).getName());
                    ds.setL2(mp.getMethods().get(i).getName());
                    System.out.print(".");
                    bw.write(ds.getL1()+"|"+ds.getL2()+"|"+ds.countSemanticSimilarity()+"\n");
                }
                System.out.println("");
            }
            bw.close();
            System.out.println(file.getName()+".txt Done...");
        }
    }
    
    private static File[] getFiles(String path,String type) 
    {
        File dir = new File(path);
        return dir.listFiles((File dir1, String name) -> name.toLowerCase().endsWith(type));
    }
}
