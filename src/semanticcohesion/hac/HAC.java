/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semanticcohesion.hac;

import java.io.IOException;

/**
 *
 * @author bayu
 */
public class HAC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        SimilarityMatrix sim = new SimilarityMatrix();
        
        sim.setValue("m4", "m4", 0);
        sim.setValue("m4", "m5", 0.23);
        sim.setValue("m4", "m6", 0.33);
        sim.setValue("m4", "m7", 0.25);
        sim.setValue("m4", "m8", 0.25);
        sim.setValue("m4", "m9", 0.32);
        sim.setValue("m4", "m10", 0.25);
        
        sim.setValue("m5", "m4", 0.25);
        sim.setValue("m5", "m5", 0);
        sim.setValue("m5", "m6", 0.18);
        sim.setValue("m5", "m7", 0.18);
        sim.setValue("m5", "m8", 0.18);
        sim.setValue("m5", "m9", 0.19);
        sim.setValue("m5", "m10", 0.18);
        
        sim.setValue("m6", "m4", 0.33);
        sim.setValue("m6", "m5", 0.18);
        sim.setValue("m6", "m6", 0);
        sim.setValue("m6", "m7", 0.08);
        sim.setValue("m6", "m8", 0.08);
        sim.setValue("m6", "m9", 0.26);
        sim.setValue("m6", "m10", 0.2);
        
        sim.setValue("m7", "m4", 0.25);
        sim.setValue("m7", "m5", 0.18);
        sim.setValue("m7", "m6", 0.08);
        sim.setValue("m7", "m7", 0);
        sim.setValue("m7", "m8", 0.22);
        sim.setValue("m7", "m9", 0.12);
        sim.setValue("m7", "m10", 0.05);
        
        sim.setValue("m8", "m4", 0.25);
        sim.setValue("m8", "m5", 0.18);
        sim.setValue("m8", "m6", 0.08);
        sim.setValue("m8", "m7", 0.22);
        sim.setValue("m8", "m8", 0);
        sim.setValue("m8", "m9", 0.05);
        sim.setValue("m8", "m10", 0.05);
        
        sim.setValue("m9", "m4", 0.32);
        sim.setValue("m9", "m5", 0.19);
        sim.setValue("m9", "m6", 0.26);
        sim.setValue("m9", "m7", 0.12);
        sim.setValue("m9", "m8", 0.05);
        sim.setValue("m9", "m9", 0);
        sim.setValue("m9", "m10", 0.19);
      
        sim.setValue("m10", "m4", 0.25);
        sim.setValue("m10", "m5", 0.18);
        sim.setValue("m10", "m6", 0.2);
        sim.setValue("m10", "m7", 0.05);
        sim.setValue("m10", "m8", 0.05);
        sim.setValue("m10", "m9", 0.19);
        sim.setValue("m10", "m10", 0);
        
        
        /*sim.setValue("runAcademicServices,runAcademicServices,1;" +
"runAcademicServices,runAcademicServices,1;" +
"getFinalGrade,runAcademicServices,0.7083;" +
"runAcademicServices,getFinalGrade,0.7083;" +
"formTranscript,runAcademicServices,0.5;" +
"runAcademicServices,formTranscript,0.5;" +
"canGraduate,runAcademicServices,0.575;" +
"runAcademicServices,canGraduate,0.575;" +
"isPassed,runAcademicServices,0.5938;" +
"runAcademicServices,isPassed,0.5938;" +
"releaseFinalGrade,runAcademicServices,0.625;" +
"runAcademicServices,releaseFinalGrade,0.625;" +
"releaseTranscript,runAcademicServices,0.5;" +
"runAcademicServices,releaseTranscript,0.5;" +
"runAcademicServices,getFinalGrade,0.7083;" +
"getFinalGrade,runAcademicServices,0.7083;" +
"getFinalGrade,getFinalGrade,1;" +
"getFinalGrade,getFinalGrade,1;" +
"formTranscript,getFinalGrade,0.5417;" +
"getFinalGrade,formTranscript,0.5417;" +
"canGraduate,getFinalGrade,0.5938;" +
"getFinalGrade,canGraduate,0.5938;" +
"isPassed,getFinalGrade,0.9091;" +
"getFinalGrade,isPassed,0.9091;" +
"releaseFinalGrade,getFinalGrade,1;" +
"getFinalGrade,releaseFinalGrade,1;" +
"releaseTranscript,getFinalGrade,0.5833;" +
"getFinalGrade,releaseTranscript,0.5833;" +
"runAcademicServices,formTranscript,0.5;" +
"formTranscript,runAcademicServices,0.5;" +
"getFinalGrade,formTranscript,0.5417;" +
"formTranscript,getFinalGrade,0.5417;" +
"formTranscript,formTranscript,1;" +
"formTranscript,formTranscript,1;" +
"canGraduate,formTranscript,0.5;" +
"formTranscript,canGraduate,0.5;" +
"isPassed,formTranscript,0.5469;" +
"formTranscript,isPassed,0.5469;" +
"releaseFinalGrade,formTranscript,0.5417;" +
"formTranscript,releaseFinalGrade,0.5417;" +
"releaseTranscript,formTranscript,0.6667;" +
"formTranscript,releaseTranscript,0.6667;" +
"runAcademicServices,canGraduate,0.575;" +
"canGraduate,runAcademicServices,0.575;" +
"getFinalGrade,canGraduate,0.5938;" +
"canGraduate,getFinalGrade,0.5938;" +
"formTranscript,canGraduate,0.5;" +
"canGraduate,formTranscript,0.5;" +
"canGraduate,canGraduate,1;" +
"canGraduate,canGraduate,1;" +
"isPassed,canGraduate,0.5536;" +
"canGraduate,isPassed,0.5536;" +
"releaseFinalGrade,canGraduate,0.6406;" +
"canGraduate,releaseFinalGrade,0.6406;" +
"releaseTranscript,canGraduate,0.65;" +
"canGraduate,releaseTranscript,0.65;" +
"runAcademicServices,isPassed,0.5938;" +
"isPassed,runAcademicServices,0.5938;" +
"getFinalGrade,isPassed,0.9091;" +
"isPassed,getFinalGrade,0.9091;" +
"formTranscript,isPassed,0.5469;" +
"isPassed,formTranscript,0.5469;" +
"canGraduate,isPassed,0.5536;" +
"isPassed,canGraduate,0.5536;" +
"isPassed,isPassed,1;" +
"isPassed,isPassed,1;" +
"releaseFinalGrade,isPassed,0.9091;" +
"isPassed,releaseFinalGrade,0.9091;" +
"releaseTranscript,isPassed,0.5469;" +
"isPassed,releaseTranscript,0.5469;" +
"runAcademicServices,releaseFinalGrade,0.625;" +
"releaseFinalGrade,runAcademicServices,0.625;" +
"getFinalGrade,releaseFinalGrade,1;" +
"releaseFinalGrade,getFinalGrade,1;" +
"formTranscript,releaseFinalGrade,0.5417;" +
"releaseFinalGrade,formTranscript,0.5417;" +
"canGraduate,releaseFinalGrade,0.6406;" +
"releaseFinalGrade,canGraduate,0.6406;" +
"isPassed,releaseFinalGrade,0.9091;" +
"releaseFinalGrade,isPassed,0.9091;" +
"releaseFinalGrade,releaseFinalGrade,1;" +
"releaseFinalGrade,releaseFinalGrade,1;" +
"releaseTranscript,releaseFinalGrade,0.6528;" +
"releaseFinalGrade,releaseTranscript,0.6528;" +
"runAcademicServices,releaseTranscript,0.5;" +
"releaseTranscript,runAcademicServices,0.5;" +
"getFinalGrade,releaseTranscript,0.5833;" +
"releaseTranscript,getFinalGrade,0.5833;" +
"formTranscript,releaseTranscript,0.6667;" +
"releaseTranscript,formTranscript,0.6667;" +
"canGraduate,releaseTranscript,0.65;" +
"releaseTranscript,canGraduate,0.65;" +
"isPassed,releaseTranscript,0.5469;" +
"releaseTranscript,isPassed,0.5469;" +
"releaseFinalGrade,releaseTranscript,0.6528;" +
"releaseTranscript,releaseFinalGrade,0.6528;" +
"releaseTranscript,releaseTranscript,1;" +
"releaseTranscript,releaseTranscript,1;" +
"transcriptType,runAcademicServices,0;" +
"runAcademicServices,transcriptType,0;" +
"major,runAcademicServices,0.075;" +
"runAcademicServices,major,0.075;" +
"transcriptType,getFinalGrade,0;" +
"getFinalGrade,transcriptType,0;" +
"major,getFinalGrade,0;" +
"getFinalGrade,major,0;" +
"transcriptType,formTranscript,0.5;" +
"formTranscript,transcriptType,0.5;" +
"major,formTranscript,0.5;" +
"formTranscript,major,0.5;" +
"transcriptType,canGraduate,0;" +
"canGraduate,transcriptType,0;" +
"major,canGraduate,0.0938;" +
"canGraduate,major,0.0938;" +
"transcriptType,isPassed,0;" +
"isPassed,transcriptType,0;" +
"major,isPassed,0;" +
"isPassed,major,0;" +
"transcriptType,releaseFinalGrade,0;" +
"releaseFinalGrade,transcriptType,0;" +
"major,releaseFinalGrade,0;" +
"releaseFinalGrade,major,0;" +
"transcriptType,releaseTranscript,0.7;" +
"releaseTranscript,transcriptType,0.7;" +
"major,releaseTranscript,0.5;" +
"releaseTranscript,major,0.5;" +
"transcriptType,transcriptType,1;" +
"transcriptType,transcriptType,1;" +
"major,transcriptType,0.5;" +
"transcriptType,major,0.5;" +
"transcriptType,major,0.5;" +
"major,transcriptType,0.5;");*/
        
        ClusterManager cm = new ClusterManager(sim);
        sim.printSimilarity();
        //cm.doDynamicClustering(sim.getSimilarityMatrix());
        
        cm.doStaticClustering(sim.getSimilarityMatrix());
        
        System.out.println("Temporary Clusters :");
        cm.PrintCluster();
        //System.out.println(cm.getClusters());
        System.out.println("==========================");
        System.out.println("Silhouettes Calculation!!!");
        ClusterOptimizer SCO = new ClusterOptimizer(cm.getClusters(), sim.getDissimilarityMatrix(),0.5,0.5);
        SCO.optimize();
        cm.cleanCluster();
        //System.out.println(cm.getClusters());
        System.out.println("==========================");
        cm.PrintCluster();
    }
    
}
