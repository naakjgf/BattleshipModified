package cs3500.pa04.NewStuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader {

  public static String readJsonDataFromSocket(Socket socket) throws IOException {
    StringBuilder jsonData = new StringBuilder();

    InputStream inputStream = socket.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader reader = new BufferedReader(inputStreamReader);

    String line;
    while ((line = reader.readLine()) != null) {
      jsonData.append(line);
    }

    // Close the input stream and socket connection
    reader.close();
    socket.close();

    return jsonData.toString();
  }
}

