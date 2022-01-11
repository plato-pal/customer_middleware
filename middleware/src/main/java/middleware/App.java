package middleware;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class App {
 
    public static void main(String[] args) throws IOException, TimeoutException {
       QueueListener.consumeMessage();
    }
}
