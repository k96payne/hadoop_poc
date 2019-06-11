package org.galatea.hadooppoc.hdfs;

import java.io.File;

import org.apache.hadoop.fs.FileSystem;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HdfsApplication {

	@SneakyThrows
	public static void main(final String[] args) {

		FileSystem fs = FileSystemFactory.newDefaultFileSystem();
		UpstreamDataManager manager = UpstreamDataManager.newManager(FileWriter.newFileWriter(fs));
		manager.manageData(new File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/instruments.json"));
		manager.manageData(new File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/counterparty.json"));
		manager.manageData(new File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/swapHeader.json"));
		manager.manageData(new File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/legalEntity.json"));
	}

}
