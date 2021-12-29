import java.util.*;
import java.net.*;
import java.io.*;
public class GWackChannel{
    //private int port;
    public LinkedList<ServerThread> allClients;
    private BufferedReader s_in;
    private String username = "";
    ServerSocket sSock;

    public GWackChannel(int port){
        allClients = new LinkedList<ServerThread>();
        try{
            sSock = new ServerSocket(port);
        }catch(Exception e){
            System.err.println("Cannot open server socket");
            System.exit(1);
        }
    }

    public void serve(){
        
        while(true){
            Socket clientSocket;
        try {
            clientSocket = sSock.accept();
            System.out.println("Connection from: "+clientSocket.getRemoteSocketAddress());
            s_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String client = s_in.readLine();
            if(client.substring(0,6).equals("NAME: ")){
                username = client.substring(6);
            }
            ServerThread ct = new ServerThread(clientSocket, allClients, this, username);
            allClients.add(ct);
            ct.start();
            
        } catch (IOException e) {
            e.printStackTrace();
            break;
        }
        
    }
        
    }
 

    public LinkedList<ServerThread> getClientList(){
        return this.allClients;
    }

    public void disconnectUser(ServerThread c){
        allClients.remove(c);
    }

    public String updateUsers(){
        String Users = "START_CLIENT_LIST\n";
        for(ServerThread c : allClients){
            Users = Users + c.getUName()+"\n";
        }
        Users = Users + "END_CLIENT_LIST";
        return Users;

    }

    


    public static void main(String args[]){
        if(args.length <1){
            System.out.println("Error: no port number entered");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        GWackChannel ch = new GWackChannel(port);
        ch.serve();

    }
    



}
