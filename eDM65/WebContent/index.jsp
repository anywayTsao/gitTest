<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="SYSTEM" class="_00_init.GlobalService" scope="application"/>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">




<c:set var="AppName" value="${SYSTEM.systemName}" scope="application"/>

<title>${AppName}</title>




</head>
<body style="background:#EBFFEB;">
<!-- 設定變數funcName的值為"IND", top.jsp會使用此變數-->
<c:set var="funcName" value="IND" scope="session"/>
<!-- 引入共同的頁首 -->
<jsp:include page="/fragment/top.jsp" />
 <CENTER> 
 <h1>
 歡迎光臨<BR><FONT COLOR='RED'>${AppName}</FONT><BR>
 天天有優惠，天天有好康<BR>
 今天舉辦<FONT COLOR='BLUE'>滿佰送仟</FONT>的促銷活動<BR>
  </h1> 
 <BR>
  <h2>
 本店絕不販賣盜版書、山寨書與過期書
 </h2>
 <HR>
 <!-- 
 ${logoutMessage}:
 為Logout.jsp所放入的感謝訊息
  
 ${MsgOK.InsertOK}:
 _01_register.controller.RegisterSevlet.java放入此訊息
 會員註冊成功會顯示此頁，並透過session物件送來識別字串為MsgOK
 的Map物件，透過MsgOK.InsertOK取出註冊成功的訊息  
 -->
 ${logoutMessage}${MsgOK.InsertOK}<BR>
 <BR>  
 <% 
    session.removeAttribute("MsgOK"); // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息 
 %>
 </CENTER> 
</body>
</html>