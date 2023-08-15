package CustomerDash;

import java.io.File;

import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class CustomerDashViewController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<CustomerBean> tableData;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doExportToExcelAndPrint(ActionEvent event) 
    {
    	try 
    	{
            ObservableList<CustomerBean> data = tableData.getItems();

            // Show the FileChooser to select the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(tableData.getScene().getWindow());

            // Save the data to the selected file
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(file)) {
                    // Write header
                    for (TableColumn<CustomerBean, ?> column : tableData.getColumns()) {
                        writer.print(column.getText() + ",");
                    }
                    writer.println();

                    // Write data
                    for (CustomerBean item : data) 
                    {
                        writer.print(item.getMobile() + ",");
                        writer.print(item.getCname() + ",");
                        writer.print(item.getHawker() + ",");
                        writer.print(item.getEmail() + ",");
                        writer.print(item.getAddress() + ",");
                        writer.print(item.getDos() + ",");
                        writer.println();
                    }
                }
            }
        } 
    	
    	catch (Exception e) 
    	{
            e.printStackTrace();
        }
    }
    

    @FXML
    void doFetchCustomer(ActionEvent event) 
    {
    	   TableColumn<CustomerBean, String> mobile=new TableColumn<CustomerBean, String>("Customer Mobile No");//any thing
           mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
           mobile.setMinWidth(50);
         
           TableColumn<CustomerBean, String> cname=new TableColumn<CustomerBean, String>("Customer Name");//any thing
           cname.setCellValueFactory(new PropertyValueFactory<>("cname")); //name of column 
                     	//name.setMinWidth(150);
           TableColumn<CustomerBean, String> hawkername=new TableColumn<CustomerBean, String>("Hawker Allocated");//any thing
           hawkername.setCellValueFactory(new PropertyValueFactory<>("hawker"));
           hawkername.setMinWidth(50);
                     	
           TableColumn<CustomerBean, String> email=new TableColumn<CustomerBean, String>("Customer Email");//any thing
           email.setCellValueFactory(new PropertyValueFactory<>("email"));
           email.setMinWidth(50);
                     	
           TableColumn<CustomerBean, String> address=new TableColumn<CustomerBean, String>("Customer Address");//any thing
           address.setCellValueFactory(new PropertyValueFactory<>("address"));
           address.setMinWidth(50);
           
           TableColumn<CustomerBean, String> doj=new TableColumn<CustomerBean, String>("Date of joining");//any thing
           doj.setCellValueFactory(new PropertyValueFactory<>("dos"));
           doj.setMinWidth(50);
                     	
           tableData.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile, cname, hawkername, email, address, doj)));
       	   tableData.setItems(doShowCustomerDetails());
                     	
                     	//lblResp.setText("Records Fetched Successfully...");
    }

    @FXML
    void doFilterCustomer(ActionEvent event) 
    {

 	    TableColumn<CustomerBean, String> mobile=new TableColumn<CustomerBean, String>("Customer Mobile No");//any thing
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobile.setMinWidth(50);
      
        TableColumn<CustomerBean, String> cname=new TableColumn<CustomerBean, String>("Customer Name");//any thing
        cname.setCellValueFactory(new PropertyValueFactory<>("cname")); //name of column 
                  	//name.setMinWidth(150);
        TableColumn<CustomerBean, String> hawkername=new TableColumn<CustomerBean, String>("Hawker Allocated");//any thing
        hawkername.setCellValueFactory(new PropertyValueFactory<>("hawker"));
        hawkername.setMinWidth(50);
                  	
        TableColumn<CustomerBean, String> email=new TableColumn<CustomerBean, String>("Customer Email");//any thing
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setMinWidth(50);
                  	
        TableColumn<CustomerBean, String> address=new TableColumn<CustomerBean, String>("Customer Address");//any thing
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        address.setMinWidth(50);
        
        TableColumn<CustomerBean, String> doj=new TableColumn<CustomerBean, String>("Date of joining");//any thing
        doj.setCellValueFactory(new PropertyValueFactory<>("dos"));
        doj.setMinWidth(50);
                  	
        tableData.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile, cname, hawkername, email, address, doj)));
    	tableData.setItems(doShowCustomerDetailsWithArea());
                 
    }
    
    void doFillPaper()
    {
    	try 
    	{
			pst=con.prepareStatement("select * from nmaster");
			
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
				comboPaper.getItems().add(table.getString("paper"));
			}
			
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void doFillArea()
    {
    	try {
            pst = con.prepareStatement("select alloareas from hawkermanagement");
            ResultSet table = pst.executeQuery();
            
            ObservableList<String> allocatedAreasList = FXCollections.observableArrayList();
            
            while (table.next()) {
                String allocatedAreas = table.getString("alloareas");
                
                // Split areas based on commas and add them as separate items
                String[] areasArray = allocatedAreas.split(", ");
                allocatedAreasList.addAll(areasArray);
            }
            
            comboArea.setItems(allocatedAreasList);
        } 
    	catch (SQLException e) 
    	{
            e.printStackTrace();
        }
    }
    
    ObservableList<CustomerBean> doShowCustomerDetails()
    {
    	ObservableList<CustomerBean> ary=	FXCollections.observableArrayList();
    	String selectedPaper = comboPaper.getSelectionModel().getSelectedItem();
    	try
    	{
			pst=con.prepareStatement("select * from customermanagement where spapers like ?");
			pst.setString(1, "%"+selectedPaper+"%");
		   ResultSet table=pst.executeQuery(); //array of objects
		
	
		while(table.next())
		  {
			String mob=table.getString("mobile");
			String name=table.getString("cname");
		    String hname=table.getString("hawker");
		    String mail=table.getString("email");
		    String address=table.getString("address");
		    String date=table.getString("dos");
			
			ary.add(new CustomerBean(mob, name, hname, mail, address, date )); 
				
		  }
		
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
    	
    	return ary;
    }
    
   ObservableList<CustomerBean> doShowCustomerDetailsWithArea()
    {
	     ObservableList<CustomerBean> ary= FXCollections.observableArrayList();
   	     String selectedArea = comboArea.getSelectionModel().getSelectedItem();
   	try
   	{
			pst=con.prepareStatement("select * from customermanagement where area like ?");
			pst.setString(1, "%"+selectedArea+"%");
		   ResultSet table=pst.executeQuery(); //array of objects
		
	
		while(table.next())
		  {
			String mob=table.getString("mobile");
			String name=table.getString("cname");
		    String hname=table.getString("hawker");
		    String mail=table.getString("email");
		    String address=table.getString("address");
		    String date=table.getString("dos");
			
			ary.add(new CustomerBean(mob, name, hname, mail, address, date )); 
				
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
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'CustomerDashView.fxml'.";
        assert comboPaper != null : "fx:id=\"comboPaper\" was not injected: check your FXML file 'CustomerDashView.fxml'.";
        assert tableData != null : "fx:id=\"tableData\" was not injected: check your FXML file 'CustomerDashView.fxml'.";

        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
        doFillPaper();
        doFillArea();
        
    }

}
