/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.d3c2;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import semanticcohesion.SCForm;
import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;
import semanticcohesion.dijkmanSemS.dijkmanSemantic;
import semanticcohesion.hac.ClusterManager;
import semanticcohesion.hac.ClusterOptimizer;
import semanticcohesion.hac.SimilarityMatrix;

/**
 *
 * @author bhajoe
 */
public class GBCMMatrix {
    private class_model cclass;
    private double SynWeight = 0.5;
    private double SemWeight = 0.5;
    private double Threshold = 0.5;
    private int[][] SemanticMARmatrix;
    private int MAR;
    private int MMR;
    private int AAR;
    private int attribute;
    private int method;
    private int Capacity;
    private dijkmanSemantic ds = new dijkmanSemantic();
    private SCForm fm;
    
    public void setFM(SCForm form)
    {
        this.fm = form;
    }
    
    public GBCMMatrix(class_model cclass, double wa, double wb, boolean isStatic) throws IOException {
        this.init(wa,wb,isStatic);
    }
    
    public GBCMMatrix()
    {
    }

    public void setCclass(class_model cclass,double wa, double wb, boolean isStatic) throws IOException {
        this.cclass = cclass;
        this.init(wa,wb,isStatic);
    }

    public void setSynWeight(double weight) {
        this.SynWeight = weight;
    }
    
    public void setSemWeight(double weight) {
        this.SemWeight = weight;
    }
    
    public void setThreshold(double th, double sth) {
        this.Threshold = th;
        this.ds.setSemanticThreshold(sth);
    }
    
    public int[][] getSemanticDATMatrix() {
        return SemanticMARmatrix;
    }
    
