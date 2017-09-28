package com.niit.testcases;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class TrainDataFileFromTestReport {
	
	String propertiesFileName = "config.properties" ;
	String testCaseFileNameFilePath = null;
	
	Integer reportSheetNumber = null ;
	
	Integer reqNameColNumber = null ;
	Integer reqIDColNumber = null;
	Integer testNameColNumber = null;
	Integer testIDColNumber = null;
	Integer testCreatedByColNumber = null;
	Integer testExecutedOnColNumber = null;
	Integer runIDColNumber = null;
	Integer runStatusColNumber = null;
	
	String testCaseSummaryDetailsFileName = null;
	
	
	private void readProperties(){
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			//input = new FileInputStream("config.properties");
			input = TrainDataFileFromTestReport.class.getClassLoader().getResourceAsStream(propertiesFileName);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			testCaseFileNameFilePath = prop.getProperty("testCaseFileName").replace("\\","\\\\") ;
			System.out.println("Test Case File Name Path==="+testCaseFileNameFilePath);
			
			testCaseSummaryDetailsFileName = prop.getProperty("testCaseSummaryDetailsFileName").replace("\\","\\\\") ;
			System.out.println("Test Case Summary Output File Name Path==="+testCaseSummaryDetailsFileName);
			
			
			reportSheetNumber = Integer.parseInt(prop.getProperty("reportSheetNumber"));
			
			reqNameColNumber = Integer.parseInt(prop.getProperty("reqNameColNumber"));
			reqIDColNumber = Integer.parseInt(prop.getProperty("reqIDColNumber"));
			testNameColNumber = Integer.parseInt(prop.getProperty("testNameColNumber"));
			testIDColNumber = Integer.parseInt(prop.getProperty("testIDColNumber"));
			testCreatedByColNumber = Integer.parseInt(prop.getProperty("testCreatedByColNumber"));
			testExecutedOnColNumber = Integer.parseInt(prop.getProperty("testExecutedOnColNumber"));
			runIDColNumber = Integer.parseInt(prop.getProperty("runIDColNumber"));
			runStatusColNumber = Integer.parseInt(prop.getProperty("runStatusColNumber"));
			

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void getReportDetails(){
		
		readProperties();
		
		Workbook workbook = null;
		
		int numberOfRows = 0;
		
		
		HashMap<String,ArrayList<TestCaseDetails>>  requirmentTestCaseMap = new HashMap<String,ArrayList<TestCaseDetails>>();
		
		HashMap<Double,ArrayList<TestCaseDetails>> reqMap = new HashMap<Double,ArrayList<TestCaseDetails>>();
		
		try {
			
			  File file = new File(testCaseFileNameFilePath);
			  System.out.println("File Exist:::"+file.exists());
			
			  workbook = new XSSFWorkbook(new FileInputStream(new File(testCaseFileNameFilePath))) ;
			  System.out.println("workbook:::"+workbook);
			  Sheet datatypeSheet = workbook.getSheetAt(reportSheetNumber);
			  //StringBuilder stringBuilder = new StringBuilder();
			
			 
			  numberOfRows = datatypeSheet.getPhysicalNumberOfRows();
			  System.out.println(numberOfRows);
			  
			  ArrayList<TestCaseDetails> totalDetailsList = new  ArrayList<TestCaseDetails>();
			  Set<Double> testCaseIdSet = new HashSet<Double>();
			  for(int i = 1; i < numberOfRows ; i++)
              {
				  Row row =  datatypeSheet.getRow(i); 
				  
				  //Requirement Name
				  Cell reqNameCell = row.getCell(reqNameColNumber); 
				  String requirementName = reqNameCell.getStringCellValue();
				  //System.out.println(reqNameColNumber+":"+requirementName);
				  
				  //Requirement ID 
				  //System.out.println(reqIDColNumber+":reqIDColNumber");
				  Cell reqIDCell = row.getCell(reqIDColNumber);
				  Double reqId = null;
				  
				  if(reqIDCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
					      reqId = reqIDCell.getNumericCellValue();
				  else
						  reqId = Double.parseDouble(reqIDCell.getStringCellValue());
				  
				  //TestName 
				  Cell testNameCell = row.getCell(testNameColNumber);
				  String testName = testNameCell.getStringCellValue();
				  
				  //TestID
				  Double testID = null;
				  Cell testIDCell = row.getCell(testIDColNumber);
				  if(testIDCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				    testID = testIDCell.getNumericCellValue();
				  else
					  testID = Double.parseDouble(testIDCell.getStringCellValue());
				  
				  //TestCreatedBy
				  Cell testCreatedByCell = row.getCell(testCreatedByColNumber);
				  String testCreatedBy = testCreatedByCell.getStringCellValue();
				  
				  //Test Executed On
				  Cell testExecutedOnCell = row.getCell(testExecutedOnColNumber);
				  Date testExecutedOn = testExecutedOnCell.getDateCellValue();
				  
				  //Run ID
				  Cell runIDCell = row.getCell(runIDColNumber);
				  Double runID = runIDCell.getNumericCellValue();
				  
				  //Run Status
				  Cell runStatusCell = row.getCell(runStatusColNumber);
				  String runStatusStr = runStatusCell.getStringCellValue();
				  Integer runStatus = null;
				  if(runStatusStr.equals("Passed"))
					  runStatus = 0;
				  else
					  runStatus = 1;
					  
				  
				  //TestID mapped with TestCaseDetails
				  TestCaseDetails testCaseDetails = new TestCaseDetails(reqId,testID,runID,runStatus,testExecutedOn,testCreatedBy,testName);
				  testCaseIdSet.add(testID);//Total number of unique test case id
				  //testCaseMap.put(testID, testCaseDetails);
				  
				  // Grouping on the basis of RequirementID+TestCaseID
				  
				  if(requirmentTestCaseMap.containsKey(reqId+"_"+testID))
				  {
					  ArrayList<TestCaseDetails> testCaseDtlList = requirmentTestCaseMap.get(reqId+"_"+testID);
					  testCaseDtlList.add(testCaseDetails);
					  requirmentTestCaseMap.put(reqId+"_"+testID, testCaseDtlList);
					  
				  }else{
					  ArrayList<TestCaseDetails> testCaseDtlList = new ArrayList<TestCaseDetails>(); 
					  testCaseDtlList.add(testCaseDetails);
					  requirmentTestCaseMap.put(reqId+"_"+testID, testCaseDtlList);
				  }
				  
				  
				  if(reqMap.containsKey(reqId))
				  {
					  ArrayList<TestCaseDetails> testCaseDtlList = reqMap.get(reqId);
					  testCaseDtlList.add(testCaseDetails);
					  reqMap.put(reqId, testCaseDtlList);
					  totalDetailsList.add(testCaseDetails);
					  
				  }else{
					  ArrayList<TestCaseDetails> testCaseDtlList = new ArrayList<TestCaseDetails>(); 
					  testCaseDtlList.add(testCaseDetails);
					  reqMap.put(reqId, testCaseDtlList);
					  totalDetailsList.add(testCaseDetails);
				  }
				  
				   
              }
			  
			 // System.out.println(requirmentTestCaseMap);
			 requirmentTestCaseMap = setRunCycleAndRegWt(requirmentTestCaseMap); // set RunCycle and RegWt in TestCaseDetails
			 //System.out.println(requirmentTestCaseMap);
			 
			 HashMap<Double,RequestDetails> reqDetailsSummary = setModuleCriticalityAndQuality(reqMap,testCaseIdSet.size()); // set Module Criticality and Module Quality
			 ArrayList<TestCaseDetails> totalTCDetailsList = setTestCaseDetailsFinal(totalDetailsList,reqDetailsSummary);
			 //System.out.println(totalTCDetailsList);
			 
			 //Flush the TestCase Details in CSV file
			 TestCaseDetailWriter.writeCsvFile(testCaseSummaryDetailsFileName, totalTCDetailsList);
			  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
			  
		
	}
	
	
	private ArrayList<TestCaseDetails> setTestCaseDetailsFinal(ArrayList<TestCaseDetails> totalDetailsList,HashMap<Double, RequestDetails> reqDetailsSummary) {
			
			//System.out.println(totalDetailsList.size());
		    Iterator<TestCaseDetails> testCaseListItr = totalDetailsList.iterator();
		    while(testCaseListItr.hasNext()){
		    	TestCaseDetails testCaseDetails = testCaseListItr.next();
		    	if(reqDetailsSummary.containsKey(testCaseDetails.getRequirementId())){
		    		RequestDetails reqDtl = reqDetailsSummary.get(testCaseDetails.getRequirementId());
		    		testCaseDetails.setModuleCriticality(reqDtl.getModuleCriticality());
		    		testCaseDetails.setModuleQuality(reqDtl.getModuleQuality());
		    	}
		    }
		    //System.out.println(totalDetailsList);
		    return totalDetailsList;
	}


	private HashMap<Double,RequestDetails> setModuleCriticalityAndQuality(HashMap<Double, ArrayList<TestCaseDetails>> requirmentTestCaseMap,int totalTestCaseId) {
		 Iterator<Map.Entry<Double,ArrayList<TestCaseDetails>>> iterator = requirmentTestCaseMap.entrySet().iterator();
		 HashMap<Double,RequestDetails>  requirmentDetailsMap = new HashMap<Double,RequestDetails>();
		 System.out.println("Total Unique TCs:::"+totalTestCaseId);
		 double totalFailurePercentage = 0;
		 double totalMeanOfTCRun = 0;
		 // Iterations around Requirement based on TestCase Details
		 while(iterator.hasNext()){
	            Map.Entry<Double,ArrayList<TestCaseDetails>> entry = iterator.next();
	           /* System.out.printf("Key : %s and Value: %s %n", entry.getKey(), 
	                                                           entry.getValue().size());*/
	            //iterator.remove(); // right way to remove entries from Map,  avoids ConcurrentModificationException
	            Double reqId = entry.getKey(); // Combination of ReqID 
	            ArrayList<TestCaseDetails> testCaseList = entry.getValue();
	            int size = testCaseList.size();
	            int totalPassCount = 0; //PASS = 0
	            int totalFailCount = 0; //FAIL = 1
	            
	            Set<Double> testCaseIdSet = new HashSet<Double>();
	            for(int i=0; i < size ; i++){
	            	TestCaseDetails testCase = testCaseList.get(i);
	            	if(testCase.getRunStatus() == 0) {
	            		totalPassCount = totalPassCount + 1;
	            	}else{
	            		totalFailCount = totalFailCount + 1;
	            	}
	            	testCaseIdSet.add(testCase.getTestCaseId());
	            }
	            int totalTCPerReqId =  testCaseIdSet.size(); // Total Number of TC's per module/requirement
	            int totalTCRun = totalPassCount + totalFailCount ; // Total Number of TC's run which should be equal to size of testCaseList
	            
	            double meanOfFailedTC = (double)totalFailCount / (double)totalTCPerReqId ;
	            double meanOfTCRun = (double)totalTCRun / (double)totalTCPerReqId ;
	            totalMeanOfTCRun = totalMeanOfTCRun + meanOfTCRun;
	            
	            double failurePercentage = (double)totalFailCount*100/totalTCRun;
	            totalFailurePercentage = totalFailurePercentage + failurePercentage;
	            //double meanOfTCPerReq = (double)totalTCPerReqId / (double)totalTestCaseId ;
	     
	            RequestDetails reqDtl = new RequestDetails(reqId,totalTCPerReqId, totalTCRun, failurePercentage,totalPassCount, totalFailCount);
	            requirmentDetailsMap.put(reqId,reqDtl);
	            //System.out.println("ReqId:::"+reqId.intValue()+ ", No.OfTC::"+totalTCPerReqId + ", totalTCRun:::"+totalTCRun +", SizOfTCList::"+size +", TotalPass:::"+totalPassCount +", TotalFail::"+ totalFailCount +", MeanOfFailedTC::"+ meanOfFailedTC + ", MeanOfTCRun:::"+ meanOfTCRun + ", MeanOfTCPerReq:::"+meanOfTCPerReq);
		 }
             Double averageFailurePercentage = 	(double)totalFailurePercentage / (double)requirmentTestCaseMap.size() ;
             Double averageTCRunMean = (double)totalMeanOfTCRun / (double)requirmentTestCaseMap.size() ;
            // System.out.println(requirmentDetailsMap);
             
             // Iterations through summary of RequirementDetails to update ModuleQuality
             Iterator<Map.Entry<Double,RequestDetails>> iteratorReq = requirmentDetailsMap.entrySet().iterator();           
             while(iteratorReq.hasNext()){
 	            Map.Entry<Double,RequestDetails> entry = iteratorReq.next();
 	           /* System.out.printf("Key : %s and Value: %s %n", entry.getKey(), 
 	                                                           entry.getValue().size());*/
 	            //iterator.remove(); // right way to remove entries from Map,  avoids ConcurrentModificationException
 	             Double reqId = entry.getKey(); 
 	             RequestDetails  reqDetls = entry.getValue(); // requirement level module details
 	             Double failurePercentage = reqDetls.getFailurePercentage();
 	             Integer moduleQuality = 0;
 	             //Setting Module Quality
 	             if(failurePercentage < averageFailurePercentage){
 	            	moduleQuality = 1;
 	             }else if( failurePercentage > (2*averageFailurePercentage)){
 	            	moduleQuality = 3 ;
 	             }else{
 	            	moduleQuality = 2 ;
 	             }
 	             
 	           reqDetls.setModuleQuality(moduleQuality);

 	           //Setting the Module Criticality
 	            Integer moduleCriticality = 0;
 	            Double meanOfTCRun = (double)reqDetls.getTotalTCRun() / (double)reqDetls.getTotalTC() ;
 	            if(meanOfTCRun < averageTCRunMean){
 	            	moduleCriticality = 3;
	             }else if( meanOfTCRun > (2*averageTCRunMean)){
	            	 moduleCriticality = 1 ;
	             }else{
	            	 moduleCriticality = 2 ;
	             }
 	           
 	            reqDetls.setModuleCriticality(moduleCriticality);
 	            
 	            requirmentDetailsMap.put(reqId, reqDetls);
 	            
             }
             System.out.println(requirmentDetailsMap);
             
		return requirmentDetailsMap;
	}


	private HashMap<String,ArrayList<TestCaseDetails>> setRunCycleAndRegWt(HashMap<String,ArrayList<TestCaseDetails>> requirmentTestCaseMap){
	  	 HashMap<String,ArrayList<TestCaseDetails>>  requirmentTestCaseMapNew = new HashMap<String,ArrayList<TestCaseDetails>>();
		 Iterator<Map.Entry<String,ArrayList<TestCaseDetails>>> iterator = requirmentTestCaseMap.entrySet().iterator();
		 while(iterator.hasNext()){
	            Map.Entry<String,ArrayList<TestCaseDetails>> entry = iterator.next();
	           /* System.out.printf("Key : %s and Value: %s %n", entry.getKey(), 
	                                                           entry.getValue().size());*/
	            //iterator.remove(); // right way to remove entries from Map,  avoids ConcurrentModificationException
	            
	            String reqId_testId = entry.getKey();
	           // System.out.println("***************************************"+reqId_testId);
	            ArrayList<TestCaseDetails> testCaseList = entry.getValue();
	            ArrayList<TestCaseDetails> testCaseListRC = new ArrayList<TestCaseDetails>() ; // for overriding the existing list
	            Collections.sort(testCaseList);
	            int size = testCaseList.size();
	            for(int i=0; i < size ; i++){
	            	TestCaseDetails testCase = testCaseList.get(i);
	            	//System.out.println("***************************************"+testCase.getRunID());
	            	testCase.setRunCycle(i+1);
	            	
	            	int regWt = ((int) Math.pow(2, testCase.getRunCycle())) * testCase.getRunStatus() ;
	            	//int regWt = ((int) Math.pow(2, testCase.getRunCycle()));
	            	testCase.setRegWt(regWt);
	            	
	            	//System.out.println("RunID:"+testCase.getRunID()+", RunCycle:"+testCase.getRunCycle()+ ", RegWt:"+testCase.getRegWt()+", RunStatus:"+testCase.getRunStatus());
	            	
	            	
	            	testCaseListRC.add(testCase);
	            	entry.setValue(testCaseListRC);
	            }
	            requirmentTestCaseMapNew.put(reqId_testId, entry.getValue());      
	            //entry.setValue(testCaseListRC);
	            //System.out.println(requirmentTestCaseMapNew);
	            
	      }

          return  requirmentTestCaseMapNew;       		
	}
	
	

}
