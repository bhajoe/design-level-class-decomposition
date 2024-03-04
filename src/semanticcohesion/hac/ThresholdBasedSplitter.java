/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author bayu
 */
public class ThresholdBasedSplitter {
    private MultiKeyMap<String,Double> matrix=new MultiKeyMap<>();
    private ArrayList<Double> STranscript;
    private HashMap<String, Double> HighestDistance;
    private double S;
    private double s;
    private double e;
    private double T;
    private MultiKeyMap<String,Double> temp1;
    private MultiKeyMap<String,Double> temp2; 
    private ArrayList<String> cluster1;
    private ArrayList<String> cluster2;

    public ThresholdBasedSplitter(MultiKeyMap<String,Double> matrix) {
        this.matrix = matrix;
        this.STranscript = new ArrayList<>();
        this.HighestDistance = new HashMap<>();
        this.matrix.forEach((key,value)->{
            this.HighestDistance.put(key.getKey(0), 0.0);
        });
        this.temp1 = new MultiKeyMap<>();
        this.temp2 = new MultiKeyMap<>();
        this.cluster1 = new ArrayList<>();
        this.cluster2 = new ArrayList<>();
        //System.out.println(this.HighestDistance);
    }
    
    public double getThreshold()
    {
        return this.T;
    }
    
    public double setThreshold(double th)
    {
        return this.T = th;
    }
    
    public void searchHigestPosition()
    {
        this.matrix.forEach((key,value)->{
            double tempValue = this.HighestDistance.get(key.getKey(0));
            //System.out.println(key.getKey(0)+" "+tempValue);
            this.matrix.forEach((k,v)->{
                //System.out.println("2. "+k+"-"+v);
                double val = this.matrix.get(key.getKey(0), k.getKey(1));
                if ((tempValue < val) && (key.getKey(0).equals(k.getKey(0))))
                {
                    //System.out.println(key.getKey(0)+"="+val);
                    this.HighestDistance.replace(key.getKey(0), val);
                }
                //System.out.println(this.HighestDistance);
            });
        });
        //System.out.println(this.HighestDistance);
        this.STranscript = removeDuplicates(this.HighestDistance);
        CountThreshold();
    }
    
    public HashMap<String, Double> getHighestDistance()
    {
        return this.HighestDistance;
    }
    
    public void printAllHigest()
    {
        System.out.println("S : "+ this.S);
        System.out.println("s : "+ this.s);
        System.out.println("e : "+ this.e);
        System.out.println("Threshold : "+this.T);
    }
    
    private ArrayList<Double> removeDuplicates(HashMap<String, Double> list)
    {
        ArrayList<Double> newList = new ArrayList<>();
  
        for (Double element : list.values()) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
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
    
    public MultiKeyMap<String,Double> getLowerCluster()
    {
        return this.temp2;
    }
    
    public MultiKeyMap<String,Double> getUpperCluster()
    {
        return this.temp1;
    }
    
    public ArrayList<MultiKeyMap<String,Double>> SplitUp()
    {
        this.temp1 = new MultiKeyMap<>();
        this.temp2 = new MultiKeyMap<>();
        ArrayList<MultiKeyMap<String,Double>> subCluster = new ArrayList<>();
        Set<String> set = new HashSet<> ();
                
        for (Map.Entry<String, Double> entry : this.HighestDistance.entrySet()) {
            String ky = entry.getKey();
            double val = entry.getValue();
            if ((val < this.T))
            {
                for (Map.Entry<String, Double> ent : this.HighestDistance.entrySet()) {
                    String k = ent.getKey();
                    double v = ent.getValue();
                    if ((v < this.T))
                    {
                        // Generate lower threshold similarity matrix
                        this.temp2.put(ky, k, this.matrix.get(ky, k));
                        if (!this.cluster2.contains(k))
                            this.cluster2.add(k);
                    } 
                }
            } else
            {
                for (Map.Entry<String, Double> ent : this.HighestDistance.entrySet()) {
                    String k = ent.getKey();
                    double v = ent.getValue();
                    if ((v >= this.T))
                    {
                        // Generate lower threshold similarity matrix
                        //System.out.println(ky+", "+k+" -> "+this.matrix.get(ky, k));
                        this.temp1.put(ky, k, this.matrix.get(ky, k));
                        if (!this.cluster1.contains(k))
                            this.cluster1.add(k);
                    } 
                }
            }
        }
        if ((this.temp1.size() > 0))
        {
            subCluster.add(this.temp1);
        }
        if (((this.temp2.size() > 0)))
        {
            subCluster.add(this.temp2);
        }
        return subCluster;
    }
    
    public ArrayList<String> getCluster1()
    {
        return this.cluster1;
    }
    
    public ArrayList<String> getCluster2()
    {
        return this.cluster2;
    }
}
