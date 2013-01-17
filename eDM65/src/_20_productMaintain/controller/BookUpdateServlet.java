package _20_productMaintain.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import _03_listBooks.model.*;

public class BookUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookBean bb ;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (!session.isNew())  {
			bb = (BookBean) session.getAttribute("bean");
		} else {

		}
		//System.out.println("pageNo=" + request.getParameter("pageNo"));
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
		String pageNo = "1";
		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setCharacterEncoding("UTF-8");		
		req.setAttribute("ErrMsg", errorMsgs);
		HttpSession session = req.getSession();
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
		                bb.setTitle(title);
		                if (title == null || title.trim().length() == 0) {
		                	errorMsgs.put("errTitle" , "必須輸入書名");
		                } else {
		                	//session.setAttribute("title", title);
		                	req.setAttribute("title", title);
		                }
					} else if (fldName.equals("author")){
						author = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						bb.setAuthor(author);
						if (author == null || author.trim().length() == 0) {
		                	errorMsgs.put("errAuthor" , "必須輸入作者");
						} else {
		                	//session.setAttribute("author", author);
		                	req.setAttribute("author", author);
		                }
					} else if (fldName.equals("price")){
						priceStr = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						priceStr = priceStr.trim();
						bb.setPriceStr(priceStr);
						if (priceStr == null || priceStr.trim().length() == 0) {
		                	errorMsgs.put("errPrice" , "必須輸入價格");
		                } else {
		                	try {
								price = Double.parseDouble(priceStr);
							} catch(NumberFormatException e) {
								errorMsgs.put("errPrice" , "價格必須是數值");
							}    
		                }						
						//session.setAttribute("price", priceStr);
						req.setAttribute("price", priceStr);
					} else if (fldName.equals("bookNo")){
						bookNo = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						bb.setBookNo(bookNo);
						if (bookNo == null || bookNo.trim().length() == 0) {
		                	errorMsgs.put("errBookNo" , "必須輸入書號");
		                } else {
			                //session.setAttribute("bookNo", bookNo);
			                req.setAttribute("bookNo", bookNo);
		                }
					} else if (fldName.equals("companyID")){
						companyID = new String(fitem.getString().getBytes("ISO-8859-1"), "UTF-8");
						int tempID  = Integer.parseInt(companyID);
						bb.setCompanyID(tempID);
						if (companyID == null || companyID.trim().length() == 0) {
		                	errorMsgs.put("errCompanyID" , "必須輸入出版社");
		                }
						//session.setAttribute("cID", companyID);
						req.setAttribute("cID", companyID);
					} else if (fldName.equals("pageNo")){
						pageNo = fitem.getString();
					} 
				} else {
					//String fieldName = fitem.getFieldName();
					pathName = fitem.getName();
					if (pathName != null && pathName.trim().length() > 0) {
						//String contentType = fitem.getContentType();  // fitem有getContentType()
						sizeInBytes = (int)fitem.getSize();
						String fileName = pathName.substring(pathName.lastIndexOf("\\") + 1);
						//int fileLength = (int)new File(pathName).length();
						is = fitem.getInputStream();
					} else {
                        // 沒有挑選圖片，表示不修改圖片，此時 pathName.length() == 0 
					}
				}
			}
			if (!errorMsgs.isEmpty()) {
			   RequestDispatcher rd = req.getRequestDispatcher("BookUpdate.jsp");
			   rd.forward(req, res);
			   return;
			} 
			int cID = Integer.parseInt(companyID);
			BookBean newBean = new BookBean(bb.getBookID(), title, author, price, 1.0, cID, pathName, bookNo);
			BookAccessBean  bab = new BookAccessBean();
			bab.updateBook(newBean, is, sizeInBytes, pathName); 
			errorMsgs.put("success" , "資料修改成功");
			RequestDispatcher rd = req.getRequestDispatcher("DisplayPageProducts?pageNo=" + pageNo);
			rd.forward(req, res);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.put("errDBMessage", e.getMessage());
			RequestDispatcher rd = req.getRequestDispatcher("BookError.jsp");
			rd.forward(req, res);
		}
	}

}
