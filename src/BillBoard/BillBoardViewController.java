package BillBoard;

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


import PaperMaster.mysqlconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class BillBoardViewController 
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radPaid;

    @FXML
    private RadioButton radPending;

    @FXML
    private TableView<BillBoardBean> tableData;

    @FXML
    private ToggleGroup toggle;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtMobile;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doExport(ActionEvent event) 
    {
    	try 
    	{
            ObservableList<BillBoardBean> data = tableData.getItems();

            // Show the FileChooser to select the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(tableData.getScene().getWindow());

            // Save the data to the selected file
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(file)) 
                {
                    // Write header
                    for (TableColumn<BillBoardBean, ?> column : tableData.getColumns()) {
                        writer.print(column.getText() + ",");
                    }
                    writer.println();

                    // Write data
                    for (BillBoardBean item : data) 
                    {
                        writer.print(item.getMobile() + ",");
                        writer.print(item.getDatefrom() + ",");
                        writer.print(item.getDateto() + ",");
                        writer.print(item.getBill() + ",");
                        writer.print(item.getBillstatus() + ",");
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
    void doSearchBillHistory(ActionEvent event) 
    {
    	 TableColumn<BillBoardBean, String> mobile=new TableColumn<BillBoardBean, String>("Customer Mobile No");//any thing
         mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
         mobile.setMinWidth(50);
        
        TableColumn<BillBoardBean, String> dos=new TableColumn<BillBoardBean, String>("Date of joining");//any thing
        dos.setCellValueFactory(new PropertyValueFactory<>("datefrom"));
        dos.setMinWidth(50);
        
        TableColumn<BillBoardBean, String> doe=new TableColumn<BillBoardBean, String>("Date of LastBilling");//any thing
        doe.setCellValueFactory(new PropertyValueFactory<>("dateto"));
        doe.setMinWidth(50);
        
        TableColumn<BillBoardBean, String> bill=new TableColumn<BillBoardBean, String>("Total Bill");//any thing
        bill.setCellValueFactory(new PropertyValueFactory<>("bill")); //name of column 
        bill.setMinWidth(150);
        
        TableColumn<BillBoardBean, String> bstatus=new TableColumn<BillBoardBean, String>("Bill Status");//any thing
        bstatus.setCellValueFactory(new PropertyValueFactory<>("billstatus"));
        bstatus.setMinWidth(50);
                  	
        tableData.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile, dos, doe, bill, bstatus )));
    	tableData.setItems(doShowCustomerBillHistory());
               
    }

    @FXML
    void doSearchPendingAndPaid(ActionEvent event) 
    {
    	TableColumn<BillBoardBean, String> mobile=new TableColumn<BillBoardBean, String>("Customer Mobile No");//any thing
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobile.setMinWidth(50);
       
       TableColumn<BillBoardBean, String> dos=new TableColumn<BillBoardBean, String>("Date of joining");//any thing
       dos.setCellValueFactory(new PropertyValueFactory<>("datefrom"));
       dos.setMinWidth(50);
       
       TableColumn<BillBoardBean, String> doe=new TableColumn<BillBoardBean, String>("Date of LastBilling");//any thing
       doe.setCellValueFactory(new PropertyValueFactory<>("dateto"));
       doe.setMinWidth(50);
       
       TableColumn<BillBoardBean, String> bill=new TableColumn<BillBoardBean, String>("Total Bill");//any thing
       bill.setCellValueFactory(new PropertyValueFactory<>("bill")); //name of column 
       bill.setMinWidth(150);
       
       TableColumn<BillBoardBean, String> bstatus=new TableColumn<BillBoardBean, String>("Bill Status");//any thing
       bstatus.setCellValueFactory(new PropertyValueFactory<>("billstatus"));
       bstatus.setMinWidth(50);
                 	
       tableData.getColumns().addAll(new ArrayList<>(Arrays.asList(mobile, dos, doe, bill, bstatus )));
   	   tableData.setItems(doShowPaidAndPendingBill());
    }
    
    ObservableList<BillBoardBean> doShowCustomerBillHistory()
    {
    	ObservableList<BillBoardBean> ary= FXCollections.observableArrayList();
  	     String mob = txtMobile.getText();
  	try
  	{
			pst=con.prepareStatement("select * from billgenerator where mobile = ? ");
			pst.setString(1, mob);
		    ResultSet table=pst.executeQuery(); //array of objects
		
	
		while(table.next())
		  {
			String dos=table.getString("datefrom");
		    String doe=table.getString("dateto");
		    String bill=table.getString("bill");
		    String bstatus=table.getString("billstatus");
		    
			
			ary.add(new BillBoardBean(mob, dos, doe, bill, bstatus )); 
				
		  }
		
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
  	
  	return ary;
    }
    
    ObservableList<BillBoardBean> doShowPaidAndPendingBill()
    {
    	ObservableList<BillBoardBean> ary= FXCollections.observableArrayList();
			
		if(radPending.isSelected())
		{
		     try 
		     {
				pst=con.prepareStatement("select * from billgenerator where billstatus=0 ");
				
				 ResultSet table= pst.executeQuery();
				 
				 while(table.next())
				 {
					 String mob=table.getString("mobile");
					 String dos=table.getString("datefrom");
					 String doe=table.getString("dateto");
					 String bill=table.getString("bill");
					 String bstatus=table.getString("billstatus");
					 
					 ary.add(new BillBoardBean(mob, dos, doe, bill, bstatus ));
				 }
			} 
		     catch (SQLException e) 
		     {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		
		else
		{
			try 
		     {
				pst=con.prepareStatement("select * from billgenerator where billstatus=1 ");
				
				 ResultSet table= pst.executeQuery();
				 
				 while(table.next())
				 {
					 String mob=table.getString("mobile");
					 String dos=table.getString("datefrom");
					 String doe=table.getString("dateto");
					 String bill=table.getString("bill");
					 String bstatus=table.getString("billstatus");
					 
					 ary.add(new BillBoardBean(mob, dos, doe, bill, bstatus ));
				 }
			} 
		     catch (SQLException e) 
		     {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		}
		return ary;
    	
    }

    @FXML
    void initialize() 
    {
        assert radPaid != null : "fx:id=\"radPaid\" was not injected: check your FXML file 'BillBoardView.fxml'.";
        assert radPending != null : "fx:id=\"radPending\" was not injected: check your FXML file 'BillBoardView.fxml'.";
        assert tableData != null : "fx:id=\"tableData\" was not injected: check your FXML file 'BillBoardView.fxml'.";
        assert toggle != null : "fx:id=\"toggle\" was not injected: check your FXML file 'BillBoardView.fxml'.";
        assert txtAmount != null : "fx:id=\"txtAmount\" was not injected: check your FXML file 'BillBoardView.fxml'.";
        assert txtMobile != null : "fx:id=\"txtMobile\" was not injected: check your FXML file 'BillBoardView.fxml'.";

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
