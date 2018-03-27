package com.demo.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.lang.Boolean;
import java.lang.Integer;
import java.math.BigDecimal;
import java.lang.Float;
import java.lang.Double;
import java.lang.Byte;
import java.lang.String;
import java.util.Date;

import com.demo.entity.BaseEntity;

@Entity
@Table(name="test2")
public class Test2 extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 我是有注解的字段
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bigint_", nullable=false, length=20)
	private BigInteger bigint;
	
	/**
	 * 板凳，我是必不可少的
	 */
	@Column(name="tinyint_", nullable=false, length=1)
	private Boolean tinyint;
	
	@Column(name="smallint_", length=10)
	private Integer smallint;
	
	@Column(name="mediumint_", length=10)
	private Integer mediumint;
	
	@Column(name="int_", length=10)
	private Integer Int;
	
	@Column(name="integer_", length=10)
	private Integer integer;
	
	@Column(name="numeric_")
	private BigDecimal numeric;
	
	@Column(name="decimal_")
	private BigDecimal decimal;
	
	@Column(name="float_")
	private Float Float;
	
	@Column(name="double_")
	private Double Double;
	
	@Column(name="bit_", length=5)
	private Boolean bit;
	
	@Lob
	@Column(name="binary_", length=5)
	private byte[] binary;
	
	@Lob
	@Column(name="tinyblob_")
	private byte[] tinyblob;
	
	@Lob
	@Column(name="mediumblob_")
	private byte[] mediumblob;
	
	@Lob
	@Column(name="blob_")
	private byte[] blob;
	
	@Lob
	@Column(name="longblob_")
	private byte[] longblob;
	
	@Column(name="char_", length=10)
	private String Char;
	
	@Column(name="varchar_", length=10)
	private String varchar;
	
	@Column(name="tinytext_")
	private String tinytext;
	
	@Column(name="mediumetext_")
	private String mediumetext;
	
	@Column(name="text_")
	private String text;
	
	@Column(name="longtext_")
	private String longtext;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_", length=6)
	private Date time;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_")
	private Date date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="datetime_", length=6)
	private Date datetime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timestamp_", length=6)
	private Date timestamp;

	public Test2(){
	}
	
	public BigInteger getBigint(){
		return this.bigint;
	}
	
	public void setBigint(BigInteger bigint){
		this.bigint = bigint;
	}
	
	public Boolean getTinyint(){
		return this.tinyint;
	}
	
	public void setTinyint(Boolean tinyint){
		this.tinyint = tinyint;
	}
	
	public Integer getSmallint(){
		return this.smallint;
	}
	
	public void setSmallint(Integer smallint){
		this.smallint = smallint;
	}
	
	public Integer getMediumint(){
		return this.mediumint;
	}
	
	public void setMediumint(Integer mediumint){
		this.mediumint = mediumint;
	}
	
	public Integer getInt(){
		return this.Int;
	}
	
	public void setInt(Integer Int){
		this.Int = Int;
	}
	
	public Integer getInteger(){
		return this.integer;
	}
	
	public void setInteger(Integer integer){
		this.integer = integer;
	}
	
	public BigDecimal getNumeric(){
		return this.numeric;
	}
	
	public void setNumeric(BigDecimal numeric){
		this.numeric = numeric;
	}
	
	public BigDecimal getDecimal(){
		return this.decimal;
	}
	
	public void setDecimal(BigDecimal decimal){
		this.decimal = decimal;
	}
	
	public Float getFloat(){
		return this.Float;
	}
	
	public void setFloat(Float Float){
		this.Float = Float;
	}
	
	public Double getDouble(){
		return this.Double;
	}
	
	public void setDouble(Double Double){
		this.Double = Double;
	}
	
	public Boolean getBit(){
		return this.bit;
	}
	
	public void setBit(Boolean bit){
		this.bit = bit;
	}
	
	public byte[] getBinary(){
		return this.binary;
	}
	
	public void setBinary(byte[] binary){
		this.binary = binary;
	}
	
	public byte[] getTinyblob(){
		return this.tinyblob;
	}
	
	public void setTinyblob(byte[] tinyblob){
		this.tinyblob = tinyblob;
	}
	
	public byte[] getMediumblob(){
		return this.mediumblob;
	}
	
	public void setMediumblob(byte[] mediumblob){
		this.mediumblob = mediumblob;
	}
	
	public byte[] getBlob(){
		return this.blob;
	}
	
	public void setBlob(byte[] blob){
		this.blob = blob;
	}
	
	public byte[] getLongblob(){
		return this.longblob;
	}
	
	public void setLongblob(byte[] longblob){
		this.longblob = longblob;
	}
	
	public String getChar(){
		return this.Char;
	}
	
	public void setChar(String Char){
		this.Char = Char;
	}
	
	public String getVarchar(){
		return this.varchar;
	}
	
	public void setVarchar(String varchar){
		this.varchar = varchar;
	}
	
	public String getTinytext(){
		return this.tinytext;
	}
	
	public void setTinytext(String tinytext){
		this.tinytext = tinytext;
	}
	
	public String getMediumetext(){
		return this.mediumetext;
	}
	
	public void setMediumetext(String mediumetext){
		this.mediumetext = mediumetext;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getLongtext(){
		return this.longtext;
	}
	
	public void setLongtext(String longtext){
		this.longtext = longtext;
	}
	
	public Date getTime(){
		return this.time;
	}
	
	public void setTime(Date time){
		this.time = time;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDatetime(){
		return this.datetime;
	}
	
	public void setDatetime(Date datetime){
		this.datetime = datetime;
	}
	
	public Date getTimestamp(){
		return this.timestamp;
	}
	
	public void setTimestamp(Date timestamp){
		this.timestamp = timestamp;
	}
}