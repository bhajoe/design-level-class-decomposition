/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.wordnet;

import java.util.ArrayList;

/**
 *
 * @author bayu
 */
public class MySegmenWord {
    
    private String label;

    MySegmenWord(String lbl) {
        this.label = lbl+" ";
    }

    public MySegmenWord() {
    }
    
    public void setLabel(String lbl)
    {
        this.label = lbl;
    }
    
    public ArrayList<String> getWords()
    {
        int start=0;
        int prev_start=0;
        boolean go = true;
        ArrayList<String> list = new ArrayList<>();
        while (go)
        {
            //System.out.println(prev_start+" "+start+" "+this.label.length());
            for (int i=this.label.length();i>=start;i--)
            {
                WordSegmentation dict = new WordSegmentation();
                if (dict.getDict().contains(this.label.substring(start, i).toLowerCase()))
                {
                    list.add(this.label.substring(start,i));
                    start = i;
                } else
                {
                    if (this.label.substring(start,i).length()<2)
                    {
                        go = false;
                    }
                }
            }
        }
        return list;
    }
}
