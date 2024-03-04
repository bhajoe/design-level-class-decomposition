/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;

/**
 *
 * @author bhajoe
 */
public class WordNetDB {
    private static ILexicalDatabase db = new NictWordNet();
    
    public ILexicalDatabase getDb()
    {
        return this.db;
    }
    
}
