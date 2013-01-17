<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var='debug' value='true' scope='application'/>
<TABLE border='0' style="color:blue;border-collapse:collapse;background:#EBFFEB;left-margin:10px;top-margin:10px;">
  <TR height="48"> 
    <TD width="60" rowspan='2'><img width="60" height="40" src="${pageContext.request.contextPath}/images/BookStore.gif"></TD>
    <TD width="240" align="left">
       <TABLE   style="color:blue;">
         <TR>
           <TD width="5">
                  &nbsp;  
           </TD>
           <TD width="40">
              <c:if test="${empty LoginOK }">
                 <A href="<c:url value='/_02_login/login.jsp' />">登入 </A>
              </c:if>
           </TD>
           <TD width="40">
              <c:if test="${ funcName != 'SHO' }">
                 <A href="<c:url value='/_03_listBooks/DisplayPageProducts' />">
                                                   購物
                 </A>                      
              </c:if>                                      
              <c:if test="${ funcName == 'SHO' }"> 
                                                    購物
              </c:if>
           </TD>
           <TD width="40">
              <c:if test="${ funcName != 'CHE' }"> 
                 <A href="<c:url value='/_04_ShoppingCart/ShowCartContent.jsp' />"> 
                                                     結帳
                 </A>                      
              </c:if>
              <c:if test="${ funcName == 'CHE' }"> 
                                                     結帳
              </c:if>                                     
           </TD>
           <TD width="40">
             <c:if test="${ funcName != 'ORD' }"> 
                 <A href="<c:url value='/_05_orderProcess/OrderList.jsp' />"> 
                                                 訂單
                 </A>                     
             </c:if>                                              
             <c:if test="${ funcName == 'ORD' }"> 
                  
                                                訂單
                                      
             </c:if>
           </TD>
                 <TD width="40">
                     <c:if test="${ funcName != 'BMT' }"> 
                        <A href="<c:url value='/_20_productMaintain/DisplayPageProducts' />"> 
                           維護
                         </A>                     
                     </c:if>
                     <c:if test="${ funcName == 'BMT' }"> 
                           維護
                     </c:if>   
                 </TD>
                 
                 
              </TR>
            </TABLE>
        </TD>
        <TD width='300'>
           <c:if test="${ debug }" >
               <small>${pageContext.session.id }</small>
           </c:if>                    
        </TD>
        
        <TD width='40'>
           <c:if test="${! empty LoginOK }">
               <img height='40' width='30' 
                   src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${LoginOK.memberId}&type=MEMBER'>
           </c:if>
        </TD>
        
        <c:if test="${ !debug }" >
          <TD width='360'>&nbsp;</TD>
        </c:if>
        
        <TD align="right">
            <TABLE style="color:blue;">
              <TR>
                 <TD width="40">
                     <c:if test="${ ! empty LoginOK }"> 
                         <A href="<c:url value='/_02_login/logout.jsp' />">
                                                                登出
                         </A>
                     </c:if>
                 </TD>
                 <TD width="40">
                     <c:if test="${ funcName != 'REG' }"> 
                         <A href="<c:url value='/_01_register/register.jsp' />">
                                                                     註冊
                         </A>
                     </c:if>
                     <c:if test="${ funcName == 'REG' }"> 
                                                                     註冊
                     </c:if>
                 </TD>
                 <TD width="50">
                     <c:if test="${ funcName != 'IND' }"> 
                         <A href="<c:url value='/index.jsp' />">
                                                                     回首頁
                         </A>
                     </c:if>
                 </TD>
              </TR>
            </TABLE>
        </TD>
     </TR>
     <TR>
     <TD colspan='3'>
       <HR style="color: #f00;background-color: #f00;height: 2px;">
     </TD>
     </TR>
 </TABLE>