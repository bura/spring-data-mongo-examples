package org.bura.examples.spring.data.mongo.dbo;


public class JournalRecord {

	public static final Integer INFO = 0;

	public static final Integer ERROR = 1;

	private Integer type;

	private String text;

	public JournalRecord() {}

	public JournalRecord(Integer type, String text) {
		this.type = type;
		this.text = text;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
		if (text != null) {
			builder.append("text=").append(text);
		}
		builder.append("]");

		return builder.toString();
	}

}
