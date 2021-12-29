Slack Simulator

## Describe the OOP design of your GWack Slack Simulator

Please provide a short description of your programming progress

For some reason I started off with the server because I understood that better. I aproached the server by making a new clas, ServerThread, that extends Thread. This class was used so that the server could handel multiple clients. In the GWackChannel class when run the server opens at the port specified in the command line. The server then listens and accepts clients when they join. The Channel then waits for the message "NAME: ~username~" to pass the clients user name when starting their thread. In the ServerThread the server is constanly listening for a message from the specific clients sockt. When it reads one it it then sends it back to all users including the sender with the username added to the front. The Channel also keeps a list of all ServerThreads active and frequently sends the list of all active/conected users to all of the clients so they can be up to date on who else is online.

For the client GUI I started by forrmatting everything with buttons, textAreas, a keyListeners. Then in the connect button I start the socket at the IP and port taken in from the GUI making sure to error check that they are valid. Because I made my own server I have an extra check that determines what is intialy sent. If connected to the class server then the password and usernme are sent, if connecting to mine then only the name is sent. After the inital contact has been made I open a thread that constantly is listening for messages from the server. If the server sends the client list than I make sure to update the approprite text area, otherwise I append any incoming mesages to the message window. In the disconnect button I reset all of the text fields and close the clients socket print writer  and buffered reader. The send and keyListener for enter are roughly the same. When clicked they simply read in the text in the composing window and write them to the server to be distributed. 

I struggled a little with the client because I intially made it in seperate files and tried calling it in the GUI, however after I got stuck for the millionth time because of access trouble I rewrote the client directly into the GUI s access wouldn't be as confusing for me :)

## What additional features did you attempt and how can we test them

The extra feature I attempted was making multiple themes. This can be tested by clicking the settings button. This button opens a new JFrame I made that has a JComboBox with various themes. When one is selected and the save button is clicked the GUI's color theme changes. This can be done as many times as one desires, and the theme change also changes the theme of the setting panel.


