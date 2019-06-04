package org.galatea.hadooppoc;

import java.util.Scanner;

import org.galatea.hadooppoc.spark.SwapDataAccessor;
import org.galatea.hadooppoc.spark.SwapDataFiles;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

	@SneakyThrows
	public static void main(final String[] args) {

		Scanner userInput = new Scanner(System.in);
		checkForArgs(args);
		SwapDataFiles dataFiles = SwapDataFiles.newIncludeAllSwapDataFiles(args[0]);

		try (SwapDataAccessor accessor = SwapDataAccessor.newDataAccessor()) {
			accessor.initializeSwapData(dataFiles);
			while (true) {
				log.info("Waiting for user input...");
				String input = userInput.nextLine();
				if (input.equals("quit")) {
					break;
				} else {
					executeUserCommand(accessor, input);
				}
			}
		}

		userInput.close();

	}

	private static void executeUserCommand(final SwapDataAccessor accessor, final String command) {
		try {
			accessor.executeSql(command);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Invalid Input");
		}
	}

	private static void checkForArgs(final String[] args) {
		if (args.length == 0) {
			log.error("Usage: *.jar <path to data files>");
		}
	}

}
