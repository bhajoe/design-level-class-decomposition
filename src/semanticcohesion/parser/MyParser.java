/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import semanticcohesion.data.attribute;
import semanticcohesion.data.method;

/**
 *
 * @author bhajoe
 */
public class MyParser {
    private ArrayList<method> Methods;
    private ArrayList<attribute> Attributes;
    private String pathFile;
    private CompilationUnit cu;

    public MyParser(String path) throws ParseException, IOException {
        this.pathFile = path;
        init();
    }
    
    private void init() throws FileNotFoundException, ParseException, IOException
    {
        FileInputStream in = new FileInputStream(this.pathFile);
        try {
            // parse the file
            this.cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
    }
    
    public ArrayList<method> getMethods()
    {
        return this.Methods;
    }
    
    public ArrayList<attribute> getAttributes()
    {
        return this.Attributes;
    }
    
    public void parse()
    {
        MethodParser mp = new MethodParser();
        mp.visit(this.cu, null);
        this.Methods = mp.getMethods();
        
        FieldParser fp = new FieldParser();
        fp.visit(this.cu, null);
        this.Attributes = fp.getAttributes();
    }
}
