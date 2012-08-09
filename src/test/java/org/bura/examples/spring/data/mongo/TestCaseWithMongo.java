package org.bura.examples.spring.data.mongo;


import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;


/**
 * Base class for test cases with MongoDB. Provides starting and stopping MongoDB service.
 */
public class TestCaseWithMongo {

	public static final int DB_PORT = 12345;

	public static final String DEFAULT_DB_NAME = "test";

	private MongodExecutable mongodExe;

	private MongodProcess mongod;

	private Mongo mongo;

	/**
	 * For each case own DB .
	 */
	@Before
	public void setUp() throws IOException {
		MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
		mongodExe = runtime.prepare(new MongodConfig(Version.V2_0_5, DB_PORT, true));
		mongod = mongodExe.start();
		mongo = new Mongo("localhost", DB_PORT);
	}

	@After
	public void tearDown() throws Exception {
		mongod.stop();
		mongodExe.cleanup();
	}

	public Mongo getMongo() {
		return mongo;
	}

	public MongoTemplate getMongoTemplate() {
		return new MongoTemplate(this.getMongo(), DEFAULT_DB_NAME);
	}

	public MongoTemplate getMongoTemplate(String dbName) {
		return new MongoTemplate(this.getMongo(), dbName);
	}

}
