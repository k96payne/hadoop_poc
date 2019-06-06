package org.galatea.hadooppoc.rest;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.galatea.hadooppoc.spark.SwapDataAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class SparkController {

//	@Autowired
//	private SwapDataAccessor accessor;
//
//	@RequestMapping("/spark")
//	public Dataset<Row> test() {
//		// try (SwapDataAccessor accessor = SwapDataAccessor.newDataAccessor()) {
//		return accessor.joinSwapPositionsAndContracts();
//		// }
//
//	}
}
