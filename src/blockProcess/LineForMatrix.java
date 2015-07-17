package blockProcess;

import java.io.Serializable;
import java.util.ArrayList;

public class LineForMatrix implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3632770488196852764L;
	public int lineNum=0;
	public ArrayList<Integer> line=new ArrayList<Integer>();
	
	public LineForMatrix(){
		for(int i=0;i<324;i++){
			line.add(0);
		}
	}
}
