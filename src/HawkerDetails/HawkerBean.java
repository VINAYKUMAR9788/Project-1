package HawkerDetails;

public class HawkerBean 
{
	String hname;
	String mobile;
	String alloareas;
	String aadhar;
	
	public HawkerBean() {}
	public HawkerBean(String hname, String mobile, String alloareas, String aadhar) 
	{
		super();
		this.hname = hname;
		this.mobile = mobile;
		this.alloareas = alloareas;
		this.aadhar = aadhar;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAlloareas() {
		return alloareas;
	}
	public void setAlloareas(String alloareas) {
		this.alloareas = alloareas;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	
	
	
	
}
