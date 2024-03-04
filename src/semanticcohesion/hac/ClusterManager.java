/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.util.ArrayList;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author bayu
 */
public class ClusterManager {
    private ArrayList<MultiKeyMap<String,Double>> matrixs;
    private ArrayList<ArrayList<String>> clusters;
    private SimilarityMatrix sm;

    public ClusterManager(SimilarityMatrix sm) {
        this.matrixs = new ArrayList<>();
        this.clusters = new ArrayList<>();
        this.sm = sm;
    }
    
    public void doDynamicClustering(MultiKeyMap<String,Double> matrix)
    {
        ThresholdBasedSplitter TC = new ThresholdBasedSplitter(matrix);
        TC.searchHigestPosition();
        System.out.println("=====================");
        System.out.println("T = "+TC.getHighestDistance());
        TC.printAllHigest();
        ArrayList<MultiKeyMap<String,Double>> subCluster = TC.SplitUp();
        System.out.println("Current Step's Clusters = "+TC.getCluster1()+" & "+TC.getCluster2());
        System.out.println("");
        
        if (TC.getLowerCluster().size() != 0)
        //if (TC.getCluster1().size() != 0)
        {
            doDynamicClustering(TC.getLowerCluster());
            doDynamicClustering(TC.getUpperCluster());
        } 
        if (TC.getCluster1().size()>0)
            this.clusters.add(TC.getCluster1());
        
        cleanCluster();
    }
    
    public void cleanCluster()
    {
        //System.out.println("Cluster 2 : "+this.clusters.get(2).size());
        for (int i = 0; i<this.clusters.size(); i++)
        {
            System.out.println("Size : "+this.clusters.get(i).size());
            if (this.clusters.get(i).size() == 0)
                this.clusters.remove(i);
            else if (this.clusters.get(i) == null)
                System.out.println("Ini null");
        }
        for (int i = 0; i<this.clusters.size(); i++)
        {
            for (int j = 0; j < this.clusters.get(i).size(); j++)
            {
                for (int k = i+1; k < this.clusters.size(); k++)
                {
                    for (int l = 0; l < this.clusters.get(k).size(); l++)
                    {
                        //System.out.print("Cluster "+i+" => "+this.clusters.get(i).get(j));
                        //System.out.println(" vs Cluster "+k+" => "+this.clusters.get(k).get(l));
                        if ((this.clusters.get(i).get(j).equals(this.clusters.get(k).get(l))) && (this.clusters.get(i).size()<=this.clusters.get(k).size()))
                        {
                            this.clusters.remove(k);
                            //System.out.println("Cluster "+k+" removed.");
                            break; 
                        }
                    }
                }
            }
        }
    }
    
    public void doStaticClustering(MultiKeyMap<String,Double> matrix)
    {
        ThresholdBasedSplitter TC = new ThresholdBasedSplitter(matrix);
        TC.searchHigestPosition();
        System.out.println("=====================");
        System.out.println("T = "+TC.getHighestDistance());
        TC.printAllHigest();
        ArrayList<MultiKeyMap<String,Double>> subCluster = TC.SplitUp();
        System.out.println("Current Step's Clusters = "+TC.getCluster1()+" & "+TC.getCluster2());
        if (TC.getCluster1().size() != 0)
        {
            doStaticClustering2(TC.getLowerCluster(),TC.getThreshold());
            doStaticClustering2(TC.getUpperCluster(),TC.getThreshold());
        } else
        {
            if (TC.getCluster1().size()>0)
                this.clusters.add(TC.getCluster1());
                this.clusters.add(TC.getCluster2());
            //if (TC.getCluster2().size()>0)
                //this.clusters.add(TC.getCluster2());
            this.matrixs.addAll(subCluster);
            cleanCluster();
        }
    }
    
    public void doStaticClustering2(MultiKeyMap<String,Double> matrix, double th)
    {
        ThresholdBasedSplitter TC = new ThresholdBasedSplitter(matrix);
        System.out.println("=====================");
        System.out.println("T = "+TC.getHighestDistance());
        TC.setThreshold(th);
        System.out.println("Threshold = "+TC.getThreshold());
        ArrayList<MultiKeyMap<String,Double>> subCluster = TC.SplitUp();
        System.out.println("Current Step's Clusters = "+TC.getCluster1()+" & "+TC.getCluster2());
        
        if (TC.getCluster1().size() != 0)
        {
            doStaticClustering2(TC.getLowerCluster(),TC.getThreshold());
        } else
        {
            //if (TC.getCluster1().size()>0)
            this.clusters.add(TC.getCluster1());
            this.clusters.add(TC.getCluster2());
            //if (TC.getCluster2().size()>0)
                //this.clusters.add(TC.getCluster2());
            this.matrixs.addAll(subCluster);
            cleanCluster();
        }
    }
    
    public ArrayList<MultiKeyMap<String,Double>> getMatrixes()
    {
        return this.matrixs;
    }
    
    public ArrayList<ArrayList<String>> getClusters()
    {
        return this.clusters;
    }
    
    public void PrintCluster()
    {
        for (int i = 0; i<this.clusters.size(); i++)
        {
            System.out.println("Class no. "+(i+1)+" :");
            for (int j = 0; j < this.clusters.get(i).size(); j++)
            {
                System.out.println("   "+this.clusters.get(i).get(j));
            }
            System.out.println("----------");
        }
    }
}
