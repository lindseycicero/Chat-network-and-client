import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
public class GWackClientFrame extends JFrame implements KeyListener{

    private String name="";
    private String IP ="";
    private int portNum;
    public boolean connected = false;
    private JTextArea input;
    private JTextArea mOnline;
    private BufferedReader s_in; 
    private Socket cSock;
    private PrintWriter pw;
    private Thread clientThread;
    private String[] Themes = {"Default","Dark Mode","Warm","Cool","Hacker"};
    private String th ="";
    
    

    public GWackClientFrame(){
        super();
        this.setTitle("GWack");
        this.setSize(1000,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //object creation
        JPanel n = new JPanel();
        n.setLayout(new BoxLayout(n,BoxLayout.LINE_AXIS));
        JPanel c = new JPanel();
        c.setLayout(new BoxLayout(c,BoxLayout.PAGE_AXIS));
        JPanel s = new JPanel();
        s.setLayout(new BoxLayout(s,BoxLayout.LINE_AXIS));
        JPanel ea = new JPanel(new BorderLayout());
        JPanel w = new JPanel();
        w.setLayout(new BoxLayout(w,BoxLayout.PAGE_AXIS));
        JButton connector = new JButton("Connect");
        JButton disconnector = new JButton("Disconnect");
        JButton settings = new JButton("Settings");
        disconnector.setVisible(false);
        JComboBox<String> themes = new JComboBox<String>(Themes);
        JButton sender = new JButton("Send");
        JLabel u = new JLabel("Name");
        JTextField username = new JTextField(5);
        JLabel h = new JLabel("IP Address");
        JTextField host = new JTextField(5);
        JLabel p = new JLabel("Port");
        JTextField port = new JTextField(5);
        JLabel members = new JLabel("Members Online");
        mOnline = new JTextArea(20,20);
        mOnline.setAutoscrolls(true);
        mOnline.setEditable(false);
        JScrollPane scrollm = new JScrollPane(mOnline);
        scrollm.getViewport().setPreferredSize(new Dimension(110,700));
        scrollm.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollm.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollm.setAutoscrolls(true);
        JLabel in = new JLabel("Messages");
        input = new JTextArea(10,10);
        input.setAutoscrolls(true);
        input.setEditable(false);
        DefaultCaret caret = (DefaultCaret)input.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        //input.setPreferredSize(new Dimension(700,1000));
        JScrollPane scrolli = new JScrollPane(input);
        scrolli.getViewport().setPreferredSize(new Dimension(470,500));
        scrolli.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrolli.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrolli.setAutoscrolls(true);
        JLabel out = new JLabel("Compose");
        JTextArea output = new JTextArea(5,5);
        output.setAutoscrolls(true);
        JScrollPane scrollo = new JScrollPane(output);
        scrollo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollo.getViewport().setPreferredSize(new Dimension(470,150));
        scrollo.setAutoscrolls(true);
        
        JFrame Pop = new JFrame();
        JPanel temp = new JPanel();
        JPanel temp2 = new JPanel();
        JPanel temp3 = new JPanel();
        JLabel l1 = new JLabel("Choose a Theme");

        JButton save = new JButton("Save");
        Pop.setTitle("Settings");
        Pop.setSize(450,150);
        Pop.setLocation(this.getLocation().x, this.getLocation().y);
        temp.setLayout(new BoxLayout(temp,BoxLayout.PAGE_AXIS));
        temp3.setLayout(new BoxLayout(temp3,BoxLayout.PAGE_AXIS));                                    
        temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        temp3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        temp3.add(Box.createRigidArea(new Dimension(0,5)));
        temp3.add(l1);
        temp3.add(Box.createRigidArea(new Dimension(0,20)));
        temp.add(themes);
        temp2.add(save);
        Pop.add(temp,BorderLayout.CENTER);
        Pop.add(temp2,BorderLayout.SOUTH);
        Pop.add(temp3,BorderLayout.WEST);

        Border border1 = BorderFactory.createLineBorder(Color.BLACK);
        
        username.setBorder(border1);
        host.setBorder(border1);
        port.setBorder(border1);
        mOnline.setBorder(border1);
        input.setBorder(border1);
        output.setBorder(border1);


        //formatting
        n.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        n.add(Box.createRigidArea(new Dimension(170,0)));
        n.add(u);
        n.add(Box.createRigidArea(new Dimension(5,0)));
        n.add(username);
        n.add(Box.createRigidArea(new Dimension(7,0)));
        n.add(h);
        n.add(Box.createRigidArea(new Dimension(5,0)));
        n.add(host);
        n.add(Box.createRigidArea(new Dimension(7,0)));
        n.add(p);
        n.add(Box.createRigidArea(new Dimension(5,0)));
        n.add(port);
        n.add(Box.createRigidArea(new Dimension(10,0)));
        n.add(connector);
        n.add(disconnector);

        c.add(in);
        in.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        c.add(Box.createRigidArea(new Dimension(0,5)));
        c.add(scrolli);
        c.add(Box.createRigidArea(new Dimension(0,10)));
        c.add(out);
        c.add(Box.createRigidArea(new Dimension(0,5)));
        c.add(scrollo);

        s.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        s.add(Box.createRigidArea(new Dimension(915,0)));
        s.add(sender);

        w.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        w.add(members);
        w.add(Box.createRigidArea(new Dimension(0,5)));
        w.add(scrollm);
        w.add(Box.createRigidArea(new Dimension(50,5)));
        w.add(settings);

        this.add(n,BorderLayout.NORTH);
        this.add(c,BorderLayout.CENTER);
        this.add(s,BorderLayout.SOUTH);
        this.add(ea,BorderLayout.EAST);
        this.add(w,BorderLayout.WEST);

        //functionality
        
        
        connector.addActionListener((e)->{  connector.setVisible(false);
                                            disconnector.setVisible(true);
                                            connected = true;
                                            name = username.getText();
                                            username.setEditable(false);
                                            IP = host.getText();
                                            host.setEditable(false);
                                            portNum = Integer.parseInt(port.getText());
                                            port.setEditable(false);
                                            if(port.getText().length()<4||port.getText() == null){
                                                disconnector.setVisible(false);
                                                connector.setVisible(true);
                                                connected = false;
                                                JOptionPane.showMessageDialog(this,"Invalid Port","ERROR",JOptionPane.ERROR_MESSAGE);
                                                
                                            }
                                            
                                            
                                            if(!(IP.equals("ssh-cs2113.adamaviv.com")||IP.equals("localhost"))){
                                                disconnector.setVisible(false);
                                                connector.setVisible(true);
                                                connected = false;
                                                JOptionPane.showMessageDialog(this,"Invalid Host","ERROR",JOptionPane.ERROR_MESSAGE);
                                            }

                                            try{
                                                cSock = new Socket(IP,portNum);
                                                pw = new PrintWriter(cSock.getOutputStream());
                                                s_in = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
                                            }catch(Exception ex){
                                                disconnector.setVisible(false);
                                                connector.setVisible(true);
                                                connected = false;
                                                JOptionPane.showMessageDialog(this,"Cannot Connect to Server","ERROR",JOptionPane.ERROR_MESSAGE);
                                                
                                            }

                                            if(IP.equals("ssh-cs2113.adamaviv.com")){
                                                pw.println("SECRET");
                                                pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
                                                pw.println("NAME");
                                                pw.println(name);
                                                pw.flush();

                                            }else if(IP.equals("localhost")){
                                                pw.println("NAME: "+name);
                                                pw.flush();
                                            }

                                            clientThread = new Thread(() ->{
                                                try{
                                                    String message = "";                                                   
                                                    while(connected){
                                                        if(s_in.ready()){
                                                            message = s_in.readLine();
                                                        }else{
                                            
                                                            continue;
                                                        }
                                                        if(message.equals("START_CLIENT_LIST")){
                                                            String allClients="";
                                                            message = s_in.readLine();
                                                            while(!message.equals("END_CLIENT_LIST")){
                                                                allClients+=message+"\n";
                                                                message = s_in.readLine();

                                                            }
                                                            mOnline.setText(allClients);

                                                        }else{
                                                            input.append(message+"\n");
                                                        }
                                                    }

                                                }catch(Exception ex){
                                                    
                                                }
                                            });
                                            clientThread.start();
                                                                                                                
         }); 

        disconnector.addActionListener((e)->{   disconnector.setVisible(false);
                                                connector.setVisible(true);
                                                connected = false;   
                                                name = "";
                                                username.setEditable(true);
                                                IP ="";
                                                host.setEditable(true);
                                                portNum = 0;
                                                port.setEditable(true);
                                                mOnline.setText("");
                                                input.setText("");
                                                try{
                                                    if(IP.equals("localhost")){
                                                        pw.println("DIE");
                                                        pw.flush();
                                                    }
                                                    pw.close();
                                                    s_in.close();
                                                    cSock.close();
                                                }catch(IOException ex){
                                                    JOptionPane.showMessageDialog(this,"Error: Cannot Disconnect","ERROR",JOptionPane.ERROR_MESSAGE);
                                                }


                                             
        }); 

        settings.addActionListener((e)->{   Pop.setVisible(true);
                                            
                                            save.addActionListener((e2)->{  th = (String)themes.getSelectedItem();
                                                                            if(th.equals("Default")){
                                                                                Border border2 = BorderFactory.createLineBorder(Color.GRAY);
                                                                                username.setBorder(border1);
                                                                                host.setBorder(border1);
                                                                                port.setBorder(border1);
                                                                                mOnline.setBorder(border1);
                                                                                input.setBorder(border1);
                                                                                output.setBorder(border1);
                                                                                this.setBackground(null);
                                                                                Pop.setBackground(null);
                                                                                n.setBackground(null);
                                                                                s.setBackground(null);
                                                                                ea.setBackground(null);
                                                                                w.setBackground(null);
                                                                                c.setBackground(null);
                                                                                scrolli.setBackground(null);
                                                                                scrolli.getVerticalScrollBar().setBackground(null);
                                                                                scrollo.setBackground(null);
                                                                                scrollo.getVerticalScrollBar().setBackground(null);
                                                                                scrollm.setBackground(null);
                                                                                scrollm.getVerticalScrollBar().setBackground(null);
                                                                                temp.setBackground(null);
                                                                                temp2.setBackground(null);
                                                                                temp3.setBackground(null);
                                                                                in.setForeground(null);
                                                                                out.setBackground(null);
                                                                                username.setForeground(null);
                                                                                host.setForeground(null);
                                                                                port.setForeground(null);
                                                                                connector.setForeground(null);
                                                                                disconnector.setForeground(null);
                                                                                mOnline.setForeground(null);
                                                                                input.setForeground(null);
                                                                                output.setForeground(null);
                                                                                sender.setForeground(null);
                                                                                settings.setForeground(null);
                                                                                themes.setForeground(null);
                                                                                save.setForeground(null);
                                                                                username.setBackground(Color.WHITE);
                                                                                host.setBackground(Color.WHITE);
                                                                                port.setBackground(Color.WHITE);
                                                                                connector.setBackground(null);
                                                                                disconnector.setBackground(null);
                                                                                mOnline.setBackground(null);
                                                                                input.setBackground(null);
                                                                                output.setBackground(null);
                                                                                settings.setBackground(null);
                                                                                sender.setBackground(null);
                                                                                themes.setBackground(null);
                                                                                save.setBackground(null);
                                                                                scrolli.setForeground(null);
                                                                                scrollo.setForeground(null);
                                                                                scrollm.setForeground(null);
                                                                                u.setForeground(null);
                                                                                h.setForeground(null);
                                                                                p.setForeground(null);
                                                                                in.setForeground(null);
                                                                                out.setForeground(null);
                                                                                members.setForeground(null);
                                                                                l1.setForeground(null);
                                                                                sender.setBackground(null);
                                                                                sender.setOpaque(false);
                                                                                sender.setBorderPainted(true);
                                                                                connector.setBackground(null);
                                                                                connector.setOpaque(false);
                                                                                connector.setBorderPainted(true);
                                                                                settings.setBackground(null);
                                                                                settings.setOpaque(false);
                                                                                settings.setBorderPainted(true);
                                                                                disconnector.setBackground(null);
                                                                                disconnector.setOpaque(false);
                                                                                disconnector.setBorderPainted(true);
                                                                                save.setBackground(null);
                                                                                save.setOpaque(false);
                                                                                save.setBorderPainted(true);
                                                                            }
                                                                            if(th.equals("Dark Mode")){
                                                                                Border border2 = BorderFactory.createLineBorder(Color.GRAY);
                                                                                username.setBorder(border2);
                                                                                host.setBorder(border2);
                                                                                port.setBorder(border2);
                                                                                mOnline.setBorder(border2);
                                                                                input.setBorder(border2);
                                                                                output.setBorder(border2);
                                                                                this.setBackground(Color.BLACK);
                                                                                Pop.setBackground(Color.BLACK);
                                                                                n.setBackground(new Color(38,38,38));
                                                                                s.setBackground(new Color(38,38,38));
                                                                                ea.setBackground(new Color(38,38,38));
                                                                                w.setBackground(new Color(38,38,38));
                                                                                c.setBackground(new Color(38,38,38));
                                                                                scrolli.setBackground(new Color(38,38,38));
                                                                                scrolli.getVerticalScrollBar().setBackground(new Color(38,38,38));
                                                                                scrollo.setBackground(new Color(38,38,38));
                                                                                scrollo.getVerticalScrollBar().setBackground(new Color(38,38,38));
                                                                                scrollm.setBackground(new Color(38,38,38));
                                                                                scrollm.getVerticalScrollBar().setBackground(new Color(38,38,38));
                                                                                temp.setBackground(new Color(38,38,38));
                                                                                temp2.setBackground(new Color(38,38,38));
                                                                                temp3.setBackground(new Color(38,38,38));
                                                                                in.setForeground(new Color(38,38,38));
                                                                                out.setBackground(new Color(38,38,38));
                                                                                username.setForeground(new Color(210,210,210));
                                                                                host.setForeground(new Color(210,210,210));
                                                                                port.setForeground(new Color(210,210,210));
                                                                                connector.setForeground(new Color(210,210,210));
                                                                                disconnector.setForeground(new Color(210,210,210));
                                                                                mOnline.setForeground(new Color(210,210,210));
                                                                                input.setForeground(new Color(210,210,210));
                                                                                output.setForeground(new Color(210,210,210));
                                                                                sender.setForeground(new Color(210,210,210));
                                                                                settings.setForeground(new Color(210,210,210));
                                                                                themes.setForeground(new Color(210,210,210));
                                                                                save.setForeground(new Color(210,210,210));
                                                                                username.setBackground(new Color(98,98,98));
                                                                                host.setBackground(new Color(98,98,98));
                                                                                port.setBackground(new Color(98,98,98));
                                                                                connector.setBackground(new Color(98,98,98));
                                                                                disconnector.setBackground(new Color(98,98,98));
                                                                                mOnline.setBackground(new Color(98,98,98));
                                                                                input.setBackground(new Color(98,98,98));
                                                                                output.setBackground(new Color(98,98,98));
                                                                                settings.setBackground(new Color(98,98,98));
                                                                                sender.setBackground(new Color(98,98,98));
                                                                                themes.setBackground(new Color(98,98,98));
                                                                                save.setBackground(new Color(98,98,98));
                                                                                scrolli.setForeground(new Color(38,38,38));
                                                                                scrollo.setForeground(new Color(38,38,38));
                                                                                scrollm.setForeground(new Color(38,38,38));
                                                                                u.setForeground(new Color(210,210,210));
                                                                                h.setForeground(new Color(210,210,210));
                                                                                p.setForeground(new Color(210,210,210));
                                                                                in.setForeground(new Color(210,210,210));
                                                                                out.setForeground(new Color(210,210,210));
                                                                                members.setForeground(new Color(210,210,210));
                                                                                l1.setForeground(new Color(210,210,210));
                                                                                sender.setBackground(Color.BLACK);
                                                                                sender.setOpaque(true);
                                                                                sender.setBorderPainted(false);
                                                                                connector.setBackground(Color.BLACK);
                                                                                connector.setOpaque(true);
                                                                                connector.setBorderPainted(false);
                                                                                settings.setBackground(Color.BLACK);
                                                                                settings.setOpaque(true);
                                                                                settings.setBorderPainted(false);
                                                                                disconnector.setBackground(Color.BLACK);
                                                                                disconnector.setOpaque(true);
                                                                                disconnector.setBorderPainted(false);
                                                                                save.setBackground(Color.BLACK);
                                                                                save.setOpaque(true);
                                                                                save.setBorderPainted(false);

                                                                            }
                                                                            if(th.equals("Warm")){
                                                                                Border border3 = BorderFactory.createLineBorder(new Color(155,24,24));
                                                                                username.setBorder(border3);
                                                                                host.setBorder(border3);
                                                                                port.setBorder(border3);
                                                                                mOnline.setBorder(border3);
                                                                                input.setBorder(border3);
                                                                                output.setBorder(border3);
                                                                                this.setBackground(Color.RED);
                                                                                Pop.setBackground(Color.RED);
                                                                                n.setBackground(new Color(196,24,24));
                                                                                s.setBackground(new Color(196,24,24));
                                                                                ea.setBackground(new Color(196,24,24));
                                                                                w.setBackground(new Color(196,24,24));
                                                                                c.setBackground(new Color(196,24,24));
                                                                                scrolli.setBackground(new Color(196,24,24));
                                                                                scrolli.getVerticalScrollBar().setBackground(new Color(196,24,24));
                                                                                scrollo.setBackground(new Color(196,24,24));
                                                                                scrollo.getVerticalScrollBar().setBackground(new Color(196,24,24));
                                                                                scrollm.setBackground(new Color(196,24,24));
                                                                                scrollm.getVerticalScrollBar().setBackground(new Color(196,24,24));
                                                                                temp.setBackground(new Color(196,24,24));
                                                                                temp2.setBackground(new Color(196,24,24));
                                                                                temp3.setBackground(new Color(196,24,24));
                                                                                in.setForeground(new Color(196,24,24));
                                                                                out.setBackground(new Color(196,24,24));
                                                                                username.setForeground(new Color(255,204,102));
                                                                                host.setForeground(new Color(255,204,102));//
                                                                                port.setForeground(new Color(255,204,102));
                                                                                connector.setForeground(new Color(255,204,102));
                                                                                disconnector.setForeground(new Color(255,204,102));
                                                                                mOnline.setForeground(new Color(255,204,102));
                                                                                input.setForeground(new Color(255,204,102));
                                                                                output.setForeground(new Color(255,204,102));
                                                                                sender.setForeground(new Color(255,204,102));
                                                                                settings.setForeground(new Color(255,204,102));
                                                                                themes.setForeground(new Color(255,204,102));
                                                                                save.setForeground(new Color(255,204,102));
                                                                                username.setBackground(new Color(202,97,37));
                                                                                host.setBackground(new Color(202,97,37));//
                                                                                port.setBackground(new Color(202,97,37));
                                                                                connector.setBackground(new Color(202,97,37));
                                                                                disconnector.setBackground(new Color(202,97,37));
                                                                                mOnline.setBackground(new Color(202,97,37));
                                                                                input.setBackground(new Color(202,97,37));
                                                                                output.setBackground(new Color(202,97,37));
                                                                                settings.setBackground(new Color(202,97,37));
                                                                                sender.setBackground(new Color(202,97,37));
                                                                                themes.setBackground(new Color(202,97,37));
                                                                                save.setBackground(new Color(202,97,37));
                                                                                scrolli.setForeground(new Color(196,24,24));
                                                                                scrollo.setForeground(new Color(196,24,24));
                                                                                scrollm.setForeground(new Color(196,24,24));
                                                                                u.setForeground(new Color(255,204,102));
                                                                                h.setForeground(new Color(255,204,102));
                                                                                p.setForeground(new Color(255,204,102));
                                                                                in.setForeground(new Color(255,204,102));
                                                                                out.setForeground(new Color(255,204,102));
                                                                                members.setForeground(new Color(255,204,102));
                                                                                l1.setForeground(new Color(255,204,102));
                                                                                sender.setBackground(new Color(155,24,24));//
                                                                                sender.setOpaque(true);
                                                                                sender.setBorderPainted(false);
                                                                                connector.setBackground(new Color(155,24,24));
                                                                                connector.setOpaque(true);
                                                                                connector.setBorderPainted(false);
                                                                                settings.setBackground(new Color(155,24,24));
                                                                                settings.setOpaque(true);
                                                                                settings.setBorderPainted(false);
                                                                                disconnector.setBackground(new Color(155,24,24));
                                                                                disconnector.setOpaque(true);
                                                                                disconnector.setBorderPainted(false);
                                                                                save.setBackground(new Color(155,24,24));
                                                                                save.setOpaque(true);
                                                                                save.setBorderPainted(false);
                                                                            }
                                                                            if(th.equals("Cool")){
                                                                                Border border4 = BorderFactory.createLineBorder(new Color(0,0,51));
                                                                                username.setBorder(border4);
                                                                                host.setBorder(border4);
                                                                                port.setBorder(border4);
                                                                                mOnline.setBorder(border4);
                                                                                input.setBorder(border4);
                                                                                output.setBorder(border4);
                                                                                this.setBackground(Color.BLUE);
                                                                                Pop.setBackground(Color.BLUE);
                                                                                n.setBackground(new Color(39,39,151));
                                                                                s.setBackground(new Color(39,39,151));
                                                                                ea.setBackground(new Color(39,39,151));
                                                                                w.setBackground(new Color(39,39,151));
                                                                                c.setBackground(new Color(39,39,151));
                                                                                scrolli.setBackground(new Color(39,39,151));
                                                                                scrolli.getVerticalScrollBar().setBackground(new Color(39,39,151));
                                                                                scrollo.setBackground(new Color(39,39,151));
                                                                                scrollo.getVerticalScrollBar().setBackground(new Color(39,39,151));
                                                                                scrollm.setBackground(new Color(39,39,151));
                                                                                scrollm.getVerticalScrollBar().setBackground(new Color(39,39,151));
                                                                                temp.setBackground(new Color(39,39,151));
                                                                                temp2.setBackground(new Color(39,39,151));
                                                                                temp3.setBackground(new Color(39,39,151));
                                                                                in.setForeground(new Color(39,39,151));
                                                                                out.setBackground(new Color(39,39,151));
                                                                                username.setForeground(new Color(201,139,232));
                                                                                host.setForeground(new Color(201,139,232));//
                                                                                port.setForeground(new Color(201,139,232));
                                                                                connector.setForeground(new Color(201,139,232));
                                                                                disconnector.setForeground(new Color(201,139,232));
                                                                                mOnline.setForeground(new Color(201,139,232));
                                                                                input.setForeground(new Color(201,139,232));
                                                                                output.setForeground(new Color(201,139,232));
                                                                                sender.setForeground(new Color(201,139,232));
                                                                                settings.setForeground(new Color(201,139,232));
                                                                                themes.setForeground(new Color(201,139,232));
                                                                                save.setForeground(new Color(201,139,232));
                                                                                username.setBackground(new Color(82,144,206));
                                                                                host.setBackground(new Color(82,144,206));//
                                                                                port.setBackground(new Color(82,144,206));
                                                                                connector.setBackground(new Color(82,144,206));
                                                                                disconnector.setBackground(new Color(82,144,206));
                                                                                mOnline.setBackground(new Color(82,144,206));
                                                                                input.setBackground(new Color(82,144,206));
                                                                                output.setBackground(new Color(82,144,206));
                                                                                settings.setBackground(new Color(82,144,206));
                                                                                sender.setBackground(new Color(82,144,206));
                                                                                themes.setBackground(new Color(82,144,206));
                                                                                save.setBackground(new Color(82,144,206));
                                                                                scrolli.setForeground(new Color(39,39,151));
                                                                                scrollo.setForeground(new Color(39,39,151));
                                                                                scrollm.setForeground(new Color(39,39,151));
                                                                                u.setForeground(new Color(201,139,232));
                                                                                h.setForeground(new Color(201,139,232));
                                                                                p.setForeground(new Color(201,139,232));
                                                                                in.setForeground(new Color(201,139,232));
                                                                                out.setForeground(new Color(201,139,232));
                                                                                members.setForeground(new Color(201,139,232));
                                                                                l1.setForeground(new Color(201,139,232));
                                                                                sender.setBackground(new Color(0,0,51));//
                                                                                sender.setOpaque(true);
                                                                                sender.setBorderPainted(false);
                                                                                connector.setBackground(new Color(0,0,51));
                                                                                connector.setOpaque(true);
                                                                                connector.setBorderPainted(false);
                                                                                settings.setBackground(new Color(0,0,51));
                                                                                settings.setOpaque(true);
                                                                                settings.setBorderPainted(false);
                                                                                disconnector.setBackground(new Color(0,0,51));
                                                                                disconnector.setOpaque(true);
                                                                                disconnector.setBorderPainted(false);
                                                                                save.setBackground(new Color(0,0,51));
                                                                                save.setOpaque(true);
                                                                                save.setBorderPainted(false);
                                                                            }
                                                                            if(th.equals("Hacker")){
                                                                                Border border5 = BorderFactory.createLineBorder(Color.WHITE);
                                                                                username.setBorder(border5);
                                                                                host.setBorder(border5);
                                                                                port.setBorder(border5);
                                                                                mOnline.setBorder(border5);
                                                                                input.setBorder(border5);
                                                                                output.setBorder(border5);
                                                                                this.setBackground(Color.BLACK);
                                                                                Pop.setBackground(Color.BLACK);
                                                                                n.setBackground(Color.BLACK);
                                                                                s.setBackground(Color.BLACK);
                                                                                ea.setBackground(Color.BLACK);
                                                                                w.setBackground(Color.BLACK);
                                                                                c.setBackground(Color.BLACK);
                                                                                scrolli.setBackground(Color.BLACK);
                                                                                scrolli.getVerticalScrollBar().setBackground(Color.BLACK);
                                                                                scrollo.setBackground(Color.BLACK);
                                                                                scrollo.getVerticalScrollBar().setBackground(Color.BLACK);
                                                                                scrollm.setBackground(Color.BLACK);
                                                                                scrollm.getVerticalScrollBar().setBackground(Color.BLACK);
                                                                                temp.setBackground(Color.BLACK);
                                                                                temp2.setBackground(Color.BLACK);
                                                                                temp3.setBackground(Color.BLACK);
                                                                                in.setForeground(Color.BLACK);
                                                                                out.setBackground(Color.BLACK);
                                                                                username.setForeground(new Color(0,255,0));
                                                                                host.setForeground(new Color(0,255,0));//
                                                                                port.setForeground(new Color(0,255,0));
                                                                                connector.setForeground(new Color(0,255,0));
                                                                                disconnector.setForeground(new Color(0,255,0));
                                                                                mOnline.setForeground(new Color(0,255,0));
                                                                                input.setForeground(new Color(0,255,0));
                                                                                output.setForeground(new Color(0,255,0));
                                                                                sender.setForeground(new Color(0,255,0));
                                                                                settings.setForeground(new Color(0,255,0));
                                                                                themes.setForeground(new Color(0,255,0));
                                                                                save.setForeground(new Color(0,255,0));
                                                                                username.setBackground(Color.BLACK);
                                                                                host.setBackground(Color.BLACK);//
                                                                                port.setBackground(Color.BLACK);
                                                                                connector.setBackground(Color.BLACK);
                                                                                disconnector.setBackground(Color.BLACK);
                                                                                mOnline.setBackground(Color.BLACK);
                                                                                input.setBackground(Color.BLACK);
                                                                                output.setBackground(Color.BLACK);
                                                                                settings.setBackground(Color.BLACK);
                                                                                sender.setBackground(Color.BLACK);
                                                                                themes.setBackground(Color.BLACK);
                                                                                save.setBackground(Color.BLACK);
                                                                                scrolli.setForeground(Color.BLACK);
                                                                                scrollo.setForeground(Color.BLACK);
                                                                                scrollm.setForeground(Color.BLACK);
                                                                                u.setForeground(new Color(0,255,0));
                                                                                h.setForeground(new Color(0,255,0));
                                                                                p.setForeground(new Color(0,255,0));
                                                                                in.setForeground(new Color(0,255,02));
                                                                                out.setForeground(new Color(0,255,0));
                                                                                members.setForeground(new Color(0,255,0));
                                                                                l1.setForeground(new Color(0,255,0));
                                                                                sender.setBackground(Color.BLACK);//
                                                                                sender.setOpaque(true);
                                                                                sender.setBorderPainted(false);
                                                                                connector.setBackground(Color.BLACK);
                                                                                connector.setOpaque(true);
                                                                                connector.setBorderPainted(false);
                                                                                settings.setBackground(Color.BLACK);
                                                                                settings.setOpaque(true);
                                                                                settings.setBorderPainted(false);
                                                                                disconnector.setBackground(Color.BLACK);
                                                                                disconnector.setOpaque(true);
                                                                                disconnector.setBorderPainted(false);
                                                                                save.setBackground(Color.BLACK);
                                                                                save.setOpaque(true);
                                                                                save.setBorderPainted(false);
                                                                            }

                                                                            
                                                                            Pop.dispose();


                                            });
                                        

            
            
            

        });
 

        sender.addActionListener((e)->{ String message = output.getText();
                                        pw.println(message);
                                        pw.flush();
                                        output.setText("");
                                        
                                        
                                        

        });

        output.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String message = output.getText();
                                            pw.println(message);
                                            pw.flush();
                }
            }

            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    output.setText("");
                }
            }
        });

  

    }

    public String getName(){
        return this.name;
    }

    
    

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    
    
}


