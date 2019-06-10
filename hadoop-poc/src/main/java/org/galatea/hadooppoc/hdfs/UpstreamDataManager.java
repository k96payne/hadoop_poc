package org.galatea.hadooppoc.hdfs;

import java.io.File;

public class UpstreamDataManager {

	private FileWriter writer;

	private UpstreamDataManager(final FileWriter writer) {
		this.writer = writer;
	}

	public static UpstreamDataManager newManager(final FileWriter writer) {
		return new UpstreamDataManager(writer);
	}

	public void manageData(final File file) {
		String dataName = extractDataName(file);
		if (!dataName.isEmpty()) {

		}
	}

	private String extractDataName(final File file) {
		String filename = file.getName();
		int extensionIndex = filename.indexOf(".");
		if (extensionIndex != -1) {
			return filename.substring(0, extensionIndex);
		} else {
			return "";
		}
	}

}