    private void init(double wa, double wb, boolean isStatic) throws IOException
    {
        this.SemanticMARmatrix = new int[this.cclass.getMethods().size()][this.cclass.getAttributes().size()];
        SimilarityMatrix sm = new SimilarityMatrix();
        //System.out.println("Methods vs Methods");
        //System.out.println("Method size "+this.cclass.getMethods().size());
        for (int i = 0; i<this.cclass.getMethods().size(); i++)
        {
            for (int j = 0; j<this.cclass.getMethods().size(); j++)
            {
                double sem = SemanticSimilarityMethods(this.cclass.getMethods().get(j), this.cclass.getMethods().get(i));
                //System.out.println("x1");
                double syn = 0;
                //System.out.println("x2");
                if (isSameTypeMethods(this.cclass.getMethods().get(j), this.cclass.getMethods().get(i)))
                {
                    //System.out.println("x3");
                    syn = 1;
                } 
                //System.out.println("x4");
                double sim = ((this.SynWeight*syn)+(this.SemWeight*sem));
                //System.out.println("SIM = "+sim);
                sm.setValue(this.cclass.getMethods().get(j).getName()+"|"+this.cclass.getMethods().get(j).getVisibility()+"Method",this.cclass.getMethods().get(i).getName()+"|"+this.cclass.getMethods().get(i).getVisibility()+"Method",sim);
                sm.setValue(this.cclass.getMethods().get(i).getName()+"|"+this.cclass.getMethods().get(i).getVisibility()+"Method",this.cclass.getMethods().get(j).getName()+"|"+this.cclass.getMethods().get(j).getVisibility()+"Method",sim);
                
                /*DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(this.cclass.getMethods().get(j).getName()+","+this.cclass.getMethods().get(i).getName()+","+df.format(sim)+";");
                System.out.println(this.cclass.getMethods().get(i).getName()+","+this.cclass.getMethods().get(j).getName()+","+df.format(sim)+";");*/
            }
        }
        //System.out.println("Methods vs Attributes");
        for (int i = 0; i<this.cclass.getMethods().size(); i++)
        {
            for (int j = 0; j<this.cclass.getAttributes().size(); j++)
            {
                double sem = SemanticSimilarity(this.cclass.getAttributes().get(j), this.cclass.getMethods().get(i));
                double syn = 0;

                if (isSameType(this.cclass.getAttributes().get(j), this.cclass.getMethods().get(i)))
                {
                    syn = 1;
                } 
                
                double sim = ((this.SynWeight*syn)+(this.SemWeight*sem));
                
                sm.setValue(this.cclass.getAttributes().get(j).getName()+"|"+this.cclass.getAttributes().get(j).getVisibility(),this.cclass.getMethods().get(i).getName()+"|"+this.cclass.getMethods().get(i).getVisibility()+"Method",sim);
                sm.setValue(this.cclass.getMethods().get(i).getName()+"|"+this.cclass.getMethods().get(i).getVisibility()+"Method",this.cclass.getAttributes().get(j).getName()+"|"+this.cclass.getAttributes().get(j).getVisibility(),sim);
                
                /*DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(this.cclass.getAttributes().get(j).getName()+","+this.cclass.getMethods().get(i).getName()+","+df.format(sim)+";");
                System.out.println(this.cclass.getMethods().get(i).getName()+","+this.cclass.getAttributes().get(j).getName()+","+df.format(sim)+";");*/
                if (sim > this.Threshold)
                    this.SemanticMARmatrix[i][j] = 1;
                else
                    this.SemanticMARmatrix[i][j] = 0;
            }
        }
        
        //System.out.println("Attributes vs Attributes");
        //System.out.println("Jumlah : "+this.cclass.getAttributes().size());
        for (int i = 0; i<this.cclass.getAttributes().size(); i++)
        {
            for (int j = 0; j<this.cclass.getAttributes().size(); j++)
            {
                double sem = SemanticSimilarityAttributes(this.cclass.getAttributes().get(j), this.cclass.getAttributes().get(i));
                double syn = 0;

                if (isSameTypeAttributes(this.cclass.getAttributes().get(j), this.cclass.getAttributes().get(i)))
                {
                    syn = 1;
                } 
                double sim = ((this.SynWeight*syn)+(this.SemWeight*sem));;
                sm.setValue(this.cclass.getAttributes().get(j).getName()+"|"+this.cclass.getAttributes().get(j).getVisibility(),this.cclass.getAttributes().get(i).getName()+"|"+this.cclass.getAttributes().get(i).getVisibility(),sim);
                sm.setValue(this.cclass.getAttributes().get(i).getName()+"|"+this.cclass.getAttributes().get(i).getVisibility(),this.cclass.getAttributes().get(j).getName()+"|"+this.cclass.getAttributes().get(j).getVisibility(),sim);
                
                /*DecimalFormat df = new DecimalFormat("#.####");
                System.out.println(this.cclass.getAttributes().get(j).getName()+","+this.cclass.getAttributes().get(i).getName()+","+df.format(sim)+";");
                System.out.println(this.cclass.getAttributes().get(i).getName()+","+this.cclass.getAttributes().get(j).getName()+","+df.format(sim)+";");*/
            }
        }
        
        System.out.println("Ini masuk");
        
        ClusterManager cm = new ClusterManager(sm);
        sm.printSimilarity();
        
        //
        int El_Threshold = 33;
        int mEl_Threshold = 16;
        System.out.println("Element = "+(this.cclass.getMethods().size()+this.cclass.getAttributes().size()));
        System.out.println("m Element = "+this.cclass.getMethods().size());
        
        if (((this.cclass.getMethods().size()+this.cclass.getAttributes().size()) > El_Threshold) || (this.cclass.getMethods().size() > mEl_Threshold))
        {
            System.out.println("Dynamic Threshold");
            cm.doDynamicClustering(sm.getSimilarityMatrix());
        } else
        {
            System.out.println("Static Threshold");
            cm.doStaticClustering(sm.getSimilarityMatrix());
        }
        
        /*if (isStatic) {
            System.out.println("Static Threshold");
            cm.doStaticClustering(sm.getSimilarityMatrix());
        } else {
            System.out.println("Dynamic Threshold");
            cm.doDynamicClustering(sm.getSimilarityMatrix());
        }*/
        
        System.out.println("Temporary Clusters :");
        cm.PrintCluster();
        //System.out.println(cm.getClusters());
        System.out.println("==========================");
        System.out.println("Silhouettes Calculation!!!");
        ClusterOptimizer SCO = new ClusterOptimizer(cm.getClusters(), sm.getDissimilarityMatrix(),wa,wb);
        SCO.optimize();
        cm.cleanCluster();
        //System.out.println(cm.getClusters());
        System.out.println("==========================");
        cm.PrintCluster();
        
                
        int m = this.cclass.getMethods().size(); //methods
        this.method = m;
        int a = this.cclass.getAttributes().size(); //attributes
        this.attribute = a;
        this.Capacity = (int) ((m+a)*((m+a)-1))/2;
        
        
        //count MAR
        int mar = 0;
        for (int r = 0; r<m; r++)
        {
            for (int c = 0; c<a; c++)
            {
                //System.out.print(this.SemanticMARmatrix[r][c]);
                if (this.SemanticMARmatrix[r][c] == 1)
                    mar++;
            }
            //System.out.println("");
        }
        this.MAR = mar;
        
        //count MMR
        HashMap<String, String> mmr = new HashMap<String, String>();
        //int mmr = 0;
        
        for (int c = 0; c<a; c++)
        {
            for (int i = 0; i<(m-1); i++)
            {
                for (int r = i+1; r<m; r++)
                {
                    if ((this.SemanticMARmatrix[i][c] == 1) && (this.SemanticMARmatrix[r][c] == 1))
                        mmr.put(Integer.toString(i)+Integer.toString(r), this.cclass.getMethodById(i).getName()+"-"+this.cclass.getMethodById(r).getName());
                }
            }
        }
        this.MMR = mmr.size();
        
        //count AAR
        HashMap<String, String> aar = new HashMap<String, String>();
        //int aar = 0;
        
        for (int r = 0; r<m; r++)
        {
            for (int i = 0; i<(a-1); i++)
            {
                for (int c = i+1; c<a; c++)
                {
                    if ((this.SemanticMARmatrix[r][i] == 1) && (this.SemanticMARmatrix[r][c] == 1))
                        aar.put(Integer.toString(i)+Integer.toString(c), this.cclass.getAttributeById(i).getName()+"-"+this.cclass.getAttributeById(c).getName());
                }
            }
        }
        this.AAR = aar.size();
        
    }
    
