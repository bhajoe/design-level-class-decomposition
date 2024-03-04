/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import java.io.IOException;

/**
 *
 * @author bhajoe
 */
public class testToken {
    
    public static void main(String[] args) throws IOException
    {
        //luceneTokenizer lt = new luceneTokenizer();
        //lt.Tokenizing("getName");
        
        DistanceCounter dc = new DistanceCounter();
        dc.setWord1("configure");
        dc.setWord2("arrange");
        System.out.println(dc.countDistance());
       //System.out.println("Test : "+dc.testCalc("genders","sex"));
    }
    
}
