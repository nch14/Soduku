package blockProcess;

import java.util.ArrayList;

public class LineForMatrix {
	public int lineNum=0;
	public ArrayList<Integer> line=new ArrayList<Integer>();
	
	public LineForMatrix(){
		for(int i=0;i<324;i++){
			line.add(0);
		}
	}
}
