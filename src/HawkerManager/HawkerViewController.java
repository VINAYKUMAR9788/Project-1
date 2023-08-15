package HawkerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import PaperMaster.mysqlconnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HawkerViewController 
{
	
	private static final String MySqlConnector = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAreas;

    @FXML
    private ComboBox<String> comboHawker;

    @FXML
    private Label lblPicPath;

    @FXML
    private Label lblResp;

    @FXML
    private ImageView picPrev;

    @FXML
    private TextField txtAadhar;

    @FXML
    private TextField txtAllocatedArea;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtmobile;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doEnroll(ActionEvent event) 
    {
    	//String name=comboHawker.getSelectionModel().getSelectedItem();
    	//String mob=txtmobile.getText();
    	//String address=String.valueOf(txtaddress.getText());
    	//String area=String.valueOf(comboAreas.getSelectionModel().getSelectedItem());
    	//String aadhar=txtAadhar.getText();
    	
    	try 
    	{
			pst=con.prepareStatement("insert into hawkermanagement values(?,?,?,?,?,?,?)");
			
			pst.setString(1,comboHawker.getSelectionModel().getSelectedItem());
			pst.setString(2,txtmobile.getText());
			pst.setString(3,txtaddress.getText());
			pst.setString(4,comboAreas.getSelectionModel().getSelectedItem());
			pst.setString(5,txtAllocatedArea.getText());
			pst.setString(6,txtAadhar.getText());
			pst.setString(7,lblPicPath.getText());

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
    void doFire(ActionEvent event) 
    {
    	try 
    	{
    		String name=comboHawker.getSelectionModel().getSelectedItem();
			pst=con.prepareStatement("delete from hawkermanagement where hname=?");
			
			pst.setString(1, name);
			
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
    void doNew(ActionEvent event) 
    {
    	comboAreas.getSelectionModel().clearSelection();
    	comboAreas.getEditor().setText(null);
    	
    	txtmobile.setText(null);
    	
    	comboAreas.getSelectionModel().clearSelection();
    	comboAreas.getEditor().setText(null);
    	
    	txtaddress.setText(null);
        txtAllocatedArea.setText(null);
    	txtAadhar.setText(null);
    }

    @FXML
    void doSearch(ActionEvent event) 
    {
    	try
    	{
			pst=con.prepareStatement("select * from hawkermanagement where hname=?");
			String name= comboHawker.getSelectionModel().getSelectedItem();
			pst.setString(1, name);
			
			
		ResultSet table=pst.executeQuery(); //array of objects
		while(table.next())
		{
			String mob=table.getString("mobile");
			String address=table.getString("address");
		    String alloarea=table.getString("alloareas");
		    String aadhar=table.getString("aadhar");
			String picPath= table.getString("picpath");
			
			txtmobile.setText(String.valueOf(mob));
			txtaddress.setText(String.valueOf(address));
			txtAllocatedArea.setText(String.valueOf(alloarea));
			txtAadhar.setText(String.valueOf(aadhar));
			lblPicPath.setText(picPath);
						
			picPrev.setImage(new Image(new FileInputStream(picPath)));
			
			//System.out.println(roll+"\t"+sname+"\t"+per+"\t"+dob.toString() +"\t"+picPath);
			
		}
		
		}	
		catch(Exception exp)
		{
			System.out.println(exp);
		}
    }

    @FXML
    void doSelPick(ActionEvent event) throws FileNotFoundException 
    {
    	FileChooser fileChooser = new FileChooser();
   	    fileChooser.setTitle("Open Resource File");
   	    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif") );
   	    File selectedFile = fileChooser.showOpenDialog(null);
   	 
   	 if (selectedFile != null) {
   	    lblPicPath.setText(selectedFile.getPath());
   	    Image img=new Image(selectedFile.toURI().toString());
   	    System.out.println(selectedFile.toURI().toString());
   	    picPrev.setImage(new Image(new FileInputStream(selectedFile)));
   	    //picPrev.setImage(img);
   	 }
   	 else
   	 {
   		lblPicPath.setText("nopic.jpg");
   	 }
    }
    
    @FXML
    void doShowAllocatedArea(ActionEvent event) 
    {
    	String ar=comboAreas.getSelectionModel().getSelectedItem();
    	
    	if(txtAllocatedArea.getText()==null)
    	{
    		txtAllocatedArea.setText(ar);
    	}
    	else
    	{
    		txtAllocatedArea.setText(txtAllocatedArea.getText()+ ", " +ar);
    	}
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	String name=comboHawker.getSelectionModel().getSelectedItem();
    	
    															//in parameters
    	try {
				pst=con.prepareStatement("update hawkermanagement set mobile=?, address=?, areas=?, alloareas=?, aadhar=?, picpath=? where hname=?");
				
				pst.setString(1, txtmobile.getText());
				pst.setString(2, txtaddress.getText());
				pst.setString(3, comboAreas.getSelectionModel().getSelectedItem());
				pst.setString(4, txtAllocatedArea.getText());
				pst.setString(5, txtAadhar.getText());
				pst.setString(6, lblPicPath.getText());
				pst.setString(7, name);
				
				int count=pst.executeUpdate();
				
				lblResp.setText(count+ " Record(s) Updated........");
				
			} 
    	catch (SQLException e) 
    		{
			  e.printStackTrace();
			}
    }
    
    void doFillHawker()
    {
    	try 
    	{
			pst=con.prepareStatement(" select * from hawkermanagement");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				String name=table.getString("hname");
				
				comboHawker.getItems().add(name);
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
    	try 
    	{
			pst=con.prepareStatement(" select * from hawkermanagement");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				String area=table.getString("areas");
				
				comboAreas.getItems().add(area);
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert comboAreas != null : "fx:id=\"comboAreas\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert comboHawker != null : "fx:id=\"comboHawker\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert lblPicPath != null : "fx:id=\"lblPicPath\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert lblResp != null : "fx:id=\"lblResp\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert picPrev != null : "fx:id=\"picPrev\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert txtAadhar != null : "fx:id=\"txtAadhar\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert txtAllocatedArea != null : "fx:id=\"txtAllocatedArea\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert txtaddress != null : "fx:id=\"txtaddress\" was not injected: check your FXML file 'HawkerView.fxml'.";
        assert txtmobile != null : "fx:id=\"txtmobile\" was not injected: check your FXML file 'HawkerView.fxml'.";
        
        lblPicPath.setText("nopic.jpg");

        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
        doFillHawker();
        doFillArea();
    }

}
