/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.PorterStemmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import java.util.List;

/**
 *
 * @author bhajoe
 */
public class DistanceCounter extends WordNetDB{
    private String word1;
    private String word2;
    private double threshold = 0.5;
    //Singleton
    private static DistanceCounter instance = new DistanceCounter();

    //Get instance of DistanceCounter
    public static DistanceCounter getInstance()
    {
        return instance;
    }
    
    public DistanceCounter() {
    }
    
    public void setWord1(String w1)
    {
        this.word1 = w1;
    }
    
    public void setWord2(String w2)
    {
        this.word2 = w2;
    }
    
    public void setThreshold(double th)
    {
        this.threshold = th;
    }
    
    /*public double countDistance()
    {
        WS4JConfiguration.getInstance().setMFS(true);
        WuPalmer wp = new WuPalmer(getDb());
        
        double s = wp.calcRelatednessOfWords(this.word1, this.word2);
        if (s > 1.0)
            s = 1.0;
        //if (s > threshold)
            //System.out.println(this.word1+" vs "+this.word2+" = "+s);
        return s;
        
        
    }*/
    
    public double countDistance()
    {
        ILexicalDatabase db = new NictWordNet();
        WS4JConfiguration.getInstance().setMFS(true);
        RelatednessCalculator rc = new WuPalmer(db);
        PorterStemmer ps = new PorterStemmer();
        
        String wd1 = ps.stemWord(this.word1);
        String wd2 = ps.stemWord(this.word2);

        List<POS[]> posPairs = rc.getPOSPairs();
        double maxScore = -1D;

        for(POS[] posPair: posPairs) {
            List<Concept> synsets1 = (List<Concept>)db.getAllConcepts(wd1, posPair[0].toString());
            List<Concept> synsets2 = (List<Concept>)db.getAllConcepts(wd2, posPair[1].toString());

            for(Concept synset1: synsets1) {
                for (Concept synset2: synsets2) {
                    Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
                    double score = relatedness.getScore();
                    if (score > maxScore) { 
                        maxScore = score;
                    }
                }
            }
        }

        if (maxScore == -1D) {
            maxScore = 0.0;
        }

        return maxScore;
    }
    
    public boolean isRelated()
    {
        if (countDistance() >= this.threshold)
        {
            return true;
        } else
            return false;
    }
    
}
