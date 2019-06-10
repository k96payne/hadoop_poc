package org.galatea.hadooppoc.hdfs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import lombok.SneakyThrows;

public class FileWriter {

	private FileSystem fileSystem;

	@SneakyThrows
	private FileWriter(final FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public static FileWriter newFileWriter(final FileSystem fileSystem) {
		return new FileWriter(fileSystem);
	}

	@SneakyThrows
	public void addFile(final Path path, final File file) {
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FSDataOutputStream outputStream = fileSystem.create(path)) {
			byte[] b = new byte[1024];
			int numBytes = 0;
			while ((numBytes = inputStream.read(b)) > 0) {
				outputStream.write(b, 0, numBytes);
			}
		}
	}

	@SneakyThrows
	public void appendFile(final Path path, final File file) {
		try (FSDataOutputStream outputStream = fileSystem.append(path);
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			byte[] b = new byte[1024];
			int numBytes = 0;
			while ((numBytes = inputStream.read(b)) > 0) {
				outputStream.write(b, 0, numBytes);
			}

		}
	}

}
