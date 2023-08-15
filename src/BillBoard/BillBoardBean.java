package BillBoard;

public class BillBoardBean 
{
	String mobile;
	String datefrom;
	String dateto;
	String bill;
	String billstatus;
	public BillBoardBean(){}
	public BillBoardBean(String mobile, String datefrom, String dateto, String bill, String billstatus) 
	{
		super();
		this.mobile = mobile;
		this.datefrom = datefrom;
		this.dateto = dateto;
		this.bill = bill;
		this.billstatus = billstatus;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}
	public String getDateto() {
		return dateto;
	}
	public void setDateto(String dateto) {
		this.dateto = dateto;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getBillstatus() {
		return billstatus;
	}
	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}
	
	
	
}
