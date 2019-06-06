package org.galatea.hadooppoc.hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HdfsApplication {

	@SneakyThrows
	public static void main(final String[] args) {
		HdfsAccessor accessor = HdfsAccessor.newDefaultHdfsAccessor();
		// accessor.getFileSystem().get
		FileSystem fs = accessor.getFileSystem();
		for (FileStatus f : fs.listStatus(new Path("/"))) {
			System.out.println(f.getPath().getName());
		}
	}

}
