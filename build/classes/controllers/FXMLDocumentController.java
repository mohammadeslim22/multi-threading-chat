/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author M.Eslim
 */
public class FXMLDocumentController implements Initializable {
    
   public static BufferedReader in;
   public static PrintWriter out;
    
        @FXML
    private Button login;

    @FXML
    private TextField ip;

    @FXML
    void enter(ActionEvent event) {
enter();

    }

    @FXML
    void login(ActionEvent event) {
enter();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("i am running ");
    }    
    
    private void enter()
    {
            try {
                String serverAddress = ip.getText();
                      Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ClientChat.fxml"));
                
                Parent contant = loader.load();
                Scene scene = new Scene(contant, 400, 300);
                
                Stage clientchat = new Stage();
                clientchat.setTitle("New Client ");
                clientchat.setScene(scene);
                clientchat.initModality(Modality.APPLICATION_MODAL);
                clientchat.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
}
