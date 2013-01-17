package _20_productMaintain.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import _03_listBooks.model.*;

public class BookInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			processMultipart(request, response);
		} else {
			//errorMsgs.put("errMultipart" , "不是 Multipart 資料");
			//RequestDispatcher rd = request
			//		.getRequestDispatcher("InsertRecord/insertBLOBError.jsp");
			//rd.forward(request, response);
			//return;
		}

	}

	private void processMultipart(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		
		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		req.setAttribute("ErrMsg", errorMsgs);
		session.setAttribute("ErrMsg", errorMsgs);
		try {
			String  title = ""; 
			String  author = "";
			String  priceStr = "";
			double  price = 0;
			String bookNo = "";
			String companyID = "";
			String pathName = "";
			int sizeInBytes = 0 ;
			InputStream is = null ; 
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> itor = items.iterator();
			while (itor.hasNext()) {
				FileItem fitem =  itor.next();
				if (fitem.isFormField()) {
					String fldName = fitem.getFieldName();
					if (fldName.equals("title")){
		                title = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
		                //System.out.println("BookInsertServlet.java, title=" + title);
		                if (title == null || title.trim().length() == 0) {
		                	//System.out.println("BookInsertServlet.java, title Error");
		                	errorMsgs.put("errTitle" , "必須輸入書名");
		                } else {
		                	req.setAttribute("title", title);
		                }
					} else if (fldName.equals("author")){
						author = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						if (author == null || author.trim().length() == 0) {
		                	errorMsgs.put("errAuthor" , "必須輸入作者");
						} else {
		                	req.setAttribute("author", author);
		                }
					} else if (fldName.equals("price")){
						priceStr = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						priceStr = priceStr.trim();
						if (priceStr == null || priceStr.trim().length() == 0) {
		                	errorMsgs.put("errPrice" , "必須輸入價格");
		                } else {
		                	try {
								price = Double.parseDouble(priceStr);
							} catch(NumberFormatException e) {
								errorMsgs.put("errPrice" , "價格必須是數值");
							}    
		                }						
						req.setAttribute("price", priceStr);
					} else if (fldName.equals("bookNo")){
						bookNo = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						if (bookNo == null || bookNo.trim().length() == 0) {
		                	errorMsgs.put("errBookNo" , "必須輸入書號");
		                } else {
			                req.setAttribute("bookNo", bookNo);
		                }
					} else if (fldName.equals("companyID")){
						companyID = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						if (companyID == null || companyID.trim().length() == 0) {
		                	errorMsgs.put("errCompanyID" , "必須輸入出版社");
		                }
						req.setAttribute("cID", companyID);
					} 
				} else {
					String fieldName = fitem.getFieldName();
					pathName = fitem.getName();  // 此為圖片檔的檔名
					if (pathName != null && pathName.trim().length() > 0) {
						String contentType = fitem.getContentType();  
						sizeInBytes = (int)fitem.getSize();   
						is = fitem.getInputStream();
					} else {
						errorMsgs.put("errPicture" , "必須挑選圖片檔");
					}
				}
			}
			if (!errorMsgs.isEmpty()) {
			   RequestDispatcher rd = req.getRequestDispatcher("BookInsert.jsp");
			   rd.forward(req, res);
			   return;
			} 
			int cID = Integer.parseInt(companyID);
			BookBean bb = new BookBean(0, title, author, price, 1.0, cID, pathName, bookNo);
			BookAccessBean  bab = new BookAccessBean();
			bab.insertBook(bb, is, sizeInBytes);
			errorMsgs.put("success" , "資料新增成功");
			res.sendRedirect(res.encodeRedirectURL("BookInsert.jsp"));
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.put("Exception", e.getMessage());
			RequestDispatcher rd = req.getRequestDispatcher("BookInsert.jsp");
			rd.forward(req, res);
		}
	}
	
}