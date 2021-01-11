package board;

import java.sql.Date;

public class BoardBean {
	
	
	private int bno;
	private String writer;
	private String title;
	private String content;
	private String file;
	private int read_count;
	private Date date;
	
	public BoardBean() {}
	public BoardBean(int bno, String writer, String title, String content) {
		this.bno = bno;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	
	public BoardBean(int bno, String writer, String title, String content, int read_count, Date date) {
		this.bno = bno;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.read_count = read_count;
		this.date = date;
	}
	public BoardBean(int bno, String writer, String title, String content, String file, int read_count, Date date) {
		this.bno = bno;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.file = file;
		this.read_count = read_count;
		this.date = date;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "BoardBean [bno=" + bno + ", writer=" + writer + ", title=" + title + ", content=" + content + ", file="
				+ file + ", read_count=" + read_count + ", date=" + date + "]";
	}

	
}
