package middleware;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;


public class QueueListener {

	private final static String CUSTOMER_QUEUE_NAME = "CustomerQueue";
	private final static String CUSOTMER_PORTAL_URL = "http://localhost:4567/new-account";

	public static void consumeMessage() throws IOException, TimeoutException {
		 
		    System.out.println("Consuming message in middleware...........");
		    Connection connection =ConnectionUtils.getConnection();
        	Channel channel = connection.createChannel();
        	channel.queueDeclare(CUSTOMER_QUEUE_NAME,false,false,false,null);
        	DeliverCallback deliverCallback = callBack();
        	channel.basicConsume(CUSTOMER_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		    
	}

	private static DeliverCallback callBack() {
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		    String message = new String(delivery.getBody(), "UTF-8");
		    System.out.println(" [x] Received '" + message + "'");
		    callCustomerPortal(createRequest(message));
		};
		return deliverCallback;
	}
	
	private static String createRequest(String msgStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = objectMapper.readValue(msgStr, Customer.class);
		CustomerMessage customerMessage = new CustomerMessage(customer);
	    return objectMapper.writeValueAsString(customerMessage);
	}
	
	private static void callCustomerPortal(String request) {
		  HttpURLConnection connection = null;
		  try {
			    //Create connection
			    URL url = new URL(CUSOTMER_PORTAL_URL);
			    connection = (HttpURLConnection) url.openConnection();
			    connection.setRequestMethod("POST");
			    connection.setRequestProperty("Content-Type", "application/json");
			    connection.setUseCaches(false);
			    connection.setDoOutput(true);
			    
			    //Send request
			    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			    wr.writeBytes(request);
			    wr.close();
			    
			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
				
				 String line; while ((line = rd.readLine()) != null) { response.append(line);
				 response.append('\r'); }
				
			    System.out.println("Customer portal responds with " +  response );
			    rd.close();
			  } catch (Exception e) {
			    e.printStackTrace();
			  } finally {
			    if (connection != null) {
			      connection.disconnect();
			    }
			  }
	}
	
	
}
