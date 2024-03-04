/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author bayu
 */
public class ClusterOptimizer implements Serializable{
    
    private ArrayList<ArrayList<String>> clusters;
    private ArrayList<ArrayList<ClusterElement>> clustersSil;
    private MultiKeyMap<String,Double> dissimilarity;
    private int idLowCluster = 0;
    private int idLowElement = 0;
    private double a;
    private double b;
    private double avgSil;

    public ClusterOptimizer(ArrayList<ArrayList<String>> clusters, MultiKeyMap<String, Double> dissimilarity, double a, double b) {
        this.clusters = clusters;
        this.dissimilarity = dissimilarity;
        this.clustersSil = new ArrayList<>();
        this.a=a;
        this.b=b;
    }
    
    public void optimize() throws IOException
    {
        calcEval();
        findLowestEvalElement();
        double deltaEvalPrev;
        double deltaEvalNext;
        double sumDelta=100;
        double avgSilPrev = 0;
        //while (findNegatifElement())
        boolean a = true;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmss");  
        LocalDateTime now = LocalDateTime.now();  
        LogWriter lw = new LogWriter("C:\\Users\\Bayu\\Documents\\decomposition\\HAC_Log"+dtf.format(now)+".txt");
            
        while (Math.abs(sumDelta) != 0)
        {
            deltaEvalPrev = (this.avgSil+avgSilPrev)/2;
            avgSilPrev = this.avgSil;
            
            findLowestEvalElement();
            moveTheLowestSilhouettes();
            
            deltaEvalNext = (this.avgSil+avgSilPrev)/2;
            sumDelta = deltaEvalNext-deltaEvalPrev;
            lw.writeLog(Double.toString(this.avgSil)+"|"+Double.toString(avgSilPrev)+"|"+Double.toString(deltaEvalNext-deltaEvalPrev)+"|"+Double.toString(sumDelta));
            //System.out.println("PERBANDINGAN : "+avgSilPrev+" >< "+this.avgSil);
            //System.out.println("Temporary Cluster : "+this.clusters);
        }
        System.out.println("");
        System.out.println("============");
        System.out.println("Final Cluster : "+this.clusters);
    }
    
    private void calcEval()
    {
        double sumSil = 0;  
        double sumEval = 0;
        int numMember = 0;
        this.clustersSil.clear();
        for (int i = 0; i<this.clusters.size(); i++) // Iterate Number of cluster
        {
            System.out.println("Cluster "+i);
            ArrayList<ClusterElement> cl = new ArrayList<>();
            for (int j = 0; j < this.clusters.get(i).size(); j++) //Iterate every cluster member
            {
                numMember++;
                System.out.print(this.clusters.get(i).get(j)+" A=");
                // Count inner distance average
                double A = 0;
                double temp_A = 0;
                int numberData = 0;
                int numMPublic = 0;
                double CUsability = -1;
                for (int m = 0; m < this.clusters.get(i).size(); m++) //Iterate inner current cluster
                {
                    if (j!=m)
                    {
                        temp_A += this.dissimilarity.get(this.clusters.get(i).get(j), this.clusters.get(i).get(m));
                        numberData++;
                    }
                    //System.out.println("INI :"+this.clusters.get(i).get(m));
                    
                    if (this.clusters.get(i).get(m).split("\\|")[1].equals("publicMethod")||this.clusters.get(i).get(m).split("\\|")[1].equals("protectedMethod"))
                        numMPublic++;
                }
                
                if (numMPublic>0)
                {
                    CUsability = 1;
                }
                System.out.println("CUsability = "+CUsability);
                
                if (this.clusters.get(i).size() == 1)
                    A = 1;
                else
                    A = temp_A/numberData;
                //System.out.print(A+", ");
                // Count outher distance average
                double B = 0;
                double temp_avgB1 = 1;
                for (int k = 0; k < this.clusters.size(); k++) // Compare with other clusters
                {
                    if (i!=k)
                    {
                        double temp_B2 = 0;
                        double temp_avgB = 0;
                        int numberB = 0;
                        for (int l = 0; l < this.clusters.get(k).size(); l++) // Iterate every other cluster member
                        {
                            temp_B2 += this.dissimilarity.get(this.clusters.get(i).get(j), this.clusters.get(k).get(l));
                            numberB++;
                        }
                        temp_avgB = temp_B2/numberB;
                        if (temp_avgB1 > temp_avgB)
                        {
                            temp_avgB1 = temp_avgB;
                        }
                    }
                    B = temp_avgB1;
                }
                
                double sil = 0;
                if ((A>0)||(B>0))
                    sil = (B-A)/(Math.max(A, B));
                
                double eval = (this.a*sil)+(this.b*CUsability);
                System.out.println("==========================");
                System.out.println("Eval : "+eval);
                sumSil += sil;
                sumEval += eval;
                System.out.println(" A ="+A+"; B ="+B+"; Silhouettes="+sil);
                System.out.println("==========================");
                ClusterElement el = new ClusterElement(this.clusters.get(i).get(j), eval);
                cl.add(el);
            }
            this.clustersSil.add(cl);
        }
        System.out.println("Average Silhouettes :"+(sumSil/numMember));
        System.out.println("Average Eval :"+(sumEval/numMember));
        System.out.println("Average Eval :"+(sumEval/numMember));
        this.avgSil = sumEval/numMember;
        
    }
    
