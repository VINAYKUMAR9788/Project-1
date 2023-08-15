package AdminLogin;

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class LoginController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Label lblResp;

    
    private static final String setPassword = "12345678";

    @FXML
    void doLogin(ActionEvent event) 
    {
    	String enteredPassword = txtPassword.getText();

        if (enteredPassword.equals(setPassword)) 
        {
            try 
            {
                Parent root = FXMLLoader.load(getClass().getResource("/AdminDesk/AdminDeskView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // to hide the opened window
                /*
                 * Scene scene1=(Scene)lblResp.getScene(); scene1.getWindow().hide();
                 */

            } 
            
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        } 
        else 
        {
        	 TextInputDialog dialog = new TextInputDialog("");
             dialog.setTitle("Incorrect Password!!!");
             dialog.setHeaderText(null);
             dialog.setContentText("Hint: 'Board Roll Number'. Enter password:   ");

             Optional<String> result = dialog.showAndWait();
             // Check if the user entered the correct password after the hint
             if (result.isPresent() && result.get().equals(setPassword)) 
             {
                 try 
                 {
                     Parent root = FXMLLoader.load(getClass().getResource("/AdminDesk/AdminDeskView.fxml"));
                     Scene scene = new Scene(root);
                     Stage stage = new Stage();
                     stage.setScene(scene);
                     stage.show();


                 } 
                 catch (Exception e) 
                 {
                     e.printStackTrace();
                 }
             } 
             else 
             {
                 // Show a message if the password is still incorrect
                 showMsg("Incorrect password. Please try again.");
             }
        }
    }
    
    void showMsg(String msg)
    {
    	//Alert alert = new Alert(AlertType.INFORMATION);
    			//Alert alert = new Alert(AlertType.WARNING);
    			Alert alert = new Alert(AlertType.WARNING);
    			
    			//alert.setTitle("Information Dialog");
    				//or
    			alert.setTitle(null);
    			
    			alert.setHeaderText("Look, an Information Dialog");
    			alert.setContentText(msg);

    			alert.showAndWait();
    }

    @FXML
    void initialize() 
    {
    	assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
