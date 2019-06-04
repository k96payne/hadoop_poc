package org.galatea.hadooppoc.spark;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SwapDataFiles {

	private String path;
	private Collection<SwapDataFilename> filenames;

	public static SwapDataFiles newIncludeAllSwapDataFiles(final String path) {
		return new SwapDataFiles(path, generateSwapDataFilenames());
	}

	private static Collection<SwapDataFilename> generateSwapDataFilenames() {
		Collection<SwapDataFilename> filenames = new ArrayList<>();
		for (SwapDataFilename filename : SwapDataFilename.values()) {
			filenames.add(filename);
		}
		return filenames;
	}

	@AllArgsConstructor
	@Getter
	public static enum SwapDataFilename {
		BACK_OFFICE_POSITIONS("back_office_positions"), CASH("cash"), DEPOT_POSITIONS("depot_positions"),
		FRONT_OFFICE_POSITIONS("front_office_positions"), ORDER_EXECUTIONS("order_executions"), PRICES("prices"),
		STOCK_LOAN_POSITIONS("stock_loan_positions"), SWAP_CONTRACTS("swap_contracts"),
		SWAP_POSITIONS("swap_positions"), INST_REFS("inst_refs");

		private String filename;
	}

}