    public double getAvgSil()
    {
        return this.avgSil;
    }
    
    private boolean findNegatifElement()
    {
        double tempSil = 100;
        this.idLowCluster = 0;
        this.idLowElement = 0;
        boolean negatifExist = false;
        for (int i = 0; i<this.clustersSil.size(); i++)
        {
            for (int j = 0; j<this.clustersSil.get(i).size(); j++)
            {
                //System.out.println(tempSil+" <> "+this.clustersSil.get(i).get(j).getSilhouettes());
                if (tempSil > this.clustersSil.get(i).get(j).getSilhouettes())
                {
                    this.idLowCluster = i;
                    this.idLowElement = j;
                    tempSil = this.clustersSil.get(i).get(j).getSilhouettes();
                    if ((tempSil < 0)&&(this.clustersSil.get(i).get(j).getIsMoved()==false))
                    {
                        //System.out.println("negatif :"+tempSil);
                        negatifExist = true;
                        //this.clustersSil.get(i).get(j).setIsMoved();
                    }
                }
            }
        }
        //System.out.println("Lowest is cluster ="+this.idLowCluster+", element ="+this.idLowElement);
        return negatifExist;
    }
    
    private boolean findLowestEvalElement()
    {
        double tempSil = 100;
        this.idLowCluster = 0;
        this.idLowElement = 0;
        boolean negatifExist = false;
        for (int i = 0; i<this.clustersSil.size(); i++)
        {
            for (int j = 0; j<this.clustersSil.get(i).size(); j++)
            {
                //System.out.println(tempSil+" <> "+this.clustersSil.get(i).get(j).getSilhouettes());
                if (tempSil > this.clustersSil.get(i).get(j).getSilhouettes())
                {
                    this.idLowCluster = i;
                    this.idLowElement = j;
                    tempSil = this.clustersSil.get(i).get(j).getSilhouettes();
                    if ((this.clustersSil.get(i).get(j).getIsMoved()==false))
                    {
                        //System.out.println("negatif :"+tempSil);
                        negatifExist = true;
                        //this.clustersSil.get(i).get(j).setIsMoved();
                    }
                }
            }
        }
        //System.out.println("Lowest is cluster ="+this.idLowCluster+", element ="+this.idLowElement);
        return negatifExist;
    }
    
    private void moveTheLowestSilhouettes()
    {
        int preIdLowCluster = this.idLowCluster;
        int preIdLowElement = this.idLowElement;
        int tempClusterId = this.idLowCluster;
        ArrayList<ArrayList<String>> tempCluster = (ArrayList<ArrayList<String>>) this.clusters.clone();
        //Move the lowest element to other cluster
        String element = this.clusters.get(preIdLowCluster).get(preIdLowElement);
        //System.out.println("---->>>"+element);
        double prevSil = getSilOfElement(element);
        this.clusters.get(this.idLowCluster).remove(this.idLowElement);
        for (int i = 0; i<this.clusters.size(); i++) // Iterate Number of cluster
        {
            if (i != preIdLowCluster)
            {
                System.out.println("");
                System.out.println("Move "+element+" to Cluster "+i);
                this.clusters.get(i).add(element);
                calcEval();
                System.out.println("Eval of '"+element+"' : "+getSilOfElement(element));
                removeElement(element);
                if (prevSil < getSilOfElement(element))
                {
                    tempClusterId = i;
                    prevSil = getSilOfElement(element);
                }
                
                /*if (prevSil < getSilOfElement(element))
                {
                    System.out.println("masuk untuk pindah!!");
                    tempClusterId = i;
                    prevSil = getSilOfElement(element);
                } else
                {
                    tempClusterId = this.idLowCluster;
                }*/
            }
        }
        //Put the element to cluster
        this.clusters.get(tempClusterId).add(element);
        calcEval();
        System.out.println("Cluster : "+this.clusters);
        System.out.println("========");        
    }
    
    private double getSilOfElement(String name)
    {
        double result = 0;
        for (int i = 0; i < this.clustersSil.size(); i ++)
        {
            for (int j = 0; j < this.clustersSil.get(i).size(); j++)
            {
                if (this.clustersSil.get(i).get(j).getName().equals(name))
                {
                    result = this.clustersSil.get(i).get(j).getSilhouettes();
                }
            }
        }
        return result;
    }
    
    private void removeElement(String name)
    {
        for (int i = 0; i < this.clusters.size(); i ++)
        {
            for (int j = 0; j < this.clusters.get(i).size(); j++)
            {
                if (this.clusters.get(i).get(j).equals(name))
                {
                    this.clusters.get(i).remove(j);
                }
            }
        }
    }
    
    
    
    
}
