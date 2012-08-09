package org.bura.examples.spring.data.mongo.dbo;


import java.util.List;


public class Journal {

	private String id;

	private String name;

	private List<JournalRecord> records;

	private Integer recordCount;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Journal other = (Journal) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Journal[");
		if (id != null) {
			builder.append("id=").append(id).append(", ");
		}
		if (name != null) {
			builder.append("name=").append(name).append(", ");
		}
		if (records != null) {
			builder.append("records=").append(records).append(", ");
		}
		if (recordCount != null) {
			builder.append("recordCount=").append(recordCount);
		}
		builder.append("]");

		return builder.toString();
	}

}
