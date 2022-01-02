/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.ف
 */
package others;

import static controllers.ClientChatController.ArrayNames;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import static others.ChatServer.makereal;

/**
 *
 * @author M.Eslim
 */
public class MThreadingC extends Application {

    public static String serverAddress;

    public static String name;
    public static boolean off = false;

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                null,
                "Enter IP Address of the Server:",
                "Welcome to the Chatter",
                JOptionPane.QUESTION_MESSAGE);
    }

    private String getName() {
        return JOptionPane.showInputDialog(
                null,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("i am running the server ");
        serverAddress = getServerAddress();
        name = getName();

        Parent root = FXMLLoader.load(getClass().getResource("/Views/ClientChat.fxml"));
        // root.setVisible(false);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("     chat application                 " + name);

        stage.setOnCloseRequest(e -> {
            off = true;
            makereal();
            System.out.println(off);
            System.out.println("i am closed ! ");
            Platform.exit();

        });

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //   ChatServer s = new ChatServer();
        launch(args);
    }

}
