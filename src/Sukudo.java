//*begin*//
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import blockProcess.*;


public class Sukudo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4848187383172573547L;
	BlockPO[][] blocks;
	ArrayList<LineForMatrix> lines=new ArrayList<LineForMatrix>() ;
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		Sukudo sudo=new Sukudo();
		sudo.startGame();
	}
	
	public void startGame() throws IOException{
		blocks=new BlockPO[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				blocks[i][j]=new BlockPO();
			}
		}
		
		//等待用户输入
		System.out.println("请输入设定的数独初始化数据，以如下形式：0 0 1（行，列，数值，并用空格隔开）");
		/*while(true){
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String lines=br.readLine();
			
			if(lines.equals("end")){
				break;
			}
			String datas[]=lines.split(" ");
			int x=Integer.parseInt(datas[0])-1;//实际的数组从0位开始，用户视角从1开始
			int y=Integer.parseInt(datas[1])-1;//实际的数组从0位开始，用户视角从1开始
			int value=Integer.parseInt(datas[2]);
			
			blocks[x][y].isSet=true;
			blocks[x][y].value.add(value);
			
			System.out.println("请继续输入设定的数独初始化数据，以如下形式：0 0 1（行，列，数值，并用空格隔开），如果您已初始化完毕，请敲入“end表示结束");
		}*/
		File file=new File("2");
		BufferedReader b=new BufferedReader(new FileReader(file));
		String data = b.readLine();//一次读入一行，直到读入null为文件结束   
		while(data!=null){   
			String datas[]=data.split(" ");
			int x=Integer.parseInt(datas[0])-1;//实际的数组从0位开始，用户视角从1开始
			int y=Integer.parseInt(datas[1])-1;//实际的数组从0位开始，用户视角从1开始
			int value=Integer.parseInt(datas[2]);
			
			blocks[x][y].isSet=true;
			blocks[x][y].value.add(value);
		    data =b.readLine(); 
		}
		b.close();
		
		
		
		//初始化棋盘所有块
		initializationBlocks();
		/*
		*/
		
		//初始化棋盘的布尔矩阵
		initializationMatrix();
		/*for(int i=0;i<lines.size();i++){
			LineForMatrix a=lines.get(i);
			ArrayList<Integer> line=a.line;
			System.out.print(a.lineNum+"  ");
			for(int j=0;j<line.size();j++){
				System.out.print(line.get(j));
			}
			System.out.println();
		}*/
		
		
		//执行Alogorithm X算法
		AlogorithmX(lines);
		
	}
	


	public void initializationBlocks(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(!(blocks[i][j].isSet)){
					
					//初始化每一个块的值，默认有9种可能
					for(int k=1;k<10;k++){
						blocks[i][j].value.add(k);
					}
					//这一行有多少个元素被确定
					for(int l=0;l<9;l++){
						if(blocks[i][l].isSet){
							blocks[i][j].value.remove(blocks[i][l].value.get(0));
						}
					}
					//这一列有多少个元素被确定
					for(int l=0;l<9;l++){
						if(blocks[l][j].isSet){
							blocks[i][j].value.remove(blocks[l][j].value.get(0));
						}
					}
					//所在九宫格内有多少个元素被确定
					int x=((i)/3)*3;
					int y=((j)/3)*3;
					for(int m=0;m<3;m++){
						for(int n=0;n<3;n++){
							if(blocks[x][y].isSet){
								blocks[i][j].value.remove(blocks[x][y].value.get(0));
							}
							x+=1;
						}
						y+=1;
						x-=3;
					}					
				}
			}
		}
	}//方法结束
	
	public void initializationMatrix(){
		/*
		 * 布尔矩阵每一行有324列
		 * 第一个81列用于限制1：即每个空格必须写入一个数字
		 * 第二个81列用于限制2：即每列必须包含所有数字
		 * 第三个81列用于限制3：即每行必须包含所有数字
		 * 第四个81列用于限制4：即每个矩形格子里有所有数字
		 */
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				BlockPO block=blocks[i][j];
				
				//x，y表示这个方格所在的九宫格的左上方格坐标
				int x=((i)/3)*3;
				int y=((j)/3)*3;
				int numBigBlock=x+y/3;
				//对每个格子每一种的可能生成矩阵的一行
				for(int k=0;k<block.value.size();k++){
					int value=block.value.get(k);
					
					LineForMatrix aLine=new LineForMatrix();
					int indexOfLimit1=i*9+j;
					int indexOfLimit2=(value-1)*9+i+81;
					int indexOfLimit3=(value-1)*9+j+162;
					int indexOfLimit4=(value-1)*9+numBigBlock+243;
					
					aLine.line.set(indexOfLimit1, 1);
					aLine.line.set(indexOfLimit2, 1);
					aLine.line.set(indexOfLimit3, 1);
					aLine.line.set(indexOfLimit4, 1);
					
					lines.add(aLine);
					aLine.lineNum=lines.indexOf(aLine);	
				}	
			}
		}
	}
	
	public void AlogorithmX(ArrayList<LineForMatrix> linesThis) {
		if(step1(linesThis)==0){
			//无解
			//System.out.println("失败了一条路");
		}else{
			step2(linesThis);
		}
		
	}
	//Succ表示用来表示它处于递归中调用，和原方法有参数上的差异
	public void AlogorithmXSucc(ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer) {
		if(step1(linesThis)==0){
			//无解
			//System.out.println("失败了一条路");
		}else{
			step2Succ(linesThis,answer);
		}
	}
	
	public int step1(ArrayList<LineForMatrix> linesThis){
		//初始化一个数组放数据
		System.out.println(linesThis.get(0).line.size());
				int length=linesThis.get(0).line.size();
				int[] numberOfEachColumn=new int[length];
				for(int i=0;i<length;i++){
					numberOfEachColumn[i]=0;
				}
				//整理数据
				for(int i=0;i<linesThis.size();i++){
					LineForMatrix aLine=linesThis.get(i);
					for(int j=0;j<aLine.line.size();j++){
						int value=aLine.line.get(j);
						if(value==1){
							numberOfEachColumn[j]=1+numberOfEachColumn[j];
						}
					}
				}
				/*for(int i=0;i<length;i++){
					System.out.println(numberOfEachColumn[i]);
				}*/
				//冒泡排序
				int[] temp=numberOfEachColumn;//这名字太长了，打着累
				for(int i=0;i<length-1;i++){
					if(temp[i]<temp[i+1]){
						int a=temp[i];
						temp[i]=temp[i+1];
						temp[i+1]=a;
					}else{
						
					}
				}
				return temp[length-1];
	}
	
	public void step2(ArrayList<LineForMatrix> linesThis){
				//初始化一个数组放数据
				int length=linesThis.get(0).line.size();
				int[] numberOfEachColumn=new int[length];
				for(int i=0;i<length;i++){
					numberOfEachColumn[i]=0;
				}
				//整理数据
				for(int i=0;i<linesThis.size();i++){
					LineForMatrix aLine=linesThis.get(i);
					for(int j=0;j<length;j++){
						int value=aLine.line.get(j);
						if(value==1){
							numberOfEachColumn[j]+=1;
						}
					}
				}
				//满足有最小1的列c
				int leastSize=step1(linesThis);
			//	System.out.println("leastSize"+leastSize);
				ArrayList<Integer> columnNum=new ArrayList<Integer>();//它用来放列的标号
				for(int i=0;i<length;i++){
					if(numberOfEachColumn[i]==leastSize){
						columnNum.add(i);
						//System.out.println("添加进去的列号："+i);
					}
				}
				//开始第三个步骤
				for(int i=0;i<columnNum.size();i++){
					ArrayList<LineForMatrix> forNext=copy(linesThis);
					step3(columnNum.get(i),forNext);
				}
	}

	public void step2Succ(ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer){
		//初始化一个数组放数据
		int length=linesThis.get(0).line.size();
		int[] numberOfEachColumn=new int[length];
		for(int i=0;i<length;i++){
			numberOfEachColumn[i]=0;
		}
		//整理数据
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			for(int j=0;j<length;j++){
				int value=aLine.line.get(j);
				if(value==1){
					numberOfEachColumn[j]+=1;
				}
			}
		}
		//满足有最小1的列c
		int leastSize=step1(linesThis);
	//	System.out.println("leastSize"+leastSize);
		ArrayList<Integer> columnNum=new ArrayList<Integer>();//它用来放列的标号
		
		for(int i=0;i<length;i++){
			if(numberOfEachColumn[i]==leastSize){
				columnNum.add(i);
				//System.out.println("columnNum.add(i)"+i);
			}
		}
		//开始第三个步骤
		for(int i=0;i<columnNum.size();i++){
			ArrayList<LineForMatrix> forNext=copy(linesThis);
			ArrayList<Integer> answerForNext=copy2(answer);
			step3Succ(columnNum.get(i),forNext,answerForNext);
		}
}
	
	public void step3(Integer column,ArrayList<LineForMatrix> linesThis) {
		//初始化一个数组，把矩阵中列C有1的行都放进去
		ArrayList<Integer> linesContainThis=new ArrayList<Integer>();
		//System.out.println("linesThis.size()"+linesThis.size());
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			if(aLine.line.get(column)==1){
				linesContainThis.add(i);
			}
		}		
		//System.out.println("column"+column);
		//System.out.println("linesContainThis.size()"+linesContainThis.size());
		for(int i=0;i<linesContainThis.size();i++){
			int lineNum=linesContainThis.get(i);
			ArrayList<Integer> answer=new ArrayList<Integer>();//标记答案的array
			LineForMatrix aLine=linesThis.get(lineNum);
			answer.add(aLine.lineNum);
			//System.out.println("lineNum"+lineNum);
			ArrayList<Integer> answerForNext=copy2(answer);
			ArrayList<LineForMatrix> forNext=copy(linesThis);
			step4(lineNum,forNext,answerForNext);
		}
	}

	public void step3Succ(Integer column,ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer) {
		//初始化一个数组，把矩阵中列C有1的行都放进来 		
		ArrayList<Integer> linesContainThis=new ArrayList<Integer>();
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			if(aLine.line.get(column)==1){
				linesContainThis.add(i);
			}
		}
	//	System.out.println("column"+column);
	//	System.out.println("linesContainThis"+linesContainThis.get(0));
	
		for(int i=0;i<linesContainThis.size();i++){
			int lineNum=linesContainThis.get(i);
			LineForMatrix aLine=linesThis.get(lineNum);
			//System.out.println("aLine.lineNum"+aLine.lineNum);
			answer.add(aLine.lineNum);
			ArrayList<Integer> answerForNext=copy2(answer);
			ArrayList<LineForMatrix> forNext=copy(linesThis);
			step4(lineNum,forNext,answerForNext);
		}
	}
	
	public void step4(int lineNum, ArrayList<LineForMatrix> linesThis,
			ArrayList<Integer> answer) {
		
		//System.out.println("计量开始"+linesThis.size());
		LineForMatrix aLine=linesThis.get(lineNum);
		//System.out.println("linenum"+aLine.lineNum);
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix tempLine=linesThis.get(i);
				for(int j=0;j<aLine.line.size();j++){
					if((tempLine.line.get(j)==1)&&(aLine.line.get(j)==1)){
						linesThis.remove(i);
						i=i-1;
						//System.out.print("1");
						break;
						
					}	
				}	
		}
		linesThis.add(aLine);
				//消去行r中所有的值为1的列
		//System.out.println(linesThis.size());
		ArrayList<Integer> numberOfColumn=new ArrayList<Integer>();
		for(int i=0;i<aLine.line.size();i++){
			if(aLine.line.get(i)==1){
				numberOfColumn.add(i);
			}
		}
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix lineTemp=linesThis.get(i);
			
			for(int j=0;j<numberOfColumn.size();j++){
				lineTemp.line.remove(numberOfColumn.get(j)-j);
			}
		}
		System.out.println("aLine.line.size()"+aLine.line.size());
		System.out.println("linesThis.size()"+linesThis.size());
		int index=linesThis.indexOf(aLine);
		linesThis.remove(index);
		if(linesThis.size()==0||aLine.line.size()==0){
			System.out.println("YOU GET IT!");
			getKey(answer);

		}else{
			//System.out.println("answer"+answer.get(0));
			AlogorithmXSucc(linesThis,answer);
			//System.out.println("YOU lose once!");
		}
	}
	
	public ArrayList<LineForMatrix> copy(ArrayList<LineForMatrix> ori) {
	         FileOutputStream fileStream;
			try {
				/* fileStream = new FileOutputStream("MyMatrix.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileStream);
		         out.writeObject(ori);
		         out.close();
		         
		         FileInputStream byteIn = new FileInputStream("MyMatrix.ser");
		         ObjectInputStream in =new ObjectInputStream(byteIn);
		         ArrayList<LineForMatrix> dest = ( ArrayList<LineForMatrix>)in.readObject();
		         in.close();*/
				
				 ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		         ObjectOutputStream out = new ObjectOutputStream(byteOut);
		         out.writeObject(ori);
		    
		         ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		         ObjectInputStream in =new ObjectInputStream(byteIn);
		         ArrayList<LineForMatrix> dest = ( ArrayList<LineForMatrix>)in.readObject();
		         return dest;
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("发生异常");
				 return ori;
			}       
		}
	
	public ArrayList<Integer> copy2(ArrayList<Integer> ori) {
        FileOutputStream fileStream;
		try {
			/* fileStream = new FileOutputStream("MyMatrix.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileStream);
	         out.writeObject(ori);
	         out.close();
	         
	         FileInputStream byteIn = new FileInputStream("MyMatrix.ser");
	         ObjectInputStream in =new ObjectInputStream(byteIn);
	         ArrayList<LineForMatrix> dest = ( ArrayList<LineForMatrix>)in.readObject();
	         in.close();*/
			
			 ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	         ObjectOutputStream out = new ObjectOutputStream(byteOut);
	         out.writeObject(ori);
	    
	         ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	         ObjectInputStream in =new ObjectInputStream(byteIn);
	         ArrayList<Integer> dest = (  ArrayList<Integer>)in.readObject();
	         return dest;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("发生异常");
			 return ori;
		}       
	}
	public void getKey(ArrayList<Integer> answer){
		int[][] result=new int[9][9];
		for(int i=0;i<answer.size();i++){
			LineForMatrix tempLine=lines.get(answer.get(i));
			int limit1=0;
			int limit2=0;
			int limit3=0;
			for(int j=0;j<81;j++){
				if(tempLine.line.get(j)==1){
					limit1=j;
				}
			}
			for(int j=81;j<162;j++){
				if(tempLine.line.get(j)==1){
					limit2=j;
				}
			}
			for(int j=162;j<243;j++){
				if(tempLine.line.get(j)==1){
					limit3=j;
				}
			}
			int x=(81+limit1+limit2-limit3)/10;
			int y=limit1-9*x;
			int z=(limit2-x-81)/9+1;
			result[x][y]=z;
			
		}
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(result[i][j]);
			}
			System.out.println();
		}
	}	
}
//*end*//
