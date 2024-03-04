/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.dijkmanSemS;

import java.io.IOException;
import java.util.ArrayList;
import semanticcohesion.wordnet.DistanceCounter;
import semanticcohesion.wordnet.WordSegmentation;
import semanticcohesion.wordnet.luceneTokenizer;

/**
 *
 * @author bhajoe
 */
public class dijkmanSemantic {
    private String l1;
    private String l2;
    private ArrayList<String> w1;
    private ArrayList<String> w2;
    private double wi = 1.0;
    private double ws = 0.75;
    private DistanceCounter dc = DistanceCounter.getInstance();
    private WordSegmentation wSeg;

    public dijkmanSemantic() {   
        this.wSeg = new WordSegmentation();
    }
    
    public dijkmanSemantic(ArrayList<String> w1, ArrayList<String> w2) {
        this.wSeg = new WordSegmentation();
        this.w1 = w1;
        this.w2 = w2;
    }
    
    public dijkmanSemantic(String l1, String l2)
    {
        luceneTokenizer lt = new luceneTokenizer();
        this.l1 = l1;
        this.l2 = l2;
        try
        {
            lt.Tokenizing(this.l1);
            this.w1 = lt.getTokens();
            lt.Tokenizing(this.l2);
            this.w2 = lt.getTokens();
        } catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void setSemanticThreshold(double th)
    {
        this.dc.setThreshold(th);
    }

    public void setL1(String l1) 
    {
        //System.out.println(l1);
        //WordSegmentation ws1 = new WordSegmentation();
        this.l1 = l1;
        //ws1.wordBreak(this.l1);
        //ws1.verifyWords();
        //this.w1 = ws1.getWords();
        
        
        try 
        {
            luceneTokenizer lt = new luceneTokenizer();
            lt.Tokenizing(this.l1);
            this.w1 = lt.getTokens();
        } catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }

    public void setL2(String l2) {
        //WordSegmentation ws2 = new WordSegmentation();
        this.l2 = l2;
        //ws2.wordBreak(this.l2);
        //ws2.verifyWords();
        //this.w2 = ws2.getWords();
        
        try 
        {
            luceneTokenizer lt = new luceneTokenizer();
            lt.Tokenizing(this.l2);
            this.w2 = lt.getTokens();
        } catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }
    
    
    
    public ArrayList<String> getW1() {
        return w1;
    }
    
    public int getW1Length() {
        return w1.size();
    }

    public void setW1(ArrayList<String> w1) {
        this.w1 = w1;
    }

    public ArrayList<String> getW2() {
        return w2;
    }
    
    public int getW2Length() {
        return w2.size();
    }

    public void setW2(ArrayList<String> w2) {
        this.w2 = w2;
    }

    public double getWi() {
        return wi;
    }

    public void setWi(double wi) {
        this.wi = wi;
    }

    public double getWs() {
        return ws;
    }

    public void setWs(double ws) {
        this.ws = ws;
    }

    public String getL1() {
        return l1;
    }

    public String getL2() {
        return l2;
    }
    
    public double countSemanticSimilarity()
    {
        return ((((2*wi*getNumIntersection())+(ws*getNumSemantic()))/(w1.size()+w2.size()))) > 1 ? 1 : (((2*wi*getNumIntersection())+(ws*getNumSemantic()))/(w1.size()+w2.size()));
    }
    
    private int getNumIntersection()
    {
        int NumIntersection = 0;
        
        for (int i=0; i < this.w1.size(); i++)
        {
            for (int j=0; j< this.w2.size(); j++)
            {
                if (this.w1.get(i).toUpperCase().equals(this.w2.get(j).toUpperCase()))
                {
                    //System.out.println(w1[i]+" = "+w2[j]);
                    NumIntersection++;
                }
            }
        }
        return NumIntersection;
    }
    
    private int getNumSemantic()
    {
        int NumSemantic = 0;
        for (int i=0; i < this.w1.size(); i++)
        {
            for (int j=0; j< this.w2.size(); j++)
            {
                dc.setWord1(this.w1.get(i).toUpperCase());
                dc.setWord2(this.w2.get(j).toUpperCase());
                //System.out.println(this.w1.get(i).toUpperCase()+" >< "+this.w2.get(j).toUpperCase());
                if ((dc.isRelated()) && (!w1.get(i).toUpperCase().equals(w2.get(j).toUpperCase())))
                {
                    //System.out.println(w1[i]+" = "+w2[j]);
                    NumSemantic++;
                }
            }
        }
        return NumSemantic;
    }
}
