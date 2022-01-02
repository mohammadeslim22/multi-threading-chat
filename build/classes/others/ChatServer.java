/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import org.omg.CORBA.DataInputStream;
import static others.MThreadingC.off;
import others.cription;

/**
 *
 * @author M.Eslim
 */
public class ChatServer {

    // The port that the server listens on.
    private static final int PORT = 9001;
    private static final int PORT2 = 9002;
    
    public static boolean off2 = false;

    /**
     * The set of all names of clients in the chat room. Maintained so that we
     * can check that new clients are not registering name already in use.
     */
    private static ArrayList<String> names = new ArrayList<String>();

    /**
     * The set of all the print writers for all the clients. This set is kept so
     * we can easily broadcast messages.
     */
    private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

    /**
     * The appplication main method, which just listens on a port and spawns
     * handler threads.
     */
    
    public static void makereal()
    {
        
                  System.out.println(" اي ش  نكتب هان  ؟off  inside the off statement  ");
                    writers.forEach((writer) -> {
                    names.forEach((name1) -> {
                        writer.println("DELETED" + name1);
                    });
                });
        off2= true;
    }
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                new Handler(listener.accept()).start();
                System.out.println("handle ");
                //  new Handler(listener.accept(),listener2.accept()).start();
            }
        } finally {
            listener.close();
        }

    }

    private static class Handler extends Thread {

        private String name;
        private String reciever;
        private Socket socket;
        // private Socket socket2;
        private BufferedReader in;
        private PrintWriter out;
        //  private BufferedReader inreciever;
        //  private PrintWriter outreciever;

        /**
         * Constructs a handler thread, squirreling away the socket. All the
         * interesting work is done in the run method.
         */
//        public Handler(Socket socket,Socket socket2) {
//            this.socket = socket;
//                this.socket2 = socket2;
//        }
        public Handler(Socket socket) {
            this.socket = socket;

        }

        /**
         * Services this thread's client by repeatedly requesting a screen name
         * until a unique one has been submitted, then acknowledges the name and
         * registers the output stream for the client in a global set, then
         * repeatedly gets inputs and broadcasts them.
         */
        @Override
        public void run() {
            try {
                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("SUBMITNAME");

                    name = in.readLine();

                    System.out.println("name ?? " + name);
                    if (name == null) {
                        return;
                    }

                    synchronized (names) {
                        if (!names.contains(name)) {

                            names.add(name);
                            break;
                        }

                    }

                }

                out.println("NAMEACCEPTED");

                writers.add(out);

                writers.forEach((writer) -> {
                    names.forEach((name1) -> {
                        writer.println("NAME" + name1);
                    });
                });
                System.out.println("وصلنا ل جملة ال اف الي بدك ياها ؟ ");
               if (off2 == true) {
                    
                    
                    System.out.println(" اي ش  نكتب هان  ؟off  inside the off statement  ");
                    writers.forEach((writer) -> {
                    names.forEach((name1) -> {
                        writer.println("DELETED" + name1);
                    });
                });
                    
               }
                     System.out.println("وصلنا ل جملة ال اف الي بدك ياها ؟ ");
                // Accept messages from this client and broadcast them.
                // Ignore other clients that we do not want to  be broadcasted to.
                while (true) {
                    String input[] = in.readLine().split(":");
                    String x = cription.decrypt("1234567890123456", "1234567890123456", input[0]);
                   //  String x =  input[0];
                    String y = input[1].trim().toLowerCase();
                    //  String z = input[2].trim().toLowerCase();
                    //   String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    if ("".equals(y) || y == null) {
                        JOptionPane.showMessageDialog(null, "Specify receiver please");
                    } else if (y.equals("all")) {
                        int i = 0;
                        writers.forEach((writer) -> {

                            if (writer == out) {
                                writer.println("MESSAGE " + "You: " + x);

                            } else {
                                writer.println("MESSAGE " + name + ": " + x);
                            }

                        });

                    } else {
                        for (int i = 0; i < names.size(); i++) {
                            if (names.get(i) == null ? y == null : names.get(i).equals(y) && (y != name)) {
                                writers.get(i).println("MESSAGE special for you from :" + name + ":" + x);
                            }

                            if (names.get(i) == null ? name == null : names.get(i).equals(name)) {
                                writers.get(i).println("MESSAGE You: " + x);

                            }

                        }

                    }
                    System.out.println("client name " + name);

                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (name != null) {

                    names.remove(name);
//                    System.out.println("name has been removed ");
//                    writers.forEach((writer) -> {
//                        names.forEach((name1) -> {
//                            writer.println("NAME" + name1);
//
//                            System.out.println("the final system is here ");
//
//       });
//   });

                }
                if (out != null) {

                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }

        }
    }

}
