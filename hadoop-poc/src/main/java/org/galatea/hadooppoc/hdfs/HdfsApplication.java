package org.galatea.hadooppoc.hdfs;

import java.io.File;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HdfsApplication {

	@SneakyThrows
	public static void main(final String[] args) {

		FileSystem fs = FileSystemFactory.newDefaultFileSystem();

		FilenameConstructor constructor = FilenameConstructor.newConstructor();
		String filename = constructor.constructFilename("instruments", "");

		Path path = new Path("/cs/data/instrument/" + filename);

		// fs.createNewFile(path);

		// System.out.println(fs.getConf().get("dfs.client.use.datanode.hostname"));
		// System.out.println(fs.getConf().get("dfs.encrypt.data.transfer"));

		FileWriter writer = FileWriter.newFileWriter(fs);
		// writer.addFile(path, new
		// File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/instruments_A.json"));
		// writer.appendFile(path, new
		// File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/instruments.json"));
		File file = new File("C://Users/kpayne/Documents/Hadoop_AWS/swap_data_test/instruments.json");

	}

}
