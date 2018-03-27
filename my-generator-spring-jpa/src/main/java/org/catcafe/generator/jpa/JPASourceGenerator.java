/*
 * 1）表的数据库说明还未读取
 * 2）数据库部分text类的字段类型：text，tinytext，longtext，mediumetext需要标注@Lob，还未实现
 * 3）主键的生成策略目前写死自增长，实际不是的情况有问题
 */
package org.catcafe.generator.jpa;

import static org.catcafe.generator.jpa.FreeMarkerUtils.generateFileByFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class JPASourceGenerator {
	
	private String basePackage;
	private Configuration config;
	private MysqlInfoUtil mysqlInfoUnit;
	
	public JPASourceGenerator(Configuration config, String basePackage) throws IOException 
	{
		this.config = config;
		this.basePackage = basePackage;
	}
	
	public JPASourceGenerator(String templateBasePath, String basePackage) throws IOException 
	{
		Configuration cfg = new Configuration(Configuration.getVersion());
		cfg.setClassForTemplateLoading(this.getClass(), templateBasePath);
		//cfg.setTemplateLoader(new ClassTemplateLoader(SourceGenerator.class, "/flt/java"));
		//cfg.setDirectoryForTemplateLoading(new File("/src/main/resource/flt/java"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		this.config = cfg;
		this.basePackage = basePackage;
	}
	
	public JPASourceGenerator(String url, String userName, String password, String database, String templateBasePath, String basePackage) throws IOException 
	{
		Configuration cfg = new Configuration(Configuration.getVersion());
		cfg.setClassForTemplateLoading(this.getClass(), templateBasePath);
		//cfg.setTemplateLoader(new ClassTemplateLoader(SourceGenerator.class, "/flt/java"));
		//cfg.setDirectoryForTemplateLoading(new File("/src/main/resource/flt/java"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		this.config = cfg;
		this.basePackage = basePackage;
		this.mysqlInfoUnit = new MysqlInfoUtil(url, userName, password, database);
	}

	public void generateEntity(String entityTemplateFileName, String entityPackage, String tableName, boolean overwrite, String baseEntityPackage, String baseEntityName){
		try {
			if (this.getMysqlInfoUnit() != null) {
				generateEntity(entityTemplateFileName, entityPackage, this.getMysqlInfoUnit().getTableInfo(tableName).process(), overwrite, baseEntityPackage, baseEntityName);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateEntity(String entityTemplateFileName, String entityPackage, TableInfo tableInfo, boolean overwrite, String baseEntityPackage, String baseEntityName) throws IOException, TemplateException {
		try {
			String tableName = tableInfo.getName();
			String className = tableInfo.getJavaClassName();
			String destFolderPath = (this.basePackage + "." + entityPackage).replace('.', '/');
			String destFilePath = "src/main/java/" + destFolderPath + "/" + className + ".java"; 
			
			String baseEntityImport = null;
			if(StringUtils.isNotBlank(baseEntityPackage) && StringUtils.isNotBlank(baseEntityName)) {
				baseEntityImport = baseEntityPackage + "." + baseEntityName + ";";
			}
			
			EntityModel entityModel = new EntityModel();
			entityModel.setBasePackage(basePackage);
			entityModel.setEntityPackage(entityPackage);
			entityModel.setBaseEntityImport(baseEntityImport);
			entityModel.setTableName(tableName);
			entityModel.setClassName(className);
			entityModel.setBaseEntityName(baseEntityName);
			entityModel.setTableInfo(tableInfo);
			entityModel.processImportClassList();
			
			generateFileByFile(entityTemplateFileName, destFilePath, this.config, entityModel, overwrite);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateRepository() {
		//TODO
	}
	
	public static class EntityModel{
		private String basePackage;
		private String entityPackage;
		private String baseEntityImport;
		private String tableName;
		private String className;
		private String baseEntityName;
		private List<String> importClassList = new ArrayList<String>();
		private TableInfo tableInfo;
		
		public void processImportClassList() {
			this.importClassList = new ArrayList<String>();
			if(null!=tableInfo && tableInfo.getColumnInfos()!=null) {
				List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
				for(ColumnInfo ci : columnInfos) {
					if(!importClassList.contains(ci.getJavaClass())) {
						importClassList.add(ci.getJavaClass());
					}
				}
			}
		}
		public String getBasePackage() {
			return basePackage;
		}
		public void setBasePackage(String basePackage) {
			this.basePackage = basePackage;
		}
		public String getEntityPackage() {
			return entityPackage;
		}
		public void setEntityPackage(String entityPackage) {
			this.entityPackage = entityPackage;
		}
		public String getBaseEntityImport() {
			return baseEntityImport;
		}
		public void setBaseEntityImport(String baseEntityImport) {
			this.baseEntityImport = baseEntityImport;
		}
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getBaseEntityName() {
			return baseEntityName;
		}
		public void setBaseEntityName(String baseEntityName) {
			this.baseEntityName = baseEntityName;
		}
		public List<String> getImportClassList() {
			return importClassList;
		}
		public void setImportClassList(List<String> importClassList) {
			this.importClassList = importClassList;
		}
		public TableInfo getTableInfo() {
			return tableInfo;
		}
		public void setTableInfo(TableInfo tableInfo) {
			this.tableInfo = tableInfo;
		}
	}
	
	public static class EntityAttribute{
		private String typeClassName;
		private String name;
		public String getTypeClassName() {
			return typeClassName;
		}
		public void setTypeClassName(String typeClassName) {
			this.typeClassName = typeClassName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 数据库命名变为Java类名
	 * 将aa_bb的格式变为AaBb
	 * @param dbName
	 * @return
	 */
	private String dbName2JavaClassName(String dbName) {
		String[] strArr = dbName.split("_");
		StringBuilder sb = new StringBuilder();
		for(String str : strArr) {
			sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
		}
		return sb.toString();
	}
	
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	public Configuration getConfig() {
		return config;
	}
	public void setConfig(Configuration config) {
		this.config = config;
	}
	public MysqlInfoUtil getMysqlInfoUnit() {
		return mysqlInfoUnit;
	}
	public void setMysqlInfoUnit(MysqlInfoUtil mysqlInfoUnit) {
		this.mysqlInfoUnit = mysqlInfoUnit;
	}
	
	public static void main(String[] args) throws IOException, TemplateException, SQLException 
	{
		String url = "jdbc:mysql://rm-uf69svh1l840s9kd5zo.mysql.rds.aliyuncs.com/wxmall?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false"; 
		String userName = "muzusoftroot"; 
		String password = "39fIdkwls230oP"; 
		String database = "wxmall"; 
		
		JPASourceGenerator sourceGenerator = new JPASourceGenerator(
				url, userName, password, database, 
				"/flt/java", "com.springpool.wxmall");
		
		MysqlInfoUtil mysqlInfoUtil = new MysqlInfoUtil(url, userName, password, database);
		List<String> tableNames = mysqlInfoUtil.getTableNames();
		for(String tableName : tableNames) {
			sourceGenerator.generateEntity("entity.flt", "dao.entity", tableName, true, "dao.entity", "BaseEntity");
		}
	}
}
