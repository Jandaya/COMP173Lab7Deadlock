/*
   Joseph Andaya
    COMP 173
    Lab: 7
    11/6/2015
Note: This code was produced with provided algorithm in lab link: https://en.wikipedia.org/wiki/Banker%27s_algorithm
*/
package lab7.comp.pkg173;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class BankersAlgorithm extends javax.swing.JFrame {

    private File selectedFile;
    private JFileChooser fc = new JFileChooser();
    private String sFile;
    private int numProcess;
    private int numResourceTypes;
    private List<Integer> availableResourceList = new ArrayList<Integer>();
    private List<List<Integer>> AllocationMatrix = new ArrayList<List<Integer>>();
    private List<List<Integer>> RequestMatrix = new ArrayList<List<Integer>>();
    private List<List<Integer>> NeedMatrix = new ArrayList<List<Integer>>();
    private List<Integer> Line = new ArrayList<Integer>();
    private List<Integer> RunningList = new ArrayList<Integer>();
    private int ProcessesRemaining = 0;
    private int DeadlockCount = 0;

    
    
    
    public BankersAlgorithm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fileOpenedLabel = new javax.swing.JLabel();
        openFileButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banker's Algorithm by Joseph Andaya");

        fileOpenedLabel.setText("File Opened:");

        openFileButton.setText("Open File");
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileButtonActionPerformed(evt);
            }
        });

        runButton.setText("Run");
        runButton.setEnabled(false);
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        printButton.setText("Print Input");
        printButton.setEnabled(false);
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.setEnabled(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileOpenedLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(openFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printButton)
                        .addGap(18, 18, 18)
                        .addComponent(runButton)))
                .addContainerGap(239, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resetButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileOpenedLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openFileButton)
                    .addComponent(runButton)
                    .addComponent(printButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(resetButton))
        );

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileButtonActionPerformed
        int returnVal = fc.showOpenDialog(null);
        // gets the file to read from the filechooser
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            resetValues();
            // clear the list for new file
            selectedFile  = fc.getSelectedFile();
            sFile = selectedFile.toString();
            try {
                // input in stuff
                readFile(selectedFile);
                // calculate the need matrix
                calculateNeed();
                // initialize the running processes
                initRunningProcesses();
                // display the opened file to user, and set all buttons to ready.
                fileOpenedLabel.setText("File Opened: " + sFile);
                runButton.setEnabled(true);
                printButton.setEnabled(true);
                resetButton.setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(BankersAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openFileButtonActionPerformed

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        // display what has been loaded to the program
        printList();
    }//GEN-LAST:event_printButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        // run the simulation
        runSimulation();
    }//GEN-LAST:event_runButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        textArea.setText("");
        textArea.append("Values Reset.\n");
        resetValues();
        try {
                // input in stuff
                readFile(selectedFile);
                calculateNeed();
                initRunningProcesses();
            } catch (IOException ex) {
                Logger.getLogger(BankersAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_resetButtonActionPerformed


    public void resetValues(){
         availableResourceList = new ArrayList<Integer>();
         AllocationMatrix = new ArrayList<List<Integer>>();
         RequestMatrix = new ArrayList<List<Integer>>();
         NeedMatrix = new ArrayList<List<Integer>>();
         Line = new ArrayList<Integer>();
         RunningList = new ArrayList<Integer>();
         ProcessesRemaining = 0;
         DeadlockCount = 0;
         /*
        try {
                // input in stuff
                readFile(selectedFile);
                calculateNeed();
                initRunningProcesses();
            } catch (IOException ex) {
                Logger.getLogger(BankersAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        */
    }
    public void printList(){
         List<Integer> LocalLine = new ArrayList<Integer>();
        textArea.append("Number of processes: " + numProcess + "\n");
        textArea.append("Number of Resource Types: " + numResourceTypes + "\n");
        textArea.append("Available resources: \n");
        for (int a = 0; a < numResourceTypes; a++ ){
            textArea.append(availableResourceList.get(a) + " ");
        }
        textArea.append("\n");
        textArea.append("Allocation Matrix: \n");
        displayMatrix(AllocationMatrix);
        textArea.append("Request Matrix: \n");
        displayMatrix(RequestMatrix);
        textArea.append("Need Matrix: \n");
        displayMatrix(NeedMatrix);
        textArea.append("\n");

    }
    
    public void displayMatrix(List<List<Integer>> a){
        List<Integer> LocalLine = new ArrayList<Integer>();
        for (int j = 0; j < numProcess; j++){
            LocalLine = a.get(j);
            for(int k = 0; k < numResourceTypes; k++){
                textArea.append(LocalLine.get(k) + " ");
            }
            textArea.append("\n");
            LocalLine = new ArrayList<Integer>();
        }
    }
    
    public void calculateNeed(){
        List<Integer> LocalLine = new ArrayList<Integer>();
        int temp, temp2;
        boolean isSafe = true;
        for (int j = 0; j < numProcess; j++){
            
            for(int k = 0; k < numResourceTypes; k++){
                 temp = RequestMatrix.get(j).get(k) - AllocationMatrix.get(j).get(k);
                 LocalLine.add(temp);
            }
            NeedMatrix.add(LocalLine);
            LocalLine = new ArrayList<Integer>();
        }
    }
    
    public void initRunningProcesses(){
        RunningList = new ArrayList<Integer>();
        // creates a list of the available running process 1 means they need jobs to finish 0 means they have completed.
        for(int i = 0; i < numProcess; i++){
            RunningList.add(1);
            ProcessesRemaining++;
        }
    }
    
    public void runSimulation(){
        List<Integer> LocalLine = new ArrayList<Integer>();
        int need, temp2;
        boolean isSafe = true, allFinished = false;
        boolean loopCheck = true;
        // similar to algorithm provided in link above
        // so long as each process is not finished stay in loop
        while(!allFinished){
            for (int j = 0; j < numProcess; j++){
                // so long as the process has jobs to finish, continue.
                if(RunningList.get(j) == 1){
                    for(int k = 0; k < numResourceTypes; k++){
                        // calculate if the amount of each resource they need, and check if it will give them a safe state
                        // need = RequestMatrix.get(j).get(k) - AllocationMatrix.get(j).get(k);
                         // so long as it is safe to give the resources add the element to a local line that will be pushed to a list of lists
                         if(RequestMatrix.get(j).get(k) <= availableResourceList.get(k))
                            LocalLine.add(AllocationMatrix.get(j).get(k));
                         else{
                            // this tells the loop that we are now in an unsafe state, and that the process will not be allowed to get the resources
                            isSafe = false;
                            textArea.append("Unsafe State occurred. Exited.\n");
                            break;
                         }

                    }
                    // so long as it is safe, the process will get all the resources needed, 
                    // complete it's job, and return the resources back to the main
                    if(isSafe){
                        textArea.append("Process " + (j+1) + " is Complete.\n");
                        releaseResources(LocalLine);
                        //process of index j is finished.
                        RunningList.set(j, 0);
                        // decrement amount of processes in queue
                        ProcessesRemaining--;
                        textArea.append("Number of processes remaining: " + ProcessesRemaining + "\n");
                        printResourceMatrix();
                        textArea.append("\n");
                        // this is a check designed so that the next process will run, then reset to try any processes
                        // that were put on hold
                        if(loopCheck == false){
                            loopCheck = true;
                            j = 0;
                        }
                        
                    }
                    else{
                        //places the process on hold and allows the next process to run through
                        textArea.append("Process " + (j+1) + " is placed on hold.\n\n");
                        isSafe = true;
                        loopCheck = false;
                        //j = 0;
                    }
                    isSafe = true;
                    LocalLine = new ArrayList<Integer>();
                }
            }
            // deadlock count is used just in case we get stuck into an infinite loop
            // deadlockCount's check is set to 1000 so that 1000 iterations will be needed 
            // to determine if this is an infinite loop.
            DeadlockCount++;
            // if all the count of the remaining process is 0 then we are all finished
            if(ProcessesRemaining <= 0)
                allFinished = true;
            
            // checks for a deadlock. basically if this loop never ends number set to relatively high iteration.
            if(DeadlockCount >= 1000){
                textArea.append("\nTimed out... deadlocks occurred.\n");
                displayLeftOverProcesses(RunningList);
                break;
            }
        }
        // provides a final verdict that all processes have finished
        if(allFinished){
            textArea.append("\nAll processes completed. No deadlocks occurred.\n");
        }
    }
    
    // give the resources to the process asking
    public void grantResources(List<Integer> a){
        int temp;
        for (int i = 0; i < numResourceTypes; i++){
            temp = availableResourceList.get(i);
            availableResourceList.set(i, temp - a.get(i));
        }
    }
    // release the resources back to the List.
    public void releaseResources(List<Integer> a){
        int temp;
        for(int i = 0; i < numResourceTypes; i++){
            temp = availableResourceList.get(i);
            availableResourceList.set(i, temp + a.get(i));
        }
    }
    
    public void displayLeftOverProcesses(List<Integer> alist ){
                textArea.append("\nProcesses Left over: ");
        for(int i = 0; i < numProcess; i++){
            if(alist.get(i) == 1){
                textArea.append("\nProcess " + (i+1));
            }
        }
    }
    
    // display the a resource matrix
    public void printResourceMatrix(){
        int temp;
        textArea.append("Available Resources: \n");
        for(int i = 0; i < numResourceTypes; i++){
            textArea.append(availableResourceList.get(i) + " ");
        }
        textArea.append("\n");
    }
    
    
    // algorithm for reading the file, specific to the instructions of the lab.
    public void readFile(File Selected)throws IOException{
        Scanner scan = new Scanner(selectedFile);
        int count1 = 0;
        int i = 0, j = 0, k = 0;
        double indata = 0.0;
        while(scan.hasNext()){
            if(count1 == 0){
                numProcess = scan.nextInt();
            }
            else if(count1 == 1){
                numResourceTypes = scan.nextInt();
            }
            else if (count1 == 2){
                while(i < numResourceTypes){
                    availableResourceList.add(scan.nextInt());
                    i++;
                }
            }
            else if (count1 == 3){
                for (j = 0; j < numProcess; j++){
                    for(k = 0; k < numResourceTypes; k++){
                        Line.add(scan.nextInt());
                    }
                    AllocationMatrix.add(Line);
                    Line = new ArrayList<Integer>();
                }
            }
            else if(count1 >= 4){
                for (j = 0; j < numProcess; j++){
                    for(k = 0; k < numResourceTypes; k++){
                        Line.add(scan.nextInt());
                    }
                    RequestMatrix.add(Line);
                    Line = new ArrayList<Integer>();
                }
            }
            
            count1++;

        }
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
            java.util.logging.Logger.getLogger(BankersAlgorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BankersAlgorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BankersAlgorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BankersAlgorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BankersAlgorithm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileOpenedLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openFileButton;
    private javax.swing.JButton printButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton runButton;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
