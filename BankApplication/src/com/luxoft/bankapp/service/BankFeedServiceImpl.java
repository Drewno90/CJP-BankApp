package com.luxoft.bankapp.service;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.comands.BankCommander;

public class BankFeedServiceImpl implements BankFeedService{

	private final static Logger LOG = LoggerFactory.getLogger(BankFeedServiceImpl.class);
	
	public static final String FILE_PATH = "test2.txt";
	
	
	@Override
	public void loadFeed() {
		FileReader fileReader = null;
		BufferedReader bufferedReader;
		try {
			fileReader =  new FileReader(FILE_PATH);
			bufferedReader = new BufferedReader(fileReader);
			Map<String, String> feedMap = new HashMap<String, String>();
			String []splitLine;
			String line = bufferedReader.readLine();
			String []splitValues;
            while(line != null){
            	
                splitLine = line.split(";");
                for(String nodes: splitLine){
                	splitValues = nodes.split("=");
                	feedMap.put( splitValues[0], splitValues[1]);
                }
                BankCommander.currentBank.parseFeed(feedMap);
                
                feedMap.clear();
                splitLine = null;
                line = bufferedReader.readLine();
            }           
		} catch (FileNotFoundException e) {
			LOG.error("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("IOException");
			e.printStackTrace();
		}
		
		
	}

}

