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
        }catch(Exception e) {
            popup.PopupWindow("Cannot find environment variable file(.env)...Exiting", "Setup error", false);
            Thread.sleep(10000);
        }

        // connecting to the database using method
        connectToDB();
    }

    // connection managing method
    protected static void connectToDB(){
        // DB variables
        String dbUrl = envReader.get("DATABASEURL");
        String dbPort = envReader.get("DATABASEPORT");
        String dbName = envReader.get("DATABASENAME");
        String dbUsername = envReader.get("DATABASEUSERNAME");
        String dbPassword = envReader.get("DATABASEPASSWORD");
        int connectionInterval = Integer.parseInt(Objects.requireNonNull(envReader.get("INTERVALTIME"))); // time in seconds to try to connect to db.

        String connectionURL = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=false", dbUrl, dbPort, dbName, dbUsername, dbPassword);
        System.out.println("connection url is: " + connectionURL);


        try{

            DriverManager.setLoginTimeout(connectionInterval);
            Popup.conLoad(connectionInterval);
            connectionDB =  DriverManager.getConnection(connectionURL);
            connectionStatus = true;

            System.out.println("Connection established");
            System.out.println("Connection status: " + connectionStatus);


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


    public void AddBook(HashMap<String, String> bookMap){
        // will be used when sending data to SQL server
        String tableName = envReader.get("TABLENAME");

        int bookName = Integer.parseInt(bookMap.get("bookName"));
        String authorName = bookMap.get("authorName");
        int bookId = Integer.parseInt(bookMap.get("bookIdNumber"));
        int bookPrice = Integer.parseInt(bookMap.get("bookPrice"));

        // Adding data to DB
        try{
            Statement statement = connectionDB.createStatement();

            // REFERENCE: INSERT INTO "LibraryShelf"("BookName", "AuthorName", "BookID", "BookPrice") VALUES('JAVA For beginners', 'Again Shahid', '102933', '1002');

            String query = String.format("INSERT INTO \"%s\"(\"BookID\", \"BookName\", \"AuthorName\", \"BookPrice\") VALUES('%d', '%s', '%s', '%d')", tableName, bookId, bookName, authorName, bookPrice);
            statement.executeQuery(query);
            System.out.printf("Adding to table: %s\nQuery is:%s", tableName, query);

            System.out.println("Hashmap received & info added to the DB");
        }
        catch(SQLException e){
            System.out.println("Error while writing data to DB");
        }
    }

    public ResultSet sendBook(){
        try{
            if(!connectionStatus){
                connectToDB();
            }

            if(connectionStatus){
                Statement statement = connectionDB.createStatement();
                String query = "SELECT * FROM LibraryShelf";
                return statement.executeQuery(query);
            }
            else{
                popup.PopupWindow("Error: Cannot pull data. Database offline...", "Database error", false);
            }

        } catch(Exception e){
            popup.PopupWindow("Error: Cannot pull data. Database offline...", "Database error", false);
        }
        return null;
    }
}
