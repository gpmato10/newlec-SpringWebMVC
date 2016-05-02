package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.Notice;

public class NoticeDao {
	
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT FROM NOTICES WHERE "+field+" LIKE ?";
		
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		
		// 3. 결과
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int cnt = rs.getInt("cnt");
		
		rs.close();
		st.close();
		con.close();
		
		return cnt;
	}
	
	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException
	{

		int srow = 0 + (page-1) * 15;
		int erow = 14 + (page-1) * 15;
		
//		String sql = "SELECT * FROM";
//		sql += "(SELECT ROWNUM NUM, N.* FROM (SELECT * FROM NOTICES WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N)";
//		sql += "WHERE NUM BETWEEN ? AND ?";
		String sql = "select * from";
		sql += " notices where " + field + " like ? order by regdate desc limit ?,?";

		// 0. 드라이버 로드
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, srow);
		st.setInt(3, erow);
		// 3. 결과
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while(rs.next()){
			Notice n = new Notice();
			n.setSeq(rs.getString("seq"));
			n.setTitle(rs.getString("title"));
			n.setWriter(rs.getString("writer"));
			n.setContent(rs.getString("content"));
			n.setRegdate(rs.getDate("regdate"));
			n.setHit(rs.getInt("hit"));
			n.setFileSrc(rs.getString("filesrc"));
			
			list.add(n);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		// 2. 데이터 베이스 연동을 위한 쿼리와 실행 코드 작성
		String sql = "DELETE FROM NOTICES WHERE SEQ=?";
		// 0. 드라이버 로드
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);	
		st.setString(1, seq);
		
		int af = st.executeUpdate();
		
		return af;
	}
	
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		// 2. 데이터 베이스를 연동하기 위한 쿼리와 데이터베이스 연동을 위한 코드를 작성
		String sql = "UPDATE NOTICES SET TITLE=?, CONTENT=?, FILESRC=? WHERE SEQ=?";
		// 0. 드라이버 로드
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getFileSrc());
		st.setString(4, notice.getSeq());		
		
		int af = st.executeUpdate();
		
		return af;
	}
	
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * FROM NOTICES WHERE SEQ="+seq;
		// 0. 드라이버 로드
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		Statement st = con.createStatement();
		// 3. 결과
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		
		Notice n = new Notice();
		n.setSeq(rs.getString("seq"));
		n.setTitle(rs.getString("title"));
		n.setWriter(rs.getString("writer"));
		n.setRegdate(rs.getDate("regdate"));
		n.setHit(rs.getInt("hit"));
		n.setContent(rs.getString("content"));
		n.setFileSrc(rs.getString("fileSrc"));
		
		rs.close();
		st.close();
		con.close();
		
		return n;
	}

	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO NOTICES(TITLE, CONTENT, WRITER, HIT, FILESRC) \n" +
				"VALUES(?, ?, 'newlec', HIT, ?)";
		// 0. 드라이버 로드
		Class.forName("com.mysql.jdbc.Driver");
		// 1. 접속
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/newlecspring",
				"root", "123123");
		// 2. 실행
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, n.getTitle());
		st.setString(2, n.getContent());
		st.setString(3, n.getFileSrc());
		
		int af = st.executeUpdate();
		
		st.close();
		con.close();
		
		return af;
	}
}
