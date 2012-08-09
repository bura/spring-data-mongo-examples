package org.bura.examples.spring.data.mongo;


import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.bura.examples.spring.data.mongo.dbo.Journal;
import org.bura.examples.spring.data.mongo.dbo.JournalRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class UpdatePullTest extends TestCaseWithMongo {

	private static final Logger log = LoggerFactory.getLogger(UpdatePullTest.class);

	@Test
	public void testUpdatePullMultipleCriteria() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", Arrays.asList(new JournalRecord(JournalRecord.INFO, "text1-1"),
				new JournalRecord(JournalRecord.ERROR, "text1-2"), new JournalRecord(JournalRecord.ERROR, "text1-3"),
				new JournalRecord(JournalRecord.INFO, "text1-4"), new JournalRecord(JournalRecord.ERROR, "text1-5"))));
		mng.insert(new Journal("2", "J-2", Arrays.asList(new JournalRecord(JournalRecord.ERROR, "text2-1"),
				new JournalRecord(JournalRecord.INFO, "text2-2"), new JournalRecord(JournalRecord.ERROR, "text2-3"),
				new JournalRecord(JournalRecord.INFO, "text2-4"), new JournalRecord(JournalRecord.INFO, "text2-5"))));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before pull: " + list);

		mng.updateMulti(
				null,
				new Update().pull("records", Criteria.where("type").is(JournalRecord.ERROR).and("text").regex("^.*3$")
						.getCriteriaObject()), Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 2);
		for (Journal journal : list) {
			for (JournalRecord record : journal.getRecords()) {
				Assert.assertFalse(record.getType().equals(JournalRecord.ERROR) && record.getText().endsWith("3"));
			}
		}
	}

	@Test
	public void testMultipleUpdatePull() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", Arrays.asList(new JournalRecord(JournalRecord.INFO, "text1-1"),
				new JournalRecord(JournalRecord.ERROR, "text1-2"), new JournalRecord(JournalRecord.ERROR, "text1-3"),
				new JournalRecord(JournalRecord.INFO, "text1-4"), new JournalRecord(JournalRecord.ERROR, "text1-5"))));
		mng.insert(new Journal("2", "J-2", Arrays.asList(new JournalRecord(JournalRecord.ERROR, "text2-1"),
				new JournalRecord(JournalRecord.INFO, "text2-2"), new JournalRecord(JournalRecord.ERROR, "text2-3"),
				new JournalRecord(JournalRecord.INFO, "text2-4"), new JournalRecord(JournalRecord.INFO, "text2-5"))));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before pull: " + list);

		mng.updateMulti(null,
				new Update().pull("records", Criteria.where("type").is(JournalRecord.INFO).getCriteriaObject()),
				Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 2);
		for (Journal journal : list) {
			for (JournalRecord record : journal.getRecords()) {
				Assert.assertEquals(record.getType(), JournalRecord.ERROR);
			}
		}
	}

	@Test
	public void testFirstUpdatePull() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", Arrays.asList(new JournalRecord(JournalRecord.INFO, "text1"),
				new JournalRecord(JournalRecord.ERROR, "text2"), new JournalRecord(JournalRecord.ERROR, "text3"),
				new JournalRecord(JournalRecord.INFO, "text4"), new JournalRecord(JournalRecord.ERROR, "text5"))));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before pull: " + list);

		mng.updateFirst(Query.query(Criteria.where("_id").is("1")),
				new Update().pull("records", Criteria.where("type").is(JournalRecord.ERROR).getCriteriaObject()),
				Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 1);
		Assert.assertEquals(list.get(0).getRecords().size(), 2);
		for (JournalRecord record : list.get(0).getRecords()) {
			Assert.assertEquals(record.getType(), JournalRecord.INFO);
		}
	}

}
