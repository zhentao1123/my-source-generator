package org.catcafe.generator.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.catcafe.generator.util.JsonUtil;

/**
 * 数据库工具类
 * 
 * @author zhangzhengtao
 *
 */
public class MysqlInfoUtil {

	Log log = LogFactory.getLog(MysqlInfoUtil.class);

	private String driver;
	private String url;
	private String username;
	private String password;
	private String database;

	private Connection conn = null;

	public MysqlInfoUtil() {
	}

	public MysqlInfoUtil(String url, String userName, String password, String database, String driver) {
		this.url = url;
		this.username = userName;
		this.password = password;
		this.database = database;
		this.driver = driver;
	}
	
	public MysqlInfoUtil(String url, String userName, String password, String database) {
		this.url = url;
		this.username = userName;
		this.password = password;
		this.database = database;
		this.driver = "com.mysql.jdbc.Driver";
	}

	private Connection openConn() throws SQLException {
		if (null == conn) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(url, username, password);
		}
		return conn;
	}

	private void closeConn() throws SQLException {
		if (null != conn && !conn.isClosed()) {
			conn.close();
			conn = null;
		}
	}

	public List<String> getTableNames() throws SQLException {
		List<String> tableNames = new ArrayList<String>();
		Connection conn = openConn();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show table status from " + database);

		while (rs.next()) {
			String comment = rs.getString("Comment");
			if(StringUtils.isNoneBlank(comment) && comment.contains("VIEW")) { //过滤视图
				continue;
			}else {
				String tableName = rs.getString("Name");
				tableNames.add(tableName);
				// log.info(tableName);
			}
		}

		rs.close();
		stmt.close();
		closeConn();
		return tableNames;
	}

	public TableInfo getTableInfo(String tableName) throws SQLException {

		TableInfo tableInfo = new TableInfo();
		tableInfo.setName(tableName);
		List<ColumnInfo> tableFieldList = new ArrayList<ColumnInfo>();

		Connection conn = openConn();
		Statement stmt = conn.createStatement();

		//获取表信息（注释）
		ResultSet rs2 = stmt.executeQuery("show table status where name = '" + tableName + "'");		
		if(rs2.next()) {
			tableInfo.setComment(rs2.getString("Comment"));
		}
		rs2.close();
		
		//获取字段信息
		ResultSet rs = stmt.executeQuery("select * from `" + tableName + "` where 1=2");		
		ResultSetMetaData rsmd = rs.getMetaData();
		ResultSet rs1 = stmt.executeQuery("show full columns from " + tableName);

		int i = 0;
		while (rs1.next()) {
			i++;
			ColumnInfo columnInfo = new ColumnInfo();

			columnInfo.setIndex(i);
			columnInfo.setField(rs1.getString("Field").replaceAll(" ", ""));
			String type = rs1.getString("Type");
			if (type.contains("(")) {
				columnInfo.setType(type.substring(0, type.indexOf("(")));
				columnInfo.setLength(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
			}else {
				columnInfo.setType(type);
			}
			columnInfo.setComment(rs1.getString("Comment"));
			columnInfo.setJavaClass(rsmd.getColumnClassName(i));
			columnInfo.setPk("PRI".equals(rs1.getString("Key")));
			columnInfo.setExtra(rs1.getString("Extra"));
			columnInfo.setRequire(!"YES".equals(rs1.getString("Null")));

			tableFieldList.add(columnInfo);

			if ("PRI".equals(rs1.getString("Key"))) {
				tableInfo.setPk(columnInfo);
			}
		}
		tableInfo.setColumnInfos(tableFieldList);

		rs.close();
		rs1.close();
		closeConn();
		stmt.close();
		log.info("--------------------------------------------");
		log.info(JsonUtil.obj2json(tableInfo));
		log.info("--------------------------------------------");
		return tableInfo;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
