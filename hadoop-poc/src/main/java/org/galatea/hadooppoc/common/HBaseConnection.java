package org.galatea.hadooppoc.common;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.log4j.Logger;

import lombok.NoArgsConstructor;

/**
 *
 * @author Naresh Bharadwaj This class contains functionality for Creating Hbase
 *         Connection
 *
 */
@NoArgsConstructor
public class HBaseConnection {

	private static final Logger _LOGGER = Logger.getLogger(HBaseConnection.class);

	// private Boolean isKerberosEnabled;

	public Connection getConnection() throws IOException {

		Configuration conf = HBaseConfiguration.create();

		// Base
//		conf.set("hbase.zookeeper.quorum", propertyUtil.getValue("hbase.zookeeper.quorum"));
//		conf.set("hbase.zookeeper.property.clientPort", propertyUtil.getValue("hbase.zookeeper.property.clientPort"));
//		conf.set("zookeeper.znode.parent", propertyUtil.getValue("hbase.zookeeper.znode.parent"));
		conf.set("hbase.zookeeper.quorum", "ec2-user@ec2-52-15-235-199.us-east-2.compute.amazonaws.com");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		// conf.set("hbase.cluster.distributed", "true");
		conf.set("zookeeper.znode.parent", "/hbase-unsecure");

		return ConnectionFactory.createConnection(conf);
	}

	public void closeConnection(final Connection connection) {
		try {
			connection.close();
		} catch (IOException e) {
			_LOGGER.error("Error while closing connection ", e);
		}
	}

}