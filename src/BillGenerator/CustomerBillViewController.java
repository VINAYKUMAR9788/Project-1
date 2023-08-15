package BillGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import PaperMaster.mysqlconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CustomerBillViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMobile;

    @FXML
    private DatePicker dateOfEnd;

    @FXML
    private DatePicker dateOfStart;

    @FXML
    private Label lblResp;

    @FXML
    private TextField txtBill;

    @FXML
    private TextField txtMissingDays;

    @FXML
    private TextField txtSelectedNewsPapers;

    @FXML
    private TextField txtSelectedNewsPapersCost;

    @FXML
    private TextField txtTotalPrice;
    
    PreparedStatement pst;
    Connection con;
    
    
    @FXML
    void doFetchDetails(ActionEvent event) 
    {
    	String mobile=comboMobile.getSelectionModel().getSelectedItem();
    	
    	if(mobile!=null)
    	{
    		try 
    		{
				pst=con.prepareStatement("select * from customermanagement where mobile=?");
				pst.setString(1, mobile);
				
				ResultSet table= pst.executeQuery();
                if (table.next()) 
                {
                	String selectedPapers = table.getString("spapers"); 
                    String selectedPrices = table.getString("sprices"); 

                    // Split comma-separated strings to create ObservableLists
                    ObservableList<String> selectedPapersList = FXCollections.observableArrayList(Arrays.asList(selectedPapers.split(", ")));
                    ObservableList<String> selectedPricesList = FXCollections.observableArrayList(Arrays.asList(selectedPrices.split(", ")));

                    // Set the ObservableLists as comma-separated strings to the text fields
                    txtSelectedNewsPapers.setText(String.join(", ", selectedPapersList));
                   
					txtSelectedNewsPapersCost.setText(String.join(", ", selectedPricesList));
					
					java.sql.Date doss= table.getDate("dos");
					dateOfStart.setValue(doss.toLocalDate());
					
					double totalPrice = 0.0;
                    for (String price : selectedPricesList) 
                    {
                        try 
                        {
                            double itemPrice = Double.parseDouble(price);
                            totalPrice += itemPrice;
                        } 
                        
                        catch (NumberFormatException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                    txtTotalPrice.setText(String.valueOf(totalPrice));
                }
			} 
    		catch (SQLException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }

    @FXML
    void doGenerateAndSaveBill(ActionEvent event) 
    {
    	LocalDate dos= dateOfStart.getValue();
    	java.sql.Date dts=java.sql.Date.valueOf(dos);
    	
    	LocalDate doe= dateOfEnd.getValue();
    	java.sql.Date dte=java.sql.Date.valueOf(doe);

        try 
        {
            // Calculate the total number of days between start and end dates
            long totalDays = java.time.temporal.ChronoUnit.DAYS.between(dateOfStart.getValue(), dateOfEnd.getValue());

            int missingDays = Integer.parseInt(txtMissingDays.getText());

            // Calculate the total bill
            double totalPrice = Double.parseDouble(txtTotalPrice.getText());
            double totalBill = (totalDays - missingDays) * totalPrice;
            
            txtBill.setText(String.valueOf(totalBill));

            // Save the total bill in the database
            pst = con.prepareStatement("insert into billgenerator (mobile, datefrom, dateto, bill) values(?,?,?,?)");
            pst.setString(1, comboMobile.getSelectionModel().getSelectedItem());
            pst.setDate(2, dts);
            pst.setDate(3, dte);
            pst.setString(4, txtBill.getText());
            
            int count=pst.executeUpdate();

            
            if(count!=0)
            {
            	lblResp.setText("Bill generated and saved successfully!");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    void doAddMobileNumbers()
    {
    	
    	try 
    	{
			pst=con.prepareStatement(" select * from billgenerator");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				String mob=table.getString("mobile");
				
				comboMobile.getItems().add(mob);
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void doDeleteRecords(ActionEvent event) 
    {
    	try 
    	{
    		String mobile=comboMobile.getSelectionModel().getSelectedItem();
			pst=con.prepareStatement("delete from billgenerator where mobile=?");
			
			pst.setString(1,mobile);
			
			int count=pst.executeUpdate();
			if(count!=0)
			{
				lblResp.setText("Record Deleted");
			}
			else
			{
				lblResp.setText("Invalid User");
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
        assert comboMobile != null : "fx:id=\"comboMobile\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert dateOfEnd != null : "fx:id=\"dateOfEnd\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert dateOfStart != null : "fx:id=\"dateOfStart\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert txtBill != null : "fx:id=\"txtBill\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert txtMissingDays != null : "fx:id=\"txtMissingDays\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert txtSelectedNewsPapers != null : "fx:id=\"txtSelectedNewsPapers\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert txtSelectedNewsPapersCost != null : "fx:id=\"txtSelectedNewsPapersCost\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        assert txtTotalPrice != null : "fx:id=\"txtTotalPrice\" was not injected: check your FXML file 'CustomerBillView.fxml'.";
        
        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
        
        doAddMobileNumbers();
    }

}
