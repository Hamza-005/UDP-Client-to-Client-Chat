package com.mycompany.mavenproject2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.text.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Hamza
 */
public class ClientChat extends javax.swing.JFrame implements UDPClient.MessageListener{
    private List<String> chatHistory = new ArrayList<>();
    private final UDPClient Client = new UDPClient();
    private List<String> deletedMessages = new ArrayList<>();
    private List<String> archivedMessages = new ArrayList<>();
    private static final String LOG_FILE = "chatlog.txt"; // Log file path
    
    private String getCurrentTimestamp() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy--hh:mm:ss a");
    return LocalDateTime.now().format(formatter);
}

    private void logEvent(String event) {
        String logEntry = getCurrentTimestamp() + " - " + event;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



  @Override
public void onMessageReceived(String message) {
    String serverIP = RemoteIP.getText().trim();
    int serverPort = Integer.parseInt(RemotePort.getText().trim());

    if (message.startsWith("DELETE: ")) {
        // Extract the message text that should be deleted
        String messageToDelete = message.substring("DELETE: ".length()).trim().replace("Me", "Server");
        // Remove the message with the given text from chat history, ensuring it's your message
        reqdel(messageToDelete);
        logEvent("Received DELETE request: " + messageToDelete);
    } else if (message.startsWith("ARCHIVE: ")) {
        String archivedMessage = message.substring("ARCHIVE: ".length()).trim().replace("Me", "Server");
        archivedMessages.add(archivedMessage);
        logEvent("Received ARCHIVE request: " + archivedMessage);
    }else if (message.equals("DELETE_ALL")) {
        reqdelall();
        logEvent("Received DELETE_ALL request");
    }else if (message.startsWith("RESTORE: ")) {
        reqrest(message);
        logEvent("Received RESTORE request: " + message);
    }else {
        // Display regular server message
        appendMessage(message, Color.RED, "Server", serverPort, serverIP);
        logEvent("Message received from Server: " + message);
    }
}


//TEMP FUNCS TO HANDLE INCOMING REQUESTS
private void reqdelall(){
    archivedMessages.addAll(chatHistory); // Add all messages to archived messages
    chatHistory.clear(); // Clear chat history
    refreshChatDisplay(); // Refresh UI to show the updated chat history
    logEvent("All messages deleted from chat and archived.");
    startAutoRemoveFromArchive("AllMessages"); // Use a unique identifier
    logEvent("All messages permenantly deleted and removed from archived.");
    
}

private void reqdel(String selectedMessage){

    if (selectedMessage != null) {
        // Move the selected message to the archive
        archivedMessages.add(selectedMessage); // Add to archived messages
        chatHistory.removeIf(msg -> msg.startsWith("Server: ") && msg.contains(selectedMessage));
        refreshChatDisplay(); // Refresh UI to show the updated chat history
        logEvent("Message deleted: " + selectedMessage);
        startAutoRemoveFromArchive(selectedMessage);
        logEvent("Message permenantly deleted and removed from archive: " + selectedMessage);
}
}

private void reqrest(String message){
    String messageToRestore;
        if(message.substring("RESTORE: ".length()).startsWith("Me")){
        messageToRestore = message.substring("RESTORE: ".length()).trim().replace("Me", "Server"); 
        }else{
        messageToRestore = message.substring("RESTORE: ".length()).trim().replace("Server", "Me");    
        }
        // Restore the message
        chatHistory.add(messageToRestore); // Add to chat history
        archivedMessages.remove(messageToRestore);
        refreshChatDisplay(); // Refresh UI to show the updated chat history
        logEvent("Message restored: " + messageToRestore);
}

//END OF TEMP FUNCS TO HANDLE INCOMING REQUESTS





    
    private void populateAvailableInterfaces() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            AvailableInterfaces.removeAllItems();  // Clear previous items

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    AvailableInterfaces.addItem(networkInterface.getDisplayName());  // Add interface
                }
            }
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(this, 
                "Error retrieving network interfaces: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    

private void appendMessage(String message, Color color, String sender, int port, String ip) {
    StyledDocument doc = jTextPane1.getStyledDocument();
    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setForeground(attrs, color);

    // Format: Me / Server: Message [port--ip--timestamp--id]
    String formattedMessage = String.format(
            "%s: %s [%d--%s--%s]", // Add unique ID to the message
            sender, message, port, ip, getCurrentTimestamp());

    try {
        doc.insertString(doc.getLength(), formattedMessage + "\n", attrs);
        chatHistory.add(formattedMessage); // Store in chat history
        logEvent("Message added to chat: " + formattedMessage);
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
    jTextPane1.setCaretPosition(doc.getLength()); // Auto-scroll to the bottom
}





    

    /**
     * Creates new form NewJFrame
     */
    public ClientChat() {
        initComponents();
        populateAvailableInterfaces();
        Client.setMessageListener(this); // Set the message listener
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Send = new javax.swing.JButton();
        ConnectionStatusLabel = new javax.swing.JLabel();
        AvailableInterfaces = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        LocalIP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        LocalPort = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        RemoteIP = new javax.swing.JTextField();
        RemotePort = new javax.swing.JTextField();
        TestConnectionBtn = new javax.swing.JButton();
        RetreiveInfo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        DeleteAll = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Archive = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgtxt = new javax.swing.JTextArea();
        connectionname = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Send.setText("Send");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        ConnectionStatusLabel.setForeground(new java.awt.Color(255, 0, 0));
        ConnectionStatusLabel.setText("Connection Status");

        AvailableInterfaces.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AvailableInterfaces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvailableInterfacesActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Available Interfaces");

        jLabel5.setText("Local IP :");

        jLabel6.setText("Local Port:");

        LocalPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LocalPortActionPerformed(evt);
            }
        });

        jLabel7.setText("Remote IP:");

        jLabel8.setText("Remote Port:");

        RemotePort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemotePortActionPerformed(evt);
            }
        });

        TestConnectionBtn.setText("Test Connection");
        TestConnectionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestConnectionBtnActionPerformed(evt);
            }
        });

        RetreiveInfo.setText("Retreive Info");
        RetreiveInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetreiveInfoActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        DeleteAll.setText("Delete All");
        DeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAllActionPerformed(evt);
            }
        });

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        Archive.setText("Archive");
        Archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArchiveActionPerformed(evt);
            }
        });

        msgtxt.setColumns(20);
        msgtxt.setRows(5);
        jScrollPane2.setViewportView(msgtxt);

        connectionname.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Send))
                    .addComponent(connectionname))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(DeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(RemoteIP)
                                            .addComponent(RemotePort, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(Archive, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LocalIP)
                                            .addComponent(LocalPort)))
                                    .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TestConnectionBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AvailableInterfaces, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(RetreiveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(ConnectionStatusLabel)
                                .addGap(98, 98, 98))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LocalIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(LocalPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(Send))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(RetreiveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(12, 12, 12)
                                .addComponent(AvailableInterfaces, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RemoteIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(RemotePort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TestConnectionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ConnectionStatusLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Archive, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(connectionname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
         String msg = msgtxt.getText().trim();
    if (!msg.isEmpty()) {
        try {
            Client.sendMessage(msg); // Send message to server
            String localIP = LocalIP.getText().trim();
            int localPort = Integer.parseInt(LocalPort.getText().trim());

            // Append message with "Me:" prefix
            appendMessage(msg, Color.BLUE, "Me", localPort, localIP);
            logEvent("Message sent: " + msg);

            msgtxt.setText(""); // Clear input field
        } catch (IOException e) {
            appendMessage("Failed to send message: " + e.getMessage(), Color.RED, "Me", 0, "N/A");
        }
    }
    }//GEN-LAST:event_SendActionPerformed

    private void AvailableInterfacesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvailableInterfacesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AvailableInterfacesActionPerformed

    private void LocalPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LocalPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LocalPortActionPerformed

    private void RemotePortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemotePortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RemotePortActionPerformed

    private void TestConnectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestConnectionBtnActionPerformed
         String remoteIP = RemoteIP.getText().trim();
    if (remoteIP.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a valid remote IP.", 
            "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int remotePort;
    try {
        remotePort = Integer.parseInt(RemotePort.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a valid remote port number.", 
            "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int localPort;
    try {
        localPort = Integer.parseInt(LocalPort.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a valid local port number.", 
            "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        Client.updateConnection(remoteIP, remotePort);  // Update connection details
        Client.start(localPort);  // Start the client with the local port
        
        ConnectionStatusLabel.setForeground(new java.awt.Color(0, 255, 0));  // Green for connected
        connectionname.setText("Connected to " + remoteIP + ":" + remotePort);
        logEvent("Connected to " + remoteIP + ":" + remotePort);
    } catch (Exception e) {
        ConnectionStatusLabel.setForeground(new java.awt.Color(255, 0, 0));  // Red for error
        connectionname.setText("Failed to connect to " + remoteIP + ":" + remotePort);
        logEvent("Failed to connect: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_TestConnectionBtnActionPerformed
  
    
    private void RetreiveInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetreiveInfoActionPerformed
 String selectedInterfaceName = (String) AvailableInterfaces.getSelectedItem();

    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.getDisplayName().equals(selectedInterfaceName)) {
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        LocalIP.setText(address.getHostAddress());  // Set local IP
                        LocalPort.setText("50000");  // Default local port
                        break;
                    }
                }
                break;
            }
        }
    } catch (SocketException e) {
        JOptionPane.showMessageDialog(this, 
            "Error retrieving network information: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_RetreiveInfoActionPerformed

    
    //here if you want to not make it archive msgs without deleting them only keep showArchivedMessages
    private void ArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchiveActionPerformed
    showArchivedMessages();
    }//GEN-LAST:event_ArchiveActionPerformed

    private void showArchivedMessages() {
    // Create a new dialog for archived messages
    JDialog archiveDialog = new JDialog(this, "Archived Messages", true);
    archiveDialog.setSize(400, 300);
    archiveDialog.setLayout(new BorderLayout());

    // Create a list for archived messages
    JList<String> archiveList = new JList<>(archivedMessages.toArray(new String[0]));
    archiveDialog.add(new JScrollPane(archiveList), BorderLayout.CENTER);

    // Create Restore button
    JButton restoreButton = new JButton("Restore");
    restoreButton.addActionListener(e -> {
        String selectedMessage = archiveList.getSelectedValue(); // Get the selected message
        if (selectedMessage != null) {
            // Restore the selected message
            restoreMessage(selectedMessage);
            archiveDialog.dispose(); // Close the dialog after restoring
        } else {
            JOptionPane.showMessageDialog(archiveDialog, "No message was selected to restore.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    archiveDialog.add(restoreButton, BorderLayout.SOUTH);

    archiveDialog.setLocationRelativeTo(this); // Center the dialog
    archiveDialog.setVisible(true); // Show the dialog
}

    private void restoreMessage(String message) {
    // Add the message back to chat history
    chatHistory.add(message); // Add to chat history
    archivedMessages.remove(message); // Remove from archived messages
    refreshChatDisplay(); // Refresh the chat display to show the restored message
    logEvent("Message restored: " + message);

    // Optionally, you can also send a restore command to other instances
    try {
        Client.sendMessage("RESTORE: " + message); // Broadcast restore message
    } catch (IOException e) {
        e.printStackTrace(); // Handle exception
    }
    }
    
    
    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
       String selectedMessage = getSelectedMessage(); // Get the selected message based on the typed text

    if (selectedMessage != null) {
        // Move the selected message to the archive
        archivedMessages.add(selectedMessage); // Add to archived messages
        chatHistory.remove(selectedMessage); // Remove from chat history
        refreshChatDisplay(); // Refresh UI to show the updated chat history
        logEvent("Message deleted: " + selectedMessage);

        // Send delete command to other instances with the full message
        try {
            Client.sendMessage("DELETE: " + selectedMessage); // Notify other instances about deletion
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
        startAutoRemoveFromArchive(selectedMessage);
    } else {
        JOptionPane.showMessageDialog(this, "No message found to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_DeleteActionPerformed

    private void DeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAllActionPerformed
       // Check if there are messages to delete
    if (!chatHistory.isEmpty()) {
        // Archive all messages
        archivedMessages.addAll(chatHistory); // Add all messages to archived messages
        chatHistory.clear(); // Clear chat history
        refreshChatDisplay(); // Refresh UI to show the updated chat history
        logEvent("All messages deleted from chat and archived.");

        // Notify other instances about the deletion of all messages
        try {
            Client.sendMessage("DELETE_ALL"); // You can decide to send a specific message for delete all
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }

        // Schedule removal from archive after 2 minutes
        startAutoRemoveFromArchive("AllMessages"); // Use a unique identifier
    } else {
        JOptionPane.showMessageDialog(this, "No messages found to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_DeleteAllActionPerformed

    
    
    private void startAutoRemoveFromArchive(String msg) {
    new Thread(() -> {
        try {
            Thread.sleep(2 * 60 * 1000); // Wait for 2 minutes (120000 milliseconds)

            // Remove the message from the archive
            if(msg.equals("AllMessages")){
                archivedMessages.clear();
                logEvent("All messages permenantly deleted and removed from archived.");
            }else
            archivedMessages.remove(msg);
            logEvent("Message permenantly deleted and removed from archive: " + msg);
            

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            e.printStackTrace();
        }
    }).start();
}

    
    
    private void refreshChatDisplay() {
    // Clear the chat display (JTextPane or equivalent)
    StyledDocument doc = jTextPane1.getStyledDocument();
    try {
        doc.remove(0, doc.getLength()); // Clear existing cont ent

        // Re-add messages from chatHistory to the display
        for (String message : chatHistory) {
            // Determine the color based on the sender
            Color color = message.startsWith("Me: ") ? Color.BLUE : Color.RED;

            // Create a new style for the message
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, color);
            
            // Append the message to the document
            doc.insertString(doc.getLength(), message + "\n", attrs);
        }

        // Auto-scroll to the bottom
        jTextPane1.setCaretPosition(doc.getLength());

    } catch (BadLocationException e) {
        e.printStackTrace(); // Handle exception as needed
    }
}


    
     private String getSelectedMessage() {
    String typedMessage = msgtxt.getText().trim(); // Get the typed message

    // Search for an exact match (ignoring case) in the chat history
    for (String message : chatHistory) {
        // Check if the message starts with "Me: " to ensure it's your message
        if (message.startsWith("Me: ")) {
            // Extract the actual message content by removing "Me: " prefix and metadata
            String[] parts = message.split(" \\[", 2); // Split at the metadata start
            String content = parts[0].substring("Me: ".length()).trim(); // Get content

            // Check if the content matches the typed message (case-insensitive)
            if (content.equalsIgnoreCase(typedMessage)) {
                return message; // Return the matching message for deletion
            }
        }
    }

    return null; // Return null if no match is found
}


     
     
     @Override
    public void dispose() {
        super.dispose();
        Client.close();  // Close the UDP socket
        logEvent("Client closed.");
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
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientChat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Archive;
    private javax.swing.JComboBox<String> AvailableInterfaces;
    private javax.swing.JLabel ConnectionStatusLabel;
    private javax.swing.JButton Delete;
    private javax.swing.JButton DeleteAll;
    private javax.swing.JTextField LocalIP;
    private javax.swing.JTextField LocalPort;
    private javax.swing.JTextField RemoteIP;
    private javax.swing.JTextField RemotePort;
    private javax.swing.JButton RetreiveInfo;
    private javax.swing.JButton Send;
    private javax.swing.JButton TestConnectionBtn;
    private javax.swing.JTextField connectionname;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextArea msgtxt;
    // End of variables declaration//GEN-END:variables
}
