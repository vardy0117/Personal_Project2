package notice;

import java.sql.Date;

public class NoticeBean {

	private int nno;
	private String writer;
	private String title;
	private String content;
	private String file;
	private int read_count;
	private Date date;
	
	public NoticeBean() {}
	public NoticeBean(int nno, String writer, String title, String content, String file, int read_count, Date date) {
		this.nno = nno;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.file = file;
		this.read_count = read_count;
		this.date = date;
	}
	public int getNno() {
		return nno;
	}
	public void setNno(int nno) {
		this.nno = nno;
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
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
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
	@Override
	public String toString() {
		return "NoticeBean [nno=" + nno + ", writer=" + writer + ", title=" + title + ", content=" + content + ", file="
				+ file + ", read_count=" + read_count + ", date=" + date + "]";
	}
	
	
	
}
