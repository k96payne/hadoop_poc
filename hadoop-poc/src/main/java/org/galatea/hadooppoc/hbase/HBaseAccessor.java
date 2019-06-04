package org.galatea.hadooppoc.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class HBaseAccessor {

	private static final String ZOOKEEPER_CLIENT_PORT = "2181";
	private static final String ZOOKEEPER_QUORUM = "ec2-18-222-172-50.us-east-2.compute.amazonaws.com";
	private static final String HBASE_MASTER_HOSTNAME = "ec2-18-222-172-50.us-east-2.compute.amazonaws.com";
	private static final String HBASE_MASTER_PORT = "16000";
	private static final String ZOOKEEPER_ZNODE_PARENT = "/hbase-unsecure";

	private Configuration configuration;

	private HBaseAccessor(final Configuration configuration) {
		this.configuration = configuration;
	}

	public static HBaseAccessor newDefaultAccessor() {
		return new HBaseAccessor(getDefaultInitializedConfiguration());
	}

	private static Configuration getDefaultInitializedConfiguration() {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.property.clientPort", ZOOKEEPER_CLIENT_PORT);
		configuration.set("hbase.zookeeper.quorum", ZOOKEEPER_QUORUM);
		configuration.set("hbase.master.hostname", HBASE_MASTER_HOSTNAME);
		configuration.set("hbase.master.port", HBASE_MASTER_PORT);
		configuration.set("zookeeper.znode.parent", ZOOKEEPER_ZNODE_PARENT);
		return configuration;
	}

	@SneakyThrows
	public void checkHbaseAvailable() {
		HBaseAdmin.checkHBaseAvailable(configuration);
	}

	@SneakyThrows
	public void putTable(final HTableDescriptor tableDescriptor) {
		try (Connection connection = ConnectionFactory.createConnection(configuration)) {
			connection.getAdmin().createTable(tableDescriptor);
		}
	}

	@SneakyThrows
	public void deleteTable(final TableName tableName) {
		try (Connection connection = ConnectionFactory.createConnection(configuration)) {
			Admin admin = connection.getAdmin();
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}
	}

}
