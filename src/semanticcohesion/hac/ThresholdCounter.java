/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.util.ArrayList;

/**
 *
 * @author bayu
 */
public class ThresholdCounter {
    
    private String[] label;
    private ArrayList<Double> value;
    private ArrayList<Double> STranscript;
    private double[][] matrix;
    private double S;
    private double s;
    private double e;
    private double T;

    public ThresholdCounter(String[] label, double[][] matrix) {
        this.label = label;
        this.matrix = matrix;
        this.value = new ArrayList<>();
    }
    
    public void searchHigestPosition()
    {
        for (int i = 0; i<this.matrix.length;i++)
        {
            double max = 0;
            String lb = "";
            for (int j = 0; j<this.matrix.length;j++)
            {
                if (max < this.matrix[i][j])
                {
                    max = this.matrix[i][j];
                }
            }
            this.value.add(max);
        }
        
        this.STranscript = removeDuplicates(this.value);
        CountThreshold();
    }
    
    public void printAllHigest()
    {
        for (int i = 0; i<this.STranscript.size(); i++)
        {
            System.out.println(this.STranscript.get(i));
        }
        System.out.println("S : "+ this.S);
        System.out.println("s : "+ this.s);
        System.out.println("e : "+ this.e);
        System.out.println("Threshold : "+this.T);
    }
    
    private ArrayList<Double> removeDuplicates(ArrayList<Double> list)
    {
        // Create a new ArrayList
        ArrayList<Double> newList = new ArrayList<>();
  
        // Traverse through the first list
        for (Double element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
    
    private void CountThreshold()
    {
        this.S = 0;
        this.s = 10000;
        this.e = 0;
        double sum_e = 0;
        for (double value : this.STranscript)
        {
            if (value > this.S)
                this.S = value;
            if (value < this.s)
                this.s = value;
            sum_e += value;
        }
        
        this.e = sum_e/this.STranscript.size();
        this.T = ((this.S - this.s)*this.e)+this.s;
    }
}
