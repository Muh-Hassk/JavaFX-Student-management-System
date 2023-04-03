package StudentManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StdManagementSystem extends Application {

    public String url = ("jdbc:mysql://localhost:3306/DataBaseName");
    public String user = "root";
    public String pass = "Password";
    public Connection conn;
    Font nameFont = Font.font("defaut", FontWeight.BOLD, 14);
    Font ErrorNoFound = Font.font("defaut", FontWeight.BOLD, 20); // red
    Font ErrorInsert = Font.font("defaut", FontWeight.BOLD, 13); // red
    Font SuccessInsert = Font.font("defaut", FontWeight.BOLD, 10);
    Tab addNewStudent = new Tab("Add new Student");
    Tab showStudent = new Tab("Search for Student");

    DatePicker d = new DatePicker(); // Declare Our Date Picker
    Label errorLabel = new Label(""); // Our Error Date Label
    Label errorNameLabel = new Label(""); // Error Name Label
    Label dateOfBirth = new Label(); // Date on birth Label
    Label fullName = new Label(); // Full name Label
    Label gpa = new Label(); // GPA Label
    TextField fN = new TextField(); // Name Text Field
    String BtnArray[] = {"Insert", "Exit"}; // Strings to apply them into our buttons
    final Button[] buttonsInsert = new Button[2]; // Array of type Button
    Label SliderV = new Label("0.0"); // Our Starter GPA Value
    Slider slider = new Slider(0.0, 4, 0); // Slider Declaration

   
    final Button[] buttonsShow = new Button[3]; // Array of type Button
    Label searchError = new Label(""); // Search Error Label
    String SearchButton[] = {"Refresh", "Search", "Exit"}; // Strings to apply them into our buttons
    TextField sdtext = new TextField(); // Search Student Text
    Label errorSearchLabel = new Label(""); // Error Search outside Table View Label

    TableView tableview = new TableView(); // Table View 

    // Table view Columns Names 
    TableColumn ID = new TableColumn("ID");
    TableColumn Name = new TableColumn("Full Name");
    TableColumn GPA = new TableColumn("GPA");
    TableColumn DateOFbirth = new TableColumn("Date of Birth");

    // Connection method
    Connection getConnection() throws SQLException 
    {
        Connection conn;
        String url = ("jdbc:mysql://localhost:3306/DataBaseName");
        String user = "root";
        String pass = "Password";
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
// Header of Students names , ID , Group number

    Node Getname() 
    {
        HBox box = new HBox();
        Label info = new Label();
     
        info.setTextFill(Color.BLUE);
        //box.setAlignment(Pos.CENTER);// centerliaze it's Place
        box.getChildren().addAll(info); // Added them to Box
        return box; // Return it to added it to pane
    }

    // Project name etc.
    VBox getHeader()
    {
        VBox box = new VBox();
        Label label = new Label("JavaFX & DB Simple Project");
        label.setFont(ErrorNoFound);
        label.setTextFill(Color.BLUE);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(Getname() ,label);
        return box;
    }

    //First Tab Methods
    // full Name Label Creation and Layout
    Label fullNameLabel()
    {
        fullName.setText("Full Name: ");
        //To set it Place on Pane 
        fullName.setLayoutX(54);
        fullName.setLayoutY(260);
        return fullName; // To add it to pane
    }
// Date of birth Label Creation and Layout

    Label dateOFbirthLabel() 
    {
        dateOfBirth.setText("Date of Birth"); // Label Text Appearence
        //To set it Place on Pane 
        dateOfBirth.setLayoutX(54);
        dateOfBirth.setLayoutY(300);
        return dateOfBirth; // To add it to pane
    }
// GPA Label layout and Size

    Label gpaLabel() 
    {
        gpa.setText("GPA");
        //To set it Place on Pane 
        gpa.setLayoutX(54);
        gpa.setLayoutY(340);
        return gpa; // To add it to pane 
    }

    // Name Text field Layout and Size 
    TextField fullName() 
    {
        //To set it Place on Pane 
        fN.setLayoutX(146);
        fN.setLayoutY(260);
        // Size of Text field
        fN.setPrefHeight(26);
        fN.setPrefWidth(159);
        return fN;
    }
// Date Picker Layout and size 

    DatePicker getDatePicker()
    {
        //To set it Place on Pane Horizintally
        d.setLayoutX(146);
        d.setLayoutY(300);

        // Size of Date Picker
        d.setPrefHeight(26);
        d.setPrefWidth(159);
        return d;
    }
// Slider Layout and Size 

    Slider getSlider()
    {
        //To set it Place on Pane
        slider.setLayoutX(151);
        slider.setLayoutY(340);
        // Size of Slider 
        slider.setPrefWidth(159);
        slider.setPrefHeight(15);
        // To add it to Pane
        return slider;
    }

    Label sliderValue()
    {
        //To set it Place on Pane Horizintally
        SliderV.setLayoutX(211);
        SliderV.setLayoutY(367);
        // To make Label text 0.0 at start of running app 
        SliderV.setText("0.0");
        slider.valueProperty().addListener(cl -> {
            //to let it only 1 decimal point as requrid and added it to Label
            String result = String.format("%.1f", slider.getValue());
            SliderV.setText(result);
        });
        return SliderV; // To add it to Pane 
    }

    // Creation of Error Label 
    Label errLabelDate() 
    {
        errorLabel.setLayoutX(140); // To set it Place on Pane Horizintally
        errorLabel.setLayoutY(395); //To set it Place on Pane vertically
        return errorLabel;
    }

    Label errLabelName() 
    {
        errorNameLabel.setLayoutX(140); // To set it Place on Pane Horizintally
        errorNameLabel.setLayoutY(422); //To set it Place on Pane vertically
        return errorNameLabel;
    }

    private String getFormattedDate(String date) 
    {
        //date picker format
        String dpFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dpFormat);
        String formattedDate = "";
        try 
        {
            Date oldDate = sdf.parse(date);
            //change the format
            sdf.applyPattern("yyyy-MM-dd");
            //apply the new format on the original date and return it as a string
            formattedDate = sdf.format(oldDate);
        } 
        catch (ParseException e)
        {
            ErrorOutputDate("You must pick a date !");
        }
        return formattedDate;
    }

    // To Collect Values then send it to the Inseration Method
    void dataCollector() throws ParseException, SQLException
    {
        String n = fN.getText();
        String today = d.getEditor().getText(); // Get Date Picker Value as a String
        String output = getFormattedDate(today); // Change the Date Picker Value to the SQL Format
        float s = (float) slider.getValue(); // Get Slider Value 
        DecimalFormat df = new DecimalFormat("#.#"); // one Decimal Point in GPA
        float twoDigitsF = Float.valueOf(df.format(s)); // Applay format to SLider Value
        addToDs(n, output, twoDigitsF); // Send it to Insertation method
    }
// Adding exit , Insert Button in Box

    HBox addBtn() 
    {
        HBox box = new HBox();
        for (int i = 0; i < 2; i++) 
        {
            buttonsInsert[i] = new Button(BtnArray[i]); // Names Taken from Array
            buttonsInsert[i].setPrefHeight(26); // Sizes
            buttonsInsert[i].setPrefWidth(54);// Sizes
            buttonsInsert[i].setAlignment(Pos.CENTER); // To set it in center
        }
        buttonsInsert[0].setOnMouseClicked(eh -> 
        {
            // if Insert Button clicked
            addName(); // Check Name 
            getDate(); // Check Date
            try 
            {
                dataCollector(); // Collect Data
            } 
            catch (ParseException | SQLException ignored) 
            {
                
            }

        });
        box.getChildren().addAll(buttonsInsert);
        box.setSpacing(70); // size between Buttons
        box.setPadding(new Insets(0, 0, 0, 10)); // Padding
        // Layout in Pane 
        box.setLayoutX(126);
        box.setLayoutY(448);
        return box; // To add it into pane 
    }

    boolean addName() 
    {
        String regex = "^[a-z A-Z]+$"; // Regex for only Alphabitical and Space 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fN.getText());
        if (matcher.matches()) 
        {
            SuccessOutputName("");
            return true;
        } 
        else if (fN.getText().equals("")) 
        {
            ErrorOutputName("You Must Enter a Name!");// Tell him Error masseage
            
            return false;
        }
        else 
        {
            ErrorOutputName("Invalid Name shall not have digits!");// Tell him Error masseage
            return false;
        }
    }

    void SuccessOutputName(String s)
    {
        errorNameLabel.setTextFill(Color.GREEN);
        errorNameLabel.setFont(Font.font("default", FontWeight.BOLD, 13));
        errorNameLabel.setText(s);
    }

    void ErrorOutputName(String s)
    {
        errorNameLabel.setTextFill(Color.RED);
        errorNameLabel.setFont(Font.font("default", FontWeight.BOLD, 13));
        errorNameLabel.setText(s);
    }

    boolean getDate() 
    {
        if ((d.getValue() == null)) 
        {
            ErrorOutputDate("Date shouldn't Be Empty!"); // If date is null appear this Text
            return false;
        }
        
        else 
            
        {
            SuccessOutputDate("");
            return true;
        }
    }

    void ErrorOutputDate(String s) 
    { // if the insertation is not Correct we Send A meassage to user
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("default", FontWeight.BOLD, 13));
        errorLabel.setText(s);
    }

    void SuccessOutputDate(String s) 
    { // if the insertation is Correct we Send A meassage to user
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setFont(Font.font("default", FontWeight.BOLD, 13));
        errorLabel.setText(s);
    }

    void addToDs(String Name, String output, float GPA) throws SQLException, ParseException
    {
        if (addName() & getDate()) 
        { // If name & Date Are both in the Right form we make the Insertation
            String N = Name;
            float gpa = GPA;
            String date1 = output;
            conn = getConnection(); // Open Conncetion 
            PreparedStatement stmt = conn.prepareStatement("insert into TableName (default,?,?,?);"); // Default because of the Auto increament
            stmt.setString(2, N);
            stmt.setFloat(1, gpa);
            stmt.setString(3, date1);
            stmt.executeUpdate(); // apply insert into DS
            SuccessOutputName("Successfull " + fN.getText() + " insert."); // To let user know which name he inserted
            fN.setText(""); // to delete privious user input after insert
            d.getEditor().setText("");// to delete privious user input after insert
            slider.setValue(0.0);
            String result = String.format("%.1f", slider.getValue());
            SliderV.setText(result);
            stmt.close();// Prepared Statment Close
            conn.close(); // Connection Close 
        }
    }

    // Handler to Exit application
    void exitBtn() 
    {
        buttonsInsert[1].setOnMouseClicked(eh -> 
        {
            Platform.exit();
        });
    }
           
           

    // Second Tab Methods
    // Buttons added into VBOX then set layout and Size 
    VBox ButtonsSearch() 
    {
        VBox box = new VBox();
        for (int i = 0; i < 3; i++) 
        {
            buttonsShow[i] = new Button(SearchButton[i]);
            buttonsShow[i].setAlignment(Pos.CENTER);
            buttonsShow[i].setPrefWidth(80);
            buttonsShow[i].setPrefHeight(20);
        }
        box.getChildren().addAll(buttonsShow);
        box.setPrefWidth(100);
        box.setPrefHeight(144);
        box.setSpacing(20);
        box.setLayoutX(192);
        box.setLayoutY(18);
        box.setAlignment(Pos.CENTER);
        return box; // To added it to pane 
    }

    // Label of Search text field 
    Label SearchLabel() 
    {
        Label sd = new Label("Search for A student"); // Label Text
        // Layout in pane 
        sd.setLayoutX(41);
        sd.setLayoutY(46);
        // Alignment
        sd.setAlignment(Pos.CENTER_LEFT);
        return sd; // To added it to pane 
    }

    // Message Appear in Table view when running the Application
    void errorSearch() 
    {
        searchError.setFont(ErrorInsert);
        searchError.setText("Search To Get Records\n");
        tableview.setPlaceholder(searchError);
    }
