/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author bhajoe
 */
public class WordSegmentation {
    private String DictionaryFile = "words.txt";
    private Set<String> dict = new TreeSet<String>();
    private ArrayList<String> words = new ArrayList<String>();
        
    public WordSegmentation() {
        init();
    }

    public Set<String> getDict() {
        return dict;
    }
    
    public ArrayList<String> getWords()
    {
        return this.words;
    }
    
    private void init() 
    {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(DictionaryFile));
            while ((sCurrentLine = br.readLine()) != null) 
            {
                String[] words = sCurrentLine.split("\\|");
                for (String word : words)
                {
                    if (word.length()>2)
                        dict.add(word);    
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
	} finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
        }
	}
        
    }
 
    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        boolean[] t = new boolean[s.length()+1];
        ArrayList<String> words = new ArrayList<String>();
        t[0] = true; //set first to be true, why?
        //Because we need initial state
 
        for(int i=0; i<s.length(); i++){
            //should continue from match position
            if(!t[i]) 
                continue;
 
            for(String a: dict){
                int len = a.length();
                int end = i + len;
                if(end > s.length())
                    continue;
 
                if(t[end]) continue;
 
                if(s.substring(i, end).equals(a))
                {
                    t[end] = true;
                    words.add(s.substring(i, end));
                }
            }
        }
        return words;
    }
    
    public boolean wordBreak(String str)
    {
        int size = str.length();
        /*if (dict.contains("Name"))
            System.out.println("Ada");*/
        //Base case
        if (size == 0) 
        {
            return true;
        }
        
        for (int i=1;i<=size; i++)
        {
            //System.out.println("0,"+i+"=>"+i+","+(size-i));
            //System.out.println(str.substring(0, i)+" "+str.substring(i, size));
            
            if ((dict.contains(str.substring(0, i).toLowerCase())) &&
                    wordBreak(str.substring(i, size).toLowerCase()))
            //if (dict.contains(str.substring(0, i).toLowerCase()))
            {
                //System.out.println(str.substring(0, i));
                //wordBreak(str.substring(i, size).toLowerCase());
                words.add(str.substring(0, i).toLowerCase());
                return true;
            }
        }
        return false;
    }
    
    public void verifyWords()
    {
        int size = 0;
        while (size != this.words.size())
        {
            size = this.words.size();
            for (int i = 0; i<size-1;i++)
            {
                System.out.println((i+1)+" "+size);
                String word_temp = this.words.get(i+1)+this.words.get(i);
                System.out.println(word_temp);
                if (dict.contains(word_temp))
                {
                    System.out.println("Ada");
                    this.words.set(i, this.words.get(i+1)+this.words.get(i));
                    /*this.words.set(i+1,"");*/
                }
            }
        }
    }
}
