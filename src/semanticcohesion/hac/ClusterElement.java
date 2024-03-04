/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

/**
 *
 * @author bayu
 */
public class ClusterElement {
    private String name;
    private double silhouettes;
    private boolean ismoved;

    public ClusterElement(String name, double silhouettes) {
        this.name = name;
        this.silhouettes = silhouettes;
        this.ismoved = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSilhouettes() {
        return silhouettes;
    }

    public void setSilhouettes(double silhouettes) {
        this.silhouettes = silhouettes;
    }
    
    public void setIsMoved()
    {
        this.ismoved = true;
    }
    
    public boolean getIsMoved()
    {
        return this.ismoved;
    }
    
}
