package HawkerDetails;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import PaperMaster.mysqlconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class HawkerTableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HawkerBean> tableData;
    
    @FXML
    private Label lblResp;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doFetchDetails(ActionEvent event) 
    {
    	TableColumn<HawkerBean, String> name=new TableColumn<HawkerBean, String>("Hawker Name");//any thing
    	name.setCellValueFactory(new PropertyValueFactory<>("hname")); //name of column 
    	//name.setMinWidth(150);
    	
    	TableColumn<HawkerBean, String> mobile=new TableColumn<HawkerBean, String>("Hawker Mobile No");//any thing
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(50);
    	
    	TableColumn<HawkerBean, String> alloarea=new TableColumn<HawkerBean, String>("Allocated Areas");//any thing
    	alloarea.setCellValueFactory(new PropertyValueFactory<>("alloareas"));
    	alloarea.setMinWidth(50);
    	
    	TableColumn<HawkerBean, String> aadhar=new TableColumn<HawkerBean, String>("Aadhar Card Number");//any thing
    	aadhar.setCellValueFactory(new PropertyValueFactory<>("aadhar"));
    	aadhar.setMinWidth(50);
    	
    	tableData.getColumns().addAll(new ArrayList<>(Arrays.asList(name, mobile, alloarea, aadhar)));
    	tableData.setItems(FetchAllHawkers());
    	
    	lblResp.setText("Records Fetched Successfully...");
    }
    
    ObservableList FetchAllHawkers()
    {
    	ObservableList<HawkerBean> ary=	FXCollections.observableArrayList();
    	try
    	{
			pst=con.prepareStatement("select * from hawkermanagement");
			
		ResultSet table=pst.executeQuery(); //array of objects
		
	
		while(table.next())
		  {
			String name=table.getString("hname");
			String mob=table.getString("mobile");
		    String alloarea=table.getString("alloareas");
		    String aadhar=table.getString("aadhar");
			
			ary.add(new HawkerBean(name, mob, alloarea, aadhar)); 
				
		  }
		
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
    	
    	return ary;
    }

    @FXML
    void initialize() 
    {
        assert tableData != null : "fx:id=\"tableData\" was not injected: check your FXML file 'HawkerTableView.fxml'.";
        assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'HawkerTableView.fxml'.";
         
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
