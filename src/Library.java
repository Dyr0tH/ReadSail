import java.awt.*;
import java.util.HashMap;
import java.sql.*;
import java.util.Objects;

import io.github.cdimascio.dotenv.Dotenv;


public class Library extends Component {
    private static Connection connectionDB;
    public static Boolean connectionStatus = false;
    Popup popup = new Popup();
    static Dotenv envReader;

    Library() throws InterruptedException {
        // loading config file
        try{
            envReader = Dotenv.load();
            // connecting to the database using method
            connectToDB();

        }
        catch(Exception e) {
            popup.PopupWindow("Cannot find environment variable file(.env)...Exiting", "Setup error", false);
            Thread.sleep(10000);
            System.exit(0);
        }
    }

    // connection managing method
    protected static void connectToDB(){
        // DB variables
        String dbUrl = envReader.get("DATABASEURL");
        String dbPort = envReader.get("DATABASEPORT");
        String dbName = envReader.get("DATABASENAME");
        String dbUsername = envReader.get("DATABASEUSERNAME");
        String dbPassword = envReader.get("DATABASEPASSWORD");

        int connectionInterval = Integer.parseInt(Objects.requireNonNull(envReader.get("INTERVALTIME")));
        String connectionURL = String.format("jdbc:mysql://%s:%s/%s", dbUrl, dbPort, dbName);
        System.out.println("connection url is: " + connectionURL);


        try{
            DriverManager.setLoginTimeout(connectionInterval);
            Popup.conLoad(connectionInterval);
            connectionDB =  DriverManager.getConnection(connectionURL, dbUsername, dbPassword);
            connectionStatus = true;

            System.out.println("Connection established");
            System.out.println("Connection status: " + connectionStatus);
            Main.connection_status_show.setText("CONNECTED");


        } catch (Exception e) {
            connectionDB = null;
            System.out.println("An sql exception occurred, unable to connect");
            Popup popup = new Popup();

            popup.PopupWindow("Cannot connect to database", "DB error", false);

            // following try-catch block just pauses the application to show the popup.
            try{
                Thread.sleep(5000);
            }catch(Exception exe){
                System.out.println("Thread interrupted....while showing cannot connect msg");
            }

            System.out.println("Connection status: " + connectionStatus);
        }
    }

    // Method to add book to the Database
    public void AddBook(HashMap<String, String> bookMap){
        // will be used when sending data to SQL server
        String tableName = envReader.get("TABLENAME");

        String bookName = bookMap.get("bookName");
        String authorName = bookMap.get("authorName");
        int bookId = Integer.parseInt(bookMap.get("bookIdNumber"));
        int bookPrice = Integer.parseInt(bookMap.get("bookPrice"));
        String insertSql = String.format("INSERT INTO %s (BookID, BookName, AuthorName, BookPrice) VALUES (?, ?, ?, ?)", tableName);

        // Adding data to DB
        try(PreparedStatement preparedStatement = connectionDB.prepareStatement(insertSql)){

            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, bookName);
            preparedStatement.setString(3, authorName);
            preparedStatement.setInt(4, bookPrice);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Query executed successfully\nDatabase updated!!");
            }else{
                System.out.println("Error while executing the query\nNo changes in database");
            }

        }
        catch(SQLException e){
            System.out.println("Error while writing data to DB");
        }
    }

    // Method to fetch book data from the database
    public ResultSet sendBook(){
        try{
            if(connectionStatus){
                String tableName = envReader.get("TABLENAME");
                Statement statement = connectionDB.createStatement();
                String query = String.format("SELECT * FROM %s", tableName);
                return statement.executeQuery(query);
            }
            else{
                connectToDB();
            }

        } catch(Exception e){
            popup.PopupWindow("Error: Cannot pull data. Database offline...", "Database error", false);
        }
        return null;
    }
}