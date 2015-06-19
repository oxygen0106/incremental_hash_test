package com.cqupt.ci;

import java.util.*;

public class LTR {

	public static InputData Data = new InputData("D:/zoo.data.txt");

	private List<String> targetX = new ArrayList<String>();
	private List<List<String>> equivalencePartition = new ArrayList<List<String>>();

	public void init() {

	}

	private void computeEquivalenceClass(List<String[]> table, int remove) {
		for (int i = 0; i < Data.rowNum; i++) {
			String[] row = Data.infoTable.get(i);
			ArrayList<String> sRow = new ArrayList<String>(Arrays.asList(row));
			if(remove!=-1){
				sRow.remove(remove);
			}
			ArrayList<String> e = new ArrayList<String>();
			for (int j = 0; j < Data.rowNum; j++) {
				ArrayList<String> d = new ArrayList<String>(Arrays.asList(Data.infoTable.get(j)));
				if(remove!=-1){
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

		String[] row = Data.infoTable.get(0);
		String desionAtt = row[Data.columnNum-1];

		if (desion != 1) {
			for (int i = 0; i < Data.rowNum; i++) {
				String[] row2 = Data.infoTable.get(i);
				if (!desionAtt.equals(row2[Data.columnNum-1])) {
					desionAtt = row2[Data.columnNum-1];
					//System.out.println(desionAtt+"deA");
					break;
				}
			}
		}
		
		for (int j = 0; j < Data.rowNum; j++) {
			if (Data.infoTable.get(j)[Data.columnNum - 1].equals(desionAtt)) {
				targetX.add(j + "");
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Data.rowNum);
		LTR t = new LTR();
		t.computeEquivalenceClass(Data.infoTable,0);
		t.computeTargetSet(Data.infoTable, 2);
		System.out.println(t.equivalencePartition.size());
		System.out.println(t.equivalencePartition.get(0));
		System.out.println(t.targetX);
	}
}
