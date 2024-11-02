**ClientChat**

This program is a GUI-based UDP client chat application implemented in Java using Swing.
It allows the sending and receiving UDP messages. It connects to a server via IP and port and exchanges messages in real-time.
The interface includes fields for setting local and remote IP/port, buttons to test connection, retrieve network information, send messages,
delete messages, and view archived messages in order to retreive them.

The Code is split into two classes one (UDPClient) handles the networking part and the other (ClientChat) handles the GUI .

The program works by first selecting the network card you'd like to use from the interface list which shows all available NIC's on the device 

<img width="769" alt="Screenshot 2024-11-02 at 4 20 02 PM" src="https://github.com/user-attachments/assets/9fe0d1fd-33e4-419e-b328-bbb393cd2bbc">

In the case of macbooks the default card is en0 (default card is usually loacated at the bottom of the list) after doing so the user clicks on the retreive info button to get the IP that is assigned to their device on their local network
for this example let's assume that two users are on the same local network (if they arent then they should use their network's global IP (the router's global ip) by simply typing in google whatsmyip to get their global/public IP))

After doing so, the user enters the credentials of the other user on the same local network by copying their Local IP to the Remote IP field while the Remote Port should be diffrent to that of the Local Port as both users cant use the same port.
In this example lets suppose Client A is on port 50000 and Client B is on port 50001

after entering those credentials user should click the test connection button to connect to the other client and the connection status should turn from red to green and at the botttom of the program the address which the user is connected to should appear.

Users can then start to message below is an example of two clients on the same local network:


Client A/Client B

<img width="400" alt="Screenshot 2024-11-02 at 4 31 49 PM" src="https://github.com/user-attachments/assets/61d2584b-dde3-436a-84e6-b8d653c02a34"> <img width="400" alt="Screenshot 2024-11-02 at 4 32 14 PM" src="https://github.com/user-attachments/assets/d3f15549-4cd6-4b24-ace7-4328497be99e">


When deleting single messages users can only delete their own msg and if there are any duplicate msgs the latest one is only deleted so for example if Client A wanted to delete the second hi they'd type into the msg field hi then press the delete button

<img width="400" alt="Screenshot 2024-11-02 at 4 33 04 PM" src="https://github.com/user-attachments/assets/fc2ee22b-a386-4fa1-8655-2a6dcd347342"> <img width="400" alt="Screenshot 2024-11-02 at 4 33 27 PM" src="https://github.com/user-attachments/assets/76b31ace-8744-4631-9adf-80ae05c84e61">

The 2nd Hi is removed from chat and is moved to the archive where it will be automatically removed from there after two minutes


<img width="400" alt="Screenshot 2024-11-02 at 4 33 40 PM" src="https://github.com/user-attachments/assets/bb36cb28-ca79-421a-a24b-c15074a02ce2"> <img width="400" alt="Screenshot 2024-11-02 at 4 33 50 PM" src="https://github.com/user-attachments/assets/6b7fdfab-6151-4d9a-b89c-69b8de9b3cee">


Two minutes later:


<img width="400" alt="Screenshot 2024-11-02 at 4 35 23 PM" src="https://github.com/user-attachments/assets/74d35a45-bd47-409d-b142-c3784266679e"> <img width="400" alt="Screenshot 2024-11-02 at 4 35 37 PM" src="https://github.com/user-attachments/assets/21cac138-5d9d-41f9-b068-2745f48f2594">

if a user wants to restore a message back to the chat it can be restored before the two minutes have passed by clicking on the message they'd like to restore and clicking on the restore button

**its worthy to note that the archive is unifed on both sides so any msgs in the archive can be seen by both users and any user can restore a msg as long as the two minutes havent gone by**

any user can delete the entire chat by simply clicking on the delete all button where the msgs are deleted in a similar manner to how single msgs are deleted through the archive

<img width="400" alt="Screenshot 2024-11-02 at 4 41 27 PM" src="https://github.com/user-attachments/assets/e018b480-454c-4e56-a24d-24b63bf4645d"> <img width="400" alt="Screenshot 2024-11-02 at 4 41 36 PM" src="https://github.com/user-attachments/assets/3133ed1c-9c53-424e-8854-ecaaa5d9d2be">

I'd like to also highlight the fact that all actions performed in the program are logged in a .txt file called log.txt


