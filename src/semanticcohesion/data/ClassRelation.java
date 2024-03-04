/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.data;

/**
 *
 * @author bayu
 */
public class ClassRelation {
    private String type;
    private class_model ClassTo;

    public ClassRelation(String type, class_model ClassTo) {
        this.type = type;
        this.ClassTo = ClassTo;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public class_model getClassTo() {
        return ClassTo;
    }

    public void setClassTo(class_model ClassTo) {
        this.ClassTo = ClassTo;
    }
    
}
