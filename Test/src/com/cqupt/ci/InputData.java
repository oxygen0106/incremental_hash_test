package com.cqupt.ci;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: InputData
 * @Description: TODO
 * @author 姚龙洋
 * @date 2015-6-19 下午1:45:26
 * 
 */
public class InputData {

	int rowNum = 0;
	int columnNum = 0;

	List<String[]> infoTable = new ArrayList<String[]>();
	
	public InputData(String filePath) {
		// TODO Auto-generated constructor stub
		process(filePath);
	}

	public void process(String filePath) {
		try {
//            Scanner in = new Scanner(new File("D:/zoo.data.txt"));
            Scanner in = new Scanner(new File(filePath));
 
            while (in.hasNextLine()) {
                String strLine = in.nextLine();
 
                infoTable.add(strLine.split(","));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		if(infoTable!=null){
			rowNum = infoTable.size();
			columnNum = infoTable.get(0).length;
		}
	}
	
}
