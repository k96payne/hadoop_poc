package org.galatea.hadooppoc.hdfs;

import lombok.SneakyThrows;

public class HdfsApplication {

	@SneakyThrows
	public static void main(final String[] args) {

		HdfsAccessor accessor = HdfsAccessor.newDefaultHdfsAccessor();
		// accessor.getFileSystem().get
	}

}
