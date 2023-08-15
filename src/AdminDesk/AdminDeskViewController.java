package AdminDesk;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminDeskViewController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doOpenAboutUs(ActionEvent event) 
    {
    	try 
    	{
            Parent root = FXMLLoader.load(getClass().getResource("/AboutUs/AboutUsView.fxml"));
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

    @FXML
    void doOpenBillBoard(ActionEvent event) 
    {
    	try 
    	{
            Parent root = FXMLLoader.load(getClass().getResource("/BillBoard/BillBoardView.fxml"));
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

    @FXML
    void doOpenBillCollector(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/BillCollector/BillCollectorView.fxml"));
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

    @FXML
    void doOpenBillGenerator(ActionEvent event) 
    {
    	try 
    	{
            Parent root = FXMLLoader.load(getClass().getResource("/BillGenerator/CustomerBillView.fxml"));
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

    @FXML
    void doOpenCustomerGoogler(ActionEvent event) 
    {
    	try 
    	{
            Parent root = FXMLLoader.load(getClass().getResource("/CustomerDash/CustomerDashView.fxml"));
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

    @FXML
    void doOpenCustomerManager(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/CustomerManager/CustomerView.fxml"));
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

    @FXML
    void doOpenDisplayHawkers(ActionEvent event) 
    {
    	try 
    	{
            Parent root = FXMLLoader.load(getClass().getResource("/HawkerDetails/HawkerTableView.fxml"));
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

    @FXML
    void doOpenHawkerManager(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/HawkerManager/HawkerView.fxml"));
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

    @FXML
    void doOpenPaperMaster(ActionEvent event) 
    {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/PaperMaster/PaperView.fxml"));
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

    @FXML
    void initialize() 
    {

    }

}

