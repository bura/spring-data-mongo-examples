package org.bura.examples.spring.data.mongo.dbo;


import java.util.Date;


public class JournalRecord {

	public static final Integer INFO_TYPE = 0;

	public static final Integer WARN_TYPE = 1;

	private Integer type;

	private Date createDate;

	private String text;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JournalRecord[");
		if (type != null) {
			builder.append("type=").append(type).append(", ");
		}
		if (createDate != null) {
			builder.append("createDate=").append(createDate).append(", ");
		}
		if (text != null) {
			builder.append("text=").append(text);
		}
		builder.append("]");

		return builder.toString();
	}

}
