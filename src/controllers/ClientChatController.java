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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import others.MThreadingC;
import static others.MThreadingC.name;
import static others.MThreadingC.serverAddress;
import others.cription;

/**
 * FXML Controller class
 *
 * @author M.Eslim
 */
public class ClientChatController implements Initializable {

    BufferedReader in;
    PrintWriter out;
    //String name;

    public static ObservableList list = FXCollections.observableArrayList();

    public static ArrayList<String> ArrayNames = new ArrayList<>();
 
    //   DefaultListModel<String> DIM = new DefaultListModel<>();
    /**
     * Initializes the controller class.
     */

    @FXML
    private TextArea messageArea;

    @FXML
    private Label messagelable;

    @FXML
    private TextField textField;

    @FXML
    private Label Tolable;

    @FXML
    private TextField recivername;

    @FXML
    private ListView<String> clientsList;
    



        @FXML
    void itemselected(MouseEvent event) {
         
     String item = (String) clientsList.getSelectionModel().getSelectedItem();
        System.out.println("item "+ item);
            if (item==null||item.isEmpty()) 
            {
                recivername.setText("nothing sellected");
                
            }
            else{recivername.setText(item);
            }

    }

    public void filter() {
              try {
            //  list.removeAll(list);
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ClientChat.fxml"));
            root.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ClientChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML
    void recivername(ActionEvent event) {
        
        String S = textField.getText();
        String u = recivername.getText();
         cription.encrypt("1234567890123456", "1234567890123456", S);
        out.println(S + ":" + u);
   

        textField.setText("");
    }
    @FXML
    void onAction(ActionEvent event) {
       //     recivername.setText("ALL");
        String S = textField.getText();
        String u = recivername.getText();
        cription.encrypt("1234567890123456", "1234567890123456", S);
        out.println(S + ":" + u);
       //  out.println(S);
       

        textField.setText(" ");
        //   recivername.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        recivername.setText(" ");
        System.out.println("the initialize started");
        //  clientsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  textField.setEditable(false);
  messageArea.setEditable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {

                      
                try {
                    list.add("ALL");
                    //  String name = getName();
                    // TODO
                //    String serverAddress = getServerAddress();
                    Socket socket = new Socket(serverAddress, 9001);
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);

                    System.out.println("i am after socket  ");
                    while (true) {
                        String line = in.readLine();
                        System.out.println("what ? " + line);
                        if (line.startsWith("SUBMITNAME")) {
                           // name = getName();
                            out.println(name);
                        } else if (line.startsWith("NAMEACCEPTED")) {
                          //   filter();
                            textField.setEditable(true);
                            //  nameslist.add(name, this);
                        } else if (line.startsWith("MESSAGE")) {
                            messageArea.appendText(line.substring(8) + "\n");
                        } else if (line.startsWith("NAME")) {
                            if (!list.contains(line.substring(4)) /*&& (line.substring(4) == null ? name != null : !line.substring(4).equals(name))*/)
                            {
                                clientsList.getItems().clear();
                           
                                list.add(line.substring(4));
                                // DIM.addElement(line.substring(4));
                                  list.remove(name);
                                 clientsList.getItems().addAll(list);
                            }
                        }
                        else if (line.startsWith("DELETED")) {
                            if (list.contains(line.substring(7))
                                    /*&& (line.substring(4) == null ? name != null : !line.substring(4).equals(name))*/)
                            {
                                System.out.println("اضافة الأسماء من أول ! ");
                                clientsList.getItems().clear();
                                     list.clear();
                                list.add(line.substring(7));
                                // DIM.addElement(line.substring(4));
                                  list.remove(name);
                                 clientsList.getItems().addAll(list);
                            }
                        }
                    }
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClientChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
                       
    }

}
