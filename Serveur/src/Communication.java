
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Communication {
	
	public static ServerSocket initconnexion() throws IOException{
		  ServerSocket socketserver = new ServerSocket(2009);
	      InetAddress address = InetAddress.getLocalHost();
	      String hostIP = address.getHostAddress() ;
		  String hostName = address.getHostName();
		  Quizz.textArea_1.append("Le nom de serveur est : " + hostName + "\nIP: " + hostIP+"\n");
		  Quizz.textArea_1.append("Le serveur est à l'écoute du port "+socketserver.getLocalPort()+"\n");
		  return socketserver;    
	}
}
