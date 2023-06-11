package Graghic;

/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this package have been created for Graphics
 */

import Accounts.DemandDepositAccount;
import Accounts.PeriodicTransaction;
import Accounts.Transaction;
import Clients.Client;
import Accounts.Account;
import Employee.Employee;
import Files.IOFiles;
import Manager.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import user.Regester;
import user.User;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.UnaryOperator;

import static javafx.scene.text.TextAlignment.CENTER;

/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this class have been created for graphics
 */
public class graphic extends Application {
    Scene scene_back;
    /**
     * Menu Button
     * @param stage
     * @param btn_Menu
     */
    public void Menu_Button(Stage stage , Button btn_Menu){
        EventHandler<MouseEvent> eventHandler_Menu = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    start(stage);
                }
                catch (FileNotFoundException | InvalidClassException ex) {
                    ex.printStackTrace();
                }
            }
        };
        btn_Menu.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Menu);
    }

    /**
     * this method is for creating new page with menu button
     * @param stage
     * @param str
     */
    public void new_page(Stage stage , String str){
        /**
         * The Method of Menu Buttton
         */
        Button btn_Menu = new Button();
        btn_Menu = new Button("Menu");
        btn_Menu.setPrefSize(90, 90);
        btn_Menu.setLayoutX(400);
        btn_Menu.setLayoutY(500);
        btn_Menu.setStyle(str);
        Menu_Button(stage , btn_Menu);
    }

    /**
     * this method is for creating new page with back button
     * @param stage
     * @param str
     */
    public void new_page2(Stage stage , String str){
        /**
         * The Method of Menu Buttton
         */
        Button btn_Back = new Button();
        btn_Back = new Button("Back");
        btn_Back.setPrefSize(90, 90);
        btn_Back.setLayoutX(400);
        btn_Back.setLayoutY(500);
        btn_Back.setStyle(str);
    }

    public void showTransactionHandel(Stage stage , String str , Account accounts){
        Button btn_Back = new Button();
        btn_Back = new Button("Back");
        btn_Back.setPrefSize(90, 90);
        btn_Back.setLayoutX(750);
        btn_Back.setLayoutY(500);
        btn_Back.setStyle(str);
        /**
         * table
         * this table is for showing the transactions
         */
        TableView<Account> table_showTransaction = new TableView<>();
        final ObservableList<Account> data_showTransaction =  FXCollections.observableArrayList(
                Client.showAllAccounts(accounts.getAccountNumber() , "Transactions")
        );
        //Editable the tablet
        table_showTransaction.setEditable(true);
        //Creating columns
        TableColumn AccountNumCol = new TableColumn("Account ID");
        AccountNumCol.setMinWidth(200);
        AccountNumCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountID"));
        TableColumn moneyCol = new TableColumn("money");
        moneyCol.setMinWidth(100);
        moneyCol.setCellValueFactory(new PropertyValueFactory<Account, String>("money"));
        TableColumn lnameCol = new TableColumn("Name");
        lnameCol.setMinWidth(100);
        lnameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOwnerName"));
        TableColumn User_IdCol = new TableColumn("User Id");
        User_IdCol.setMinWidth(150);
        User_IdCol.setCellValueFactory(new PropertyValueFactory<Account, String>("user_id"));
        TableColumn accountTypeCol = new TableColumn("Account Type");
        accountTypeCol.setMinWidth(200);
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountType"));
        TableColumn toIdCol = new TableColumn("To Id");
        toIdCol.setMinWidth(150);
        toIdCol.setCellValueFactory(new PropertyValueFactory<Account, String>("toId"));
        TableColumn transferCol = new TableColumn("transfer");
        transferCol.setMinWidth(100);
        transferCol.setCellValueFactory(new PropertyValueFactory<Account, String>("transfer"));

        //Adding data to the table
        table_showTransaction.setItems(data_showTransaction);
        table_showTransaction.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table_showTransaction.getColumns().addAll(AccountNumCol,moneyCol,lnameCol,User_IdCol,accountTypeCol,toIdCol,transferCol);
        table_showTransaction.setId("tables");
        table_showTransaction.setPrefSize(850 , 400);
        table_showTransaction.getStylesheets().add("style.css");
        //Pass the data to a filtered list
        FilteredList<Account> flPerson = new FilteredList(data_showTransaction, user -> true);
        table_showTransaction.setItems(flPerson);
        /**
         * Searching
         */
        //Adding ChoiceBox and TextField
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Account Number", "Account Type");
        choiceBox.setValue("Account Number");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue()) {
                case "Account Type":
                    flPerson.setPredicate(account -> account.getAccountType().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
                case "Account Number":
                    flPerson.setPredicate( account -> account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                    break;
            }
        });

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null) {
                textField.setText("");
                flPerson.setPredicate(null);
            }
        });
        HBox hBox = new HBox(choiceBox, textField);
        choiceBox.setMinWidth(150);
        textField.setMinWidth(200);
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutY(20);
        hBox.setLayoutX(5);
        table_showTransaction.setLayoutY(60);

        btn_Back.setOnAction(ex -> stage.setScene(scene_back));
        Group root = new Group(btn_Back,  table_showTransaction ,hBox);
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Accounts");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Periodic transaction all
     */
    public void PTA(){
        ArrayList<PeriodicTransaction> periodic = new ArrayList<>();
        File file = new File("database/Objects/" +"Periodic"+ ".txt");
        if(file.exists()){
            periodic.addAll(IOFiles.readFromFile("Periodic"));
        }
        for (PeriodicTransaction pt : periodic){
            new Thread() {
                @Override
                public void run()
                {
                    pt.periodicTransfer();

                }
            }.start();
            new Thread() {
                @Override
                public void run()
                {
                    pt.checkDate();
                }
            }.start();
        }
    }

    public void demand(){
        ArrayList<Account>list=new ArrayList<>();
        list.addAll(IOFiles.readFromFile("Accounts"));
        for(Account account:list){
            if(account.getClass()==DemandDepositAccount.class){
                System.out.println("aa");
                new Thread() {
                    @Override
                    public void run()
                    {
                        Account a = (DemandDepositAccount) account;
                        ((DemandDepositAccount) a).annualMonthRaise();
                        System.out.println("a");
                    }
                }.start();
                new Thread() {
                    @Override
                    public void run()
                    {
                       Account a = (DemandDepositAccount) account;
                        ((DemandDepositAccount) a).checkDate();
                    }
                }.start();
            }
        }
    }
    /**
     * start method
     * @param stage
     * @throws FileNotFoundException
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException, InvalidClassException {
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);
        Image image = new Image(new FileInputStream("image\\logo.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setX(150);
        imageView.setY(25);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        StackPane pane = new StackPane();
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        javafx.scene.control.Button btn_Enter , btn_About_Us , btn_Exit;
        /////////////////////////////////////////////////////////
        btn_Enter = new Button("Enter");
        btn_Enter.setPrefSize(310, 55);
        btn_Enter.setLayoutX(100);
        btn_Enter.setLayoutY(200);

        btn_About_Us = new Button("About us");
        btn_About_Us.setPrefSize(310, 55);
        btn_About_Us.setLayoutX(100);
        btn_About_Us.setLayoutY(300);

        btn_Exit = new Button("Exit");
        btn_Exit.setPrefSize(310, 55);
        btn_Exit.setLayoutX(100);
        btn_Exit.setLayoutY(400);

        String str = "-fx-padding: 5px;" +
                "-fx-font: normal bold 20px 'serif'; " +
                "-fx-background-color: darkslateblue;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50 50 50 50; " +
                "-fx-alignment: CENTER;" +
                "-fx-border-width : 4px; " +
                "-fx-border-color: #8B78D7;" +
                "-fx-border-radius: 50 50 50 50;" +
                "-fx-cursor: hand;";

        btn_Enter.setId("btn");
        btn_About_Us.setId("btn");
        btn_Exit.setId("btn");

        ////////////////////////////////////////////////////////
        EventHandler<MouseEvent> eventHandler_Enter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button btn_Menu;
                Button btn_Back;
                javafx.scene.control.Button btn_Management , btn_Employee , btn_Client;
                Text text;
                text = new Text();
                text.setText("Farabi Bank ");
                text.setX(50);
                text.setY(100);
                text.setStrokeWidth(2);
                text.setStroke(Color.DARKSLATEBLUE);
                text.setFont(Font.font("roboto", 30));
                text.setStyle("-fx-alignment: CENTER;");
                /////////////////////////////////////////////////////////
                btn_Management = new Button("Manager");
                btn_Management.setPrefSize(310, 55);
                btn_Management.setLayoutX(100);
                btn_Management.setLayoutY(200);

                btn_Employee = new Button("Employee");
                btn_Employee.setPrefSize(310, 55);
                btn_Employee.setLayoutX(100);
                btn_Employee.setLayoutY(300);

                btn_Client = new Button("Client");
                btn_Client.setPrefSize(310, 55);
                btn_Client.setLayoutX(100);
                btn_Client.setLayoutY(400);

                btn_Menu = new Button("Menu");
                btn_Menu.setPrefSize(90, 90);
                btn_Menu.setLayoutX(400);
                btn_Menu.setLayoutY(500);
                btn_Menu.setStyle(str);

                btn_Back = new Button("Back");
                btn_Back.setPrefSize(90, 90);
                btn_Back.setLayoutX(400);
                btn_Back.setLayoutY(500);
                btn_Back.setStyle(str);

                btn_Management.setId("btn");
                btn_Employee.setId("btn");
                btn_Client.setId("btn");

                Group root = new Group(text , btn_Management , btn_Employee , btn_Client , btn_Menu);
                Scene scene = new Scene(root, 500, 600);
                //adding css
                scene.getStylesheets().add("style.css");
                stage.setTitle("Farabi Bank");
                stage.setScene(scene);
                stage.show();

                /////////////////////////////////////////////////////////////////////// Manager
                /**
                 * Manager fields
                 * 1.Menu button
                 * 2.Login Form
                 */
                EventHandler<MouseEvent> eventHandler_Management = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Button btn_Menu;
                        /**
                         * button Menu
                         */
                        btn_Menu = new Button("Menu");
                        btn_Menu.setPrefSize(90, 90);
                        btn_Menu.setLayoutX(400);
                        btn_Menu.setLayoutY(500);
                        btn_Menu.setStyle(str);

                        /**
                         * Login Form
                         * password_Field
                         * UserName_Field
                         */
                        BorderPane bp = new BorderPane();
                        bp.setPadding(new Insets(10,50,50,50));

                        //Adding HBox
                        HBox hb = new HBox();
                        hb.setPadding(new Insets(20,20,20,30));

                        //Adding GridPane
                        GridPane gridPane = new GridPane();
                        gridPane.setPadding(new Insets(20,20,20,20));
                        gridPane.setHgap(5);
                        gridPane.setVgap(5);

                        //Implementing Nodes for GridPane
                        Label lblUserName = new Label("Username");
                        final javafx.scene.control.TextField userName = new TextField();
                        Label lblPassword = new Label("Password");
                        final PasswordField pass = new PasswordField();
                        Button btnLogin = new Button("Login");
                        final Label lblMessage = new Label();

                        //Adding Nodes to GridPane layout
                        gridPane.add(lblUserName, 0, 0);
                        gridPane.add(userName, 1, 0);
                        gridPane.add(lblPassword, 0, 1);
                        gridPane.add(pass, 1, 1);
                        gridPane.add(btnLogin, 2, 1);
                        gridPane.add(lblMessage, 1, 2);
                        String str_login = "-fx-padding: 5px;" +
                                "-fx-font: normal bold 20px 'serif'; " +
                                "-fx-background-color: white;" +
                                "-fx-text-fill: black;" +
                                "-fx-background-radius: 50 50 50 50; " +
                                "-fx-border-width : 4px; " +
                                "-fx-border-color: #8B78D7;" +
                                "-fx-border-radius: 50 50 50 50;";

                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setOffsetX(5);
                        dropShadow.setOffsetY(5);
                        Text text_Login = new Text("Login Form");
                        text_Login.setEffect(dropShadow);
                        text_Login.setFont(Font.font ("roboto", 30));
                        text_Login.setStyle("-fx-font-weight: bold;");
                        text_Login.setFill(Color.GRAY);
                        text_Login.setStroke(Color.DARKSLATEBLUE);
                        text_Login.setStrokeWidth(3);
                        hb.getChildren().add(text_Login);
                        hb.setAlignment(Pos.CENTER);
                        btnLogin.setLayoutX(20);
                        bp.setTop(hb);
                        bp.setCenter(gridPane);
                        bp.setId("bp_Login");
                        bp.getStylesheets().add("style.css");
                        bp.setLayoutY(200);
                        userName.setStyle(str_login);
                        pass.setStyle(str_login);
                        btnLogin.setId("btn_Login");
                        btnLogin.setLayoutY(400);
                        pass.setMinWidth(350);
                        userName.setMinWidth(350);
                        btnLogin.getStylesheets().add("style.css");
                        Group root = new Group(btn_Menu , bp , btnLogin , imageView);
                        /**
                         * Action for btnLogin
                         */
                        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                String checkUser = userName.getText().toString();
                                String checkPw = pass.getText().toString();
                                /**
                                 * LOGIN
                                 */
                                if (Management.Authentication(checkUser , checkPw)){
                                    ///Alert
                                    Alert a = new Alert(Alert.AlertType.NONE);
                                    a.setAlertType(Alert.AlertType.INFORMATION);
                                    a.setContentText("you are loged in successfuly");
                                    a.show();
                                    Image logo = null;
                                    try {
                                        logo = new Image(new FileInputStream("image\\logo.jpg"));
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                    ImageView logoView = new ImageView(logo);
                                    logoView.setX(200);
                                    logoView.setY(35);
                                    logoView.setFitHeight(250);
                                    logoView.setFitWidth(250);
                                    logoView.setPreserveRatio(true);
                                    StackPane pane = new StackPane();
                                    pane.getChildren().add(logoView);
                                    StackPane.setAlignment(logoView, Pos.CENTER);
                                    /**
                                     * The Method of Menu Buttton
                                     */
                                    Button btn_menu = new Button();
                                    btn_menu = new Button("Menu");
                                    btn_menu.setPrefSize(90, 90);
                                    btn_menu.setLayoutX(580);
                                    btn_menu.setLayoutY(500);
                                    btn_menu.setStyle(str);
                                    Menu_Button(stage , btn_menu);

                                    Button btn_Time;
                                    /**
                                     * button Time
                                     * this button is for changing the time of the app
                                     */
                                    btn_Time = new Button("Time");
                                    btn_Time.setPrefSize(90, 90);
                                    btn_Time.setLayoutX(5);
                                    btn_Time.setLayoutY(500);
                                    btn_Time.setStyle(str);
                                    /**
                                     * Action btn Time
                                     */
                                    btn_Time.setOnAction(ex -> {
                                        new_page(stage,str);
                                        final DatePicker datePicker_Time = new DatePicker();
                                        datePicker_Time.setLayoutX(100);
                                        datePicker_Time.setLayoutY(300);
                                        datePicker_Time.setMinSize(300,40);
                                        Button btn_datePickerSubmit = new Button("Submit");
                                        Label label = new Label(Time.getDate().toString());
                                        label.setLayoutX(150);
                                        label.setLayoutY(200);
                                        btn_datePickerSubmit.setLayoutX(100);
                                        btn_datePickerSubmit.setLayoutY(400);
                                        datePicker_Time.setId("str_login");
                                        btn_datePickerSubmit.setId("btn_Login");
                                        //action
                                        btn_datePickerSubmit.setOnAction(exx ->{
                                            Time.setDate(new Date(datePicker_Time.getValue().getYear()-1900,datePicker_Time.getValue().getMonthValue(),datePicker_Time.getValue().getDayOfMonth()));
                                            label.setText(Time.getDate().toString());
                                            PTA();
                                           // demand();
                                        });
                                        Group root = new Group(btn_Menu,label,datePicker_Time,btn_datePickerSubmit,imageView);
                                        Scene scene = new Scene(root, 500, 600);
                                        scene.getStylesheets().add("style.css");
                                        stage.setTitle("Time");
                                        stage.setScene(scene);
                                        stage.show();
                                    });

                                    javafx.scene.control.Button btn_ClientManagement , btn_AccountManagement , btn_EmployeeManagement ,  btn_TransactionManagement;
                                    btn_ClientManagement = new Button("Clients Management");
                                    btn_ClientManagement.setPrefSize(310, 55);
                                    btn_ClientManagement.setLayoutX(10);
                                    btn_ClientManagement.setLayoutY(250);

                                    btn_AccountManagement = new Button("Accounts Management");
                                    btn_AccountManagement.setPrefSize(310, 55);
                                    btn_AccountManagement.setLayoutX(350);
                                    btn_AccountManagement.setLayoutY(250);

                                    btn_EmployeeManagement = new Button("Employyee Management");
                                    btn_EmployeeManagement.setPrefSize(310, 55);
                                    btn_EmployeeManagement.setLayoutX(10);
                                    btn_EmployeeManagement.setLayoutY(350);

                                    btn_TransactionManagement = new Button("Transaction Management");
                                    btn_TransactionManagement.setPrefSize(310, 55);
                                    btn_TransactionManagement.setLayoutX(350);
                                    btn_TransactionManagement.setLayoutY(350);
                                    //adding css
                                    btn_ClientManagement.setId("btn");
                                    btn_AccountManagement.setId("btn");
                                    btn_EmployeeManagement.setId("btn");
                                    btn_TransactionManagement.setId("btn");
                                    /////////////////////////////////////////////////////////////////////////Action btn_ClientManagement
                                    EventHandler<MouseEvent> eventHandler_btn_ClientManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(750);
                                            btn_Back.setLayoutY(600);
                                            btn_Back.setStyle(str);

                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<User> table = new TableView<>();
                                            Management management = new Management("Clients");
                                            final ObservableList<User> data =  FXCollections.observableArrayList(
                                                    management.getUsers()
                                            );
                                            //Editable the table
                                            table.setEditable(true);

                                            //Delete the User
                                            TableColumn<User, User> DeleteCol = new TableColumn<>("Action");
                                            DeleteCol.setMinWidth(80);
                                            DeleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            DeleteCol.setCellFactory(param -> new TableCell<User, User>() {
                                                private final Button deleteButton = new Button("Delete");
                                                @Override
                                                protected void updateItem(User user, boolean empty) {
                                                    super.updateItem(user, empty);
                                                    if (user == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(deleteButton);
                                                    Management management = new Management("Clients");
                                                    //action Delete button
                                                    EventHandler<MouseEvent> eventHandler_Delete = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            data.remove(user);
                                                            management.delete(user.getIdCardNumber());
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Delete);
                                                }
                                            });

                                            //Edit the User
                                            TableColumn<User, User> EditCol = new TableColumn<>("Edit");
                                            EditCol.setMinWidth(50);
                                            EditCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            EditCol.setCellFactory(param -> new TableCell<User, User>() {
                                                private final Button editButton = new Button("Edit");
                                                @Override
                                                protected void updateItem(User user, boolean empty) {
                                                    super.updateItem(user, empty);
                                                    if (user == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(editButton);
                                                    //action Edit button
                                                    EventHandler<MouseEvent> eventHandler_Edit = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            management.edit();
                                                            Alert a = new Alert(Alert.AlertType.NONE);
                                                            a.setAlertType(Alert.AlertType.INFORMATION);
                                                            a.setContentText("The information was edited successfully");
                                                            a.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    editButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Edit);
                                                }
                                            });

                                            //Creating columns
                                            TableColumn firs_nameCol = new TableColumn("First Name");
                                            firs_nameCol.setMinWidth(100);
                                            firs_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("fname"));
                                            TableColumn last_nameCol = new TableColumn("Last Name");
                                            last_nameCol.setMinWidth(100);
                                            last_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("lnmae"));
                                            TableColumn genderCol = new TableColumn("Gender");
                                            genderCol.setMinWidth(100);
                                            genderCol.setCellValueFactory(new PropertyValueFactory<User , String>("gender"));
                                            TableColumn date_of_birthdayCol = new TableColumn("Date of Birthday");
                                            date_of_birthdayCol.setMinWidth(150);
                                            date_of_birthdayCol.setCellValueFactory(new PropertyValueFactory<User , String>("dateOfBirth"));
                                            TableColumn dateCol = new TableColumn("Date");
                                            dateCol.setMinWidth(100);
                                            dateCol.setCellValueFactory(new PropertyValueFactory<User , String>("dateOfMembership"));
                                            TableColumn user_nameCol = new TableColumn("Id Number");
                                            user_nameCol.setMinWidth(100);
                                            user_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("idCardNumber"));
                                            TableColumn passwordCol = new TableColumn("Password");
                                            passwordCol.setMinWidth(100);
                                            passwordCol.setCellValueFactory(new PropertyValueFactory<User , String>("password"));

                                            /**
                                             * Editthe table
                                             */
                                            //Editable the table
                                            firs_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            firs_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setFname(t.getNewValue());
                                                }
                                            });
                                            last_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            last_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setLnmae(t.getNewValue());
                                                }
                                            });
                                            genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            genderCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setGender(t.getNewValue());
                                                    if (!(t.getNewValue().equals("male") || t.getNewValue().equals("female"))){
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Enter the gender please");
                                                        alert.show();
                                                        return;
                                                    }
                                                }
                                            });

                                            date_of_birthdayCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            date_of_birthdayCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setDateOfBirth(t.getNewValue());
                                                }
                                            });
                                            dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            dateCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setDateOfMembership(t.getNewValue());
                                                }
                                            });
                                            user_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            user_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setIdCardNumber(t.getNewValue());
                                                }
                                            });
                                            passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            passwordCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<User, String> t) {
                                                    ((User) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setPassword(t.getNewValue());
                                                }
                                            });

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(DeleteCol,EditCol,firs_nameCol,last_nameCol,genderCol,date_of_birthdayCol,dateCol,user_nameCol,passwordCol);

                                            //Pass the data to a filtered list
                                            FilteredList<User> flPerson = new FilteredList(data, user -> true);
                                            table.setItems(flPerson);

                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("First Name", "Last Name", "ID");
                                            choiceBox.setValue("First Name");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "First Name":
                                                        flPerson.setPredicate(user -> user.getFname().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "Last Name":
                                                        flPerson.setPredicate(user -> user.getLnmae().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "ID":
                                                        flPerson.setPredicate(user -> user.getIdCardNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });
                                            HBox hBox = new HBox(choiceBox, textField);
                                            hBox.setAlignment(Pos.CENTER);

                                            //////////////////////////////////////////////////////////////
                                            //add button
                                            /**
                                             * add button
                                             */
                                            final TextField addfname = new TextField();
                                            addfname.setPromptText("First Name");
                                            addfname.setMaxWidth(400);
                                            final TextField addlname = new TextField();
                                            addlname.setMaxWidth(400);
                                            addlname.setPromptText("Last Name");
                                            ToggleGroup gender = new ToggleGroup();
                                            final RadioButton male = new RadioButton("A) Male");
                                            male.setToggleGroup(gender);
                                            final RadioButton female = new RadioButton("B) Female");
                                            HBox hBox1 = new HBox(male,female);
                                            female.setToggleGroup(gender);
                                            final DatePicker adddateBirth = new DatePicker();
                                            adddateBirth.setMaxWidth(400);
                                            adddateBirth.setPromptText("Date Birth");
                                            final DatePicker addDate = new DatePicker();
                                            addDate.setMaxWidth(400);
                                            addDate.setPromptText("Date");
                                            final TextField addUser = new TextField();
                                            addUser.setMaxWidth(400);
                                            addUser.setPromptText("Id Number");
                                            final TextField addPassword = new TextField();
                                            addPassword.setMaxWidth(400);
                                            addPassword.setPromptText("Password");

                                            final Button addButton = new Button("Add");
                                            addButton.setMaxWidth(400);
                                            addButton.setOnAction((ActionEvent event) -> {
                                                try {
                                                    User user = null;
                                                    if (male.isSelected()) {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        user = new User(addfname.getText(), addlname.getText(), "male", adddateBirth.getValue().toString(), addDate.getValue().toString(), addUser.getText(), addPassword.getText());
                                                        if (management.newUser(user)){
                                                            alert.setContentText("successfully added");
                                                            alert.show();
                                                            data.add(user);
                                                        }
                                                        else {
                                                            alert.setContentText("this user has already exist");
                                                            alert.show();
                                                        }
                                                    } else if (female.isSelected()) {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        user = new User(addfname.getText(), addlname.getText(), "female", adddateBirth.getValue().toString(), addDate.getValue().toString(), addUser.getText(), addPassword.getText());
                                                        if (management.newUser(user)){
                                                            alert.setContentText("successfully added");
                                                            alert.show();
                                                            data.add(user);
                                                        }
                                                        else {
                                                            alert.setContentText("this user has already exist");
                                                            alert.show();
                                                        }
                                                    }
                                                    addfname.clear();
                                                    addlname.clear();
                                                    male.setToggleGroup(gender);
                                                    female.setToggleGroup(gender);
                                                    adddateBirth.setValue(null);
                                                    addDate.setValue(null);
                                                    addUser.clear();
                                                    addPassword.clear();
                                                }catch (NullPointerException ex){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("Enter all fields please");
                                                    alert.show();
                                                }
                                            });
                                            //Setting the size of the table
                                            table.setMaxSize(850, 330);
                                            VBox vbox = new VBox();
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(50, 50, 50, 60));
                                            vbox.setLayoutY(290);
                                            vbox.getChildren().addAll(table,hBox,addfname,addlname,hBox1,adddateBirth,addDate,addUser,addPassword,addButton);
                                            Group root = new Group(btn_Back , table , vbox);
                                            Scene scene = new Scene(root,850, 720);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            scene.getStylesheets().add("style.css");
                                            //add css to table
                                            table.setId("tables");
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_ClientManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_ClientManagement);

                                    //////////////////////////////////////////////////////////////////////////Action btn_AccountManagement
                                    EventHandler<MouseEvent> eventHandler_btn_AccountManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(650);
                                            btn_Back.setLayoutY(600);
                                            btn_Back.setStyle(str);

                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<Account> table = new TableView<>();
                                            AccountManager accountManager = new AccountManager("Accounts");
                                            final ObservableList<Account> data = FXCollections.observableArrayList(
                                                    accountManager.getAccounts()
                                            );
                                            //Editable the table
                                            table.setEditable(true);

                                            //Delete the User
                                            TableColumn<Account, Account> DeleteCol = new TableColumn<>("Action");
                                            DeleteCol.setMinWidth(80);
                                            DeleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            DeleteCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button deleteButton = new Button("Delete");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(deleteButton);
                                                    AccountManager accountManager = new AccountManager("Accounts");
                                                    //delete button action
                                                    EventHandler<MouseEvent> eventHandler_Delete = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            data.remove(account);
                                                            accountManager.delete(account.getAccountNumber());
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Delete);
                                                }
                                            });

                                            //Edit the Account
                                            TableColumn<Account, Account> EditCol = new TableColumn<>("Edit");
                                            EditCol.setMinWidth(50);
                                            EditCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            EditCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button editButton = new Button("Edit");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(editButton);
                                                    //action Delete button
                                                    EventHandler<MouseEvent> eventHandler_Edit = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            accountManager.edit();
                                                            Alert a = new Alert(Alert.AlertType.NONE);
                                                            a.setAlertType(Alert.AlertType.INFORMATION);
                                                            a.setContentText("The information was edited successfully");
                                                            a.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    editButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Edit);
                                                }
                                            });
                                            //Transaction Button
                                            /**
                                             * Transaction Buttton
                                             * This button is for transaction
                                             */
                                            TableColumn<Account, Account> TransactionCol = new TableColumn<>("Action");
                                            TransactionCol.setMinWidth(80);
                                            TransactionCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            TransactionCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button TransactionButton = new Button("Transaction");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(TransactionButton);
                                                    AccountManager accountManager = new AccountManager("Transactions");
                                                    //Action transaction button
                                                    EventHandler<MouseEvent> eventHandler_Transaction = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            /**
                                                             * showTransactionHandel Method
                                                             */
                                                            showTransactionHandel(stage,str,account);
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    TransactionButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Transaction);
                                                }
                                            });
                                            ////////////////////////////////////////

                                            //Creating columns
                                            TableColumn nameCol = new TableColumn("Last Name");
                                            nameCol.setMinWidth(100);
                                            nameCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountOwnerName"));
                                            TableColumn account_numberCol = new TableColumn("Account number");
                                            account_numberCol.setMinWidth(200);
                                            account_numberCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountNumber"));
                                            TableColumn moneyCol = new TableColumn("Money");
                                            moneyCol.setMinWidth(100);
                                            moneyCol.setCellValueFactory(new PropertyValueFactory<Account , Long>("money"));
                                            TableColumn UserIdCol = new TableColumn("User Id");
                                            UserIdCol.setMinWidth(100);
                                            UserIdCol.setCellValueFactory(new PropertyValueFactory<Account , String>("user_id"));
                                            TableColumn accountTypeCol = new TableColumn("Account type");
                                            accountTypeCol.setMinWidth(200);
                                            accountTypeCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountType"));


                                            nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Account, String> t) {
                                                    ((Account) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setAccountOwnerName(t.getNewValue());
                                                }
                                            });

                                            UserIdCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            UserIdCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Account, String> t) {
                                                    ((Account) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setUser_id(t.getNewValue());
                                                }
                                            });

                                            accountTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            accountTypeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Account,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Account, String> t) {
                                                    ((Account) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setAccountType(t.getNewValue());
                                                }
                                            });

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(DeleteCol,EditCol,TransactionCol,nameCol,account_numberCol,moneyCol,UserIdCol,accountTypeCol);

                                            //Pass the data to a filtered list
                                            FilteredList<Account> flPerson = new FilteredList(data, user -> true);
                                            table.setItems(flPerson);

                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("Last Name" , "User Id" , "Account Number");
                                            choiceBox.setValue("Last Name");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "Last Name":
                                                        flPerson.setPredicate(account -> account.getAccountOwnerName().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "Account Number":
                                                        flPerson.setPredicate(account -> account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "User Id":
                                                        flPerson.setPredicate(account -> account.getUser_id().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });
                                            HBox hBox = new HBox(choiceBox, textField);
                                            hBox.setAlignment(Pos.CENTER);

                                            //add button
                                            /**
                                             * add button
                                             */
                                            final TextField addName = new TextField();
                                            addName.setPromptText("Last Name");
                                            addName.setMaxWidth(400);
                                            final TextField addUser_id = new TextField();
                                            addUser_id.setMaxWidth(400);
                                            addUser_id.setPromptText("User Id");
                                            final ChoiceBox<String> addAccountType = new ChoiceBox();
                                            addAccountType.getItems().addAll("CurrentDepositAccount","DemandDepositAccount","TimeDepositAccount");
                                            addAccountType.setValue("CurrentDepositAccount");
                                            addAccountType.setMaxWidth(400);
                                            final Button addButton = new Button("Add");
                                            addButton.setMaxWidth(400);
                                            /**
                                             * handle the InputMismatchException error
                                             * Exception for getting the Money
                                             */
                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                @Override
                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                    if (t.isReplaced())
                                                        if(t.getText().matches("[^0-9]"))
                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                    if (t.isAdded()) {
                                                        if (t.getControlText().contains(".")) {
                                                            if (t.getText().matches("[^0-9]")) {
                                                                t.setText("");
                                                            }
                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter Number please!!");
                                                            alert.show();
                                                            t.setText("");
                                                        }
                                                    }
                                                    return t;
                                                }
                                            };
                                            final TextField addMoney = new TextField();
                                            addMoney.setTextFormatter(new TextFormatter<>(filter));
                                            addMoney.setMaxWidth(400);
                                            addMoney.setPromptText("Money");
                                            addButton.setOnAction((ActionEvent event) -> {
                                                try {
                                                    Account account = new Account(addName.getText(), Long.parseLong(addMoney.getText()), addUser_id.getText(), addAccountType.getValue());
                                                    data.add(account);
                                                    addName.clear();
                                                    addMoney.clear();
                                                    addUser_id.clear();
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setContentText(accountManager.newAccount(account));
                                                    alert.show();
                                                }catch (NullPointerException | NumberFormatException ex){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("Enter all fields please");
                                                    alert.show();
                                                }
                                            });
                                            //////////////////////////////////////////////////////////////

                                            //Setting the size of the table
                                            table.setMaxSize(750, 300);
                                            VBox vbox = new VBox();
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(50, 50, 50, 60));
                                            vbox.setLayoutY(300);
                                            vbox.getChildren().addAll(table,hBox,addName,addMoney,addUser_id,addAccountType,addButton);

                                            Group root = new Group(btn_Back , table , vbox);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,750, 700);
                                            //add css to table
                                            table.setId("tables");
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_AccountManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_AccountManagement);

                                    //////////////////////////////////////////////////////////////////////////////Action btn_EmployeeManagement
                                    EventHandler<MouseEvent> eventHandler_btn_EmployeeManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(900);
                                            btn_Back.setLayoutY(650);
                                            btn_Back.setStyle(str);

                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<Employee> table = new TableView<>();
                                            Management management = new Management("Employees");
                                            final ObservableList<Employee> data =  FXCollections.observableArrayList(
                                                    management.getUsers()
                                            );

                                            //Editable the table
                                            table.setEditable(true);

                                            //Delete the User
                                            TableColumn<Employee, Employee> DeleteCol = new TableColumn<>("Action");
                                            DeleteCol.setMinWidth(80);
                                            DeleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            DeleteCol.setCellFactory(param -> new TableCell<Employee, Employee>() {
                                                private final Button deleteButton = new Button("Delete");
                                                @Override
                                                protected void updateItem(Employee emp, boolean empty) {
                                                    super.updateItem(emp, empty);
                                                    if (emp == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(deleteButton);
                                                    //delete button action
                                                    EventHandler<MouseEvent> eventHandler_Delete = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            data.remove(emp);
                                                            management.delete(emp.getIdCardNumber());
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Delete);
                                                }
                                            });

                                            //Edit the Employee
                                            TableColumn<Employee, Employee> EditCol = new TableColumn<>("Edit");
                                            EditCol.setMinWidth(50);
                                            EditCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            EditCol.setCellFactory(param -> new TableCell<Employee, Employee>() {
                                                private final Button editButton = new Button("Edit");
                                                @Override
                                                protected void updateItem(Employee emp, boolean empty) {
                                                    super.updateItem(emp, empty);
                                                    if (emp == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(editButton);
                                                    //action edit button
                                                    EventHandler<MouseEvent> eventHandler_Edit = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            management.edit();
                                                            Alert a = new Alert(Alert.AlertType.NONE);
                                                            a.setAlertType(Alert.AlertType.INFORMATION);
                                                            a.setContentText("The information was edited successfully");
                                                            a.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    editButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Edit);
                                                }
                                            });

                                            //Creating columns
                                            TableColumn firs_nameCol = new TableColumn("First Name");
                                            firs_nameCol.setMinWidth(100);
                                            firs_nameCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("fname"));
                                            TableColumn last_nameCol = new TableColumn("Last Name");
                                            last_nameCol.setMinWidth(100);
                                            last_nameCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("lnmae"));
                                            TableColumn genderCol = new TableColumn("Gender");
                                            genderCol.setMinWidth(100);
                                            genderCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("gender"));
                                            TableColumn date_of_birthdayCol = new TableColumn("Date of Birthday");
                                            date_of_birthdayCol.setMinWidth(150);
                                            date_of_birthdayCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("dateOfBirth"));
                                            TableColumn dateCol = new TableColumn("Date");
                                            dateCol.setMinWidth(100);
                                            dateCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("dateOfMembership"));
                                            TableColumn user_nameCol = new TableColumn("Id Number");
                                            user_nameCol.setMinWidth(100);
                                            user_nameCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("idCardNumber"));
                                            TableColumn passwordCol = new TableColumn("Password");
                                            passwordCol.setMinWidth(100);
                                            passwordCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("password"));

                                            TableColumn salaryCol = new TableColumn("salary");
                                            salaryCol.setMinWidth(100);
                                            salaryCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("salary"));

                                            TableColumn resumeCol = new TableColumn("resume");
                                            resumeCol.setMinWidth(100);
                                            resumeCol.setCellValueFactory(new PropertyValueFactory<Employee , String>("resume"));

                                            /**
                                             * Editthe table
                                             */
                                            //Editable the table
                                            firs_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            firs_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setFname(t.getNewValue());
                                                }
                                            });
                                            last_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            last_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setLnmae(t.getNewValue());
                                                }
                                            });
                                            genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            genderCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setGender(t.getNewValue());
                                                    if (!(t.getNewValue().equals("male") || t.getNewValue().equals("female"))){
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Enter the gender please");
                                                        alert.show();
                                                        return;
                                                    }
                                                }
                                            });

                                            date_of_birthdayCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            date_of_birthdayCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setDateOfBirth(t.getNewValue());
                                                }
                                            });
                                            dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            dateCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setDateOfMembership(t.getNewValue());
                                                }
                                            });
                                            user_nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            user_nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setIdCardNumber(t.getNewValue());
                                                }
                                            });
                                            passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            passwordCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setPassword(t.getNewValue());
                                                }
                                            });
                                            salaryCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            salaryCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setSalary(t.getNewValue());
                                                    /////handle the InputMismatchException error
                                                    char ch = ' ';
                                                    for (int i = 0; i < t.getNewValue().length(); i++){
                                                        ch = t.getNewValue().charAt(i);
                                                        int ascii = (int) ch;
                                                        if (ascii < 48 || ascii > 57) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter amount of salary please");
                                                            alert.show();
                                                            return;
                                                        }
                                                    }
                                                }
                                            });
                                            resumeCol.setCellFactory(TextFieldTableCell.forTableColumn());
                                            resumeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee,String>>() {
                                                @Override
                                                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                                                    ((Employee) t.getTableView().getItems().get(
                                                            t.getTablePosition().getRow())
                                                    ).setResume(t.getNewValue());
                                                }
                                            });

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(DeleteCol,EditCol,firs_nameCol,last_nameCol,genderCol,date_of_birthdayCol,dateCol,user_nameCol,passwordCol,salaryCol,resumeCol);

                                            //Pass the data to a filtered list
                                            FilteredList<Employee> flPerson = new FilteredList(data, emp -> true);
                                            table.setItems(flPerson);

                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("Last Name" , "ID");
                                            choiceBox.setValue("Last Name");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "Last Name":
                                                        flPerson.setPredicate(emp -> emp.getFname().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "ID":
                                                        flPerson.setPredicate(emp -> emp.getIdCardNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {//reset table and textfield when new choice is selected
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });

                                            //add button
                                            /**
                                             * add button
                                             */
                                            final TextField addfname = new TextField();
                                            addfname.setPromptText("First Name");
                                            final TextField addlname = new TextField();
                                            addlname.setPromptText("Last Name");
                                            ToggleGroup gender = new ToggleGroup();
                                            final RadioButton male = new RadioButton("A) Male");
                                            male.setToggleGroup(gender);
                                            final RadioButton female = new RadioButton("B) Female");
                                            female.setToggleGroup(gender);
                                            final DatePicker adddateBirth = new DatePicker();
                                            adddateBirth.setPromptText("Date Birth");
                                            final DatePicker addDate = new DatePicker();
                                            addDate.setPromptText("Date");
                                            final TextField addUser = new TextField();
                                            addUser.setPromptText("Id Number");
                                            final TextField addPassword = new TextField();
                                            addPassword.setPromptText("Password");
                                            final TextField addResume = new TextField();
                                            addResume.setPromptText("Resuume");
                                            final Button addButton = new Button("Add");
                                            addButton.setMaxWidth(400);
                                            /**
                                             * handle the InputMismatchException error
                                             * Exception for getting the Money
                                             */
                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                @Override
                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                    if (t.isReplaced())
                                                        if(t.getText().matches("[^0-9]"))
                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                    if (t.isAdded()) {
                                                        if (t.getControlText().contains(".")) {
                                                            if (t.getText().matches("[^0-9]")) {
                                                                t.setText("");
                                                            }
                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter Number please!!");
                                                            alert.show();
                                                            t.setText("");
                                                        }
                                                    }
                                                    return t;
                                                }
                                            };
                                            final TextField addSalary = new TextField();
                                            addSalary.setTextFormatter(new TextFormatter<>(filter));
                                            addSalary.setPromptText("Salary");
                                            HBox hBox2 = new HBox(male,female);
                                            HBox hBox3 = new HBox(adddateBirth);
                                            HBox hBox4 = new HBox(addDate);
                                            HBox hBox5 = new HBox(addUser,addPassword);
                                            HBox hBox6 = new HBox(addResume,addSalary);
                                            addButton.setOnAction((ActionEvent event) -> {
                                                try {
                                                    Employee emp = null;
                                                    if (male.isSelected()) {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        emp = new Employee(addfname.getText(), addlname.getText(), "male", adddateBirth.getValue().toString(), addDate.getValue().toString(), addUser.getText(), addPassword.getText(), addSalary.getText(), addResume.getText());
                                                        if (management.newUser(emp)){
                                                            alert.setContentText("successfully added");
                                                            alert.show();
                                                            data.add(emp);
                                                        }
                                                        else {
                                                            alert.setContentText("this user has already exist");
                                                            alert.show();
                                                        }
                                                    } else if (female.isSelected()) {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        emp = new Employee(addfname.getText(), addlname.getText(), "female", adddateBirth.getValue().toString(), addDate.getValue().toString(), addUser.getText(), addPassword.getText(), addSalary.getText(), addResume.getText());
                                                        if (management.newUser(emp)){
                                                            alert.setContentText("successfully added");
                                                            alert.show();
                                                            data.add(emp);
                                                        }
                                                        else {
                                                            alert.setContentText("this user has already exist");
                                                            alert.show();
                                                        }
                                                    }
                                                    addfname.clear();
                                                    addlname.clear();
                                                    male.setToggleGroup(gender);
                                                    female.setToggleGroup(gender);
                                                    adddateBirth.setValue(null);
                                                    addDate.setValue(null);
                                                    addUser.clear();
                                                    addPassword.clear();
                                                    addSalary.clear();
                                                    addResume.clear();

                                                }catch (NullPointerException ex){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("Enter all fields please");
                                                    alert.show();
                                                }

                                            });
                                            //////////////////////////////////////////////////////////////
                                            HBox hBox = new HBox(choiceBox, textField);
                                            hBox.setAlignment(Pos.CENTER);
                                            choiceBox.setMinWidth(150);
                                            textField.setMinWidth(200);
                                            hBox.setLayoutY(20);
                                            hBox.setLayoutX(10);
                                            //Setting the size of the table
                                            table.setMaxSize(1000, 300);
                                            table.setLayoutY(60);
                                            VBox vbox = new VBox();
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(50, 50, 50, 60));
                                            vbox.setLayoutY(350);
                                            vbox.getChildren().addAll(table,addfname,addlname,hBox2,hBox3,hBox4,hBox5,hBox6,addButton);

                                            Group root = new Group(btn_Back ,hBox , table , vbox);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,1000, 750);
                                            //add css to table
                                            table.setId("tables");
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_EmployeeManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_EmployeeManagement);
                                    ////////////////////////////////////////////////////////////////////////////////////////Action btn_TransactionManagement
                                    EventHandler<MouseEvent> eventHandler_TransactionManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(700);
                                            btn_Back.setLayoutY(500);
                                            btn_Back.setStyle(str);
                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<Transaction> table = new TableView<>();
                                            final ObservableList<Transaction> data = FXCollections.observableArrayList(
                                                    Transaction.showTransaction()
                                            );
                                            //Editable the table
                                            table.setEditable(true);
                                            //Creating columns
                                            TableColumn AccountNumCol = new TableColumn("Account ID");
                                            AccountNumCol.setMinWidth(200);
                                            AccountNumCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountID"));
                                            TableColumn moneyCol = new TableColumn("money");
                                            moneyCol.setMinWidth(100);
                                            moneyCol.setCellValueFactory(new PropertyValueFactory<Account, String>("money"));
                                            TableColumn lnameCol = new TableColumn("Name");
                                            lnameCol.setMinWidth(100);
                                            lnameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOwnerName"));
                                            TableColumn User_IdCol = new TableColumn("User Id");
                                            User_IdCol.setMinWidth(150);
                                            User_IdCol.setCellValueFactory(new PropertyValueFactory<Account, String>("user_id"));
                                            TableColumn accountTypeCol = new TableColumn("Account Type");
                                            accountTypeCol.setMinWidth(200);
                                            accountTypeCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountType"));
                                            TableColumn toIdCol = new TableColumn("To Id");
                                            toIdCol.setMinWidth(200);
                                            toIdCol.setCellValueFactory(new PropertyValueFactory<Account, String>("toId"));
                                            TableColumn transferCol = new TableColumn("transfer");
                                            transferCol.setMinWidth(100);
                                            transferCol.setCellValueFactory(new PropertyValueFactory<Account, String>("transfer"));

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(AccountNumCol,moneyCol,lnameCol,User_IdCol,accountTypeCol,toIdCol,transferCol);
                                            table.setId("tables");
                                            table.setPrefSize(800 , 400);
                                            table.getStylesheets().add("style.css");
                                            //Pass the data to a filtered list
                                            FilteredList<Transaction> flPerson = new FilteredList(data, user -> true);
                                            table.setItems(flPerson);
                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("Account Number", "Account Type");
                                            choiceBox.setValue("Account Number");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue()) {
                                                    case "Account Type":
                                                        flPerson.setPredicate(account -> account.getAccountType().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                                                        break;
                                                    case "Account Number":
                                                        flPerson.setPredicate(account -> account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {
                                                if (newVal != null) {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });
                                            HBox hBox = new HBox(choiceBox, textField);
                                            choiceBox.setMinWidth(150);
                                            textField.setMinWidth(200);
                                            hBox.setAlignment(Pos.CENTER);
                                            hBox.setLayoutY(20);
                                            hBox.setLayoutX(5);
                                            table.setLayoutY(60);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Group root = new Group(btn_Back,  table ,hBox);
                                            Scene scene = new Scene(root, 800, 600);
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();
                                        }
                                    };
                                    btn_TransactionManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_TransactionManagement);
                                    ///////////////////////////////
                                    Group root = new Group(btn_menu,logoView,btn_Time,btn_ClientManagement,btn_AccountManagement,btn_EmployeeManagement,btn_TransactionManagement);
                                    Menu_Button(stage , btn_Menu);
                                    scene_back = new Scene(root,680, 600);
                                    scene_back .getStylesheets().add("style.css");
                                    stage.setTitle("Farabi Bank");
                                    stage.setScene(scene_back );
                                    stage.show();
                                    ////////////////////////////////
                                } else {
                                    lblMessage.setText("Incorrect user or password.");
                                    lblMessage.setTextFill(Color.RED);
                                }


                                userName.setText("");
                                pass.setText("");
                            }
                        });

                        /////////////////////////////////////////////////////////////////////////////
                        /**
                         * The Method of Menu Buttton
                         */
                        Menu_Button(stage , btn_Menu);
                        Scene scene = new Scene(root, 500, 600);
                        stage.setTitle("Manager");
                        stage.setScene(scene);
                        stage.show();
                    }
                };
                btn_Management.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Management);
                ////////////////////////////////////////////////////////////////////////Employee
                /**
                 * Employee fields
                 * 1.Menu button
                 * 2.Login Form
                 */
                EventHandler<MouseEvent> eventHandler_Employee = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        /**
                         * button Menu
                         */
                        Button btn_Menu;
                        btn_Menu = new Button("Menu");
                        btn_Menu.setPrefSize(90, 90);
                        btn_Menu.setLayoutX(400);
                        btn_Menu.setLayoutY(500);
                        btn_Menu.setStyle(str);
                        /**
                         * Login Form
                         * password_Field
                         * UserName_Field
                         */
                        BorderPane bp = new BorderPane();
                        bp.setPadding(new Insets(10,40,50,45));

                        //Adding HBox
                        HBox hb = new HBox();
                        hb.setPadding(new Insets(20,16,20,20));

                        //Adding GridPane
                        GridPane gridPane = new GridPane();
                        gridPane.setPadding(new Insets(20,16,20,20));
                        gridPane.setHgap(5);
                        gridPane.setVgap(5);

                        //Implementing Nodes for GridPane
                        Label lblUserName = new Label("Id number");
                        final javafx.scene.control.TextField userName = new TextField();
                        Label lblPassword = new Label("Password");
                        final PasswordField pass = new PasswordField();
                        Button btnLogin = new Button("Login");
                        final Label lblMessage = new Label();

                        //Adding Nodes to GridPane layout
                        gridPane.add(lblUserName, 0, 0);
                        gridPane.add(userName, 1, 0);
                        gridPane.add(lblPassword, 0, 1);
                        gridPane.add(pass, 1, 1);
                        gridPane.add(btnLogin, 2, 1);
                        gridPane.add(lblMessage, 1, 2);

                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setOffsetX(5);
                        dropShadow.setOffsetY(5);
                        Text text_Login = new Text("Login Form");
                        text_Login.setEffect(dropShadow);
                        text_Login.setFont(Font.font ("roboto", 30));
                        text_Login.setStyle("-fx-font-weight: bold;");
                        text_Login.setFill(Color.GRAY);
                        text_Login.setStroke(Color.DARKSLATEBLUE);
                        text_Login.setStrokeWidth(3);
                        hb.getChildren().add(text_Login);
                        hb.setAlignment(Pos.CENTER);
                        btnLogin.setLayoutX(20);
                        bp.setTop(hb);
                        bp.setCenter(gridPane);
                        bp.setId("bp_Login");
                        bp.getStylesheets().add("style.css");
                        bp.setLayoutY(200);
                        userName.setId("str_login");
                        pass.setId("str_login");
                        btnLogin.setId("btn_Login");
                        btnLogin.setLayoutY(400);
                        pass.setMinWidth(350);
                        userName.setMinWidth(350);
                        btnLogin.getStylesheets().add("style.css");
                        Group root = new Group(btn_Menu , imageView , bp , btnLogin);

                        /**
                         * LOGIN
                         * Action for btnLogin
                         */
                        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                String checkUser = userName.getText().toString();
                                String checkPw = pass.getText().toString();
                                Regester regester = new Regester("Employees");

                                if (regester.check(checkUser , checkPw)) {
                                    ///Alert
                                    Alert a = new Alert(Alert.AlertType.NONE);
                                    a.setAlertType(Alert.AlertType.INFORMATION);
                                    a.setContentText("you are loged in successfuly");
                                    a.show();
                                    /**
                                     * The Method of new_page
                                     */
                                    new_page(stage , str);

                                    javafx.scene.control.Button btn_ClientManagement , btn_AccountManagement ,  btn_TransactionManagement;
                                    btn_ClientManagement = new Button("Clients Management");
                                    btn_ClientManagement.setPrefSize(310, 55);
                                    btn_ClientManagement.setLayoutX(100);
                                    btn_ClientManagement.setLayoutY(200);

                                    btn_AccountManagement = new Button("Accounts Management");
                                    btn_AccountManagement.setPrefSize(310, 55);
                                    btn_AccountManagement.setLayoutX(100);
                                    btn_AccountManagement.setLayoutY(300);

                                    btn_TransactionManagement = new Button("Transaction Management");
                                    btn_TransactionManagement.setPrefSize(310, 55);
                                    btn_TransactionManagement.setLayoutX(100);
                                    btn_TransactionManagement.setLayoutY(400);

                                    //adding css
                                    btn_ClientManagement.setId("btn");
                                    btn_AccountManagement.setId("btn");
                                    btn_TransactionManagement.setId("btn");

                                    /////////////////////////////////////////////////////////////////////////Action Employee btn_ClientManagement
                                    EventHandler<MouseEvent> eventHandler_btn_ClientManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(650);
                                            btn_Back.setLayoutY(600);
                                            btn_Back.setStyle(str);

                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<User> table = new TableView<>();
                                            table.setEditable(true);
                                            Management management = new Management("Clients");
                                            final ObservableList<User> data = FXCollections.observableArrayList(
                                                    management.getUsers()
                                            );
                                            /**
                                             * getting output button
                                             */
                                            //get output from clients
                                            TableColumn<User, User> OutputCol = new TableColumn<>("Action");
                                            OutputCol.setMinWidth(80);
                                            OutputCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            OutputCol.setCellFactory(param -> new TableCell<User, User>() {
                                                private final Button outputButton = new Button("Output");
                                                @Override
                                                protected void updateItem(User user, boolean empty) {
                                                    super.updateItem(user, empty);
                                                    if (user == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(outputButton);
                                                    //action Output button
                                                    EventHandler<MouseEvent> eventHandler_Output = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                            alert.setContentText("First Name : " + user.getFname() + "\n" +
                                                                    "Last Name : " + user.getLnmae() + "\n" +
                                                                    "Gender : " + user.getGender() + "\n" +
                                                                    "Date of Birthday : " + user.getDateOfBirth() + "\n" +
                                                                    "Date : " + user.getDateOfMembership() + "\n" +
                                                                    "Id : " + user.getIdCardNumber() + "\n" +
                                                                    "Password : " + user.getPassword() + "\n");
                                                            alert.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    outputButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Output);
                                                }
                                            });

                                            //Creating columns
                                            TableColumn firs_nameCol = new TableColumn("First Name");
                                            firs_nameCol.setMinWidth(100);
                                            firs_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("fname"));
                                            TableColumn last_nameCol = new TableColumn("Last Name");
                                            last_nameCol.setMinWidth(100);
                                            last_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("lnmae"));
                                            TableColumn genderCol = new TableColumn("Gender");
                                            genderCol.setMinWidth(100);
                                            genderCol.setCellValueFactory(new PropertyValueFactory<User , String>("gender"));
                                            TableColumn date_of_birthdayCol = new TableColumn("Date of Birthday");
                                            date_of_birthdayCol.setMinWidth(150);
                                            date_of_birthdayCol.setCellValueFactory(new PropertyValueFactory<User , String>("dateOfBirth"));
                                            TableColumn dateCol = new TableColumn("Date");
                                            dateCol.setMinWidth(100);
                                            dateCol.setCellValueFactory(new PropertyValueFactory<User , String>("dateOfMembership"));
                                            TableColumn user_nameCol = new TableColumn("Id Number");
                                            user_nameCol.setMinWidth(100);
                                            user_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("idCardNumber"));
                                            TableColumn passwordCol = new TableColumn("Password");
                                            passwordCol.setMinWidth(100);
                                            passwordCol.setCellValueFactory(new PropertyValueFactory<User , String>("password"));

                                            //Button show account
                                            /**
                                             * Show Account Button
                                             * this is a button for showing client's accounts
                                             */
                                            TableColumn<User, User> showAccountCol = new TableColumn<>("Action");
                                            showAccountCol.setMinWidth(80);
                                            showAccountCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            showAccountCol.setCellFactory(param -> new TableCell<User, User>() {
                                                private final Button showAccountButton = new Button("Show Account");
                                                @Override
                                                protected void updateItem(User user, boolean empty) {
                                                    super.updateItem(user, empty);
                                                    if (user == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(showAccountButton);

                                                    //show account button action
                                                    EventHandler<MouseEvent> eventHandler_showAccount = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            Button btn_Back = new Button();
                                                            btn_Back = new Button("Back");
                                                            btn_Back.setPrefSize(90, 90);
                                                            btn_Back.setLayoutX(600);
                                                            btn_Back.setLayoutY(500);
                                                            btn_Back.setStyle(str);
                                                            /**
                                                             * table
                                                             * this table is for showing the accounts
                                                             */
                                                            TableView<Account> table_showAccounts = new TableView<>();
                                                            final ObservableList<Account> data_showAccounts = FXCollections.observableArrayList(
                                                                    Client.showAllAccounts(user.getIdCardNumber() , "Accounts")
                                                            );
                                                            //Editable the tablet
                                                            table_showAccounts.setEditable(true);
                                                            //Creating columns
                                                            TableColumn nameCol = new TableColumn("Last Name");
                                                            nameCol.setMinWidth(100);
                                                            nameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOwnerName"));
                                                            TableColumn account_owner_nameCol = new TableColumn("Account Number");
                                                            account_owner_nameCol.setMinWidth(200);
                                                            account_owner_nameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountNumber"));
                                                            TableColumn moneyCol = new TableColumn("Money");
                                                            moneyCol.setMinWidth(100);
                                                            moneyCol.setCellValueFactory(new PropertyValueFactory<Account, String>("money"));
                                                            TableColumn User_IdCol = new TableColumn("User Id");
                                                            User_IdCol.setMinWidth(100);
                                                            User_IdCol.setCellValueFactory(new PropertyValueFactory<Account, String>("user_id"));
                                                            TableColumn accountTypeCol = new TableColumn("Account Type");
                                                            accountTypeCol.setMinWidth(200);
                                                            accountTypeCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountType"));

                                                            //Adding data to the table
                                                            table_showAccounts.setItems(data_showAccounts);
                                                            table_showAccounts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                                            table_showAccounts.getColumns().addAll(nameCol, account_owner_nameCol, moneyCol, User_IdCol, accountTypeCol);
                                                            table_showAccounts.setId("tables");
                                                            table_showAccounts.getStylesheets().add("style.css");
                                                            //Pass the data to a filtered list
                                                            FilteredList<Account> flPerson = new FilteredList(data_showAccounts, user -> true);
                                                            table_showAccounts.setItems(flPerson);
                                                            /**
                                                             * Searching
                                                             */
                                                            //Adding ChoiceBox and TextField
                                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                                            choiceBox.getItems().addAll("Account Number", "Account Type");
                                                            choiceBox.setValue("Account Number");

                                                            TextField textField = new TextField();
                                                            textField.setPromptText("Search here!");
                                                            textField.setOnKeyReleased(keyEvent ->
                                                            {
                                                                switch (choiceBox.getValue()) {
                                                                    case "Account Type":
                                                                        flPerson.setPredicate(account -> account.getAccountType().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                                        break;
                                                                    case "Account Number":
                                                                        flPerson.setPredicate(account -> account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                                        break;
                                                                }
                                                            });

                                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                                            {
                                                                if (newVal != null) {
                                                                    textField.setText("");
                                                                    flPerson.setPredicate(null);
                                                                }
                                                            });
                                                            HBox hBox = new HBox(choiceBox, textField);
                                                            choiceBox.setMinWidth(150);
                                                            textField.setMinWidth(200);
                                                            hBox.setAlignment(Pos.CENTER);
                                                            hBox.setLayoutY(20);
                                                            hBox.setLayoutX(5);
                                                            table_showAccounts.setLayoutY(60);

                                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                                            Group root = new Group(btn_Back, table_showAccounts,hBox);
                                                            Scene scene = new Scene(root, 700, 600);
                                                            stage.setTitle("Accounts");
                                                            stage.setScene(scene);
                                                            stage.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    showAccountButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_showAccount);
                                                }
                                            });
                                            ///////////////////////////////////////////////////
                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(showAccountCol,OutputCol,firs_nameCol,last_nameCol,genderCol,date_of_birthdayCol,dateCol,user_nameCol,passwordCol);
                                            //Pass the data to a filtered list
                                            FilteredList<User> flPerson = new FilteredList(data, user -> true);
                                            table.setItems(flPerson);

                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("First Name", "Last Name", "ID");
                                            choiceBox.setValue("First Name");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "First Name":
                                                        flPerson.setPredicate(user -> user.getFname().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "Last Name":
                                                        flPerson.setPredicate(user -> user.getLnmae().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "ID":
                                                        flPerson.setPredicate(user -> user.getIdCardNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {//reset table and textfield when new choice is selected
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });
                                            HBox hBox = new HBox(choiceBox, textField);
                                            hBox.setAlignment(Pos.CENTER);

                                            //////////////////////////////////////////////////////////////
                                            //Setting the size of the table
                                            table.setMaxSize(750, 500);
                                            VBox vbox = new VBox();
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(50, 50, 50, 60));
                                            vbox.setLayoutY(400);
                                            vbox.getChildren().addAll(table,hBox);

                                            Group root = new Group(btn_Back , table , vbox);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,750, 700);
                                            //add css to table
                                            table.setId("tables");
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_ClientManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_ClientManagement);

                                    ////////////////////////////////////////////////////////////////////////////////Action Employee btn_AccountManagement
                                    EventHandler<MouseEvent> eventHandler_btn_AccountManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(650);
                                            btn_Back.setLayoutY(600);
                                            btn_Back.setStyle(str);

                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<Account> table = new TableView<>();
                                            AccountManager accountManager = new AccountManager("Accounts");
                                            final ObservableList<Account> data = FXCollections.observableArrayList(
                                                    accountManager.getAccounts()
                                            );
                                            //Editable the table
                                            table.setEditable(true);
                                            /**
                                             * getting output button
                                             */
                                            //get output from clients
                                            TableColumn<Account, Account> OutputCol = new TableColumn<>("Action");
                                            OutputCol.setMinWidth(80);
                                            OutputCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            OutputCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button outputButton = new Button("Output");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(outputButton);
                                                    //action Output button
                                                    EventHandler<MouseEvent> eventHandler_Output = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                            alert.setContentText("Name : " + account.getAccountOwnerName() + "\n" +
                                                                    "Account Number : " + account.getAccountNumber() + "\n" +
                                                                    "Money : " + account.getMoney() + "\n" +
                                                                    "User Id : " + account.getUser_id() + "\n" +
                                                                    "Account Type : " + account.getAccountType() + "\n");
                                                            alert.show();
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    outputButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Output);
                                                }
                                            });

                                            //Creating columns
                                            TableColumn nameCol = new TableColumn("Last Name");
                                            nameCol.setMinWidth(100);
                                            nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("accountOwnerName"));
                                            TableColumn account_owner_nameCol = new TableColumn("Account Number");
                                            account_owner_nameCol.setMinWidth(200);
                                            account_owner_nameCol.setCellValueFactory(new PropertyValueFactory<User , String>("accountNumber"));
                                            TableColumn moneyCol = new TableColumn("Money");
                                            moneyCol.setMinWidth(100);
                                            moneyCol.setCellValueFactory(new PropertyValueFactory<User , String>("money"));
                                            TableColumn User_IdCol = new TableColumn("User Id");
                                            User_IdCol.setMinWidth(100);
                                            User_IdCol.setCellValueFactory(new PropertyValueFactory<User , String>("user_id"));
                                            TableColumn accountTypeCol = new TableColumn("Account Type");
                                            accountTypeCol.setMinWidth(200);
                                            accountTypeCol.setCellValueFactory(new PropertyValueFactory<User , String>("accountType"));

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(OutputCol,nameCol,account_owner_nameCol,moneyCol,User_IdCol,accountTypeCol);

                                            //Pass the data to a filtered list
                                            FilteredList<Account> flPerson = new FilteredList(data, account -> true);
                                            table.setItems(flPerson);

                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll("Last Name", "Account Number" , "User Id");
                                            choiceBox.setValue("Last Name");
                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "Last Name":
                                                        flPerson.setPredicate(account -> account.getAccountOwnerName().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "Account Number":
                                                        flPerson.setPredicate(account ->account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "User Id":
                                                        flPerson.setPredicate(account ->account.getUser_id().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {//reset table and textfield when new choice is selected
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });
                                            HBox hBox = new HBox(choiceBox,textField);
                                            hBox.setAlignment(Pos.CENTER);

                                            //////////////////////////////////////////////////////////////

                                            //Setting the size of the table
                                            table.setMaxSize(750, 500);
                                            VBox vbox = new VBox();
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(50, 50, 50, 60));
                                            vbox.setLayoutY(400);
                                            vbox.getChildren().addAll(table,hBox);

                                            Group root = new Group(btn_Back,vbox,table);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,750, 700);
                                            //add css to table
                                            table.setId("tables");
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();
                                        }
                                    };
                                    btn_AccountManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_AccountManagement);
                                    ///////////////////////////////////////////
                                    Group root = new Group(btn_Menu,imageView,btn_AccountManagement,btn_ClientManagement,btn_TransactionManagement);
                                    btn_Back.setOnAction(e -> stage.setScene(scene));
                                    Menu_Button(stage , btn_Menu);
                                    scene_back = new Scene(root,500, 600);
                                    scene_back.getStylesheets().add("style.css");
                                    stage.setTitle("Farabi Bank");
                                    stage.setScene(scene_back);
                                    stage.show();

                                    /////////////////////////////////////////////////////////////////////////////////////////////Action Employee btn_TransactionManagement
                                    EventHandler<MouseEvent> eventHandler_TransactionManagement = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {

                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(400);
                                            btn_Back.setLayoutY(500);
                                            btn_Back.setStyle(str);
                                            Menu_Button(stage , btn_Menu);
                                            javafx.scene.control.Button btn_TransferMoney , btn_DepositMoney;
                                            btn_TransferMoney = new Button("Money Transfer");
                                            btn_TransferMoney.setPrefSize(310, 55);
                                            btn_TransferMoney.setLayoutX(100);
                                            btn_TransferMoney.setLayoutY(250);
                                            btn_DepositMoney = new Button("Deposit Money");
                                            btn_DepositMoney.setPrefSize(310, 55);
                                            btn_DepositMoney.setLayoutX(100);
                                            btn_DepositMoney.setLayoutY(350);
                                            //adding css
                                            btn_TransferMoney.setId("btn");
                                            btn_DepositMoney.setId("btn");

                                            ///////////////////////////////////////////////////////////////////////////////////////////////////btn_Transfer
                                            EventHandler<MouseEvent> eventHandler_Transfer = new EventHandler<MouseEvent>() {
                                                @Override
                                                public void handle(MouseEvent e) {
                                                    /**
                                                     * The Method of new_page2
                                                     */
                                                    new_page2(stage, str);
                                                    Button btn_Back;
                                                    /**
                                                     * button Menu
                                                     */
                                                    btn_Back = new Button("Back");
                                                    btn_Back.setPrefSize(90, 90);
                                                    btn_Back.setLayoutX(400);
                                                    btn_Back.setLayoutY(500);
                                                    btn_Back.setStyle(str);
                                                    /**
                                                     * Transfer Money
                                                     * password_Field
                                                     * UserName_Field
                                                     */
                                                    BorderPane bp = new BorderPane();
                                                    bp.setPadding(new Insets(10,50,50,50));

                                                    //Adding HBox
                                                    HBox hb = new HBox();
                                                    hb.setPadding(new Insets(20,20,20,30));

                                                    //Adding GridPane
                                                    GridPane gridPane = new GridPane();
                                                    gridPane.setPadding(new Insets(20,40,70,20));
                                                    gridPane.setHgap(5);
                                                    gridPane.setVgap(5);

                                                    final javafx.scene.control.TextField accountNumber = new TextField();
                                                    accountNumber.setPromptText("Enter Account Number");
                                                    final javafx.scene.control.TextField distination_accountNumber = new TextField();
                                                    distination_accountNumber.setPromptText("Enter Destination Account Number");
                                                    Button btnLogin = new Button("Submit");
                                                    /**
                                                     * handle the InputMismatchException error
                                                     * Exception for getting the Money
                                                     */
                                                    UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                        @Override
                                                        public TextFormatter.Change apply(TextFormatter.Change t) {
                                                            if (t.isReplaced())
                                                                if(t.getText().matches("[^0-9]"))
                                                                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                            if (t.isAdded()) {
                                                                if (t.getControlText().contains(".")) {
                                                                    if (t.getText().matches("[^0-9]")) {
                                                                        t.setText("");
                                                                    }
                                                                } else if (t.getText().matches("[^0-9.]")) {
                                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                    alert.setContentText("Enter Number please!!");
                                                                    alert.show();
                                                                    t.setText(" ");
                                                                }
                                                            }
                                                            return t;
                                                        }
                                                    };

                                                    final javafx.scene.control.TextField Money = new TextField();
                                                    Money.setTextFormatter(new TextFormatter<>(filter));
                                                    Money.setPromptText("Enter Money");

                                                    //Adding Nodes to GridPane layout
                                                    gridPane.add(accountNumber, 1, 0);
                                                    gridPane.add(distination_accountNumber, 1, 2);
                                                    gridPane.add(Money, 1, 4);
                                                    gridPane.add(btnLogin, 1, 6);

                                                    DropShadow dropShadow = new DropShadow();
                                                    dropShadow.setOffsetX(5);
                                                    dropShadow.setOffsetY(5);
                                                    Text text_Login = new Text("Money Transfer");
                                                    text_Login.setEffect(dropShadow);
                                                    text_Login.setFont(Font.font ("roboto", 30));
                                                    text_Login.setStyle("-fx-font-weight: bold;");
                                                    text_Login.setFill(Color.GRAY);
                                                    text_Login.setStroke(Color.DARKSLATEBLUE);
                                                    text_Login.setStrokeWidth(3);
                                                    hb.getChildren().add(text_Login);
                                                    hb.setAlignment(Pos.CENTER);
                                                    btnLogin.setLayoutX(30);
                                                    bp.setTop(hb);
                                                    bp.setCenter(gridPane);
                                                    bp.setId("bp_Login");
                                                    bp.getStylesheets().add("style.css");
                                                    bp.setLayoutY(150);
                                                    accountNumber.setId("str_login");
                                                    distination_accountNumber.setId("str_login");
                                                    Money.setId("str_login");
                                                    btnLogin.setId("btn_Login");
                                                    btnLogin.setLayoutY(420);
                                                    accountNumber.setMinWidth(420);
                                                    distination_accountNumber.setMinWidth(420);
                                                    Money.setMinWidth(420);
                                                    btnLogin.setMinWidth(420);
                                                    btnLogin.getStylesheets().add("style.css");
                                                    /**
                                                     * Action btn_submit
                                                     */
                                                    btnLogin.setOnAction(ex ->{
                                                        try {
                                                            if (accountNumber.getText().isEmpty()) {
                                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                alert.setContentText("Fill out all fields please");
                                                                alert.show();
                                                                return;
                                                            }
                                                            if (distination_accountNumber.getText().isEmpty()) {
                                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                alert.setContentText("Fill out all fields please");
                                                                alert.show();
                                                                return;
                                                            }
                                                            if (Money.getText().isEmpty()) {
                                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                alert.setContentText("Fill out all fields please");
                                                                alert.show();
                                                                return;
                                                            }
                                                            Transaction transaction = new Transaction(accountNumber.getText(),Account.showInvestory(accountNumber.getText()),"",userName.toString(),"",distination_accountNumber.getText(),"withdraw");
                                                            if(Account.transfer_money(accountNumber.getText(), distination_accountNumber.getText(), Long.parseLong(Money.getText()))){
                                                                if (Long.parseLong(Money.getText()) <= Account.showInvestory(accountNumber.getText())){
                                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                    alert.setContentText(transaction.toString());
                                                                    alert.show();
                                                                    accountNumber.clear();
                                                                    distination_accountNumber.clear();
                                                                    Money.clear();
                                                                }
                                                                else {
                                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                    alert.setContentText("Inventory is not enough");
                                                                    alert.show();
                                                                    accountNumber.clear();
                                                                    distination_accountNumber.clear();
                                                                    Money.clear();
                                                                }

                                                            }
                                                            else {
                                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                alert.setContentText("you are not allow to transfer money");
                                                                alert.show();
                                                                accountNumber.clear();
                                                                distination_accountNumber.clear();
                                                                Money.clear();
                                                            }

                                                        }catch (NullPointerException | NumberFormatException e1){
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("There is no such account number");
                                                            alert.show();
                                                            accountNumber.clear();
                                                            distination_accountNumber.clear();
                                                            Money.clear();
                                                        }
                                                    });
                                                    Group root = new Group(btn_Back , bp , btnLogin);
                                                    Menu_Button(stage , btn_Menu);
                                                    btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                                    Scene scene = new Scene(root, 500, 600);
                                                    stage.setTitle("Employee");
                                                    stage.setScene(scene);
                                                    stage.show();

                                                }
                                            };
                                            btn_TransferMoney.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Transfer );

                                            ///////////////////////////////////////////////////////////////////////////////////////////////////btn_Deposit Money
                                            EventHandler<MouseEvent> eventHandler_Deposit = new EventHandler<MouseEvent>() {
                                                @Override
                                                public void handle(MouseEvent e) {
                                                    /**
                                                     * The Method of new_page2
                                                     */
                                                    new_page2(stage, str);
                                                    Button btn_Back;
                                                    /**
                                                     * button Menu
                                                     */
                                                    btn_Back = new Button("Back");
                                                    btn_Back.setPrefSize(90, 90);
                                                    btn_Back.setLayoutX(400);
                                                    btn_Back.setLayoutY(500);
                                                    btn_Back.setStyle(str);
                                                    /**
                                                     * Transfer Money
                                                     * password_Field
                                                     * UserName_Field
                                                     */
                                                    BorderPane bp = new BorderPane();
                                                    bp.setPadding(new Insets(10,50,50,50));

                                                    //Adding HBox
                                                    HBox hb = new HBox();
                                                    hb.setPadding(new Insets(20,20,20,30));

                                                    //Adding GridPane
                                                    GridPane gridPane = new GridPane();
                                                    gridPane.setPadding(new Insets(20,40,70,20));
                                                    gridPane.setHgap(5);
                                                    gridPane.setVgap(5);

                                                    final javafx.scene.control.TextField accountNumber = new TextField();
                                                    accountNumber.setPromptText("Enter Account Number");
                                                    Button btnLogin = new Button("Submit");
                                                    /**
                                                     * handle the InputMismatchException error
                                                     * Exception for getting the Money
                                                     */
                                                    UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                        @Override
                                                        public TextFormatter.Change apply(TextFormatter.Change t) {
                                                            if (t.isReplaced())
                                                                if(t.getText().matches("[^0-9]"))
                                                                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                            if (t.isAdded()) {
                                                                if (t.getControlText().contains(".")) {
                                                                    if (t.getText().matches("[^0-9]")) {
                                                                        t.setText("");
                                                                    }
                                                                } else if (t.getText().matches("[^0-9.]")) {
                                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                    alert.setContentText("Enter Number please!!");
                                                                    alert.show();
                                                                    t.setText(" ");
                                                                }
                                                            }
                                                            return t;
                                                        }
                                                    };
                                                    final javafx.scene.control.TextField Money = new TextField();
                                                    Money.setTextFormatter(new TextFormatter<>(filter));
                                                    Money.setPromptText("Enter Money");
                                                    //Adding Nodes to GridPane layout
                                                    gridPane.add(accountNumber, 1, 0);
                                                    gridPane.add(Money, 1, 2);
                                                    gridPane.add(btnLogin, 1, 4);

                                                    DropShadow dropShadow = new DropShadow();
                                                    dropShadow.setOffsetX(5);
                                                    dropShadow.setOffsetY(5);
                                                    Text text_Login = new Text("Deposit Money");
                                                    text_Login.setEffect(dropShadow);
                                                    text_Login.setFont(Font.font ("roboto", 30));
                                                    text_Login.setStyle("-fx-font-weight: bold;");
                                                    text_Login.setFill(Color.GRAY);
                                                    text_Login.setStroke(Color.DARKSLATEBLUE);
                                                    text_Login.setStrokeWidth(3);
                                                    hb.getChildren().add(text_Login);
                                                    hb.setAlignment(Pos.CENTER);
                                                    btnLogin.setLayoutX(30);
                                                    bp.setTop(hb);
                                                    bp.setCenter(gridPane);
                                                    bp.setId("bp_Login");
                                                    bp.getStylesheets().add("style.css");
                                                    bp.setLayoutY(200);
                                                    accountNumber.setId("str_login");
                                                    Money.setId("str_login");
                                                    btnLogin.setId("btn_Login");
                                                    btnLogin.setLayoutY(420);
                                                    accountNumber.setMinWidth(420);
                                                    Money.setMinWidth(420);
                                                    btnLogin.setMinWidth(420);
                                                    btnLogin.getStylesheets().add("style.css");
                                                    /**
                                                     * Action btn_submit
                                                     */
                                                    btnLogin.setOnAction(ex ->{
                                                        try {
                                                            if (accountNumber.getText().isEmpty()) {
                                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                alert.setContentText("Fill out all fields please");
                                                                alert.show();
                                                                return;
                                                            }
                                                            if (Money.getText().isEmpty()) {
                                                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                alert.setContentText("Fill out all fields please");
                                                                alert.show();
                                                                return;
                                                            }
                                                            if (Account.singleTransaction(Long.parseLong(Money.getText()) , accountNumber.getText() , "deposit")){
                                                                Transaction transaction = new Transaction(accountNumber.getText(),Account.showInvestory(accountNumber.getText()),"",userName.toString(),"","","deposit");
                                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                alert.setContentText(transaction.toString());
                                                                alert.show();
                                                                accountNumber.clear();
                                                                Money.clear();
                                                            }
                                                            else {
                                                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                                alert.setContentText("you are not allow to withdraw money");
                                                                alert.show();
                                                                accountNumber.clear();
                                                                Money.clear();
                                                            }

                                                        }catch (NullPointerException | NumberFormatException | ClassCastException e1){
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("There is no such account number");
                                                            alert.show();
                                                            accountNumber.clear();
                                                            Money.clear();
                                                        }
                                                    });
                                                    Group root = new Group(btn_Back , bp , btnLogin , imageView);
                                                    Menu_Button(stage , btn_Menu);
                                                    btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                                    Scene scene = new Scene(root, 500, 600);
                                                    stage.setTitle("Employee");
                                                    stage.setScene(scene);
                                                    stage.show();

                                                }
                                            };
                                            btn_DepositMoney.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Deposit );
                                            ///////////////////////////
                                            /**
                                             * action back button
                                             */
                                            Group root = new Group(btn_Back , btn_TransferMoney , btn_DepositMoney , imageView);
                                            Menu_Button(stage , btn_Menu);
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,500, 600);
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();
                                        }
                                    };
                                    //Registering the event filter
                                    btn_TransactionManagement.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_TransactionManagement);

                                } else {
                                    lblMessage.setText("Incorrect user or password.");
                                    lblMessage.setTextFill(Color.RED);
                                }
                                userName.setText("");
                                pass.setText("");
                            }
                        });

                        /**
                         * The method of Menu Button
                         */
                        Menu_Button(stage , btn_Menu);
                        Scene scene = new Scene(root, 500, 600);
                        stage.setTitle("Employee");
                        stage.setScene(scene);
                        stage.show();

                    }
                };
                btn_Employee.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Employee);

                ////////////////////////////////////////////////////////////////////////////////////Client
                /**
                 * Client fields
                 * 1.Menu button
                 * 2.SignUp Form
                 * 3.Login Form
                 */
                EventHandler<MouseEvent> eventHandler_Client = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        Button btn_Menu;
                        btn_Menu = new Button("Menu");
                        btn_Menu.setPrefSize(90, 90);
                        btn_Menu.setLayoutX(400);
                        btn_Menu.setLayoutY(500);
                        btn_Menu.setStyle(str);
                        ////////////////////////////
                        Button btn_signUp;
                        btn_signUp = new Button("signUp");
                        btn_signUp.setPrefSize(90, 90);
                        btn_signUp.setLayoutX(5);
                        btn_signUp.setLayoutY(500);
                        btn_signUp.setStyle(str);
                        /**
                         * Login Form
                         * password_Field
                         * UserName_Field
                         */
                        BorderPane bp = new BorderPane();
                        bp.setPadding(new Insets(10, 50, 50, 50));

                        //Adding HBox
                        HBox hb = new HBox();
                        hb.setPadding(new Insets(20, 16, 20, 30));

                        //Adding GridPane
                        GridPane gridPane = new GridPane();
                        gridPane.setPadding(new Insets(20, 16, 20, 20));
                        gridPane.setHgap(5);
                        gridPane.setVgap(5);

                        //Implementing Nodes for GridPane
                        Label lblUserName = new Label("Id number");
                        final javafx.scene.control.TextField userName = new TextField();
                        Label lblPassword = new Label("Password");
                        final PasswordField pass = new PasswordField();
                        Button btnLogin = new Button("Login");
                        final Label lblMessage = new Label();

                        //Adding Nodes to GridPane layout
                        gridPane.add(lblUserName, 0, 0);
                        gridPane.add(userName, 1, 0);
                        gridPane.add(lblPassword, 0, 1);
                        gridPane.add(pass, 1, 1);
                        gridPane.add(btnLogin, 2, 1);
                        gridPane.add(lblMessage, 1, 2);

                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setOffsetX(5);
                        dropShadow.setOffsetY(5);
                        Text text_Login = new Text("Login Form");
                        text_Login.setEffect(dropShadow);
                        text_Login.setFont(Font.font("roboto", 30));
                        text_Login.setStyle("-fx-font-weight: bold;");
                        text_Login.setFill(Color.GRAY);
                        text_Login.setStroke(Color.DARKSLATEBLUE);
                        text_Login.setStrokeWidth(3);
                        hb.getChildren().add(text_Login);
                        hb.setAlignment(Pos.CENTER);
                        btnLogin.setLayoutX(20);

                        bp.setTop(hb);
                        bp.setCenter(gridPane);
                        bp.setId("bp_Login");
                        bp.getStylesheets().add("style.css");
                        bp.setLayoutY(200);
                        userName.setId("str_login");
                        pass.setId("str_login");
                        btnLogin.setId("btn_Login");
                        btnLogin.setLayoutY(400);
                        pass.setMinWidth(350);
                        userName.setMinWidth(350);
                        btnLogin.getStylesheets().add("style.css");
                        Group root = new Group(btn_Menu, imageView , bp, btnLogin , btn_signUp);
                        Scene scene = new Scene(root, 500, 600);
                        btn_Menu.setOnAction(exx -> {
                            try {
                                start(stage);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } catch (InvalidClassException ex) {
                                ex.printStackTrace();
                            }
                        });
                        stage.setTitle("Client");
                        stage.setScene(scene);
                        stage.show();
                        /**
                         * Action
                         * Sign Up Button
                         */
                        EventHandler<MouseEvent> eventHandler_SignUp = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                /// Menu Button
                                Button btn_Menu;
                                btn_Menu = new Button("Menu");
                                btn_Menu.setPrefSize(90, 90);
                                btn_Menu.setLayoutX(400);
                                btn_Menu.setLayoutY(500);
                                btn_Menu.setStyle(str);
                                /// Next Button
                                Button btn_Back;
                                btn_Back = new Button("Back");
                                btn_Back.setPrefSize(90, 90);
                                btn_Back.setLayoutX(5);
                                btn_Back.setLayoutY(500);
                                btn_Back.setStyle(str);

                                BorderPane bp = new BorderPane();
                                bp.setPadding(new Insets(10, 50, 50, 50));

                                //Adding HBox
                                HBox hb = new HBox();
                                hb.setPadding(new Insets(20, 20, 20, 30));

                                //Adding GridPane
                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(20, 20, 20, 20));
                                gridPane.setHgap(5);
                                gridPane.setVgap(5);

                                //Implementing Nodes for GridPane
                                Label lblFirstName = new Label("Name");
                                Label lblLastName = new Label("LastName");
                                Label lblBirthday = new Label("Birthday");
                                Label lbldateOfMembership = new Label("Date");
                                Label lblUserName = new Label("Id number");
                                Label lblPassword = new Label("Password");

                                final javafx.scene.control.TextField firstName = new TextField();
                                final javafx.scene.control.TextField lastName = new TextField();
                                final DatePicker datePicker_dateOfBirth = new DatePicker();
                                final DatePicker datePicker_dateOfMembership = new DatePicker();
                                final javafx.scene.control.TextField userName = new TextField();
                                final PasswordField pass = new PasswordField();
                                final Label lblMessage = new Label();
                                ToggleGroup Gender = new ToggleGroup();
                                final RadioButton male = new RadioButton("A) Male");
                                male.setToggleGroup(Gender);
                                final RadioButton female = new RadioButton("B) Female");
                                female.setToggleGroup(Gender);
                                final Button btnRegister = new Button("Register");

                                //Adding Nodes to GridPane layout
                                gridPane.add(lblFirstName, 0, 0);
                                gridPane.add(firstName, 1, 0);
                                gridPane.add(lblLastName, 0, 1);
                                gridPane.add(lastName, 1, 1);
                                gridPane.add(lblBirthday, 0, 2);
                                gridPane.add(datePicker_dateOfBirth, 1, 2);
                                gridPane.add(lbldateOfMembership, 0, 3);
                                gridPane.add(datePicker_dateOfMembership, 1, 3);
                                gridPane.add(lblUserName, 0, 4);
                                gridPane.add(userName, 1, 4);
                                gridPane.add(lblPassword, 0, 5);
                                gridPane.add(pass, 1, 5);
                                gridPane.add(male, 0, 6);
                                gridPane.add(female, 1, 6);
                                gridPane.add(btnRegister, 0, 7);
                                gridPane.add(lblMessage, 1, 7);

                                firstName.setId("str_login");
                                lastName.setId("str_login");
                                datePicker_dateOfBirth.setId("date_Picker");
                                datePicker_dateOfBirth.setShowWeekNumbers(true);
                                datePicker_dateOfBirth.getStylesheets().add("style.css");
                                datePicker_dateOfMembership.setId("date_Picker");
                                datePicker_dateOfMembership.setShowWeekNumbers(true);
                                datePicker_dateOfMembership.getStylesheets().add("style.css");
                                DropShadow dropShadow = new DropShadow();
                                dropShadow.setOffsetX(5);
                                dropShadow.setOffsetY(5);
                                Text text_Login = new Text("Registeration Form");
                                text_Login.setEffect(dropShadow);
                                text_Login.setFont(Font.font("roboto", 30));
                                text_Login.setStyle("-fx-font-weight: bold;");
                                text_Login.setFill(Color.GRAY);
                                text_Login.setStroke(Color.DARKSLATEBLUE);
                                text_Login.setStrokeWidth(3);
                                hb.getChildren().add(text_Login);
                                hb.setAlignment(Pos.CENTER);
                                btnRegister.setLayoutX(20);

                                bp.setTop(hb);
                                bp.setCenter(gridPane);
                                bp.setId("bp_Login");
                                bp.getStylesheets().add("style.css");
                                userName.setId("str_login");
                                pass.setId("str_login");
                                btnRegister.setId("btn_Login");
                                btnRegister.setLayoutY(450);
                                pass.setMinWidth(300);
                                userName.setMinWidth(300);
                                btnRegister.getStylesheets().add("style.css");

                                Group root = new Group(btn_Menu, bp, btnRegister, btn_Back);
                                Scene scene2 = new Scene(root, 500, 600);
                                stage.setTitle("Client");
                                stage.setScene(scene2);
                                stage.show();
                                /**
                                 * The method of Menu Button
                                 */
                                Menu_Button(stage, btn_Menu);
                                /**
                                 * Action
                                 * Back Button
                                 */
                                btn_Back.setOnAction(ex -> stage.setScene(scene));
                                /**
                                 * Registration Button
                                 */
                                EventHandler<MouseEvent> eventHandler_Registration = new EventHandler<MouseEvent>() {
                                    /**
                                     * Registration Button
                                     * @param e
                                     */
                                    @Override
                                    public void handle(MouseEvent e) {
                                        try {
                                            /**
                                             * Registration Form
                                             * get informations from frontEnd
                                             * Send the informations to file
                                             */
                                            String fname = firstName.getText();
                                            String lname = lastName.getText();
                                            LocalDate dateOfBirth = datePicker_dateOfBirth.getValue();
                                            LocalDate dateOfMembership = datePicker_dateOfMembership.getValue();
                                            String idCardNumber = userName.getText();
                                            String password = pass.getText();
                                            String gender = "";
                                            if (male.isSelected()) {
                                                gender = "male";
                                            } else if (female.isSelected()) {
                                                gender = "female";
                                            }
                                            if (firstName.getText().isEmpty()) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (lastName.getText().isEmpty()) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (datePicker_dateOfBirth.getValue().toString().equals(" ")) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (datePicker_dateOfMembership.getValue().toString().equals(" ")) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (userName.getText().isEmpty()) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (pass.getText().isEmpty()) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            if (gender.equals("")) {
                                                Alert a = new Alert(Alert.AlertType.NONE);
                                                a.setAlertType(Alert.AlertType.WARNING);
                                                a.setContentText("Fill out all the fields please");
                                                a.show();
                                                return;
                                            }
                                            User user = new User(fname, lname, gender, dateOfBirth.toString(), dateOfMembership.toString(), idCardNumber, password);
                                            IOFiles.WriteToFile(user, "Clients");
                                            Regester regester = new Regester("Clients");
                                            regester.check(idCardNumber, password);
                                            regester.signUp(user);
                                            regester.logIn(idCardNumber, password);
                                            /////////////////////////////////////////////
                                            ///Alert
                                            Alert a = new Alert(Alert.AlertType.NONE);
                                            a.setAlertType(Alert.AlertType.INFORMATION);
                                            a.setContentText("The information registered successfully");
                                            a.show();
                                            firstName.clear();
                                            lastName.clear();
                                            male.setToggleGroup(Gender);
                                            female.setToggleGroup(Gender);
                                            datePicker_dateOfBirth.setValue(null);
                                            datePicker_dateOfMembership.setValue(null);
                                            userName.clear();
                                            pass.clear();

                                        } catch (Exception NullPointerException) {
                                            Alert a = new Alert(Alert.AlertType.NONE);
                                            a.setAlertType(Alert.AlertType.ERROR);
                                            a.setContentText("An error occurred \n restart the app please");
                                            a.show();
                                        }

                                        /**
                                         * The Method of Menu Button
                                         */
                                        Menu_Button(stage, btn_Menu);
                                    }
                                };
                                btnRegister.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Registration);
                            }
                        };
                        btn_signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_SignUp);

                        //////////////////////////////////////////////////////////
                        /**
                         * Action for btnLogin
                         */
                        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
                            Regester  regester = new Regester("Clients");
                            public void handle(ActionEvent event) {
                                String checkUser = userName.getText().toString();
                                String checkPw = pass.getText().toString();
                                if (regester.check(checkUser , checkPw)) {
                                    ///Alert
                                    Alert a = new Alert(Alert.AlertType.NONE);
                                    a.setAlertType(Alert.AlertType.INFORMATION);
                                    a.setContentText("you are loged in successfuly");
                                    a.show();

                                    /**
                                     * The Method of new_page
                                     */
                                    Button btn_Menu_client = new Button();
                                    btn_Menu_client = new Button("Menu");
                                    btn_Menu_client.setPrefSize(90, 90);
                                    btn_Menu_client.setLayoutX(400);
                                    btn_Menu_client.setLayoutY(500);
                                    btn_Menu_client.setStyle(str);
                                    /**
                                     * handle btn_Menu_client
                                     */
                                    btn_Menu_client.setOnAction(ex -> {
                                        try {
                                            start(stage);
                                        } catch (FileNotFoundException exc) {
                                            exc.printStackTrace();
                                        } catch (InvalidClassException exc) {
                                            exc.printStackTrace();
                                        }
                                    });

                                    Text text_Welcome = new Text();
                                    text_Welcome.setText("Welcome to Farabi Bank");
                                    text_Welcome.setEffect(dropShadow);
                                    text_Welcome.setFont(Font.font ("roboto", 30));
                                    text_Welcome.setStyle("-fx-font-weight: bold;");
                                    text_Welcome.setFill(Color.GRAY);
                                    text_Welcome.setStroke(Color.DARKSLATEBLUE);
                                    text_Welcome.setStrokeWidth(3);
                                    text_Welcome.setTextAlignment(CENTER);
                                    text_Welcome.setY(100);
                                    text_Welcome.setX(100);
                                    //Buttons
                                    javafx.scene.control.Button btn_ViewAccoount ,btn_withdraw,btn_Money_transfer;
                                    btn_ViewAccoount = new Button("View Accounts");
                                    btn_ViewAccoount.setPrefSize(310, 55);
                                    btn_ViewAccoount.setLayoutX(100);
                                    btn_ViewAccoount.setLayoutY(200);

                                    btn_withdraw = new Button("Withdraw Money");
                                    btn_withdraw.setPrefSize(310, 55);
                                    btn_withdraw.setLayoutX(100);
                                    btn_withdraw.setLayoutY(300);

                                    btn_Money_transfer = new Button("Money Transfer");
                                    btn_Money_transfer.setPrefSize(310, 55);
                                    btn_Money_transfer.setLayoutX(100);
                                    btn_Money_transfer.setLayoutY(400);

                                    //adding css
                                    btn_ViewAccoount.setId("btn");
                                    btn_withdraw.setId("btn");
                                    btn_Money_transfer.setId("btn");

                                    ////////////////////////////////////////////////////////////////////////////////Action btn_ViewAccoount
                                    EventHandler<MouseEvent> eventHandler_btn_ViewAccoount = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page2
                                             */
                                            Button btn_Back = new Button();
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(800);
                                            btn_Back.setLayoutY(600);
                                            btn_Back.setStyle(str);
                                            /**
                                             * Table for DataBase
                                             */
                                            TableView<Account> table = new TableView<>();
                                            final ObservableList<Account> data = FXCollections.observableArrayList(
                                                    Client.showAllAccounts(checkUser , "Accounts")
                                            );
                                            //Editable the table
                                            table.setEditable(true);

                                            //Delete the Account
                                            TableColumn<Account, Account> DeleteCol = new TableColumn<>("Action");
                                            DeleteCol.setMinWidth(80);
                                            DeleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            DeleteCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button deleteButton = new Button("Delete");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(deleteButton);
                                                    AccountManager accountManager = new AccountManager("Accounts");
                                                    //delete button action
                                                    EventHandler<MouseEvent> eventHandler_Delete = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            data.remove(account);
                                                            accountManager.delete(account.getAccountNumber());
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Delete);
                                                }
                                            });

                                            //Transaction Account
                                            TableColumn<Account, Account> TransactionCol = new TableColumn<>("Action");
                                            TransactionCol.setMinWidth(80);
                                            TransactionCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            TransactionCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button transactionButton = new Button("Transaction");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(transactionButton);
                                                    //transaction button action
                                                    EventHandler<MouseEvent> eventHandler_Transaction = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            /**
                                                             * Methon showTransactionHandel
                                                             */
                                                            showTransactionHandel(stage,str,account);
                                                        }
                                                    };
                                                    //Registering the event filter
                                                    transactionButton.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler_Transaction);
                                                }
                                            });

                                            //Time transfer
                                            TableColumn<Account, Account> TimetransferCol = new TableColumn<>("Action");
                                            TimetransferCol.setMinWidth(80);
                                            TimetransferCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                                            TimetransferCol.setCellFactory(param -> new TableCell<Account, Account>() {
                                                private final Button TimeTransferButton = new Button("Time Transfer");
                                                @Override
                                                protected void updateItem(Account account, boolean empty) {
                                                    super.updateItem(account, empty);
                                                    if (account == null) {
                                                        setGraphic(null);
                                                        return;
                                                    }
                                                    setGraphic(TimeTransferButton);
                                                    //delete button action
                                                    EventHandler<MouseEvent> eventHandler_TimeTransfer = new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent e) {
                                                            Button btn_Back = new Button();
                                                            btn_Back = new Button("Back");
                                                            btn_Back.setPrefSize(90, 90);
                                                            btn_Back.setLayoutX(600);
                                                            btn_Back.setLayoutY(500);
                                                            btn_Back.setStyle(str);
                                                            /**
                                                             * table
                                                             * this table is for showing the transactions
                                                             */
                                                            TableView<PeriodicTransaction> table_TimeTransfer = new TableView<>();
                                                            final ObservableList<PeriodicTransaction> data_TimeTransfer =  FXCollections.observableArrayList(
                                                                   PeriodicTransaction.showPT(account.getAccountNumber())
                                                            );
                                                            //Editable the tablet
                                                            table_TimeTransfer.setEditable(true);
                                                            //Creating columns
                                                            TableColumn AccountNumCol = new TableColumn("Account ID");
                                                            AccountNumCol.setMinWidth(200);
                                                            AccountNumCol.setCellValueFactory(new PropertyValueFactory<PeriodicTransaction, String>("fromAccount"));
                                                            TableColumn moneyCol = new TableColumn("money");
                                                            moneyCol.setMinWidth(100);
                                                            moneyCol.setCellValueFactory(new PropertyValueFactory<PeriodicTransaction, String>("money"));
                                                            TableColumn toIdCol = new TableColumn("To Id");
                                                            toIdCol.setMinWidth(200);
                                                            toIdCol.setCellValueFactory(new PropertyValueFactory<PeriodicTransaction, String>("toAccountNumber"));

                                                            //Adding data to the table
                                                            table_TimeTransfer.setItems(data_TimeTransfer);
                                                            table_TimeTransfer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                                            table_TimeTransfer.getColumns().addAll(AccountNumCol,moneyCol,toIdCol);
                                                            table_TimeTransfer.setId("tables");
                                                            table_TimeTransfer.setPrefSize(700 , 300);
                                                            table_TimeTransfer.getStylesheets().add("style.css");
                                                            //Pass the data to a filtered list
                                                            FilteredList<PeriodicTransaction> flPerson = new FilteredList(data_TimeTransfer, user -> true);
                                                            table_TimeTransfer.setItems(flPerson);
                                                            /**
                                                             * Searching
                                                             */
                                                            //Adding ChoiceBox and TextField
                                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                                            choiceBox.getItems().addAll("Account Number");
                                                            choiceBox.setValue("Account Number");

                                                            TextField textField = new TextField();
                                                            textField.setPromptText("Search here!");
                                                            textField.setOnKeyReleased(keyEvent ->
                                                            {
                                                                switch (choiceBox.getValue()) {
                                                                    case "Account Number":
                                                                        flPerson.setPredicate( periodicTransaction -> periodicTransaction.getToAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));
                                                                        break;
                                                                }
                                                            });

                                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                                            {
                                                                if (newVal != null) {
                                                                    textField.setText("");
                                                                    flPerson.setPredicate(null);
                                                                }
                                                            });
                                                            HBox hBox = new HBox(choiceBox, textField);
                                                            choiceBox.setMinWidth(150);
                                                            textField.setMinWidth(200);
                                                            hBox.setAlignment(Pos.CENTER);
                                                            hBox.setLayoutY(20);
                                                            hBox.setLayoutX(5);
                                                            table_TimeTransfer.setLayoutY(60);
                                                            //add button

                                                            ChoiceBox addMyAccounts = new ChoiceBox(FXCollections.observableArrayList(account.getAccountNumber()));
                                                            addMyAccounts.setValue("Account Number");
                                                            addMyAccounts.setMaxWidth(400);
                                                            final TextField addToAccount = new TextField();
                                                            addToAccount.setMaxWidth(400);
                                                            addToAccount.setPromptText("To Account");
                                                            final Button addButton = new Button("Add");
                                                            addButton.setMaxWidth(400);
                                                            /**
                                                             * handle the InputMismatchException error
                                                             * Exception for getting the Money
                                                             */
                                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                                @Override
                                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                                    if (t.isReplaced())
                                                                        if(t.getText().matches("[^0-9]"))
                                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                                    if (t.isAdded()) {
                                                                        if (t.getControlText().contains(".")) {
                                                                            if (t.getText().matches("[^0-9]")) {
                                                                                t.setText("");
                                                                            }
                                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                            alert.setContentText("Enter Number please!!");
                                                                            alert.show();
                                                                            t.setText("");
                                                                        }
                                                                    }
                                                                    return t;
                                                                }
                                                            };
                                                            final TextField addMoney = new TextField();
                                                            addMoney.setTextFormatter(new TextFormatter<>(filter));
                                                            addMoney.setMaxWidth(400);
                                                            addMoney.setPromptText("Money");
                                                            addButton.setOnAction((ActionEvent event) -> {
                                                                try {

                                                                    data_TimeTransfer.add(Account.addPeriodicTransaction(addMyAccounts.getValue().toString(),addToAccount.getText(),Long.parseLong(addMoney.getText())));
                                                                    addToAccount.clear();
                                                                    addMoney.clear();

                                                                }catch (NullPointerException | NumberFormatException ex){
                                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                    alert.setContentText("Enter all fields please");
                                                                    alert.show();
                                                                }
                                                            });
                                                            VBox vbox_timeTransfer = new VBox();
                                                            vbox_timeTransfer.setSpacing(10);
                                                            vbox_timeTransfer.setPadding(new Insets(50, 50, 50, 60));
                                                            vbox_timeTransfer.setLayoutY(350);
                                                            vbox_timeTransfer.getChildren().addAll(hBox,addMyAccounts,addToAccount,addMoney,addButton);

                                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                                            Group root = new Group(btn_Back,  table_TimeTransfer ,hBox,vbox_timeTransfer);
                                                            Scene scene = new Scene(root, 700, 600);
                                                            stage.setTitle("Accounts");
                                                            stage.setScene(scene);
                                                            stage.show();
                                                        }
                                                    };
                                                    TimeTransferButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_TimeTransfer);
                                                }
                                            });
                                            ///////////////////////

                                            //Creating columns
                                            TableColumn nameCol = new TableColumn("Last Name");
                                            nameCol.setMinWidth(100);
                                            nameCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountOwnerName"));
                                            TableColumn account_owner_nameCol = new TableColumn("Account Number");
                                            account_owner_nameCol.setMinWidth(200);
                                            account_owner_nameCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountNumber"));
                                            TableColumn moneyCol = new TableColumn("Money");
                                            moneyCol.setMinWidth(100);
                                            moneyCol.setCellValueFactory(new PropertyValueFactory<Account , String>("money"));
                                            TableColumn User_IdCol = new TableColumn("User Id");
                                            User_IdCol.setMinWidth(100);
                                            User_IdCol.setCellValueFactory(new PropertyValueFactory<Account , String>("user_id"));
                                            TableColumn accountTypeCol = new TableColumn("Account Type");
                                            accountTypeCol.setMinWidth(200);
                                            accountTypeCol.setCellValueFactory(new PropertyValueFactory<Account , String>("accountType"));

                                            //Adding data to the table
                                            table.setItems(data);
                                            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                            table.getColumns().addAll(TimetransferCol,TransactionCol,DeleteCol,nameCol,account_owner_nameCol,moneyCol,User_IdCol,accountTypeCol);

                                            //Pass the data to a filtered list
                                            FilteredList<Account> flPerson = new FilteredList(data, user -> true);
                                            table.setItems(flPerson);
                                            /**
                                             * Searching
                                             */
                                            //Adding ChoiceBox and TextField
                                            ChoiceBox<String> choiceBox = new ChoiceBox();
                                            choiceBox.getItems().addAll( "Account Number" , "Account Type");
                                            choiceBox.setValue("Account Number");

                                            TextField textField = new TextField();
                                            textField.setPromptText("Search here!");
                                            textField.setOnKeyReleased(keyEvent ->
                                            {
                                                switch (choiceBox.getValue())
                                                {
                                                    case "Account Type":
                                                        flPerson.setPredicate(account -> account.getAccountType().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                    case "Account Number":
                                                        flPerson.setPredicate(account -> account.getAccountNumber().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
                                                        break;
                                                }
                                            });

                                            choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
                                            {
                                                if (newVal != null)
                                                {
                                                    textField.setText("");
                                                    flPerson.setPredicate(null);
                                                }
                                            });

                                            //add button
                                            /**
                                             * add button
                                             */
                                            final TextField addName = new TextField();
                                            addName.setPromptText("Last Name");
                                            addName.setMaxWidth(350);
                                            final TextField addUser_id = new TextField();
                                            addUser_id.setMaxWidth(350);
                                            addUser_id.setPromptText("User Id");
                                            final ChoiceBox<String> addAccountType = new ChoiceBox();
                                            addAccountType.getItems().addAll("CurrentDepositAccount","DemandDepositAccount","TimeDepositAccount");
                                            addAccountType.setValue("CurrentDepositAccount");
                                            addAccountType.setMaxWidth(350);
                                            final Button addButton = new Button("Add");
                                            addButton.setMaxWidth(350);
                                            /**
                                             * handle the InputMismatchException error
                                             * Exception for getting the Money
                                             */
                                            AccountManager accountManager = new AccountManager("Accounts");
                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                @Override
                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                    if (t.isReplaced())
                                                        if(t.getText().matches("[^0-9]"))
                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                    if (t.isAdded()) {
                                                        if (t.getControlText().contains(".")) {
                                                            if (t.getText().matches("[^0-9]")) {
                                                                t.setText("");
                                                            }
                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter Number please!!");
                                                            alert.show();
                                                            t.setText("");
                                                        }
                                                    }
                                                    return t;
                                                }
                                            };
                                            final TextField addMoney = new TextField();
                                            addMoney.setTextFormatter(new TextFormatter<>(filter));
                                            addMoney.setMaxWidth(350);
                                            addMoney.setPromptText("Money");
                                            addButton.setOnAction((ActionEvent event) -> {
                                                try {
                                                    Account account = new Account(addName.getText(), Long.parseLong(addMoney.getText()), addUser_id.getText(), addAccountType.getValue());
                                                    data.add(account);
                                                    addName.clear();
                                                    addMoney.clear();
                                                    addUser_id.clear();
                                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                    alert.setContentText(accountManager.newAccount(account));
                                                    alert.show();
                                                }catch (NullPointerException | NumberFormatException ex){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("Enter all fields please");
                                                    alert.show();
                                                }
                                            });

                                            HBox hBox = new HBox(choiceBox, textField);
                                            choiceBox.setMinWidth(150);
                                            textField.setMinWidth(200);
                                            hBox.setAlignment(Pos.CENTER);

                                            //Setting the size of the table
                                            table.setMaxSize(1000, 350);
                                            VBox vbox = new VBox();
                                            vbox.setLayoutY(10);
                                            vbox.getChildren().addAll(table,hBox,addName,addMoney,addUser_id,addAccountType,addButton);
                                            vbox.setSpacing(10);
                                            vbox.setPadding(new Insets(10, 5, 50, 5));
                                            vbox.setAlignment(Pos.CENTER);
                                            /**
                                             * action back button
                                             */
                                            btn_Back.setOnAction(exx -> stage.setScene(scene_back));
                                            Group root = new Group(btn_Back , vbox);
                                            Scene scene = new Scene(root,900, 700);
                                            scene.getStylesheets().add("style.css");
                                            table.setId("tables");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_ViewAccoount.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_ViewAccoount);

                                    ///////////////////////////////////////////////////////////////////////////////Action btn_withdraw
                                    EventHandler<MouseEvent> eventHandler_btn_withdraw = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page2
                                             */
                                            new_page2(stage, str);

                                            Button btn_Back;
                                            /**
                                             * button Menu
                                             */
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(400);
                                            btn_Back.setLayoutY(500);
                                            btn_Back.setStyle(str);
                                            /**
                                             * Login Form
                                             * password_Field
                                             * UserName_Field
                                             */
                                            BorderPane bp = new BorderPane();
                                            bp.setPadding(new Insets(10,50,50,50));

                                            //Adding HBox
                                            HBox hb = new HBox();
                                            hb.setPadding(new Insets(20,20,20,30));

                                            //Adding GridPane
                                            GridPane gridPane = new GridPane();
                                            gridPane.setPadding(new Insets(20,40,70,20));
                                            gridPane.setHgap(5);
                                            gridPane.setVgap(5);

                                            // Choice box
                                            ArrayList<Account> st = Client.showAllAccounts(checkUser , "Accounts");
                                            ArrayList<String> arr_accountNumber = new ArrayList<>();
                                            for (int i = 0; i < st.size(); i++){
                                                arr_accountNumber.add(st.get(i).getAccountNumber());
                                            }
                                            ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(arr_accountNumber));
                                            choiceBox.setValue("Account Number");
                                            Button btnLogin = new Button("Submit");
                                            /**
                                             * handle the InputMismatchException error
                                             * Exception for getting the Money
                                             */
                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                @Override
                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                    if (t.isReplaced())
                                                        if(t.getText().matches("[^0-9]"))
                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                    if (t.isAdded()) {
                                                        if (t.getControlText().contains(".")) {
                                                            if (t.getText().matches("[^0-9]")) {
                                                                t.setText("");
                                                            }
                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter Number please!!");
                                                            alert.show();
                                                            t.setText(" ");
                                                        }
                                                    }
                                                    return t;
                                                }
                                            };
                                            final javafx.scene.control.TextField Money = new TextField();
                                            Money.setTextFormatter(new TextFormatter<>(filter));
                                            Money.setPromptText("Enter Money");
                                            //Adding Nodes to GridPane layout
                                            gridPane.add(choiceBox, 1, 0);
                                            gridPane.add(Money, 1, 2);
                                            gridPane.add(btnLogin, 1, 2);

                                            DropShadow dropShadow = new DropShadow();
                                            dropShadow.setOffsetX(5);
                                            dropShadow.setOffsetY(5);
                                            Text text_Login = new Text("Withdraw Money");
                                            text_Login.setEffect(dropShadow);
                                            text_Login.setFont(Font.font ("roboto", 30));
                                            text_Login.setStyle("-fx-font-weight: bold;");
                                            text_Login.setFill(Color.GRAY);
                                            text_Login.setStroke(Color.DARKSLATEBLUE);
                                            text_Login.setStrokeWidth(3);
                                            hb.getChildren().add(text_Login);
                                            hb.setAlignment(Pos.CENTER);
                                            btnLogin.setLayoutX(30);
                                            bp.setTop(hb);
                                            bp.setCenter(gridPane);
                                            bp.setId("bp_Login");
                                            bp.getStylesheets().add("style.css");
                                            bp.setLayoutY(180);
                                            btnLogin.setId("btn_Login");
                                            btnLogin.setLayoutY(400);
                                            Money.setId("str_login");
                                            choiceBox.setId("str_login");
                                            choiceBox.setMinWidth(420);
                                            Money.setMinWidth(420);
                                            btnLogin.getStylesheets().add("style.css");
                                            Group root = new Group(btn_Back , imageView , bp , btnLogin);
                                            Menu_Button(stage , btn_Menu);
                                            /**
                                             * Action btn_submit
                                             */
                                            btnLogin.setOnAction(ex ->{
                                                try {
                                                    if (choiceBox.getValue().toString().equals(" ")) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Fill out all fields please");
                                                        alert.show();
                                                        return;
                                                    }
                                                    if (Money.getText().isEmpty()) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Fill out all fields please");
                                                        alert.show();
                                                        return;
                                                    }
                                                    if (Account.singleTransaction(Long.parseLong(Money.getText()) , choiceBox.getValue().toString() , "withdraw")){
                                                        Transaction transaction = new Transaction(choiceBox.getValue().toString(),Account.showInvestory(choiceBox.getValue().toString()),"",userName.toString(),"","","withdraw");
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setContentText(transaction.toString());
                                                        alert.show();
                                                        Money.clear();
                                                    }
                                                    else {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setContentText("you are not allow to withdraw money");
                                                        alert.show();
                                                        Money.clear();
                                                    }

                                                }catch (NullPointerException | NumberFormatException e1){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("Fill out all fields please");
                                                    alert.show();
                                                    Money.clear();
                                                }
                                            });
                                            /**
                                             * action back button
                                             */
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,500, 600);
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();

                                        }
                                    };
                                    btn_withdraw.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_withdraw);
                                    /////////////////////////////////////////////////////////////////////////////////////////////Action btn_Money_Transfer
                                    EventHandler<MouseEvent> eventHandler_btn_Money_Transfert = new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent e) {
                                            /**
                                             * The Method of new_page2
                                             */
                                            new_page2(stage, str);
                                            Button btn_Back;
                                            /**
                                             * button Menu
                                             */
                                            btn_Back = new Button("Back");
                                            btn_Back.setPrefSize(90, 90);
                                            btn_Back.setLayoutX(400);
                                            btn_Back.setLayoutY(500);
                                            btn_Back.setStyle(str);
                                            /**
                                             * Login Form
                                             * password_Field
                                             * UserName_Field
                                             */
                                            BorderPane bp = new BorderPane();
                                            bp.setPadding(new Insets(10,50,50,50));

                                            //Adding HBox
                                            HBox hb = new HBox();
                                            hb.setPadding(new Insets(20,20,20,30));

                                            //Adding GridPane
                                            GridPane gridPane = new GridPane();
                                            gridPane.setPadding(new Insets(20,40,70,20));
                                            gridPane.setHgap(5);
                                            gridPane.setVgap(5);

                                            // Choice box
                                            ArrayList<Account> st = Client.showAllAccounts(checkUser , "Accounts");
                                            ArrayList<String> arr_accountNumber = new ArrayList<>();
                                            for (int i = 0; i < st.size(); i++){
                                                arr_accountNumber.add(st.get(i).getAccountNumber());
                                            }
                                            ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(arr_accountNumber));
                                            choiceBox.setValue("Account Number");
                                            final javafx.scene.control.TextField distination_accountNumber = new TextField();
                                            distination_accountNumber.setPromptText("Enter Destination Account Number");
                                            Button btnLogin = new Button("Submit");
                                            /**
                                             * handle the InputMismatchException error
                                             * Exception for getting the Money
                                             */
                                            UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
                                                @Override
                                                public TextFormatter.Change apply(TextFormatter.Change t) {
                                                    if (t.isReplaced())
                                                        if(t.getText().matches("[^0-9]"))
                                                            t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

                                                    if (t.isAdded()) {
                                                        if (t.getControlText().contains(".")) {
                                                            if (t.getText().matches("[^0-9]")) {
                                                                t.setText("");
                                                            }
                                                        } else if (t.getText().matches("[^0-9.]")) {
                                                            Alert alert = new Alert(Alert.AlertType.WARNING);
                                                            alert.setContentText("Enter Number please!!");
                                                            alert.show();
                                                            t.setText(" ");
                                                        }
                                                    }
                                                    return t;
                                                }
                                            };

                                            final javafx.scene.control.TextField Money = new TextField();
                                            Money.setTextFormatter(new TextFormatter<>(filter));
                                            Money.setPromptText("Enter Money");

                                            //Adding Nodes to GridPane layout
                                            gridPane.add(choiceBox, 1, 0);
                                            gridPane.add(distination_accountNumber, 1, 2);
                                            gridPane.add(Money, 1, 4);
                                            gridPane.add(btnLogin, 1, 6);

                                            DropShadow dropShadow = new DropShadow();
                                            dropShadow.setOffsetX(5);
                                            dropShadow.setOffsetY(5);
                                            Text text_Login = new Text("Money Transfer");
                                            text_Login.setEffect(dropShadow);
                                            text_Login.setFont(Font.font ("roboto", 30));
                                            text_Login.setStyle("-fx-font-weight: bold;");
                                            text_Login.setFill(Color.GRAY);
                                            text_Login.setStroke(Color.DARKSLATEBLUE);
                                            text_Login.setStrokeWidth(3);
                                            hb.getChildren().add(text_Login);
                                            hb.setAlignment(Pos.CENTER);
                                            btnLogin.setLayoutX(30);
                                            bp.setTop(hb);
                                            bp.setCenter(gridPane);
                                            bp.setId("bp_Login");
                                            bp.getStylesheets().add("style.css");
                                            bp.setLayoutY(160);
                                            choiceBox.setId("str_login");
                                            distination_accountNumber.setId("str_login");
                                            Money.setId("str_login");
                                            btnLogin.setId("btn_Login");
                                            btnLogin.setLayoutY(430);
                                            choiceBox.setMinWidth(420);
                                            distination_accountNumber.setMinWidth(420);
                                            Money.setMinWidth(420);
                                            btnLogin.setMinWidth(420);
                                            btnLogin.getStylesheets().add("style.css");
                                            Group root = new Group(btn_Back , imageView , bp , btnLogin);
                                            Menu_Button(stage , btn_Menu);
                                            /**
                                             * Action btn_submit
                                             */
                                            btnLogin.setOnAction(ex ->{
                                                try {
                                                    if (choiceBox.getValue().toString().equals(" ")) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Fill out all fields please");
                                                        alert.show();
                                                        return;
                                                    }
                                                    if (distination_accountNumber.getText().isEmpty()) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Fill out all fields please");
                                                        alert.show();
                                                        return;
                                                    }
                                                    if (Money.getText().isEmpty()) {
                                                        Alert alert = new Alert(Alert.AlertType.WARNING);
                                                        alert.setContentText("Fill out all fields please");
                                                        alert.show();
                                                        return;
                                                    }
                                                    Transaction transaction = new Transaction(choiceBox.getValue().toString(),Account.showInvestory(choiceBox.getValue().toString()),"",userName.toString(),"",distination_accountNumber.getText(),"withdraw");
                                                    if(Account.transfer_money(choiceBox.getValue().toString(), distination_accountNumber.getText(), Long.parseLong(Money.getText()))){
                                                        if (Long.parseLong(Money.getText()) <= Account.showInvestory(choiceBox.getValue().toString())){
                                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                            alert.setContentText(transaction.toString());
                                                            alert.show();
                                                            distination_accountNumber.clear();
                                                            Money.clear();
                                                        }
                                                        else {
                                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                            alert.setContentText("Inventory is not enough");
                                                            alert.show();
                                                            distination_accountNumber.clear();
                                                            Money.clear();
                                                        }

                                                    }
                                                    else {
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setContentText("you are not allow to transfer money");
                                                        alert.show();
                                                        distination_accountNumber.clear();
                                                        Money.clear();
                                                    }

                                                }catch (NullPointerException | NumberFormatException e1){
                                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                                    alert.setContentText("There is no such account number");
                                                    alert.show();
                                                    distination_accountNumber.clear();
                                                    Money.clear();
                                                }
                                            });
                                            /**
                                             * action back button
                                             */
                                            btn_Back.setOnAction(ex -> stage.setScene(scene_back));
                                            Scene scene = new Scene(root,500, 600);
                                            scene.getStylesheets().add("style.css");
                                            stage.setTitle("Farabi Bank");
                                            stage.setScene(scene);
                                            stage.show();
                                        }
                                    };
                                    btn_Money_transfer.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_btn_Money_Transfert);

                                    //////////////////////////////////////////////////////////
                                    /**
                                     * action back button
                                     */
                                    btn_Back.setOnAction(ex -> stage.setScene(scene));
                                    Group root = new Group(btn_Menu_client,text_Welcome,btn_ViewAccoount,btn_withdraw,btn_Money_transfer);
                                    Menu_Button(stage , btn_Menu);
                                    scene_back = new Scene(root,500, 600);
                                    scene_back.getStylesheets().add("style.css");
                                    stage.setTitle("Farabi Bank");
                                    stage.setScene(scene_back);
                                    stage.show();

                                } else {
                                    lblMessage.setText("Incorrect user or password.");
                                    lblMessage.setTextFill(Color.RED);
                                }
                                userName.setText("");
                                pass.setText("");
                            }
                        });
                    }
                };
                btn_Client.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Client);
                ////////////////////////////////////////////////////////////////////////////
                /**
                 * The method of Menu_Button
                 */
                Menu_Button(stage , btn_Menu);
            }
        };
        //About_us
        EventHandler<MouseEvent> eventHandler_about_us = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button btn;
                Text text = new Text();
                Text text2 = new Text("Welcome to Farabi Bank");
                String s = "  This App created by :\n  Mehran Advand and Hossein Sholehrasa and Daniyal Farhangi";
                text.setText(s);
                text.setStyle("-fx-font: 24 roboto;");

                text2.setX(50);
                text2.setY(100);
                text2.setStrokeWidth(2);
                text2.setStroke(Color.DARKSLATEBLUE);
                text2.setFont(Font.font("roboto", 30));
                text2.setTextAlignment(CENTER);
                text2.setStyle("-fx-alignment: CENTER;");

                text.setX(10);
                text.setY(300);
                text.setStrokeWidth(1);
                text.setStroke(Color.DARKSLATEBLUE);
                text.setStyle("-fx-alignment: CENTER;");

                btn = new Button("Menu");
                btn.setPrefSize(90, 90);
                btn.setLayoutX(400);
                btn.setLayoutY(500);
                btn.setStyle(str);

                Hyperlink hyperlink = new Hyperlink("Go to gitlab page");
                hyperlink.setLayoutX(10);
                hyperlink.setLayoutY(400);
                hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        getHostServices().showDocument("https://gitlab.com/hosseinsho/bank-system");
                    }
                });
                Group root = new Group(text , text2 , btn , hyperlink);
                Scene scene = new Scene(root, 500, 600);
                stage.setTitle("Farabi Bank");
                stage.setScene(scene);
                stage.show();
                /**
                 * The method of Menu Button
                 */
                Menu_Button(stage , btn);
            }
        };
        //Exit
        EventHandler<MouseEvent> eventHandler_Exit = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.exit(0);
            }
        };
        //Registering the event filter
        btn_Enter.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Enter);
        btn_About_Us.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_about_us);
        btn_Exit.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler_Exit);
        ///////////////////////////////////////////////////////////////////
        Group root = new Group(imageView , btn_Enter , btn_About_Us , btn_Exit);
        Scene scene = new Scene(root, 500, 600);
        //adding css
        scene.getStylesheets().add("style.css");
        imageView.setId("img");
        stage.setTitle("Farabi Bank");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

