package com.cqupt.ci;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.cqupt.ci.InputData.InfoTableObject;

/** 
* @ClassName: IUH 
* @Description: TODO
* @author yao
* @date 2015-6-17 18:05:04 
*  
*/
public class IUH {

	public InputData data;
	public BuildRS rs;
	public BuildRS newRs;
	public RuleTree ruleTree;
	public List<HashSet<String>> eClassSet = new ArrayList<HashSet<String>>();
	public HashSet<String> targetSet = new HashSet<String>();
	public HashSet<String> upperSet = new HashSet<String>();
	public HashSet<String> lowerSet = new HashSet<String>();
	public HashSet<String> newTargetSet = new HashSet<String>();
	public HashSet<String> newUpperSet = new HashSet<String>();
	public HashSet<String> newLowerSet = new HashSet<String>();
	public InfoTableObject oneObject;

	public IUH(InputData data) {
		this.data = data;
		rs = new BuildRS(data);
		this.targetSet = rs.targetSet;
		this.upperSet = rs.upperSet;
		this.lowerSet = rs.lowerSet;
		ruleTree = new RuleTree(data);
		ruleTree.initRule(rs);
	}

	public boolean isChangeTargetX(InfoTableObject oneObject) {
		ArrayList<String> one = oneObject.content;
		String dAtt = one.get(one.size() - 1);
		String[] arr = data.infoTable.get(Integer.parseInt(targetSet.iterator()
				.next()));
		if (dAtt.equals(arr[arr.length - 1])) {
			return true;
		}
		return false;
	}

	public boolean isContain(InfoTableObject oneObject) {
		ArrayList<String> one = oneObject.content;
		String dAtt = one.get(one.size() - 1);
		Iterator<String> it = targetSet.iterator();
		while (it.hasNext()) {
			String num = it.next();
			String[] arr = data.infoTable.get(Integer.parseInt(num));
			if (dAtt.equals(arr[arr.length - 1])) {
				return true;
			}
		}
		return false;
	}

	public int checkRuleTree(InfoTableObject oneObject) {
		if(data==null){
			return -1;
		}
		for (int i = 0; i < data.rowNum - 1; i++) {
			String[] row = data.infoTable.get(i);
			ArrayList<String> sRow = new ArrayList<String>(Arrays.asList(row));
			if (sRow.equals(oneObject.content)) {
				if(ruleTree!=null){
					boolean res = ruleTree.ruleTree.containsAll(oneObject.content);
					if(res==true){
						return i;
					}
				}
			}
		}
		return -1;
	}

	
	public RuleTree recreateTree(HashSet<String> e) {
		RuleTree tree = new RuleTree(data);
		HashSet<String> set = new HashSet<String>();
		set.addAll(e);
		set.retainAll(newTargetSet);
		if(!tree.ruleTree.isEmpty()&&!tree.ruleTree.contains(set)){
			tree.ruleTree.addAll(set);
			return tree;
		}
		return tree;
	}

	public void add() {
		oneObject = data.deleteObject();
		rs = new BuildRS(data);

		ArrayList<String> one = oneObject.content;
		if (one != null) {
			if (isChangeTargetX(oneObject)) {
				newTargetSet = targetSet;
			} else {
				targetSet.remove(String.valueOf(oneObject.id));
				newTargetSet = targetSet;
			}
			int r = checkRuleTree(oneObject);
			if (r != -1) {
				for (int i = 0; i < eClassSet.size() - 1; i++) {
					if (eClassSet.get(i).contains(r)) {
						HashSet<String> e = eClassSet.get(i);
						e.add(String.valueOf(oneObject.id));
						recreateTree(e);
						if (ruleTree.ruleTree.contains(oneObject.content)) {
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
						}else{
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
							newUpperSet.addAll(upperSet);
							newUpperSet.addAll(e);
						}
						
					}

				}
			}
		}
	}

	public void delete() {
		oneObject = data.deleteObject();
		rs = new BuildRS(data);

		ArrayList<String> one = oneObject.content;
		if (one != null) {
			if (isChangeTargetX(oneObject)) {
				newTargetSet = targetSet;
			} else {
				targetSet.remove(String.valueOf(oneObject.id));
				newTargetSet = targetSet;
			}
			int r = checkRuleTree(oneObject);
			if (r != -1) {
				for (int i = 0; i < eClassSet.size() - 1; i++) {
					if (eClassSet.get(i).contains(r)) {
						HashSet<String> e = eClassSet.get(i);
						e.add(String.valueOf(oneObject.id));
						recreateTree(e);
						if (ruleTree.ruleTree.contains(oneObject.content)) {
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
						}else{
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
							newUpperSet.addAll(upperSet);
							newUpperSet.remove(e);
						}
						
					}

				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcuteTimeTool timeTool = new ExcuteTimeTool();

		Scanner sc = new Scanner(System.in);
		if (sc.hasNext()) {
			String path = sc.next();
			File file = new File(path);
			if (file.exists()) {
				timeTool.start();
				IUH iuh = new IUH(new InputData(path));
				iuh.delete();
				iuh.add();
				timeTool.end();
				System.out.println("excute Time " + timeTool.durtation());
			} else {
				System.out.println("file is error.");
			}
			sc.close();
		}
	}

}
