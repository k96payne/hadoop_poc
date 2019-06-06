package org.galatea.hadooppoc.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class HdfsAccessor {

	private static final String DEFAULT_FS = "hdfs://ec2-18-222-172-50.us-east-2.compute.amazonaws.com:9000";

	private Configuration configuration;
	private FileSystem fileSystem;

	@SneakyThrows
	private HdfsAccessor(final String defaultFs) {
		configuration = new Configuration();
		configuration.set("fs.defaultFS", defaultFs);
		configuration.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
		configuration.set("fs.file.impl", LocalFileSystem.class.getName());
		fileSystem = FileSystem.get(configuration);
	}

	public static HdfsAccessor newDefaultHdfsAccessor() {
		return new HdfsAccessor(DEFAULT_FS);
	}
}
