package member;

public class MemberBean {
	private String id;
	private String pwd;
	private String name;
	private String phone;
	
	public MemberBean() {}

	public MemberBean(String id, String pwd, String name, String phone) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "MemberBean [id=" + id + ", pwd=" + pwd + ", name=" + name + ", phone=" + phone + "]";
	}
	
	
}
