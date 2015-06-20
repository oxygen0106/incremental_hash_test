package com.cqupt.ci;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName: VPRS
 * @Description: TODO
 * @author yao
 * @date 2015-6-19 22:37:48
 * 
 */
public class BuildVPRS {

	public float beta;
	public List<HashSet<String>> eClassSet = new ArrayList<HashSet<String>>();
	public HashSet<String> targetSet = new HashSet<String>();
	public HashSet<String> upperVpSet = new HashSet<String>();
	public HashSet<String> lowerVpSet = new HashSet<String>();

	public BuildVPRS(InputData data, float beta) {
		// TODO Auto-generated constructor stub
		BuildRS rs = new BuildRS(data);
		this.targetSet = rs.targetSet;
		this.eClassSet = rs.eClassSet;
		this.beta = beta;
		this.computeLower();
		this.computeUpper();
	}

	private void computeLower() {
		HashSet<String> set;
		for (int i = 0; i < eClassSet.size() - 1; i++) {
			set = new HashSet<String>();
			set.addAll(eClassSet.get(i));
//			System.out.println("1set~"+set);
			set.retainAll(targetSet);
			if (set.size() != 0) {
				float d = ((float)set.size()) / ((float)eClassSet.get(i).size());
//				System.out.println("d"+d+"---"+set+"~"+targetSet);
//				System.out.println("!!~~"+eClassSet.get(i));
				if (d >= beta) {
					lowerVpSet.addAll((eClassSet.get(i)));
				}
			}
		}
	}

	private void computeUpper() {
		HashSet<String> set;
		for (int i = 0; i < eClassSet.size() - 1; i++) {
			set = new HashSet<String>();
			set.addAll(eClassSet.get(i));
			set.retainAll(targetSet);
			if (set.size() != 0) {
				upperVpSet.addAll((eClassSet.get(i)));
			}
		}
	}
	
	private HashSet<String> copySet(HashSet<String> sourceSet){
		HashSet<String> newSet = new HashSet<String>();
		for(String s: sourceSet){
			newSet.add(s);
		}
		return newSet;
	}
	
	public static void main(String[] args) {
		BuildVPRS vprs = new BuildVPRS(new InputData("D:/zoo.data.txt"), 0.4f);
		System.out.println(vprs.lowerVpSet);
		System.out.println(vprs.upperVpSet);
	}

}
