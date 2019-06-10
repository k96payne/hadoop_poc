package org.galatea.hadooppoc.hdfs;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class FilenameConstructor {

	private static final String FILE_EXTENSION = ".json";

	private FilenameConstructor() {

	}

	public static FilenameConstructor newConstructor() {
		return new FilenameConstructor();
	}

	public String constructFilename(final String fileName, final String filePath) {
		switch (fileName) {
		case "instruments":
			return constructInstRefsFilename();
		case "legalEntity":
			return constructLegalEntityFilename();
		case "counterparty":
			return constructCounterpartyFilename();
		case "swapHeader":
			return constructSwapHeaderFilename(filePath);
		default:
			return "a";
		}
	}

	private String constructInstRefsFilename() {
		return buildFile(BaseFilename.Filename.INST_REFS.getFilename(), FILE_EXTENSION);
	}

	private String constructLegalEntityFilename() {
		return buildFile(BaseFilename.Filename.LEGAL_ENTITY.getFilename(), FILE_EXTENSION);
	}

	private String constructCounterpartyFilename() {
		return buildFile(BaseFilename.Filename.COUNTERPARTY.getFilename(), FILE_EXTENSION);
	}

	@SneakyThrows
	private String constructSwapHeaderFilename(final String filePath) {
		String counterPartyId = extractJsonNodeData(Files.readAllBytes(Paths.get(filePath)), "counter_party_id");
		return buildFile(counterPartyId, "-", BaseFilename.Filename.SWAP_HEADER.getFilename(), FILE_EXTENSION);
	}

	@SneakyThrows
	private String extractJsonNodeData(final byte[] jsonData, final String treeNode) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonData);
		return rootNode.path(treeNode).asText();
	}

	private String buildFile(final String... fileParts) {
		StringBuilder builder = new StringBuilder();
		for (String part : fileParts) {
			builder.append(part);
		}
		return builder.toString();
	}

}