    private boolean isSameType(attribute att, method mtd)
    {
        boolean result = false;
        //System.out.println(att.getName()+"("+att.getType()+") vs "+mtd.getName());
        
        /*for (methodParameter mp : mtd.getParameters())
        {
            //System.out.println("    > "+att.getType()+" ? "+mp.getName()+" ("+mp.getType()+")");
            if (att.getType().equals(mp.getType()))
            {
                result = true;
            } 
        }*/
        if ((mtd.isTypeisExist(att.getType())) || (att.getType().equals(mtd.getReturnType())))
        {
            result = true;
        }
        /*if (att.getType().equals(mtd.getReturnType()))
        {
            result = true;
        } */
        
        return result;
    }
    
    private boolean isSameTypeMethods(method mtd1, method mtd2)
    {
        //System.out.println("e1");
        boolean result = false;
        //System.out.println(att.getName()+"("+att.getType()+") vs "+mtd.getName());
        //System.out.println("e2"+mtd2.getReturnType()+" vs "+mtd1.getReturnType());
        //System.out.println("e2"+mtd2.getName()+" vs "+mtd1.getName());
        if (mtd2.getReturnType().equals(mtd1.getReturnType()))
                result = true;
        //System.out.println("e3");
        for (methodParameter mp : mtd2.getParameters())
        {
            //System.out.println("e4");
            if ((mtd1.isTypeisExist(mp.getType())) || (mp.getType().equals(mtd1.getReturnType())))
            {
                //System.out.println("e5");
                result = true;
            }
        }
        
        return result;
    }
    
