package CustomerDash;

public class CustomerBean 
{
	String mobile;
	String cname;
	String hawker;
	String email;
	String address;
	String dos;
	
	public CustomerBean() {}
	public CustomerBean(String mobile, String cname, String hawker, String email, String address, String dos) 
	{
		super();
		this.mobile = mobile;
		this.cname = cname;
		this.hawker = hawker;
		this.email = email;
		this.address = address;
		this.dos = dos;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getHawker() {
		return hawker;
	}
	public void setHawker(String hawker) {
		this.hawker = hawker;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	
	
}
