package com.cqupt.ci;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.cqupt.ci.InputData.InfoTableObject;

public class IUH {

	public InputData data;
	public BuildRS rs;
	public BuildRS newRs;
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

	public int checkPartition(InfoTableObject oneObject) {
		for (int i = 0; i < data.rowNum - 1; i++) {
			String[] row = data.infoTable.get(i);
			ArrayList<String> sRow = new ArrayList<String>(Arrays.asList(row));
			if (sRow.equals(oneObject.content)) {
				return i;
			}
		}
		return -1;
	}

	// relative degree of misclassification: rdm
	public float computeRdm(HashSet<String> e) {
		HashSet<String> set = new HashSet<String>();
		set.addAll(e);
		set.retainAll(newTargetSet);
		return 1f - (((float) set.size()) / ((float) e.size()));
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
			int r = checkPartition(oneObject);
			if (r != -1) {
				for (int i = 0; i < eClassSet.size() - 1; i++) {
					if (eClassSet.get(i).contains(r)) {
						HashSet<String> e = eClassSet.get(i);
						e.add(String.valueOf(oneObject.id));
						float rdm = computeRdm(e);
						if (rdm > beta) {
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
						}
						if (rdm <= (1 - beta)) {
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
		newVprs = new BuildVPRS(data, beta);

		ArrayList<String> one = oneObject.content;
		if (one != null) {
			if (isChangeTargetX(oneObject)) {
				newTargetSet = targetSet;
			} else {
				targetSet.remove(String.valueOf(oneObject.id));
				newTargetSet = targetSet;
			}
			int r = checkPartition(oneObject);
			if (r != -1) {
				for (int i = 0; i < eClassSet.size() - 1; i++) {
					if (eClassSet.get(i).contains(r)) {
						HashSet<String> e = eClassSet.get(i);
						e.add(String.valueOf(oneObject.id));
						float rdm = computeRdm(e);
						if (rdm > beta) {
							HashSet<String> set = new HashSet<String>();
							set.addAll(lowerSet);
							set.retainAll(e);
							newLowerSet = set;
						}
						if (rdm <= (1 - beta)) {
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
				timeTool.end();
				System.out.println("excute Time " + timeTool.durtation());
			} else {
				System.out.println("file is error.");
			}
			sc.close();
		}
	}

}
