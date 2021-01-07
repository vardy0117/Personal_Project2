package comment;

import java.sql.Date;

public class commentBean {
	private int cno;
	private String writer;
	private String comment;
	private Date date;
	private int bno;
	public commentBean() {}
	public commentBean(int cno, String writer, String comment, Date date,int bno) {
		super();
		this.cno = cno;
		this.writer = writer;
		this.comment = comment;
		this.date = date;
		this.bno = bno;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	@Override
	public String toString() {
		return "commentBean [cno=" + cno + ", writer=" + writer + ", comment=" + comment + ", date=" + date + ", bno="
				+ bno + "]";
	}
	
	
}
