package middleware;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {
	
	 public static Connection getConnection(){
		    Connection conn = null;
		    try{
		        ConnectionFactory factory = new ConnectionFactory();
		        //factory.setUsername("");
		        //factory.setPassword("");
		        //factory.setVirtualHost("");
		        factory.setHost("localhost");
		        //factory.setPort(15672);
		        conn = factory.newConnection();
		    }catch(Exception e){
	        	System.err.println("Could not get a connection due to:" +e.getMessage());
		    }
		    return conn;
		  }
	 

}
