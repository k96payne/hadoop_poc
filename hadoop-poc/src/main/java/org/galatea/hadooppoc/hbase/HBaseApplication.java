package org.galatea.hadooppoc.hbase;

import org.apache.hadoop.hbase.TableName;

public class HBaseApplication {

	private static final HBaseAccessor hBaseAccessor = HBaseAccessor.newDefaultAccessor();

	public static void main(final String[] args) {

	}

	private static void deleteTable(final String tableName) {
		try {
			hBaseAccessor.deleteTable(TableName.valueOf(tableName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void putTable(final String tableName, final String... columnFamilies) {
		try {
			hBaseAccessor.putTable(HBaseTableDescriptorCreator.newTableDescriptorCreator()
					.createDescriptor("javaHbaseTable", "columnFamily1", "columnFamily2"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
