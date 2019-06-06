package org.galatea.hadooppoc.spark;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.galatea.hadooppoc.spark.SwapDataFiles.SwapDataFilename;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapDataAccessor implements AutoCloseable {

	private SparkSession sparkSession;
	private Map<SwapDataFilename, Dataset<Row>> dataframes;

	private SwapDataAccessor() {
		sparkSession = SparkSession.builder().appName("SwapDataAccessor").getOrCreate();
		dataframes = new HashMap<>();
	}

	public static SwapDataAccessor newDataAccessor() {
		return new SwapDataAccessor();
	}

	public void initializeSwapData(final SwapDataFiles files) {
		log.info("Initializing Swap Data for selected files in path {}", files.getPath());
		for (SwapDataFilename filename : files.getFilenames()) {
			Dataset<Row> dataset = sparkSession.read().option("multiLine", true)
					.json(constructJsonFilePath(files.getPath(), filename.getFilename()));

			// jsonDataset.createOrReplaceTempView(filename.getFilename());
			// log.info("Spark Session created view ".concat(filename.getFilename()));

			// dataset.cache(); // lazy

			dataframes.put(filename, dataset);
		}
	}

	private String constructJsonFilePath(final String path, final String fileName) {
		StringBuilder builder = new StringBuilder(path);
		return builder.append(fileName).append(".json").toString();
	}

	public Dataset<Row> executeSql(final String sqlCommand) {
		log.info("Executing SQL command {}", sqlCommand);
		long startTime = System.currentTimeMillis();
		Dataset<Row> dataset = sparkSession.sql(sqlCommand);
		log.info("Execution took {} milliseconds", System.currentTimeMillis() - startTime);
		return dataset;
	}

	public void writeDataset(final Dataset<Row> dataset, final String path) {
		log.info("Writing dataset to path {}", path);
		dataset.write().mode(SaveMode.Overwrite).json(path);
	}

	public Dataset<Row> joinSwapPositionsAndContracts() {
		Dataset<Row> swapPositions = dataframes.get(SwapDataFilename.SWAP_POSITIONS);
		Dataset<Row> swapContracts = dataframes.get(SwapDataFilename.SWAP_CONTRACTS);
		log.info("Joining Swap Positions and Swap Contracts");
		long startTime = System.currentTimeMillis();
		Dataset<Row> dataset = swapPositions.join(swapContracts,
				swapPositions.col("swap_contract_id").equalTo(swapContracts.col("swap_contract_id")), "fullouter")
				.drop("swap_contract_id");
		log.info("Execution took {} milliseconds", System.currentTimeMillis() - startTime);
		dataset.show();
		return dataset;
	}

	@Override
	@SneakyThrows
	public void close() {
		sparkSession.close();
	}

}
