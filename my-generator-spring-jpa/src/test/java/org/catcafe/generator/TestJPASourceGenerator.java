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
		String url = "jdbc:mysql://rm-uf69svh1l840s9kd5zo.mysql.rds.aliyuncs.com/wxmall?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false"; 
		String userName = "muzusoftroot"; 
		String password = "39fIdkwls230oP"; 
		String database = "wxmall"; 
		
		JPASourceGenerator sourceGenerator = new JPASourceGenerator(
				url, userName, password, database, 
				"/flt/java", "com.springpool.wxmall");
		
		String singleTable = "memb_member";
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
