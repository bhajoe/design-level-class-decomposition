/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import nu.xom.ParsingException;
import semanticcohesion.d3c2.DATSemanticMatrix;
import semanticcohesion.d3c2.GBCMMatrix;
import semanticcohesion.data.attribute;
import semanticcohesion.data.class_model;
import semanticcohesion.data.method;
import semanticcohesion.data.methodParameter;
import semanticcohesion.xml.xmlXOMClassExtractor_DFS;

/**
 *
 * @author bhajoe
 */
public class SCForm extends javax.swing.JFrame implements ActionListener, PropertyChangeListener {

    private HashMap<String, class_model> classes;
    private int Progress;
    private Task task;

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
    
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() throws InterruptedException 
        {
                try {
                //xmlClassParser xmlP = new xmlClassParser();
                //xmlXOMClassExtractor xmlP = new xmlXOMClassExtractor();

                xmlXOMClassExtractor_DFS xmlP = new xmlXOMClassExtractor_DFS();

                File[] XMLFiles = getFiles(jTextField1.getText(), ".xml");
                xmlP.parse(XMLFiles);
                classes = xmlP.getClasses();
                //System.out.println("Classes: "+classes.size());
                DefaultTreeModel modelTree = new DefaultTreeModel(convertToTree(classes));            
                jTree1.setModel(modelTree);

                //Counting Metric
                GBCMMatrix mar = new GBCMMatrix();
                DATSemanticMatrix matrix = new DATSemanticMatrix();
                matrix.setThreshold(Double.parseDouble(jThreshold.getText()),Double.parseDouble(jSemanticTh.getText()));
                //Set Weight Value
                mar.setSynWeight(Double.parseDouble(jSynWeight.getText()));
                mar.setSemWeight(Double.parseDouble(jSemWeight.getText()));
                mar.setThreshold(Double.parseDouble(jRelThreshold.getText()),Double.parseDouble(jSemThreshold.getText()));
                
                
                Progress = 1;
                jProgressBar1.setMaximum(classes.size());
                jProgressBar1.setValue(0);

                for (class_model cm : classes.values())
                {
                    boolean isStatic = false;
                    if (jComboBox1.getSelectedIndex()==0)
                        isStatic = true;
                    mar.setCclass(cm,Double.parseDouble(jTWeightA.getText()),Double.parseDouble(jTWeightB.getText()),isStatic);
                    matrix.setCclass(cm);
                    //jLabel1.setText("Progress : "+(Progress++));
                    jProgressBar1.setValue(Progress++);
                    Thread.sleep(200);
                    jTextArea1.append(cm.getName());
                    jTextArea1.append("|"+mar.getMAR()); // MAR
                    jTextArea1.append("|"+mar.getMMR()); // MMR
                    jTextArea1.append("|"+mar.getAAR()); // AAR
                    jTextArea1.append("|"+mar.getNumAttribute()); // Attribute
                    jTextArea1.append("|"+mar.getPrivateAttr()); // Private Attribute
                    jTextArea1.append("|"+mar.getPublicAttr()); // Public Attribute
                    jTextArea1.append("|"+mar.getProtectedAttr()); // Protected Attribute
                    
                    jTextArea1.append("|"+mar.getNumMethod()); // Method
                    jTextArea1.append("|"+mar.getPrivateMethod()); // Private Method
                    jTextArea1.append("|"+mar.getPublicMethod()); // Public Method
                    jTextArea1.append("|"+mar.getProtectedMethod()); // Protected Method
                    
                    jTextArea1.append("|"+mar.getNumberOfGeneralization()); // Number of Generalizations
                    jTextArea1.append("|"+mar.getNumberOfRealization()); // Number of Realization
                                                            
                    jTextArea1.append("|"+mar.getCapacity()); // Capacity
                    jTextArea1.append("|"+mar.calculateCohesion()); // GBCM
                    jTextArea1.append("|"+matrix.getMMAC(0)); // MMAC
                    jTextArea1.append("|"+matrix.getAAC(0)); // ACC
                    jTextArea1.append("|"+matrix.getAMC(0)); //AMC
                    jTextArea1.append("|"+matrix.getd3c2(0)); //D3C2
                    jTextArea1.append("|"+matrix.getMMAC(1)); //MMAC
                    jTextArea1.append("|"+matrix.getAAC(1)); //ACC
                    jTextArea1.append("|"+matrix.getAMC(1)); //AMC
                    jTextArea1.append("|"+matrix.getd3c2(1)+"\n"); //SemD3C2
                }                
            } catch (IOException | ParsingException ex) {
                Logger.getLogger(SCForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SCForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            jButton3.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            jTextArea1.append("Done!\n");
        }
    }
    
    /**
     * Creates new form SCForm
     */
    public SCForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSynWeight = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSemWeight = new javax.swing.JTextField();
        jRelThreshold = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSemThreshold = new javax.swing.JTextField();
        jTWeightA = new javax.swing.JTextField();
        jTWeightB = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jThreshold = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSemanticTh = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GraphBasedCohesionMetric 1.4");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Go!!");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Progress : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Syntactic and Semantic Weight (Coh)"));

