/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.d3c2;

import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;
import semanticcohesion.dijkmanSemS.dijkmanSemantic;

/**
 *
 * @author bhajoe
 */
public class DATSemanticMatrix {
    private class_model cclass;
    private double threshold = 0.5;
    private int[][] DATmatrix;
    private int[][] SemanticDATmatrix;
    private dijkmanSemantic ds = new dijkmanSemantic();
    
    public DATSemanticMatrix(class_model cclass) {
        this.init();
    }
    
    public DATSemanticMatrix()
    {
    }

    public void setCclass(class_model cclass) {
        this.cclass = cclass;
        this.init();
        
    }

    public void setThreshold(double threshold, double sth) {
        this.threshold = threshold;
        this.ds.setSemanticThreshold(sth);
    }
    
    public int[][] getDATMatrix() {
        return DATmatrix;
    }
    
    public int[][] getSemanticDATMatrix() {
        return SemanticDATmatrix;
    }
    
    private void init()
    {
        this.DATmatrix = new int[this.cclass.getMethods().size()][this.cclass.getAttributes().size()];
        this.SemanticDATmatrix = new int[this.cclass.getMethods().size()][this.cclass.getAttributes().size()];
        
        for (int i = 0; i<this.cclass.getMethods().size(); i++)
        {
            for (int j = 0; j<this.cclass.getAttributes().size(); j++)
            {
                //System.out.println(this.cclass.getAttributes().get(j).getName()+"("+this.cclass.getAttributes().get(j).getType()+") vs "+this.cclass.getMethods().get(i).getName());
                if (isSameType(this.cclass.getAttributes().get(j), this.cclass.getMethods().get(i)))
                {
                    this.DATmatrix[i][j] = 1;
                    this.SemanticDATmatrix[i][j] = 1;
                } else if (isEqualSemantically(this.cclass.getAttributes().get(j), this.cclass.getMethods().get(i)))
                {
                    if (this.DATmatrix[i][j] != 1)
                    {
                        this.DATmatrix[i][j] = 0;
                    }
                    this.SemanticDATmatrix[i][j] = 1;
                } else
                {
                    this.DATmatrix[i][j] = 0;
                    this.SemanticDATmatrix[i][j] = 0;
                }
            }
        }
    }
    
    private boolean isSameType(attribute att, method mtd)
    {
        boolean result = false;
        //System.out.println(att.getName()+"("+att.getType()+") vs "+mtd.getName());
        
        for (methodParameter mp : mtd.getParameters())
        {
            //System.out.println("    > "+att.getType()+" ? "+mp.getName()+" ("+mp.getType()+")");
            if (att.getType().equals(mp.getType()))
            {
                result = true;
            } 
        }
        
        if (att.getType().equals(mtd.getReturnType()))
        {
            result = true;
        } 
        
        return result;
    }
    
    private boolean isEqualSemantically(attribute att, method mtd)
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
    }
    
    public double getMMAC(int type)
    {
        //count the number of 1 in column
        int k = this.cclass.getMethods().size(); //methods
        int l = this.cclass.getAttributes().size(); //attributes
        
        double mmac = 0;
        double sum = 0;
        int one;
        for (int c = 0; c<l; c++)
        {
            one = 0;
            for (int r = 0; r<k; r++)
            {
                if (type == 0)
                {
                    if (this.DATmatrix[r][c] == 1)
                        one++;
                } else if (type == 1)
                {
                    if (this.SemanticDATmatrix[r][c] == 1)
                        one++;
                }
            }
            sum += one*(one-1);
        }
        
        if ((k == 0) || (l == 0))
        {
            mmac = 0;
        } else if (k == 1)
        {
            mmac = 1;
        } else
        {
            mmac = (double)sum/(double)(l*k*(k-1));
        }
        
        //System.out.println("MMAC:");
        //System.out.println("sum of one :"+ sum);
        //System.out.println("sum of k :"+ k);
        //System.out.println("sum of l :"+ l);
        
        
        return mmac;
    }
    
    public double getAAC(int type)
    {
        //count the number of 1 in column
        int k = this.cclass.getMethods().size(); //methods
        int l = this.cclass.getAttributes().size(); //attributes
        double aac = 0;
        double sum = 0;
        int one;
        for (int r = 0; r<k; r++)
        {
            one = 0;
            for (int c = 0; c<l; c++)
            {
                if (type == 0)
                {
                    if (this.DATmatrix[r][c] == 1)
                        one++;
                } else if (type == 1)
                {
                    if (this.SemanticDATmatrix[r][c] == 1)
                        one++;
                }
            }
            sum += one*(one-1);
        }
        if ((k == 0) || (l == 0))
        {
            aac = 0;
        } else if (l == 1)
        {
            aac = 1;
        } else
        {
            aac = (double)sum/(double)(l*k*(l-1));
        }
        
        //System.out.println("AAC:");
        //System.out.println("sum of one :"+ sum);
        //System.out.println("sum of k :"+ k);
        //System.out.println("sum of l :"+ l);
        
        
        return aac;
    }
    
    public double getAMC(int type)
    {
        //count the number of 1 in column
        int k = this.cclass.getMethods().size(); //methods
        int l = this.cclass.getAttributes().size(); //attributes
        double amc;
        int one = 0;
        for (int r = 0; r<k; r++)
        {
            for (int c = 0; c<l; c++)
            {
                if (type == 0)
                {
                    if (this.DATmatrix[r][c] == 1)
                        one++;
                } else if (type == 1)
                {
                    if (this.SemanticDATmatrix[r][c] == 1)
                        one++;
                }
            }
        }
        if ((k == 0) || (l == 0))
        {
            amc = 0;
        } else
        {
            amc = (double)(one)/(double)(k*l);
            //System.out.println("amc : "+amc);
        }
        return amc;
    }
    
    public double getd3c2(int type)
    {
        double result = 0;
        int k = this.cclass.getMethods().size(); //methods
        int l = this.cclass.getAttributes().size(); //attributes
        
        if ((k == 0) && (l == 1))
            result = 0;
        else if ((k == 1) && (l == 0))
            result = 1;
        else
            result = (((k*(k-1))*getMMAC(type))+((l*(l-1))*getAAC(type))+(2*l*k*getAMC(type)))/((k*(k-1))+(l*(l-1))+(2*l*k));
        
        return result;
    }
}
