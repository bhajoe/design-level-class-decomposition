/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 *
 * @author bhajoe
 */
public class luceneTokenizer {
    private ArrayList<String> tokens = new ArrayList<String>();
    
    public ArrayList<String> getTokens()
    {
        return this.tokens;
    }
    
    public void Tokenizing(String Lbl) throws IOException
    {
        if (Lbl.matches(".*[A-Z].*"))
        {
            String[] lbls = Lbl.split("(?=\\p{Upper})");
            for (String wrd : lbls)
            {
                this.tokens.add(wrd);
            }
        } else
        {
            Reader reader = new StringReader(Lbl);      
            NGramTokenizer gramTokenizer = new NGramTokenizer(reader, 1, 10);

            CharTermAttribute charTermAttribute = gramTokenizer.addAttribute(CharTermAttribute.class);

            WordSegmentation ws = new WordSegmentation();

            while (gramTokenizer.incrementToken()) 
            {
                String token = charTermAttribute.toString();
                if (ws.getDict().contains(token.toLowerCase()))
                {
                    //System.out.println(token);        
                    this.tokens.add(token);
                }
            }
        }
    }
    
    
}
