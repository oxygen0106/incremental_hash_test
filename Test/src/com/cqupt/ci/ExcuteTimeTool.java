package com.cqupt.ci;

/** 
* @ClassName: ExcuteTimeTool 
* @Description: TODO
* @author maxyao
* @date 2015-6-20 下午3:56:28 
*  
*/
public class ExcuteTimeTool {

	private long start = 0L;
	private long end = 0L;
	private long duration= 0L;

	public long start() {
		start = System.currentTimeMillis();
		return start;
	}

	public long end(){
		if(start!=0L){
			end = System.currentTimeMillis();
		return 	end;
		}
		return 0L;
	}
	
	public long durtation(){
		if(start!=0L&&end!=0L){
			duration = end-start;
			return duration;
		}
		return 0L;
	}

}
