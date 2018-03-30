package org.catcafe.generator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.catcafe.generator.jpa.JPASourceGenerator;
import org.catcafe.generator.jpa.MysqlInfoUtil;
import org.junit.Test;

public class TestJPASourceGenerator {

	@Test
	public void generatorSource() throws IOException, SQLException {
		String url = "jdbc:mysql://c3hkngd9.2324.dnstoo.com:5509/v14np2kw?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false"; 
		String userName = "v14np2kw_f"; 
		String password = "v14np2kw"; 
		String database = "v14np2kw"; 
		
		JPASourceGenerator sourceGenerator = new JPASourceGenerator(
				url, userName, password, database, 
				"/flt/java", "com.springpool.wxmall");
		
		String singleTable = "";
		if(StringUtils.isBlank(singleTable)) {
			MysqlInfoUtil mysqlInfoUtil = new MysqlInfoUtil(url, userName, password, database);
			List<String> tableNames = mysqlInfoUtil.getTableNames();
			for(String tableName : tableNames) {
				sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
			}
		}else {
			sourceGenerator.generateEntity("entity.flt", "dao.entity", singleTable, true, "dao.entity", "BaseEntity");
		}
	}
}