    private boolean isSameTypeAttributes(attribute atr1, attribute atr2)
    {
        boolean result = false;
        //System.out.println(att.getName()+"("+att.getType()+") vs "+mtd.getName());
        
        if (atr1.getType().equals(atr2.getType()))
        {
            result = true;
        }
        return result;
    }
    
    /*private boolean isEqualSemanticallyssd(attribute att, method mtd)
    {
        boolean result = false;
        ds.setL1(att.getName());
        String l2 = mtd.getName();
        for (methodParameter mp : mtd.getParameters())
        {
             l2 += mp.getName();
        }
        ds.setL2(l2);
        double sem = ds.countSemanticSimilarity();
        //System.out.println(ds.getL1()+" vs "+ds.getL2()+" = "+sem);
        if (sem>this.threshold)
        {
            result = true;
        }
        return result;
    }*/
    
    private double SemanticSimilarity(attribute att, method mtd)
    {
        ds.setL1(att.getName());
        String l2 = mtd.getName();
        for (methodParameter mp : mtd.getParameters())
        {
             l2 += mp.getName();
        }
        ds.setL2(l2);
        double sem = ds.countSemanticSimilarity();
        
        return sem;
    }
    
    private double SemanticSimilarityMethods(method mtd1, method mtd2)
    {
        String l1 = mtd1.getName();
        String l2 = mtd2.getName();
        for (methodParameter mp : mtd1.getParameters())
        {
             l1 += mp.getName();
        }
        for (methodParameter mp : mtd2.getParameters())
        {
             l2 += mp.getName();
        }
        ds.setL1(l1);
        ds.setL2(l2);
        double sem = ds.countSemanticSimilarity();
        
        return sem;
    }
    
    private double SemanticSimilarityAttributes(attribute attr1, attribute attr2)
    {
        String l1 = attr1.getName();
        String l2 = attr2.getName();
        ds.setL1(l1);
        ds.setL2(l2);
        double sem = ds.countSemanticSimilarity();
        
        return sem;
    }
    
    public int getMAR()
    {
        return this.MAR;
    }
    
    public int getMMR()
    {
        return this.MMR;
    }
    
    public int getAAR()
    {
        return this.AAR;
    }
    
    public int getCapacity()
    {
        return this.Capacity;
    }
    
    public int getNumAttribute()
    {
        return this.attribute;
    }
    
    public int getNumMethod()
    {
        return this.method;
    }
    
    public double calculateCohesion()
    {
        if ((getNumMethod() == 1) && (getNumAttribute() == 0))
        {
            return 1;
        } else if((getNumMethod() == 0) && (getNumAttribute() > 0))
        {
            return 0;
        } else if((getNumMethod() > 1) && (getNumAttribute() == 0))
        {
            return 0;
        } else if((getNumMethod() == 0) && (getNumAttribute() == 0))
        {
            return 0;
        } else
        {
            return (double) (getMAR()+getMMR()+getAAR())/getCapacity();
        }
    }
    
    public int getPrivateAttr()
    {
        return this.cclass.getPrivateAttr();
    }
    
    public int getPublicAttr()
    {
        return this.cclass.getPublicAttr();
    }
    
    public int getProtectedAttr()
    {
        return this.cclass.getProtectedAttr();
    }
    
    public int getPrivateMethod()
    {
        return this.cclass.getPrivateMethod();
    }
    
    public int getPublicMethod()
    {
        return this.cclass.getPublicMethod();
    }
    
    public int getProtectedMethod()
    {
        return this.cclass.getProtectedMethod();
    }
    
    public int getNumberOfGeneralization()
    {
        return this.cclass.getNumOfGeneralization();
    }
    
    public int getNumberOfRealization()
    {
        return this.cclass.getNumOfRealization();
    }
    
}
