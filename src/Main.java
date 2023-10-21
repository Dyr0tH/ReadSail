import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.HashMap;

public class Main {

    // Making a scroll panel
    JScrollPane scrollPane;

    // info labels
    public static JLabel connection_status_show = new JLabel();


    private Main() throws InterruptedException {
        Library library = new Library();
        JFrame frame = new JFrame("Library Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed

        Image logo = Toolkit.getDefaultToolkit().getImage("icons/logo.png");
        frame.setIconImage(logo);
        frame.setSize(1100, 600); // Set the size of the window

        // Create a JPanel with a null layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel subUpPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JPanel westGridUp = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel eastGridUp = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel centerGrid = new JPanel(new GridLayout(0, 1, 5, 5));


        // Creating borders
        TitledBorder inputAreaBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN, 5, true), "Enter book info");
        westGridUp.setBorder(inputAreaBorder);

        TitledBorder buttonAreaBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.MAGENTA, 5, true), "Control buttons");
        buttonAreaBorder.setTitleJustification(TitledBorder.RIGHT);
        eastGridUp.setBorder(buttonAreaBorder);

        TitledBorder logBtnAreaBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 5, true), "User authentication");
        logBtnAreaBorder.setTitleJustification(TitledBorder.CENTER);
        centerGrid.setBorder(logBtnAreaBorder);


        // Icons
        int defaultIconWidthHeight = 25;

        ImageIcon bookIcon = new ImageIcon(new ImageIcon("icons/book.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon refreshIcon = new ImageIcon(new ImageIcon("icons/refresh.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon removeIcon = new ImageIcon(new ImageIcon("icons/remove.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon quitIcon = new ImageIcon(new ImageIcon("icons/quit.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon loginIcon = new ImageIcon(new ImageIcon("icons/Login.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon logoutIcon = new ImageIcon(new ImageIcon("icons/Logout.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon connectIcon = new ImageIcon(new ImageIcon("icons/Connect.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));
        ImageIcon dbIcon = new ImageIcon(new ImageIcon("icons/Database.png").getImage().getScaledInstance(defaultIconWidthHeight, defaultIconWidthHeight, Image.SCALE_DEFAULT));

        // Create labels and text fields
        JLabel book_name = new JLabel("Name of the book:");
        JTextField bookNameInput = new JTextField(20);

        JLabel author_name = new JLabel("Name of the Author:");
        JTextField authorNameInput = new JTextField(20);

        JLabel book_id_no = new JLabel("Book identification number:");
        JTextField bookIDNumber = new JTextField(20);

        JLabel book_price = new JLabel("Price of the book:");
        JTextField bookPriceInput = new JTextField(20);

        JLabel connection_status_text = new JLabel("Database connection status:");
        JLabel current_user_text = new JLabel("Logged in as:");
        JLabel current_user_show = new JLabel("GUEST");

        // Creating buttons
        JButton book_add_btn = new JButton("Add book", bookIcon);
        JButton refreshList_btn = new JButton("Refresh", refreshIcon);
        JButton removeBook_btn = new JButton("Remove a book", removeIcon);
        JButton quitBtn = new JButton("Quit Application", quitIcon);
        JButton loginBtn = new JButton("Login", loginIcon);
        JButton logoutBtn = new JButton("Logout", logoutIcon);
        JButton dbManageBtn = new JButton("Open DB manager", dbIcon);
        JButton reconnectDB = new JButton("Reconnect to Database", connectIcon);
        reconnectDB.setEnabled(false);


        // creating JTable
        String[] columnNames = {"Book", "Author", "ID", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(columnNames);
        JTable bookTable = new JTable(tableModel);
        bookTable.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(bookTable);


        // Add labels and text fields to the subUpPanel
        westGridUp.add(book_name);
        westGridUp.add(bookNameInput);

        westGridUp.add(author_name);
        westGridUp.add(authorNameInput);

        westGridUp.add(book_id_no);
        westGridUp.add(bookIDNumber);

        westGridUp.add(book_price);
        westGridUp.add(bookPriceInput);

        westGridUp.add(connection_status_text);
        westGridUp.add(connection_status_show);
        westGridUp.add(current_user_text);
        westGridUp.add(current_user_show);

        eastGridUp.add(book_add_btn);
        eastGridUp.add(refreshList_btn);
        eastGridUp.add(removeBook_btn);
        eastGridUp.add(quitBtn);
        eastGridUp.add(dbManageBtn);
        eastGridUp.add(reconnectDB);


        centerGrid.add(loginBtn);
        centerGrid.add(logoutBtn);

        // Add the subUpPanel to the frame
        subUpPanel.add(westGridUp);
        subUpPanel.add(centerGrid);
        subUpPanel.add(eastGridUp);

        mainPanel.add(subUpPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane);

        frame.add(mainPanel);
        centerFrameOnScreen(frame);

        // Make the frame visible
        frame.setVisible(true);



        Popup popup = new Popup(); 

        // Attaching listeners to buttons
        reconnectDB.addActionListener(e -> Library.connectToDB());

        dbManageBtn.addActionListener(e -> new DBManager());

        quitBtn.addActionListener(e -> popup.PopupWindow("Exit application ??", "Quit ?", true));

        book_add_btn.addActionListener(e -> {

            if(bookNameInput.getText().isEmpty() || authorNameInput.getText().isEmpty() || bookIDNumber.getText().isEmpty() || bookPriceInput.getText().isEmpty()){
                popup.PopupWindow("Please fill correct data :(", "Invalid input", false);
            }
            else if(bookIDNumber.getText().length() > 10){
                popup.PopupWindow("Book ID cannot exceed 10 characters", "Invalid input", false);
            }
            else if(bookPriceInput.getText().length() > 4){
                popup.PopupWindow("Book price cannot be more than 4 digits", "Invalid input", false);
            }

            else{

                HashMap<String, String> bookMap = new HashMap<>();
                bookMap.put("bookName", bookNameInput.getText());
                bookMap.put("authorName", authorNameInput.getText());
                bookMap.put("bookIdNumber", bookIDNumber.getText());
                bookMap.put("bookPrice", bookPriceInput.getText());

                try{
                    library.AddBook(bookMap);

                    String book = bookNameInput.getText().strip();
                    String author = authorNameInput.getText().strip();
                    String bookId = bookIDNumber.getText().strip();
                    String price = bookPriceInput.getText().strip();


                    String[] bookData = {book, author, bookId, price};
                    tableModel.addRow(bookData);

                    // clearing the text box
                    bookNameInput.setText("");
                    authorNameInput.setText("");
                    bookIDNumber.setText("");
                    bookPriceInput.setText("");
                }
                catch(Exception exception){
                    popup.PopupWindow("Error, cannot send data to server, database offline...", "Database offline", false);
                }
            }
        });

        refreshList_btn.addActionListener(e -> {

            try {
                ResultSet retrievedBook = library.sendBook();
                System.out.println("books delivered from backend");


                tableModel.setRowCount(0);

                while (retrievedBook.next()){
                    String bookName = retrievedBook.getString("BookName");
                    String authorName = retrievedBook.getString("AuthorName");
                    String bookID = retrievedBook.getString("BookID");
                    String bookPrice = retrievedBook.getString("BookPrice");

                    String[] bookData = {bookName, authorName, bookID, bookPrice};
                    tableModel.addRow(bookData);
                }
            }
            catch (Exception exception){
                System.out.println("Error DB offline");
            }
        });
    }

    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
    }

    public static void main(String[] args)throws InterruptedException{
        new Main();
    }

}
