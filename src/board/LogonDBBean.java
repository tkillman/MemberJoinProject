package board;

import java.net.URLDecoder;
import java.sql.*;
import java.util.Vector;

public class LogonDBBean {

	private static LogonDBBean instance = new LogonDBBean();

	public static LogonDBBean getInstance() {
		return instance;
	}

	private LogonDBBean() {
	}

	private Connection getConnection() throws Exception {
		String jdbcDriver = "jdbc:apache:commons:dbcp:pool";

		return DriverManager.getConnection(jdbcDriver);
	}

	// 회원등록
	public int insertMember(LogonDataBean member) throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultNum = -1;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("insert into MEMBERS2 values (?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getJumin1());
			pstmt.setString(5, member.getJumin2());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getBlog());
			pstmt.setTimestamp(8, member.getReg_date());
			pstmt.setString(9, member.getZipcode());
			pstmt.setString(10, member.getAddress());
			pstmt.setString(11, member.getRealPath());
			
			
			resultNum = pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return resultNum;

	}

	public int userCheck(String id, String passwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";
		int x = -1;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select realpath,passwd from MEMBERS2 where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbpasswd = rs.getString("passwd");
				if (dbpasswd.equals(passwd))
					x = 1; // 인증 성공
				
				else
					x = 0; // 비밀번호 틀림
			} else
				x = -1;// 해당 아이디 없음

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		
		return x;
		
	}

	public int confirmId(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";

		int x = -1;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select id from MEMBERS2 where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next())
				x = 1; // 해당 아이디 있음
			else
				x = -1;// 해당 아이디 없음
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;
	}

	
	public LogonDataBean getMember(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LogonDataBean member = null;

		try {
			
			conn = getConnection();

			pstmt = conn.prepareStatement("select * from MEMBERS2 where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				member = new LogonDataBean();
				member.setId(rs.getString("id"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setJumin1(rs.getString("jumin1"));
				member.setJumin2(rs.getString("jumin2"));
				member.setEmail(rs.getString("email"));
				member.setBlog(rs.getString("blog"));
				member.setReg_date(rs.getTimestamp("reg_date"));
				member.setZipcode(rs.getString("zipcode"));
				member.setAddress(rs.getString("address"));
				member.setRealPath(rs.getString("realpath"));

			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return member;
	}

	
	public void updateMember(LogonDataBean member) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("update MEMBERS2 set passwd=?,name=?,email=?,blog=?,realpath=? " + "where id=?");
			pstmt.setString(1, member.getPasswd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getBlog());
			pstmt.setString(5, member.getId());
			pstmt.setString(6, member.getRealPath());
			
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	}

	public int deleteMember(String id, String passwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpasswd = "";
		int x = -1;
		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select passwd from MEMBERS2 where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbpasswd = rs.getString("passwd");
				if (dbpasswd.equals(passwd)) {
					pstmt = conn.prepareStatement("delete from MEMBERS2 where id=?");
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x = 1; // 회원탈퇴 성공
				} else
					x = 0; // 비밀번호 틀림
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;
	}

	public Vector<ZipcodeBean> zipcodeRead(String area3) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<ZipcodeBean> vecList = new Vector<ZipcodeBean>();
		try {
			con = getConnection();
			String strQuery = "select * from zipcode where area4 like '" + area3 + "%'";
			pstmt = con.prepareStatement(strQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ZipcodeBean tempZipcode = new ZipcodeBean();
				tempZipcode.setZipcode(rs.getString("zipcode"));
				tempZipcode.setArea1(rs.getString("area1"));
				tempZipcode.setArea2(rs.getString("area2"));
				tempZipcode.setArea3(rs.getString("area3"));
				tempZipcode.setArea4(rs.getString("area4"));
				vecList.addElement(tempZipcode);

			}

		} catch (Exception ex) {
			System.out.println("Exception" + ex);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}

		return vecList;
	}

	//비밀번호 변경하기
	public int checkUser(String id, String name, String passwd){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int resultNum=-1;
		
		try {
			con = getConnection();
			String strQuery = "select * from members2 where id=? and name=? and passwd=?";
			pstmt = con.prepareStatement(strQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, URLDecoder.decode(name , "UTF-8"));
			System.out.println(name);
			pstmt.setString(3, passwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				resultNum=1; // 일치하는 회원이 존재
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return resultNum;
	}
	
	//비밀번호 변경
	
	public int changePasswd(String id, String passwd){
		int resultNum = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			String strQuery = "update members2 set passwd=? " +" where id=?";
			pstmt = con.prepareStatement(strQuery);
			pstmt.setString(2, id);
			System.out.println(id);
			pstmt.setString(1, passwd);
			System.out.println(passwd);
			resultNum=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		System.out.println(resultNum);
		return resultNum;
		
	}
	
	
}