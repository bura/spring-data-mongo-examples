package org.bura.examples.spring.data.mongo;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class MapFieldTest extends TestCaseWithMongo {
	
	private static final Logger log = LoggerFactory.getLogger(UpdatePullTest.class);

	@Test
	public void test() {
		MongoTemplate mng = getMongoTemplate();
		Map<String, Boolean> map = new HashMap<>();
		map.put("1", true);
		map.put("2", false);
		mng.insert(new MyItem("1", map));
		log.info("After insert: " + mng.findAll(MyItem.class));
		
		// push imitation
		mng.updateFirst(Query.query(Criteria.where("id").is("1")),
				new Update().set("items.3", true), MyItem.class);
		log.info("After set: " + mng.findAll(MyItem.class));

		// pull imitation
		mng.updateFirst(Query.query(Criteria.where("id").is("1")),
				new Update().unset("items.1"), MyItem.class);
		log.info("After unset: " + mng.findAll(MyItem.class));
		
		// slice imitation
		Query query = Query.query(Criteria.where("id").is("1"));
		query.fields().include("id").include("items.2");
		MyItem item = mng.findOne(query, MyItem.class);
		log.info("item : " + item);
	}

}

class MyItem {

	private String id;

	private Map<String, Boolean> items;

	public MyItem(String id, Map<String, Boolean> items) {
		this.id = id;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Boolean> getItems() {
		return items;
	}

	public void setItems(Map<String, Boolean> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyItem [");
		if (id != null) {
			builder.append("id=").append(id).append(", ");
		}
		if (items != null) {
			builder.append("items=").append(items);
		}
		builder.append("]");
		return builder.toString();
	}

}