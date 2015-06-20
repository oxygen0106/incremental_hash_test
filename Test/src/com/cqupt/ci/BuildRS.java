package com.cqupt.ci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/** 
* @ClassName: BuildRS 
* @Description: TODO
* @author yao
* @date 2015-6-15 19:05:28 
*  
*/
public class BuildRS {

	private InputData data;

	private List<String> targetX = new ArrayList<String>();
	private List<List<String>> equivalencePartition = new ArrayList<List<String>>();
	
	HashSet<String> targetSet;
	HashSet<String> upperSet;
	HashSet<String> lowerSet;
	List<HashSet<String>> eClassSet = new ArrayList<HashSet<String>>();

	public BuildRS(InputData data) {
		this.data = data;
		this.computeEquivalenceClass(data.infoTable, 0);
		this.computeTargetSet(data.infoTable, 1);
		this.convertSet();
		this.computeLower();
		this.computeUpper();
	}

	private void computeEquivalenceClass(List<String[]> table, int remove) {
		for (int i = 0; i < data.rowNum-1; i++) {
			String[] row = data.infoTable.get(i);
			ArrayList<String> sRow = new ArrayList<String>(Arrays.asList(row));
			if (remove != -1) {
				sRow.remove(remove);
			}
			ArrayList<String> e = new ArrayList<String>();
			for (int j = 0; j < data.rowNum-1; j++) {
				ArrayList<String> d = new ArrayList<String>(
						Arrays.asList(data.infoTable.get(j)));
				if (remove != -1) {
					d.remove(remove);
				}
				if (sRow.equals(d)) {
					e.add(j + "");
				}
			}
			if (e != null && !equivalencePartition.contains(e)) {
				equivalencePartition.add(e);
			}
		}
	}

	private void computeTargetSet(List<String[]> table, int desion) {

		String[] row = data.infoTable.get(0);
		String desionAtt = row[data.columnNum - 1];
		
		if (desion != 1) {
			for (int i = 0; i < data.rowNum-1; i++) {
				String[] row2 = data.infoTable.get(i);
				if (!desionAtt.equals(row2[data.columnNum - 1])) {
					desionAtt = row2[data.columnNum - 1];
					// System.out.println(desionAtt+"deA");
					break;
				}
			}
		}

		for (int j = 0; j < data.rowNum-1; j++) {
			if (data.infoTable.get(j)[data.columnNum - 1].equals(desionAtt)) {
				targetX.add(j + "");
			}
		}
	}

	public void  convertSet(){
		targetSet = new HashSet<String>(targetX);
		
		for(List<String> list : equivalencePartition){
			eClassSet.add(new HashSet<String>(list));
		}
//		System.out.println("eClassSet"+eClassSet);
	}
	
	public void computeUpper() {
		upperSet = new HashSet<String>();
		HashSet<String> set;
		for(int i=0;i<eClassSet.size()-1;i++){
			set = new HashSet<String>();
			set.addAll(eClassSet.get(i));
			set.retainAll(targetSet);
			if(set.size()!=0){
//				System.out.println("set"+set);
//				System.out.println("targetSet"+targetSet);
				upperSet.addAll(eClassSet.get(i));
//				System.out.println(upperSet+"~~~!!~~"+targetSet);
			}
		}
	}
	
	public void testSet(){
		HashSet<String> set1 = new HashSet<String>();
		set1.add("item11");
		set1.add("item12");
		
		HashSet<String> set2 = new HashSet<String>();
		set2.add("item2");
		set2.add("item1");
//		set2.add("4");
		
		System.out.println(set1.retainAll(set2));
		System.out.println(set1);
	}

	public void computeLower() {
//		for (int i = 0; i < equivalencePartition.size() - 1; i++) {
//			ArrayList<String> mClass = (ArrayList<String>) equivalencePartition
//					.get(i);
//			// System.out.println(mClass +"~"+targetX );
//			if (mClass.size() < targetX.size() && targetX.containsAll(mClass)) {
//				lower.addAll(mClass);
//				// System.out.println("mClass"+mClass);
//			}
//		}
		
//		System.out.println("--------------------");
		lowerSet = new HashSet<String>();
		if(targetSet==null||eClassSet==null){
			this.convertSet();
//			System.out.println("convertSet");
		}
		for(int i=0;i<eClassSet.size()-1;i++){
			lowerSet.addAll(eClassSet.get(i));
			lowerSet.retainAll(targetSet);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 InputData d= new InputData("D:/zoo.data.txt");
		System.out.println(d.rowNum);
		BuildRS t = new BuildRS(d);
		t.computeEquivalenceClass(d.infoTable, 0);
		t.computeTargetSet(d.infoTable, 1);
//		t.computeUpper();
		System.out.println("eClass.size"+t.equivalencePartition.size());
		System.out.println(t.equivalencePartition.get(0));
		System.out.println("targetX"+t.targetX);
		t.convertSet();
		t.computeLower();
		System.out.println("eClassSet"+t.eClassSet.size());
		t.computeUpper();
		System.out.println("targetSet~~~"+t.targetSet);
		System.out.println("lowerSet~~~"+t.lowerSet);
		System.out.println("upperSet~~~"+t.upperSet);
		
	}
}