// Text field for Search Text 

    TextField SearchText() 
    {
        //layout in pane
        sdtext.setLayoutX(44);
        sdtext.setLayoutY(78);
        // Sizeing
        sdtext.setPrefWidth(122);
        sdtext.setPrefHeight(26);
        return sdtext; // To added it into pane 
    }

    Label errorSearchLabel() 
    {
        errorSearchLabel.setFont(ErrorInsert);
        errorSearchLabel.setText("");
        errorSearchLabel.setTextFill(Color.RED);
        errorSearchLabel.setLayoutX(57);
        errorSearchLabel.setLayoutY(252);

        return errorSearchLabel;
    }

    // Table View Creation
    TableView mangeTblView() 
    {
        // Layout
        tableview.setLayoutX(300);
        tableview.setLayoutY(26);
        // Sizeing
        tableview.setPrefWidth(375);
        tableview.setPrefHeight(400);
        return tableview; // To added it to pane
    }

    void searchButton() 
    {
        // Search Handler
        buttonsShow[1].setOnMouseClicked(eh -> 
        {
            try 
            {
                searchDs(); // Search Method 
            } catch (SQLException ex) 
            {
                Logger.getLogger(StdManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void searchDs() throws SQLException 
    {
        conn = getConnection();
        String qurey = "select * from TableName where FullName = '" + sdtext.getText() + "'";
        PreparedStatement st = conn.prepareStatement(qurey);
        ResultSet rs = st.executeQuery();
        ObservableList<Student> data = FXCollections.observableArrayList();
        // If nothing Matched User input
        if (!rs.isBeforeFirst()) 
        {
            tableview.getItems().clear();
            errorSearchLabel.setTextFill(Color.RED);
            searchError.setText("There's NO Records in Table");
            errorSearchLabel.setText("No records available for this search criteria");
            searchError.setTextFill(Color.RED);
            tableview.setPlaceholder(searchError); // To change Text in table View
        } 
        else 
        {
            errorSearchLabel();
            errorSearchLabel.setFont(SuccessInsert);
            errorSearchLabel.setTextFill(Color.GREEN);
            if ("".equals(sdtext.getText())) 
            {
                errorSearchLabel.setText("All Recoreds");
                // If user input founded in our DS
            } 
            else 
            {
                errorSearchLabel.setText("Recored that's Contains " + sdtext.getText() + " in it");
            }
            while (rs.next()) 
            {
                data.add(new Student(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4)));
            }
            tableview.setItems(data);
            ID.setCellValueFactory(new PropertyValueFactory("stdId"));
            Name.setCellValueFactory(new PropertyValueFactory("stdName"));
            GPA.setCellValueFactory(new PropertyValueFactory("stdGpa"));
            DateOFbirth.setCellValueFactory(new PropertyValueFactory("stdDate"));
        }
    }

    
   
    
    void RefreshTBLview() throws SQLException 
    {
        try 
        {
            conn = getConnection();
            String qurey = "select * from TableName where FullName Like '%" + sdtext.getText() + "%'";
            PreparedStatement st = conn.prepareStatement(qurey);
            ResultSet rs = st.executeQuery();
            ObservableList<Student> data = FXCollections.observableArrayList();

            if (!rs.isBeforeFirst()) 
            {
                searchError.setText("There's NO Records in Table");
                searchError.setTextFill(Color.RED);
                tableview.setPlaceholder(searchError);
            }
            else 
            {

                // If user input founded in our DS
                while (rs.next()) 
                {

                    data.add(new Student(rs.getInt(1), rs.getFloat(2), rs.getString(3), rs.getString(4)));

                }
                tableview.setItems(data);
                errorSearchLabel.setText("");
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(StdManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void RefreshClicked() 
    {
        buttonsShow[0].setOnMouseClicked(eh -> 
        {
            try 
            {
                RefreshTBLview();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(StdManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    

    void onTabChange() 
    {
        showStudent.setOnSelectionChanged(eh -> 
        {
            try 
            {
                RefreshTBLview();
            } 
            catch (SQLException ex)
            {
                Logger.getLogger(StdManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    void eraseInsertSucces()
    {
        addNewStudent.setOnSelectionChanged(eh -> 
        {
             SuccessOutputName("");
        });
    }

    // Exit Button
    void exitButtonSearch() 
    {
        buttonsShow[2].setOnMouseClicked(eh -> 
        {
            Platform.exit();
        });
    }

    @Override
    
    public void start(Stage primaryStage) throws SQLException 
    {
        TabPane tabPane = new TabPane();
        Pane Search = new Pane();
        Pane insert = new Pane();
        // Creation tabs 

        // Adding Tabs 
        tabPane.getTabs().add(addNewStudent);
        tabPane.getTabs().add(showStudent);
        tableview.getColumns().addAll(ID, GPA, Name, DateOFbirth);

        // First Tab
        insert.getChildren().addAll(fullNameLabel(), dateOFbirthLabel(), gpaLabel(), fullName(), getDatePicker(), getSlider(), sliderValue(), addBtn(), errLabelDate(), errLabelName());
        addNewStudent.setContent(insert);
        addNewStudent.setClosable(false); // To make it uncloseable 
        exitBtn(); // To apply Exit Method
        eraseInsertSucces(); // To erase Masseage insertation successfully

        // Second Tab
        Search.getChildren().addAll(ButtonsSearch(), SearchLabel(), SearchText(), mangeTblView(), errorSearchLabel());
        showStudent.setContent(Search);

        RefreshTBLview();
        onTabChange();
        RefreshClicked();

        showStudent.setClosable(false); // To make it uncloseable 
        errorSearch();
        exitButtonSearch();
        searchButton();

        // Root 
        VBox root = new VBox();
        Scene scene = new Scene(root);
        root.setSpacing(10);
        root.getChildren().addAll(getHeader(), tabPane);
        primaryStage.setTitle("JavaFX , DB Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
