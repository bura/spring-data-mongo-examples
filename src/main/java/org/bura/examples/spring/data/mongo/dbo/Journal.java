package org.bura.examples.spring.data.mongo.dbo;


import java.util.List;


public class Journal {

	private String id;

	private String name;

	private List<JournalRecord> records;

	private Integer recordCount;

	public Journal() {}

	public Journal(String id, String name, List<JournalRecord> records) {
		this.id = id;
		this.name = name;
		this.records = records;
		if (records != null) {
			this.recordCount = records.size();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<JournalRecord> getRecords() {
		return records;
	}

	public void setRecords(List<JournalRecord> records) {
		this.records = records;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

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
