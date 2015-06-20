package com.cqupt.ci;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import com.cqupt.ci.InputData.InfoTableObject;

/** 
* @ClassName: RuleTree 
* @Description: TODO
* @author yao
* @date 2015-6-19 13:05:51 
*  
*/
public class RuleTree {

	public TreeSet<String> ruleTree = new TreeSet<String>();
	public InputData data;
	public BuildRS newRs;
	public List<HashSet<String>> eClassSet = new ArrayList<HashSet<String>>();
	public HashSet<String> targetSet = new HashSet<String>();
	public HashSet<String> upperSet = new HashSet<String>();
	public HashSet<String> lowerSet = new HashSet<String>();
	public HashSet<String> newTargetSet = new HashSet<String>();
	public HashSet<String> newUpperSet = new HashSet<String>();
	public HashSet<String> newLowerSet = new HashSet<String>();
	public InfoTableObject oneObject;

	public RuleTree(InputData data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	public void initRule(BuildRS rs) {
		if(data==null){
			return;
		}
		TreeSet<String> tree = new TreeSet<String>();
		for (int i = 0; i < data.rowNum - 1; i++) {
			if(data.infoTable.get(i).length!=0){
				String str = Arrays.toString(data.infoTable.get(i));
				tree.add(str);
			}
		}
		if(rs==null||rs.eClassSet==null){
			return;
		}
		for(int i=0;i<rs.eClassSet.size()-1;i++){
			if(rs.eClassSet.get(i)!=null){
				String rule = rs.eClassSet.get(i).toString();
				tree.add(rule);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		if(sc.hasNext()){
			String path= sc.next();
			File file = new File(path);
			if(file.exists()){
				InputData data = new InputData(path);
				RuleTree ruleTree = new RuleTree(data);
				ruleTree.initRule(new BuildRS(data));
			}else{
				System.out.println("file is error.");
			}
			sc.close();
		}
	}

}
