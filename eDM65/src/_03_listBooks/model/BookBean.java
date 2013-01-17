package _03_listBooks.model;

import java.io.Serializable;
// 本類別封裝單筆書籍資料
public class BookBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int     bookID ;
	private String  title;
	private String  author;
	private double  price   ;
	private double  discount		;
	private int     companyID	;
	private String  companyName	;
	private String  fileName;
	private String  bookNo;
	

	private String  discountStr;
    public BookBean(int bookID, String title, String author, double price,
    		double discount, int companyID, String fileName, String bookNo) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.author = author;
		this.price = price;
		this.discount = discount;
		this.companyID = companyID;
		this.fileName = fileName;
		this.bookNo = bookNo;
	}
	
	public BookBean() {
		super();
	}
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	private String  priceStr = null;
	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
		if (priceStr == null) {
			priceStr = String.valueOf(price);
		}
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
		if (discount == 1) {
			discountStr = "";
		} else {
			int dnt = (int)(discount * 100);
			if (dnt % 10 == 0) {
				discountStr = (dnt / 10) + "折";
			} else {
				discountStr = " "  + dnt + "折";
			} 
			
		}
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDiscountStr() {
		return discountStr;
	}	
	public String toString() {
		return  "bookID=" + bookID + "  title=" + title + "  author=" + author + "   price=" + price + "   discount=" + discount + "  companyID=" + companyID; 
	}
	 
    
}
