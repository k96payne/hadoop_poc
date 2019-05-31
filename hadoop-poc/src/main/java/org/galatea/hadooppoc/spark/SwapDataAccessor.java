package org.galatea.hadooppoc.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.galatea.hadooppoc.spark.SwapDataFiles.SwapDataFilename;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapDataAccessor implements AutoCloseable {

	private SparkSession sparkSession;

	private SwapDataAccessor() {
		sparkSession = SparkSession.builder().appName("SwapDataAccessor").getOrCreate();
	}

	public static SwapDataAccessor newDataAccessor() {
		return new SwapDataAccessor();
	}

	public void initializeSwapData(final SwapDataFiles files) {
		log.info("Initializing Swap Data for selected files in path ".concat(files.getPath()));
		for (SwapDataFilename filename : files.getFilenames()) {
			Dataset<Row> jsonDataset = sparkSession.read().option("multiLine", true)
					.json(constructJsonFilePath(files.getPath(), filename.getFilename()));
			jsonDataset.createOrReplaceTempView(filename.getFilename());
			log.info("Spark Session created view ".concat(filename.getFilename()));
		}
	}

	private String constructJsonFilePath(final String path, final String fileName) {
		StringBuilder builder = new StringBuilder(path);
		return builder.append(fileName).append(".json").toString();
	}

	public void executeSql(final String sqlCommand) {
		log.info("Executing SQL command ".concat(sqlCommand));
		Dataset<Row> dataset = sparkSession.sql(sqlCommand);
		dataset.show();
	}

	@Override
	@SneakyThrows
	public void close() {
		sparkSession.close();
	}

}
