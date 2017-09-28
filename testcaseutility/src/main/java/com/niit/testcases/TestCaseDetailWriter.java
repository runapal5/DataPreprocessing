package com.niit.testcases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TestCaseDetailWriter {

	    //Delimiter used in CSV file
		private static final String COMMA_DELIMITER = ",";
		private static final String NEW_LINE_SEPARATOR = "\n";
	
		//CSV file header
		//private static final String FILE_HEADER = "ReqID,TestId,RunID,RunStatus,RunCycle,RegWt,ModuleQuality,ModuleCriticality";
		private static final String FILE_HEADER = "RegWt,ModuleQuality,ModuleCriticality,RunStatus,ReqID,TestId,RunCycle";
		
		public static void writeCsvFile(String fileName,ArrayList<TestCaseDetails> testCaseDetailsLst) {
						
			FileWriter fileWriter = null;
					
			try {
				fileWriter = new FileWriter(fileName);

				//Write the CSV file header
				fileWriter.append(FILE_HEADER.toString());
				
				//Add a new line separator after the header
				fileWriter.append(NEW_LINE_SEPARATOR);
				
				//Write a new student object list to the CSV file
				for (TestCaseDetails testCase : testCaseDetailsLst) {
					fileWriter.append(String.valueOf(testCase.getRegWt()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getModuleQuality()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getModuleCriticality()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getRunStatus()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getRequirementId().intValue()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getTestCaseId().intValue()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(testCase.getRunCycle()));
					fileWriter.append(COMMA_DELIMITER);
					
					//fileWriter.append(String.valueOf(testCase.getRunID().intValue()));
					//fileWriter.append(COMMA_DELIMITER);
					
					
					fileWriter.append(NEW_LINE_SEPARATOR);
				}

				
				
				System.out.println("CSV file was created successfully !!!");
				
			} catch (Exception e) {
				System.out.println("Error in CsvFileWriter !!!");
				e.printStackTrace();
			} finally {
				
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					System.out.println("Error while flushing/closing fileWriter !!!");
	                e.printStackTrace();
				}
				
			}
		}
		
		
		
}
