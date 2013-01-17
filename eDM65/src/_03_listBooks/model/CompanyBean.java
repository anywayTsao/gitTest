package _03_listBooks.model;

import java.io.Serializable;
// 本類別封裝單筆出版社資料
public class CompanyBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int     id ;
	private String  name;
	
    public CompanyBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CompanyBean() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}