package org.bura.examples.spring.data.mongo;


import java.util.Arrays;

import org.bura.examples.spring.data.mongo.dbo.Journal;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class UpdateIncTest extends TestCaseWithMongo {

	private static final Logger log = LoggerFactory.getLogger(UpdatePullTest.class);

	@Test
	public void testUpdateInc() {
		MongoTemplate mng = getMongoTemplate();
		Journal journal = new Journal("J-1", null);
		mng.insert(journal);

		journal = mng.findOne(Query.query(Criteria.where("_id").is(journal.getId())), Journal.class);
		log.info("Before inc: " + journal);

		mng.updateFirst(Query.query(Criteria.where("_id").is(journal.getId())), new Update().inc("recordCount", 2),
				Journal.class);

		journal = mng.findOne(Query.query(Criteria.where("_id").is(journal.getId())), Journal.class);
		log.info("After inc: " + journal);
		
		Assert.assertNotNull(journal);
		Assert.assertEquals(journal.getRecordCount(), Integer.valueOf(2));
	}
	
	@Test
	public void testUpdateDec() {
		MongoTemplate mng = getMongoTemplate();
		Journal journal = new Journal("J-1", null);
		journal.setRecordCount(10);
		mng.insert(journal);

		journal = mng.findOne(Query.query(Criteria.where("_id").is(journal.getId())), Journal.class);
		log.info("Before dec: " + journal);

		mng.updateFirst(Query.query(Criteria.where("_id").is(journal.getId())), new Update().inc("recordCount", -5),
				Journal.class);

		journal = mng.findOne(Query.query(Criteria.where("_id").is(journal.getId())), Journal.class);
		log.info("After dec: " + journal);
		
		Assert.assertNotNull(journal);
		Assert.assertEquals(journal.getRecordCount(), Integer.valueOf(5));
	}

}