        jLabel2.setText("Syntactic Weight");

        jSynWeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jSynWeight.setText("0.5");
        jSynWeight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSynWeightFocusLost(evt);
            }
        });
        jSynWeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSynWeightKeyPressed(evt);
            }
        });

        jLabel4.setText("Semantic Weight");

        jSemWeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jSemWeight.setText("0.5");
        jSemWeight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSemWeightFocusLost(evt);
            }
        });
        jSemWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSemWeightActionPerformed(evt);
            }
        });
        jSemWeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSemWeightKeyPressed(evt);
            }
        });

        jRelThreshold.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jRelThreshold.setText("0.5");
        jRelThreshold.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRelThresholdFocusLost(evt);
            }
        });
        jRelThreshold.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRelThresholdKeyPressed(evt);
            }
        });

        jLabel5.setText("Relation Threshold");

        jLabel6.setText("Semantic Threshold");

        jSemThreshold.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jSemThreshold.setText("0.5");
        jSemThreshold.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSemThresholdFocusLost(evt);
            }
        });
        jSemThreshold.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSemThresholdKeyPressed(evt);
            }
        });

        jTWeightA.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTWeightA.setText("0.5");
        jTWeightA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTWeightAFocusLost(evt);
            }
        });
        jTWeightA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTWeightAActionPerformed(evt);
            }
        });
        jTWeightA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTWeightAKeyPressed(evt);
            }
        });

        jTWeightB.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTWeightB.setText("0.5");
        jTWeightB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTWeightBFocusLost(evt);
            }
        });
        jTWeightB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTWeightBKeyPressed(evt);
            }
        });

        jLabel8.setText("Weight a");

        jLabel9.setText("Weight b");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Static Threshold", "Dynamic Threshold" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSynWeight)
                    .addComponent(jSemWeight))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRelThreshold)
                    .addComponent(jSemThreshold))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jTWeightB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTWeightA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jRelThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTWeightA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jSynWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jSemThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jTWeightB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jSemWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Semantic Threshold (Non/SemD3C2)"));

        jLabel3.setText("Threshold");

        jThreshold.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jThreshold.setText("0.5");
        jThreshold.setName(""); // NOI18N
        jThreshold.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jThresholdFocusLost(evt);
            }
        });
        jThreshold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jThresholdActionPerformed(evt);
            }
        });
        jThreshold.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jThresholdKeyPressed(evt);
            }
        });

        jLabel7.setText("Semantic Threshold");

        jSemanticTh.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jSemanticTh.setText("0.5");
        jSemanticTh.setName(""); // NOI18N
        jSemanticTh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSemanticThFocusLost(evt);
            }
        });
        jSemanticTh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSemanticThKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jThreshold)
                    .addComponent(jSemanticTh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jSemanticTh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            jTextField1.setText(file.getPath());
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private static File[] getFiles(String path,String type) 
    {
        File dir = new File(path);
        return dir.listFiles((File dir1, String name) -> name.toLowerCase().endsWith(type));
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
        //DATSemanticMatrix matrix = new DATSemanticMatrix();
        //matrix.setThreshold(0.5);
        //get class object
        
        //matrix.setCclass(this.classes.get(selectedNode.toString()));
        
        //System.out.println(matrix.getAAC(1));
    }//GEN-LAST:event_jTree1ValueChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTextArea1.selectAll();
        jTextArea1.replaceSelection("");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jSemWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSemWeightActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jSemWeightActionPerformed

    private void jTWeightAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTWeightAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTWeightAActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jThresholdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThresholdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jThresholdActionPerformed

    private void jThresholdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jThresholdKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jSemanticTh.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jThreshold.getText())));
        }
    }//GEN-LAST:event_jThresholdKeyPressed

    private void jSemanticThKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSemanticThKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemanticTh.getText())));
        }
    }//GEN-LAST:event_jSemanticThKeyPressed

    private void jThresholdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jThresholdFocusLost
        // TODO add your handling code here:
        jSemanticTh.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jThreshold.getText())));
    }//GEN-LAST:event_jThresholdFocusLost

    private void jSemanticThFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSemanticThFocusLost
        // TODO add your handling code here:
        jThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemanticTh.getText())));
    }//GEN-LAST:event_jSemanticThFocusLost

    private void jTWeightAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTWeightAKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jTWeightB.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jTWeightA.getText())));
        }
    }//GEN-LAST:event_jTWeightAKeyPressed

    private void jTWeightAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTWeightAFocusLost
        // TODO add your handling code here:
        jTWeightB.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jTWeightA.getText())));
    }//GEN-LAST:event_jTWeightAFocusLost

    private void jTWeightBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTWeightBKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jTWeightA.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jTWeightB.getText())));
        }
    }//GEN-LAST:event_jTWeightBKeyPressed

    private void jTWeightBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTWeightBFocusLost
        // TODO add your handling code here:
        jTWeightA.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jTWeightB.getText())));
    }//GEN-LAST:event_jTWeightBFocusLost

    private void jRelThresholdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRelThresholdKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jSemThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jRelThreshold.getText())));
        }
    }//GEN-LAST:event_jRelThresholdKeyPressed

    private void jRelThresholdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRelThresholdFocusLost
        // TODO add your handling code here:
        jSemThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jRelThreshold.getText())));
    }//GEN-LAST:event_jRelThresholdFocusLost

    private void jSemThresholdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSemThresholdKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jRelThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemThreshold.getText())));
        }
    }//GEN-LAST:event_jSemThresholdKeyPressed

    private void jSemThresholdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSemThresholdFocusLost
        // TODO add your handling code here:
        jRelThreshold.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemThreshold.getText())));
    }//GEN-LAST:event_jSemThresholdFocusLost

    private void jSynWeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSynWeightKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jSemWeight.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSynWeight.getText())));
        }
    }//GEN-LAST:event_jSynWeightKeyPressed

    private void jSynWeightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSynWeightFocusLost
        // TODO add your handling code here:
        jSemWeight.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSynWeight.getText())));
    }//GEN-LAST:event_jSynWeightFocusLost

    private void jSemWeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSemWeightKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10)
        {
            jSynWeight.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemWeight.getText())));
        }
    }//GEN-LAST:event_jSemWeightKeyPressed

    private void jSemWeightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSemWeightFocusLost
        // TODO add your handling code here:
        jSynWeight.setText(String.format("%,.1f", Double.parseDouble("1.0")-Double.parseDouble(jSemWeight.getText())));
    }//GEN-LAST:event_jSemWeightFocusLost

    public DefaultMutableTreeNode convertToTree(HashMap<String, class_model> classTree)
    {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Classes");
        
        for (class_model cls : classTree.values())
        {
            DefaultMutableTreeNode single_class = new DefaultMutableTreeNode(cls.getName());
            DefaultMutableTreeNode attribute_tree = new DefaultMutableTreeNode("Attributes");
            
            for (attribute att : cls.getAttributes())
            {
                attribute_tree.add(new DefaultMutableTreeNode(att.getName()+":"+att.getType()+" ("+att.getVisibility()+")"));
            }
            single_class.add(attribute_tree);
            
            DefaultMutableTreeNode method_tree = new DefaultMutableTreeNode("Methods");
            for (method mtd : cls.getMethods())
            {
                DefaultMutableTreeNode method = new DefaultMutableTreeNode(mtd.getName()+":"+mtd.getReturnType()+" ("+mtd.getVisibility()+")");
                for (methodParameter mp : mtd.getParameters())
                {
                    method.add(new DefaultMutableTreeNode(mp.getName()+":"+mp.getType()));
                }
                method_tree.add(method);
            }
            DefaultMutableTreeNode Gen = new DefaultMutableTreeNode("Generalization : "+cls.getNumOfGeneralization());
            DefaultMutableTreeNode Assoc = new DefaultMutableTreeNode("Association : "+cls.getNumOfRealization());
            single_class.add(method_tree);
            single_class.add(Gen);
            single_class.add(Assoc);
            treeNode.add(single_class);
        }        
        return treeNode;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SCForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SCForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SCForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SCForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SCForm().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField jRelThreshold;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jSemThreshold;
    private javax.swing.JTextField jSemWeight;
    private javax.swing.JTextField jSemanticTh;
    private javax.swing.JTextField jSynWeight;
    private javax.swing.JTextField jTWeightA;
    private javax.swing.JTextField jTWeightB;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jThreshold;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
