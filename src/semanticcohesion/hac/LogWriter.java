/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author bayu
 */
public class LogWriter {
    private String fileName;

    public LogWriter(String flName) {
        this.fileName = flName;
    }
    
    public void writeLog(String log) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(log+";");

        writer.close();
    }
    
}
