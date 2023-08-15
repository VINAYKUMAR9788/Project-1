package BillCollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PaperMaster.mysqlconnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BillCollectorViewController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateOfEnd;

    @FXML
    private DatePicker dateOfStart;

    @FXML
    private Label lblResp;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtMobile;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doFetchDetails(ActionEvent event) 
    {
    	
    	try 
    	{
			pst=con.prepareStatement("select * from billgenerator where mobile=?");
			String mob=txtMobile.getText();
			pst.setString(1, mob);
			
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
				java.sql.Date dos= table.getDate("datefrom");
				java.sql.Date doe= table.getDate("dateto");
				String amount=table.getString("bill");
				
				dateOfStart.setValue(dos.toLocalDate());
				dateOfEnd.setValue(doe.toLocalDate());
				txtAmount.setText(amount);
				
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void doCompletepayment(ActionEvent event) 
    {
    	String mob=txtMobile.getText();
    	try 
    	{
			pst=con.prepareStatement("select * from billgenerator where mobile=?");
			pst.setString(1, mob);
			
			ResultSet table=pst.executeQuery();
			 while(table.next())
			 {
				 int bstatus=table.getInt("billstatus");
				 if(bstatus==0)
				 {
					 pst=con.prepareStatement("update billgenerator set billstatus=1 where mobile=?");
					 pst.setString(1, mob);
					 int count=pst.executeUpdate();
					 if(count!=0)
					 {
						 lblResp.setText("Payment Successful");
					 }
					 
					 else
					 {
						 lblResp.setText("Payment Failed");
					 }
					 
				 }
				 else
				 {
					 lblResp.setText("Payment Already Done");
				 }
				 
			 }
			
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    @FXML
    void initialize() 
    {
        assert dateOfEnd != null : "fx:id=\"dateOfEnd\" was not injected: check your FXML file 'BillCollectorView.fxml'.";
        assert dateOfStart != null : "fx:id=\"dateOfStart\" was not injected: check your FXML file 'BillCollectorView.fxml'.";
        assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'BillCollectorView.fxml'.";
        assert txtAmount != null : "fx:id=\"txtAmount\" was not injected: check your FXML file 'BillCollectorView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'BillCollectorView.fxml'.";

        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
    }

}

