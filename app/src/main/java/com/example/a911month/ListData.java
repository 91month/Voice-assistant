package com.example.a911month;
/**
 * �ѻ�ȡ�������ݷ�װ����
 * @author mingyue
 *
 */
public class ListData {
	public static final int  SEND = 1;
	public static final int  RECEIVE = 2;
	private String content;
	private int flag;//��ʶ
	private String time;
	
	public ListData(String content, int flag, String time) {
		setContent(content);
		setFlag(flag);
		setTime(time);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

}
