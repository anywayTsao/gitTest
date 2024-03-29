package _01_register.model;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import _00_init.*;
import _02_login.model.*;

public class RegisterServiceDB {
	private List<MemberBean> memberList;
	private DataSource ds = null;
	public RegisterServiceDB() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		LoginServiceDB db = new LoginServiceDB();
		memberList = db.getMemberList();
	}
	synchronized public boolean idExists(String id) throws IOException {
		boolean exist = false; // 檢查id是否已經存在
		for (MemberBean mb : memberList) {
			if (mb.getMemberId().equals(id.trim())) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	synchronized public int saveMember(MemberBean mb, InputStream is, int size)
			throws SQLException {
		PreparedStatement pstmt1 = null;
		Connection conn = ds.getConnection();
		int r = 0;
		try {
			String sql1 = "insert into eMember "
					+ " (memberID, name, password, address, email, tel, userType, "
					+ " experience, register, total_amt, memberImage) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, mb.getMemberId());
			pstmt1.setString(2, mb.getName());
			pstmt1.setString(3, GlobalService.getMD5Endocing(mb.getPassword()));
			pstmt1.setString(4, mb.getAddress());
			pstmt1.setString(5, mb.getEmail());
			pstmt1.setString(6, mb.getTel());
			pstmt1.setString(7, mb.getUserType());
			pstmt1.setInt(8, mb.getExpericnce());
			java.sql.Timestamp now = new java.sql.Timestamp(
					System.currentTimeMillis());
			pstmt1.setTimestamp(9, now);
			pstmt1.setDouble(10, mb.totalAmt);
			// 設定Image欄位
			// pstmt1.setBlob(11, is, size); // 此方法目前未支援  
			pstmt1.setBinaryStream(11, is, size);
			r = pstmt1.executeUpdate();
			if (r ==1) {
				// 寫入成功，應該將MemberBean mem立即加入LoginService的memberList內
				// 否則，最新的User將無法登入
				mb.setPassword(GlobalService.getMD5Endocing(mb.getPassword()));
				memberList.add(mb);
			} else {
				throw new SQLException("RegisterServiceDB:新增記錄數 : 0");
			}
			//System.out.println("新增一筆eMember紀錄，是否成功=" + r);
		} finally {
			try {
				// 關閉相關的物件
				if (pstmt1 != null) { 
					pstmt1.close();
				}
				if (conn != null) { 
					conn.close();
				}
			} catch (Exception e) {
				System.err.println("關閉相關物件時發生例外: " + e);
			}
		}
		return r;
	}
}
