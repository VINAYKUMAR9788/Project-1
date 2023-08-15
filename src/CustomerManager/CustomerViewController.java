package CustomerManager;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Observable;
import java.util.ResourceBundle;

import PaperMaster.mysqlconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;

public class CustomerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private DatePicker dos;

    @FXML
    private Label lblResp;

    @FXML
    private ListView<String> listAvailablePaper;

    @FXML
    private ListView<String> listAvailablePaperCost;

    @FXML
    private ListView<String> listSelectedPaper;

    @FXML
    private ListView<String> listSelectedPaperCost;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHawker;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;
    
    Connection con;
    PreparedStatement pst;
    
    @FXML
    void doSelectPaper(MouseEvent event) 
    {
    	
//    	listSelectedPaper.getItems().addAll(listAvailablePaper.getSelectionModel().getSelectedItems());
//    	listSelectedPaperCost.getSelectionModel().select(listAvailablePaper.getSelectionModel().getSelectedIndex());
//    	System.out.println(listAvailablePaper.getSelectionModel().getSelectedIndex()+".."+listAvailablePaperCost.getSelectionModel().getSelectedItem());
//    	listSelectedPaperCost.getItems().add(listAvailablePaperCost.getSelectionModel().getSelectedItem());
    	
    	ObservableList<String> selectedNewspapers = listAvailablePaper.getSelectionModel().getSelectedItems();
    	ObservableList<String> selectedNewspaperCosts = FXCollections.observableArrayList();

    	for (String newspaper : selectedNewspapers) 
    	{
    	    int index = listAvailablePaper.getItems().indexOf(newspaper);
    	    if (index >= 0 && index < listAvailablePaperCost.getItems().size()) {
    	        selectedNewspaperCosts.add(listAvailablePaperCost.getItems().get(index));
    	    } else {
    	        selectedNewspaperCosts.add("N/A");
    	    }
    	}

    	listSelectedPaper.getItems().addAll(selectedNewspapers);
    	listSelectedPaperCost.getItems().addAll(selectedNewspaperCosts);
    	
    }
    
    
    @FXML
    void doFetch(ActionEvent event) 
    {
    	try
    	{
			pst=con.prepareStatement("select * from customermanagement where mobile=?");
			String mob=txtMobile.getText();
			pst.setString(1, mob);
			
			
		ResultSet table=pst.executeQuery(); //array of objects
		while(table.next())
		{
			String name=table.getString("cname");
			
			String hawker=table.getString("hawker");
			String email=table.getString("email");
		    String address=table.getString("address");
		    java.sql.Date doss= table.getDate("dos");
			
			txtName.setText(name);
			//listSelectedPaper.setItems(spapers);
			txtHawker.setText(hawker);
			txtEmail.setText(email);
			txtAddress.setText(address);
			dos.setValue(doss.toLocalDate());
			
			String selectedPapers = table.getString("spapers"); // Assuming the column name is "selected_papers"
            String selectedPrices = table.getString("sprices"); // Assuming the column name is "selected_prices"

            // Split the comma-separated strings to create ObservableLists
            ObservableList<String> selectedPapersList = FXCollections.observableArrayList(selectedPapers.split(", "));
            ObservableList<String> selectedPricesList = FXCollections.observableArrayList(selectedPrices.split(", "));

            // Set the ObservableLists to the ListViews
            listSelectedPaper.setItems(selectedPapersList);
            listSelectedPaperCost.setItems(selectedPricesList);
			
		}
		
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
    }

    @FXML
    void doSubscribe(ActionEvent event) 
    {
    	
    	LocalDate ld= dos.getValue();
    	java.sql.Date dt=java.sql.Date.valueOf(ld);
    	try 
    	{
			pst=con.prepareStatement("insert into customermanagement values(?,?,?,?,?,?,?,?,?)");

			pst.setString(1,txtMobile.getText());
			pst.setString(2, txtName.getText());
			pst.setString(3,ListViewUtil.listToString(listSelectedPaper.getItems()));
			pst.setString(4,ListViewUtil.listToString(listSelectedPaperCost.getItems()));
			pst.setString(5,comboArea.getSelectionModel().getSelectedItem());
			pst.setString(6,txtHawker.getText());
			pst.setString(7,txtEmail.getText());
			pst.setString(8,txtAddress.getText());
			pst.setDate(9,dt);

			int count=pst.executeUpdate();
			
			if(count!=0)
			{
				lblResp.setText("Record Saved");
			}
			else
			{
				lblResp.setText("Record Not Saved");
			}
    	}	
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doUnsubscribe(ActionEvent event) 
    {
    	try 
    	{
    		String mobile=txtMobile.getText();
			pst=con.prepareStatement("delete from customermanagement where mobile=?");
			
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
    void doUpdate(ActionEvent event) 
    {
    	String mobile=txtMobile.getText();
    	
    	LocalDate ld= dos.getValue();
    	java.sql.Date dt=java.sql.Date.valueOf(ld);
    	
		//in parameters
        try 
        {
            pst=con.prepareStatement("update customermanagement set cname=?, spapers=?, sprices=?, areas=?, hawker=?, email=?, address=?, dos=? where mobile=?");

            pst.setString(1, txtName.getText());
            pst.setString(2, listSelectedPaper.getSelectionModel().getSelectedItem());
            pst.setString(3, listSelectedPaperCost.getSelectionModel().getSelectedItem());
            pst.setString(4, comboArea.getSelectionModel().getSelectedItem());
            pst.setString(5, txtHawker.getText());
            pst.setString(6, txtEmail.getText());
            pst.setString(7, txtAddress.getText());
            pst.setDate(8, dt);
            pst.setString(9, mobile);

            int count=pst.executeUpdate();

            lblResp.setText(count+" Records Updated........");

         } 
         catch (SQLException e) 
         {
             e.printStackTrace();
         }
        
        
   }
    
    void doShowComboArea()
    {
    	try 
    	{
			pst=con.prepareStatement(" select * from customermanagement");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				String area=table.getString("area");
				
				comboArea.getItems().add(area);
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void doDoubleClickDelete(MouseEvent event) 
    {
//    	if (event.getClickCount() == 2) 
//    	{
//           
//           listSelectedPaper.getItems().remove(listSelectedPaper.getSelectionModel().getSelectedItems());
//            listSelectedPaperCost.getItems().remove(listSelectedPaperCost.getItems());
//        }
    	
    }
    
    @FXML
    void doAreaToHawker(ActionEvent event) 
    {
    	String selectedArea = comboArea.getSelectionModel().getSelectedItem();
        if (selectedArea != null) {
            String hawkerName = getHawkerNameForArea(selectedArea);
            
            if (hawkerName != null) 
            {
            	txtHawker.setText(hawkerName);
                System.out.println("Hawker Name for Area " + selectedArea + ": " + hawkerName);
            } 
            else 
            {
            	txtHawker.setText("No Hawker Found for Area");
                System.out.println("No Hawker Found for Area: " + selectedArea);
            }
        }
    }
    
    private String getHawkerNameForArea(String area) 
    {
        String hawkerName = null;
        try
        {
            pst=con.prepareStatement("select hname from hawkermanagement where alloareas like ?");
             
                pst.setString(1, "%"+area+"%");
                ResultSet table= pst.executeQuery();
                    if (table.next()) 
                    {
                        hawkerName =table.getString("hname");
                    }
            
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return hawkerName;
    }
    
    void doAddPapers()
    {
    	try 
    	{
			pst=con.prepareStatement("select * from nmaster");
			
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
				listAvailablePaper.getItems().add(table.getString("paper"));
				listAvailablePaperCost.getItems().add(table.getString("price"));
			}
			
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public class ListViewUtil 
    {

        public static String listToString(ObservableList<String> list) 
        {
            return String.join(", ", list);
        }
    }

    @FXML
    void initialize() 
    {
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert dos != null : "fx:id=\"dos\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert listAvailablePaper != null : "fx:id=\"listAvailablePaper\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert listAvailablePaperCost != null : "fx:id=\"listAvailablePaperCost\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert listSelectedPaper != null : "fx:id=\"listSelectedPaper\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert listSelectedPaperCost != null : "fx:id=\"listSelectedPaperCost\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert txtHawker != null : "fx:id=\"txtHawker\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'CustomerView.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'CustomerView.fxml'.";
        
        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
        
        listAvailablePaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        doAddPapers();
        
        doShowComboArea();
        
    }

}
