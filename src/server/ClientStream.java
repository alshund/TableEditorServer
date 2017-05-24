package server;

import command.Commands;
import org.w3c.dom.Document;
import searchStrategy.SearchStrategy;
import studentDataBase.SearchResult;
import studentDataBase.Student;
import studentDataBase.StudentDataBase;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by shund on 16.05.2017.
 */
public class ClientStream implements Runnable {
    private Server server;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private StudentDataBase studentDataBase;
    private SearchResult searchResult;

    public ClientStream(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        studentDataBase = new StudentDataBase();
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            while(true) {
                String command = inputStream.readUTF();
                databaseAction(command);
            }
        } catch (IOException e) {
            server.printLog(e.getMessage());
        }
    }

    public void databaseAction(String command) {
        if (command.equals(Commands.GET_PAGE)) {
            getPage();
        } else if (command.equals(Commands.GET_SEARCH_PAGE)) {
            getSearchPage();
        }else if (command.equals(Commands.ADD_STUDENT)) {
            addStudent();
        } else if (command.equals(Commands.FIND_STUDENT)) {
            findStudent();
        } else if (command.equals(Commands.DELETE_STUDENT)) {
            deleteStudent();
        } else if (command.equals(Commands.GET_DATABASE_SIZE)) {
            getDatabaseSize();
        } else if (command.equals(Commands.GET_SEARCH_LIST_SIZE)) {
            getSearchListSize();
        }else if (command.equals(Commands.SET_STUDENTS)) {
            setStudent();
        } else if (command.equals(Commands.SAVE_TABLE)) {
            saveAction();
        } else if (command.equals(Commands.OPEN_TABLE)) {
            openAction();
        } else if (command.equals(Commands.SHUT_CONNECTION)) {
            shutConnection();
        } else if (command.equals(Commands.CHECK_CONNECTION)) {
            checkConnection();
        }
    }

    private void getPage() {
        try {
            int currentPage = inputStream.readInt();
            int recodesAmount = inputStream.readInt();
            outputStream.writeObject(studentDataBase.getPage(currentPage, recodesAmount));
            outputStream.flush();
        } catch (IOException e) {
            server.printLog(e.getMessage());
        }
    }

    private void getSearchPage() {
        try {
            int currentPage = inputStream.readInt();
            int recodesAmount = inputStream.readInt();
            outputStream.writeObject(searchResult.getPage(currentPage, recodesAmount));
        } catch (IOException e) {
            server.printLog(e.getMessage());
        }
    }

   private void addStudent() {
       try {
           Student student = (Student) inputStream.readObject();
           studentDataBase.addStudent(student);
           server.printLog(socket.getInetAddress() + " add new student");
       } catch (IOException e) {
           server.printLog(e.getMessage());
       } catch (ClassNotFoundException e) {
           server.printLog(e.getMessage());
       }

   }

   private void findStudent() {
       try {
           List<Student> studentList = studentDataBase.search((SearchStrategy) inputStream.readObject());
           searchResult = new SearchResult(studentList);
           outputStream.writeObject(studentList);
           outputStream.flush();
       } catch (IOException e) {
           server.printLog(e.getMessage());
       } catch (ClassNotFoundException e) {
           server.printLog(e.getMessage());
       }
   }

   private void deleteStudent() {
       try {
           studentDataBase.delete((SearchStrategy) inputStream.readObject());
           server.printLog(socket.getInetAddress() + " delete student('s)");
       } catch (IOException e) {
           server.printLog(e.getMessage());
       } catch (ClassNotFoundException e) {
           server.printLog(e.getMessage());
       }
   }

   private void getDatabaseSize() {
       try {
           outputStream.writeInt(studentDataBase.getStudents().size());
           outputStream.flush();
       } catch (IOException e) {
           server.printLog(e.getMessage());
       }
   }

   private void getSearchListSize() {
       try {
           outputStream.writeInt(searchResult.getStudentList().size());
           outputStream.flush();
       } catch (IOException e) {
           server.printLog(e.getMessage());
       }
   }

   private void setStudent() {
       List<Student> studentList = null;
       try {
           studentList = (List<Student>) inputStream.readObject();
           studentDataBase.setStudents(studentList);
           server.printLog(socket.getInetAddress() + " create new table");
       } catch (IOException e) {
           server.printLog(e.getMessage());
       } catch (ClassNotFoundException e) {
           server.printLog(e.getMessage());
       }
   }

   public void saveAction() {
       try {
           String fileName = inputStream.readUTF();
           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
           Document document = documentBuilder.newDocument();
           studentDataBase.saveAction(fileName, document);
           server.printLog(socket.getInetAddress() + " save table");
       } catch (IOException e) {
           server.printLog(e.getMessage());
       } catch (ParserConfigurationException e) {
           server.printLog(e.getMessage());
       } catch (TransformerException e) {
           server.printLog(e.getMessage());
       }
   }

   public void openAction() {
       try {
           String fileName = inputStream.readUTF();
           studentDataBase.openAction(fileName);
           server.printLog(socket.getInetAddress() + " open table");
       } catch (IOException e) {
           server.printLog(e.getMessage());
       }
   }

   public void shutConnection() {
       try {
           server.removeClient(this);
           socket.close();
           Thread.currentThread().interrupt();
       } catch (IOException e) {
           server.printLog(e.getMessage());
       }
   }

   public void checkConnection() {
       try {
           outputStream.writeBoolean(true);
           outputStream.flush();
       } catch (IOException e) {
           server.printLog(e.getMessage());
       }
   }
}
