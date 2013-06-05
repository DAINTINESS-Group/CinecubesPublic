package StoryMgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PptxHighlight extends Highlight {

	public float max;
	public int maxI;
	public int maxJ;
	public float min;
	public int minI;
	public int minJ;
	public Float mean[];
	public int meanI;
	public int meanJ;
	public int numOfValues;
	
	public PptxHighlight() {
		mean=new Float[2];
		mean[0]=(float) -1;
		mean[1]=(float) -1;
	}
	
	public void findMax(String[][] result){
		this.max=Float.parseFloat(result[1][1]);
		maxI=1;
		maxJ=1;
		for(int i=1;i<result.length;i++){
			for(int j=1;j<result[i].length;j++){
				if(result[i][j]!="-"){
					if(max<Float.parseFloat(result[i][j])){
						max=Float.parseFloat(result[i][j]);
						maxI=i;
						maxJ=j;
					}
				}
			}
		}
	}

	public void findMin(String[][] result){
		this.min=Float.parseFloat(result[1][1]);
		minI=1;
		minJ=1;
		for(int i=1;i<result.length;i++){
			for(int j=1;j<result[i].length;j++){
				if(result[i][j]!="-"){					
					if(min>Float.parseFloat(result[i][j])){
						min=Float.parseFloat(result[i][j]);
						minI=i;
						minJ=j;
					}
				}
			}
		}
	}
	
	public void findMedian(String[][] result){
		ArrayList<Float> tmplist=new ArrayList<Float>();
		for(int i=1;i<result.length;i++){
			for(int j=1;j<result[i].length;j++) {
				if(result[i][j]!="-") tmplist.add(Float.parseFloat(result[i][j]));
			}
		}
		Collections.sort(tmplist, new Comparator<Float>(){
			public int compare(Float a, Float b) {
				return Integer.signum((int)(a - b));
			}
		});
		int num_values=tmplist.size();
		if(num_values==2) mean[0]=mean[1]=null;
		else if(num_values==3) mean[0]=mean[1]=tmplist.get(1);
		else if(num_values%2==1)	{
			if((num_values/2)+1<num_values) mean[0]=mean[1]=tmplist.get((num_values/2)+1);
			else mean[0]=mean[1]=tmplist.get((num_values/2));
		}
		else {
			mean[0]=tmplist.get((num_values/2));
			if((num_values/2)+1<num_values) mean[1]=tmplist.get((num_values/2)+1);
			else mean[1]=tmplist.get((num_values/2));
		}
	}
		
}
