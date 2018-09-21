package org.catcafe.generator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.catcafe.generator.jpa.JPASourceGenerator;
import org.catcafe.generator.jpa.MysqlInfoUtil;
import org.catcafe.generator.jpa.TableInfo;
import org.catcafe.generator.util.JsonUtil;
import org.junit.Test;

public class TestJPASourceGenerator {

	static String url = "jdbc:mysql://c3hkngd9.2324.dnstoo.com:5509/v14np2kw?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false"; 
	static String userName = "v14np2kw_f"; 
	static String password = "v14np2kw"; 
	static String database = "v14np2kw"; 
	
	@Test
	public void generatorSource() throws IOException, SQLException {
		JPASourceGenerator sourceGenerator = new JPASourceGenerator(
				url, userName, password, database, 
				"/flt/java", "com.springpool.wxmall");
		
		String[] singleTable = new String[] {"test"};
		if(singleTable.length==0) {
			MysqlInfoUtil mysqlInfoUtil = new MysqlInfoUtil(url, userName, password, database);
			List<String> tableNames = mysqlInfoUtil.getTableNames();
			for(String tableName : tableNames) {
				sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
				sourceGenerator.generateRepository("repository.flt", "dao.repository", "dao.entity", tableName, false);
			}
		}else {
			for(String tableName : singleTable) {
				sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
				sourceGenerator.generateRepository("repository.flt", "dao.repository", "dao.entity", tableName, false);
			}
		}
	}
	
	@Test
	public void test() throws SQLException {
		MysqlInfoUtil mysqlInfoUtil = new MysqlInfoUtil(url, userName, password, database);
		TableInfo tableInfo = mysqlInfoUtil.getTableInfo("test1").process();
		System.out.println(JsonUtil.obj2json(tableInfo));
	}
}
