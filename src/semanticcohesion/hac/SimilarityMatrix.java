/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author bayu
 */
public class SimilarityMatrix {
    private MultiKeyMap<String,Double> similarity=new MultiKeyMap<>();
    private MultiKeyMap<String,Double> dissimilarity=new MultiKeyMap<>();
    
    public SimilarityMatrix() {
    }
    
    public void setValue(String x, String y, double value)
    {
        if (!x.equals(y))
        {
            this.dissimilarity.put(x,y,(1-value));
            this.similarity.put(x,y,value);
        } else
        {
            this.dissimilarity.put(x,y, 0.0);
            this.similarity.put(x,y, 0.0);
        }
    }
    
    public void setValue(String data)
    {
        String[] dt = data.split(";");
        System.out.println("length = "+dt.length);
        for (int i = 0; i < dt.length; i++)
        {
            //System.out.println(dt[i]);
            String x = dt[i].split(",")[0];
            String y = dt[i].split(",")[1];
            double value = Double.parseDouble(dt[i].split(",")[2]);
            setValue(x,y,value);
        }
        while (this.similarity.values().remove(null));
        
    }
    
    public MultiKeyMap<String,Double> getSimilarityMatrix()
    {
        return this.similarity;
    }
    
    public MultiKeyMap<String,Double> getDissimilarityMatrix()
    {
        return this.dissimilarity;
    }
    
    public void printSimilarity()
    {
        System.out.println("Similarity Matrix");
        /*for (int x=0; x<this.label.length; x++)
            System.out.print(this.label[x]+" ");*/
        System.out.println("");
        this.similarity.forEach((key,value)->{
            System.out.println(key.getKey(0)+" & "+key.getKey(1)+" -> "+value);
        });
    }
    
    public void printDissimilarity()
    {
        System.out.println("Dissimilarity Matrix");
        /*for (int x=0; x<this.label.length; x++)
            System.out.print(this.label[x]+" ");*/
        System.out.println("");
        this.dissimilarity.forEach((key,value)->{
            System.out.println(key.getKey(0)+" & "+key.getKey(1)+" -> "+value);
        });
    }
    
    /*public double[][] findMatrix(int[] indexArray)
    {
        double[][] cpArray = new double[indexArray.length][indexArray.length];
        for (int i = 0; i < indexArray.length; i++)
        {
            for (int j = 0; j < indexArray.length; j++)
            {
                cpArray[i][j] = this.dissimilarity[indexArray[i]][indexArray[j]];
            }
        }
        return cpArray;
    }*/
        
    
}
