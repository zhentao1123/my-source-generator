package org.catcafe.generator.jpa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TableInfo {
	// 表名
	private String name;
	// 表转换成的Java类名
	private String javaClassName;
	// 表注释
	private String comment;
	// 主键信息
	private ColumnInfo pk;
	// 表字段信息
	private List<ColumnInfo> columnInfos;

	public TableInfo process() {
		if(StringUtils.isNotBlank(this.name)) {
			this.javaClassName = dbName2JavaClassName(this.name);
		}
		if(null!=columnInfos && columnInfos.size()>0) {
			for(ColumnInfo item : columnInfos) {
				item.process();
			}
		}
		return this;
	}
	
	public String getJavaClassName() {
		return javaClassName;
	}
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ColumnInfo getPk() {
		return pk;
	}
	public void setPk(ColumnInfo pk) {
		this.pk = pk;
	}
	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}
	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

	private String dbName2JavaClassName(String dbName) {
		String[] strArr = dbName.split("_");
		StringBuilder sb = new StringBuilder();
		for(String str : strArr) {
			sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
		}
		return sb.toString();
	}
	
	/**
	 * 构造import所需列表（去重）
	 * 
	 * @param columnInfos
	 */
	public List<String> getImportClasses() {
		List<String> javaClasses = new ArrayList<String>();
		for (ColumnInfo field : this.columnInfos) {
			String javaClass = field.getJavaClass();
			if (!javaClasses.contains(javaClass) && StringUtils.isNotBlank(javaClass)) {
				javaClasses.add(javaClass);
			}
		}
		return javaClasses;
	}
	
}
