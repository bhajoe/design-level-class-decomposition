/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.data;

import japa.parser.ast.type.Type;

/**
 *
 * @author bhajoe
 */
public class attribute {
    private String name;
    private String visibility;
    private String type;

    public attribute() {
    }
    
    public attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public attribute(String name, String type, String visibility) {
        this.name = name;
        this.type = type;
        this.visibility = visibility;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    
    
}
