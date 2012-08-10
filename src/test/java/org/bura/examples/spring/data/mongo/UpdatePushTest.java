package org.bura.examples.spring.data.mongo;


import java.util.Arrays;
import java.util.List;

import org.bura.examples.spring.data.mongo.dbo.Journal;
import org.bura.examples.spring.data.mongo.dbo.JournalRecord;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class UpdatePushTest extends TestCaseWithMongo {

	private static final Logger log = LoggerFactory.getLogger(UpdatePushTest.class);

	@Test
	public void testUpdatePushManyAndInc() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", Arrays.asList(new JournalRecord(JournalRecord.INFO, "text1"))));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before push: " + list);

		JournalRecord[] recs = new JournalRecord[] {new JournalRecord(JournalRecord.ERROR, "text2"),
				new JournalRecord(JournalRecord.INFO, "text3")};
		mng.updateMulti(Query.query(Criteria.where("_id").is("1")),
				new Update().pushAll("records", recs).inc("recordCount", recs.length), Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 1);
		Journal journal = list.get(0);
		Assert.assertNotNull(journal.getRecords());
		Assert.assertEquals(journal.getRecords().size(), 3);
		Assert.assertNotNull(journal.getRecordCount());
		Assert.assertEquals(journal.getRecords().size(), journal.getRecordCount().intValue());
	}

	@Test
	public void testUpdatePushMany() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", Arrays.asList(new JournalRecord(JournalRecord.INFO, "text1"))));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before push: " + list);

		mng.updateMulti(
				Query.query(Criteria.where("_id").is("1")),
				new Update().pushAll("records", new JournalRecord[] {new JournalRecord(JournalRecord.ERROR, "text2"),
						new JournalRecord(JournalRecord.INFO, "text3")}), Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 1);
		Journal journal = list.get(0);
		Assert.assertNotNull(journal.getRecords());
		Assert.assertEquals(journal.getRecords().size(), 3);
	}

	@Test
	public void testUpdatePushOne() {
		MongoTemplate mng = getMongoTemplate();
		mng.insert(new Journal("1", "J-1", null));

		List<Journal> list = mng.findAll(Journal.class);
		log.info("Before push: " + list);

		mng.updateMulti(Query.query(Criteria.where("_id").is("1")),
				new Update().push("records", new JournalRecord(JournalRecord.ERROR, "text1-1")), Journal.class);

		list = mng.findAll(Journal.class);
		log.info("After pull: " + list);

		Assert.assertEquals(list.size(), 1);
		Journal journal = list.get(0);
		Assert.assertNotNull(journal.getRecords());
		Assert.assertEquals(journal.getRecords().size(), 1);
	}

}
