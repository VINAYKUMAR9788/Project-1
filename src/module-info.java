module NewspaperAgencyAutomation 
{
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens PaperMaster to javafx.graphics, javafx.fxml;
	opens HawkerManager to javafx.graphics, javafx.fxml;
	opens CustomerManager to javafx.graphics, javafx.fxml;
	opens BillGenerator to javafx.graphics, javafx.fxml;
	opens BillCollector to javafx.graphics, javafx.fxml;
	opens HawkerDetails to javafx.graphics, javafx.fxml, javafx.base;
	opens CustomerDash to javafx.graphics, javafx.fxml, javafx.base;
	opens BillBoard to javafx.graphics, javafx.fxml, javafx.base;
	opens AboutUs to javafx.graphics, javafx.fxml;
	opens AdminLogin to javafx.graphics, javafx.fxml;
	opens AdminDesk to javafx.graphics, javafx.fxml;
	
	
	
}
