package webserver;

import firstmilestone.Node;
import firstmilestone.Project;
import firstmilestone.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
  private static final int PORT = 8080; // port to listen to

  private Node currentNode;
  private final Node root;

  private int lastId = 99;


  public WebServer(Node root) {
    this.root = root;
    System.out.println(root);
    currentNode = root;
    try {
      ServerSocket serverConnect = new ServerSocket(PORT);
      System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
      // we listen until user halts server execution
      while (true) {
        // each client connection will be managed in a dedicated Thread
        new SocketThread(serverConnect.accept());
        // create dedicated thread to manage the client connection
      }
    } catch (IOException e) {
      System.err.println("Server Connection error : " + e.getMessage());
    }
  }

  private Node findActivityById(int id) {
    return root.findActivityById(id);
  }

  private class SocketThread extends Thread {
    // SocketThread sees WebServer attributes
    private final Socket insocked;
    // Client Connection via Socket Class

    SocketThread(Socket insocket) {
      this.insocked = insocket;
      this.start();
    }

    @Override
    public void run() {
      // we manage our particular client connection
      BufferedReader in;
      PrintWriter out;
      String resource;

      try {
        // we read characters from the client via input stream on the socket
        in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
        // we get character output stream to client
        out = new PrintWriter(insocked.getOutputStream());
        // get first line of the request from the client
        String input = in.readLine();
        // we parse the request with a string tokenizer

        System.out.println("sockedthread : " + input);

        StringTokenizer parse = new StringTokenizer(input);
        String method = parse.nextToken().toUpperCase();
        // we get the HTTP method of the client
        if (!method.equals("GET")) {
          System.out.println("501 Not Implemented : " + method + " method.");
        } else {
          // what comes after "localhost:8080"
          resource = parse.nextToken();
          System.out.println("input " + input);
          System.out.println("method " + method);
          System.out.println("resource " + resource);

          parse = new StringTokenizer(resource, "/[?]=&");
          int i = 0;
          String[] tokens = new String[20];
          // more than the actual number of parameters
          while (parse.hasMoreTokens()) {
            tokens[i] = parse.nextToken();
            System.out.println("token " + i + "=" + tokens[i]);
            i++;
          }

          // Make the answer as a JSON string, to be sent to the Javascript client
          String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
          System.out.println("answer\n" + answer);
          // Here we send the response to the client
          out.println(answer);
          out.flush(); // flush character output stream buffer
        }

        in.close();
        out.close();
        insocked.close(); // we close socket connection
      } catch (Exception e) {
        System.err.println("Exception : " + e);
      }
    }


    private String makeBodyAnswer(String[] tokens) throws UnsupportedEncodingException {
      String body = "";
      switch (tokens[0]) {
        case "get_tree": {
          int id = Integer.parseInt(tokens[1]);
          Node activity = findActivityById(id);
          assert (activity != null);
          body = activity.toJson(1).toString();
          break;
        }
        case "start": {
          Integer id = Integer.parseInt(tokens[1]);
          Node activity = findActivityById(id);
          assert (activity != null); //comprovar que sigui una task?
          Task task = (Task) activity;
          task.startTask(id);
          body = "{}";
          break;
        }
        case "stop": {
          Integer id = Integer.parseInt(tokens[1]);
          Node activity = findActivityById(id);
          assert (activity != null);
          Task task = (Task) activity;
          task.stopTask(id);
          body = "{}";
          break;
        }
        case "add_node": {
          //TODO: millorar la obtencio de IDs correctes pels nous nodes
          Integer parentId = Integer.parseInt(tokens[1]);
          Project parent = (Project) findActivityById(parentId);
          assert (parent != null);
          Integer isProject = Integer.parseInt(tokens[2]);
          String name = URLDecoder.decode(tokens[3], "UTF-8");
          String description = URLDecoder.decode(tokens[4], "UTF-8");
          String tagsAsString = URLDecoder.decode(tokens[5], "UTF-8");
          List<String> tags = Arrays.asList(tagsAsString.split("\\s*,\\s*"));

          Node newNode = null;

          if (isProject == 1) {
            newNode = new Project(lastId+1, name, parent);
          } else {
            newNode = new Task(lastId+1, name, parent);
          }

          assert (newNode != null);
          for (String tag : tags) {
            newNode.addTag(tag);
          }
          newNode.setDescription(description);
          parent.addNode(newNode);

          lastId++;
          break;
        }
        // TODO: edit task, project properties
        default:
          assert false;
      }
      System.out.println(body);
      return body;
    }

    private String makeHeaderAnswer() {
      String answer = "";
      answer += "HTTP/1.0 200 OK\r\n";
      answer += "Content-type: application/json\r\n";
      answer += "Access-Control-Allow-Origin: *\r\n";
      answer += "\r\n";
      // blank line between headers and content, very important !
      return answer;
    }

  } // SocketThread

} // WebServer