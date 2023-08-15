package PaperMaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

//Database---> newspaperagency and its table---> nmaster

public class PaperViewController 
{
	
	private static final String MySqlConnector = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboNewspaper;

    @FXML
    private TextField txtPrice;
    
    @FXML
    private Label lblresp;
    
    Connection con;
    PreparedStatement pst;

    @FXML
    void doAvail(ActionEvent event) 
    {
    	String papr=comboNewspaper.getSelectionModel().getSelectedItem();
    	Float pr=Float.parseFloat(txtPrice.getText());
    	
    	try 
    	{
			pst=con.prepareStatement("insert into nmaster values(?,?)");
			
			pst.setString(1,papr);
			pst.setFloat(2,pr);

			pst.executeUpdate();
			lblresp.setText("Record Saved");
    	}	
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doEdit(ActionEvent event) 
    {
    	String papr=comboNewspaper.getSelectionModel().getSelectedItem();
    	Float pr=Float.parseFloat(txtPrice.getText());
    	
    	try 
    	{
			pst=con.prepareStatement("update nmaster set price=? where paper=?");
			
			pst.setFloat(1,pr);
			pst.setString(2, papr);
			
			int count=pst.executeUpdate();
			lblresp.setText(count+"Record Update");
		} 
    	
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doSearch(ActionEvent event) 
    {
    	try 
    	{
			pst=con.prepareStatement("select * from nmaster where paper=?");
			String papr=comboNewspaper.getSelectionModel().getSelectedItem();
			pst.setString(1, papr);
			
			ResultSet table=pst.executeQuery();
			
			while(table.next())
			{
		        Float pr=table.getFloat("price");
				
				txtPrice.setText(String.valueOf(pr));
			}
		} 
    	
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doWithdraw(ActionEvent event) 
    {
  
    	try 
    	{
    		String papr=comboNewspaper.getSelectionModel().getSelectedItem();
			pst=con.prepareStatement("delete from nmaster where paper=?");
			
			pst.setString(1, papr);
			
			int count=pst.executeUpdate();
			if(count!=0)
			{
				lblresp.setText("Record Deleted");
			}
			else
			{
				lblresp.setText("Invalid Paper");
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void doFillPapers()
    {
    	try 
    	{
			pst=con.prepareStatement(" select * from nmaster");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				String papr=table.getString("paper");
				
				comboNewspaper.getItems().add(papr);
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
        assert comboNewspaper != null : "fx:id=\"comboNewspaper\" was not injected: check your FXML file 'PaperView.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'PaperView.fxml'.";
        assert lblresp != null : "fx:id=\"lblresp\" was not injected: check your FXML file 'PaperView.fxml'.";
        
        con=mysqlconnector.doConnect();
        
        if(con==null)
        {
        	System.out.println("Connection Problem");
        }
        else
        {
        	System.out.println("Connected");
        }
        
        doFillPapers();//show combo items

    }

}
