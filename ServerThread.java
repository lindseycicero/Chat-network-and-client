import java.util.*;
import java.net.*;
import java.io.*;
public class ServerThread extends Thread{
    private Socket clientSocket;
    private LinkedList<ServerThread> ll;
    private PrintWriter s_out;
    private BufferedReader s_in;
    public GWackChannel ch;
    private String username="";


    

    public ServerThread(Socket s, LinkedList<ServerThread> ll, GWackChannel ch, String username){
        super();
        this.clientSocket = s;
        this.ll=ll;    
        this.ch = ch;
        this.username = username;
        
    }

    public void run(){
        
            try{
            s_out = new PrintWriter(clientSocket.getOutputStream());
            s_in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msg="";
            this.send(ch.updateUsers());          
                while((msg=s_in.readLine()) != null){

                    this.send(ch.updateUsers());
                    String clientChat = "["+username+"] "+msg;
                    this.send(clientChat);
                    //System.out.println("["+this.username+"] "+clientChat);
                    
            }    
             
            }catch(Exception e){
                
            }
            finally{
                try{
                    if(s_out!=null){
                        s_out.close();
                    }
                    if(s_in!=null){
                        s_in.close();
                        clientSocket.close();
                        ch.disconnectUser(this);
                        this.send(ch.updateUsers());
                    }

                }catch(Exception e){}
            }
            
            

            

        }


        public void send(String message){
            
            for(ServerThread c : this.ll){
                if(!(c.getCSock().isClosed()))
                c.getWriter().println(message);
                c.getWriter().flush();
            }
        }

        

        public PrintWriter getWriter(){
            return this.s_out;
        }

        public String getUName(){
            return this.username;
        }

        public Socket getCSock(){
            return this.clientSocket;
        }
       


        

}

    

