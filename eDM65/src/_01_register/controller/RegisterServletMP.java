package _01_register.controller;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import _01_register.model.*;
import _02_login.model.*;
/*
 * 本程式讀取使用者輸入資料，進行必要的資料轉換，檢查使用者輸入資料，
 * 進行Business Logic運算，依照Business Logic運算結果來挑選適當的畫面
 * 
 */
public class RegisterServletMP extends HttpServlet {
	private static final long serialVersionUID = 1L;	
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
    	// request.setCharacterEncoding("UTF-8");
		// 準備存放錯誤訊息的Map物件
		Map<String, String> msgMap = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		Map<String, String> myParam = new HashMap<String, String>();
        // 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
        request.setAttribute("MsgMap", msgMap); 
        request.setAttribute("myParam", myParam);
        session.setAttribute("MsgOK", msgOK);
        // 下列敘述判斷瀏覽器上傳的資料是否為含有檔案資料
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) { // 如果含有檔案資料
			msgMap.put("errorIDEmpty","<B>表單並非 Multipart 表單</B>");
			// 導向原來輸入資料的畫面，顯示錯誤訊息
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
		}

		String memberID = "";
		String password  = "" ;
		String password2  = "";
		String name  = "";
		String email  = "";
		String addr  = "";
		String tel  = "";
		String expericnceStr  = "";
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> itor = items.iterator();
			// 1. 讀取使用者輸入資料
			while (itor.hasNext()) {
				FileItem fitem = (FileItem) itor.next();
				if (fitem.isFormField()) {
					String fname = fitem.getFieldName();
					String fvalue = fitem.getString();
					// 文字資料要轉內碼
					fvalue = new String(fvalue.getBytes("ISO-8859-1"), "UTF-8");
					// 將表單送來的欄位名稱與對應的值存放到myParam內，目的在
					// 若需要顯示錯誤時，表單上會有使用者原來輸入的資料
					myParam.put(fname, fvalue);
					if (fname.equalsIgnoreCase("mid")) {
						memberID = fvalue;
					} else if (fname.equalsIgnoreCase("password")) {
						password = fvalue;
					} else if (fname.equalsIgnoreCase("password2")) {
						password2 = fvalue;
					} else if (fname.equalsIgnoreCase("name")) {
						name = fvalue;
					} else if (fname.equalsIgnoreCase("eMail")) {
						email = fvalue;
					} else if (fname.equalsIgnoreCase("address")) {
						addr = fvalue;  
					} else if (fname.equalsIgnoreCase("tel")) {
						tel = fvalue;
					} else if (fname.equalsIgnoreCase("experience")) {
						expericnceStr = fvalue;  
					}
				} else {
					String pathName = fitem.getName();
					if (pathName != null && pathName.trim().length() > 0) {
						sizeInBytes = fitem.getSize();
						fileName = pathName.substring(pathName.lastIndexOf("\\") + 1);
						is = fitem.getInputStream();
					}
				}
			}
			// 2. 進行必要的資料轉換
			int experience = 0;
			try {
				experience = Integer.parseInt(expericnceStr.trim());
			} catch (NumberFormatException e) {
				msgMap.put("errorFormat","網路購物經驗格式錯誤，應該為整數");
			}
			// 3. 檢查使用者輸入資料
			if (memberID == null || memberID.trim().length() == 0) {
				msgMap.put("errorIDEmpty","帳號欄必須輸入");
			}
			if (password == null || password.trim().length() == 0) {
				msgMap.put("errorPasswordEmpty","密碼欄必須輸入");
			}
			if (password2 == null || password2.trim().length() == 0) {
				msgMap.put("errorPassword2Empty","密碼確認欄必須輸入");
			}
			if (password.trim().length() > 0 && password2.trim().length() > 0){
				if (!password.trim().equals(password2.trim())){
					msgMap.put("errorPassword2Empty","密碼欄必須與確認欄一致");
					msgMap.put("errorPasswordEmpty","*");
				}			
			}
			if (name == null || name.trim().length() == 0) {
				msgMap.put("errorName","姓名欄必須輸入");
			}
			if (addr == null || addr.trim().length() == 0) {
				msgMap.put("errorAddr","地址欄必須輸入");
			}
			if (email == null || email.trim().length() == 0) {
				msgMap.put("errorEmail","電子郵件欄必須輸入");
			}
			if (tel == null || tel.trim().length() == 0) {
				msgMap.put("errorTel","電話號碼欄必須輸入");
			}
			if (experience < 0) {
				msgMap.put("errorFormat","網路購物經驗應該為正整數或 0 ");
			}
			// 如果有錯誤
			if (!msgMap.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				return;
			}
			// 4. 進行Business Logic運算
			// RegisterServiceFile類別的功能：
			// 1.檢查帳號是否已經存在
			// 2.儲存會員的資料 
			RegisterServiceDB rs = new RegisterServiceDB();  
			if (rs.idExists(memberID)) {
				msgMap.put("errorIDDup","此代號已存在，請換新代號");
			} else {
					MemberBean mem = new MemberBean(memberID, name, password, addr, email, tel, experience);
					// 將MemberBean mem立即寫入Database				
					int n = rs.saveMember(mem, is, (int)sizeInBytes);
					if ( n == 1) {
						msgOK.put("InsertOK","<Font color='red'>新增成功，請開始使用本系統</Font>");
						response.sendRedirect("../index.jsp");
						return;
					} else {
						msgMap.put("errorIDDup","新增此筆資料有誤(RegisterServlet)");
					}
			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!msgMap.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msgMap.put("errorIDDup", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
		}		
     }
}   