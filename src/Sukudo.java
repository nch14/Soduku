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
		// TODO �Զ����ɵķ������
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
		
		//�ȴ��û�����
		System.out.println("�������趨��������ʼ�����ݣ���������ʽ��0 0 1���У��У���ֵ�����ÿո������");
		/*while(true){
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String lines=br.readLine();
			
			if(lines.equals("end")){
				break;
			}
			String datas[]=lines.split(" ");
			int x=Integer.parseInt(datas[0])-1;//ʵ�ʵ������0λ��ʼ���û��ӽǴ�1��ʼ
			int y=Integer.parseInt(datas[1])-1;//ʵ�ʵ������0λ��ʼ���û��ӽǴ�1��ʼ
			int value=Integer.parseInt(datas[2]);
			
			blocks[x][y].isSet=true;
			blocks[x][y].value.add(value);
			
			System.out.println("����������趨��������ʼ�����ݣ���������ʽ��0 0 1���У��У���ֵ�����ÿո��������������ѳ�ʼ����ϣ������롰end��ʾ����");
		}*/
		File file=new File("2");
		BufferedReader b=new BufferedReader(new FileReader(file));
		String data = b.readLine();//һ�ζ���һ�У�ֱ������nullΪ�ļ�����   
		while(data!=null){   
			String datas[]=data.split(" ");
			int x=Integer.parseInt(datas[0])-1;//ʵ�ʵ������0λ��ʼ���û��ӽǴ�1��ʼ
			int y=Integer.parseInt(datas[1])-1;//ʵ�ʵ������0λ��ʼ���û��ӽǴ�1��ʼ
			int value=Integer.parseInt(datas[2]);
			
			blocks[x][y].isSet=true;
			blocks[x][y].value.add(value);
		    data =b.readLine(); 
		}
		b.close();
		
		
		
		//��ʼ���������п�
		initializationBlocks();
		/*
		*/
		
		//��ʼ�����̵Ĳ�������
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
		
		
		//ִ��Alogorithm X�㷨
		AlogorithmX(lines);
		
	}
	


	public void initializationBlocks(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(!(blocks[i][j].isSet)){
					
					//��ʼ��ÿһ�����ֵ��Ĭ����9�ֿ���
					for(int k=1;k<10;k++){
						blocks[i][j].value.add(k);
					}
					//��һ���ж��ٸ�Ԫ�ر�ȷ��
					for(int l=0;l<9;l++){
						if(blocks[i][l].isSet){
							blocks[i][j].value.remove(blocks[i][l].value.get(0));
						}
					}
					//��һ���ж��ٸ�Ԫ�ر�ȷ��
					for(int l=0;l<9;l++){
						if(blocks[l][j].isSet){
							blocks[i][j].value.remove(blocks[l][j].value.get(0));
						}
					}
					//���ھŹ������ж��ٸ�Ԫ�ر�ȷ��
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
	}//��������
	
	public void initializationMatrix(){
		/*
		 * ��������ÿһ����324��
		 * ��һ��81����������1����ÿ���ո����д��һ������
		 * �ڶ���81����������2����ÿ�б��������������
		 * ������81����������3����ÿ�б��������������
		 * ���ĸ�81����������4����ÿ�����θ���������������
		 */
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				BlockPO block=blocks[i][j];
				
				//x��y��ʾ����������ڵľŹ�������Ϸ�������
				int x=((i)/3)*3;
				int y=((j)/3)*3;
				int numBigBlock=x+y/3;
				//��ÿ������ÿһ�ֵĿ������ɾ����һ��
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
			//�޽�
			//System.out.println("ʧ����һ��·");
		}else{
			step2(linesThis);
		}
		
	}
	//Succ��ʾ������ʾ�����ڵݹ��е��ã���ԭ�����в����ϵĲ���
	public void AlogorithmXSucc(ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer) {
		if(step1(linesThis)==0){
			//�޽�
			//System.out.println("ʧ����һ��·");
		}else{
			step2Succ(linesThis,answer);
		}
	}
	
	public int step1(ArrayList<LineForMatrix> linesThis){
		//��ʼ��һ�����������
		System.out.println(linesThis.get(0).line.size());
				int length=linesThis.get(0).line.size();
				int[] numberOfEachColumn=new int[length];
				for(int i=0;i<length;i++){
					numberOfEachColumn[i]=0;
				}
				//��������
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
				//ð������
				int[] temp=numberOfEachColumn;//������̫���ˣ�������
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
				//��ʼ��һ�����������
				int length=linesThis.get(0).line.size();
				int[] numberOfEachColumn=new int[length];
				for(int i=0;i<length;i++){
					numberOfEachColumn[i]=0;
				}
				//��������
				for(int i=0;i<linesThis.size();i++){
					LineForMatrix aLine=linesThis.get(i);
					for(int j=0;j<length;j++){
						int value=aLine.line.get(j);
						if(value==1){
							numberOfEachColumn[j]+=1;
						}
					}
				}
				//��������С1����c
				int leastSize=step1(linesThis);
			//	System.out.println("leastSize"+leastSize);
				ArrayList<Integer> columnNum=new ArrayList<Integer>();//���������еı��
				for(int i=0;i<length;i++){
					if(numberOfEachColumn[i]==leastSize){
						columnNum.add(i);
						//System.out.println("��ӽ�ȥ���кţ�"+i);
					}
				}
				//��ʼ����������
				for(int i=0;i<columnNum.size();i++){
					ArrayList<LineForMatrix> forNext=copy(linesThis);
					step3(columnNum.get(i),forNext);
				}
	}

	public void step2Succ(ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer){
		//��ʼ��һ�����������
		int length=linesThis.get(0).line.size();
		int[] numberOfEachColumn=new int[length];
		for(int i=0;i<length;i++){
			numberOfEachColumn[i]=0;
		}
		//��������
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			for(int j=0;j<length;j++){
				int value=aLine.line.get(j);
				if(value==1){
					numberOfEachColumn[j]+=1;
				}
			}
		}
		//��������С1����c
		int leastSize=step1(linesThis);
	//	System.out.println("leastSize"+leastSize);
		ArrayList<Integer> columnNum=new ArrayList<Integer>();//���������еı��
		
		for(int i=0;i<length;i++){
			if(numberOfEachColumn[i]==leastSize){
				columnNum.add(i);
				//System.out.println("columnNum.add(i)"+i);
			}
		}
		//��ʼ����������
		for(int i=0;i<columnNum.size();i++){
			ArrayList<LineForMatrix> forNext=copy(linesThis);
			ArrayList<Integer> answerForNext=copy2(answer);
			step3Succ(columnNum.get(i),forNext,answerForNext);
		}
}
	
	public void step3(Integer column,ArrayList<LineForMatrix> linesThis) {
		//��ʼ��һ�����飬�Ѿ�������C��1���ж��Ž�ȥ
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
			ArrayList<Integer> answer=new ArrayList<Integer>();//��Ǵ𰸵�array
			LineForMatrix aLine=linesThis.get(lineNum);
			answer.add(aLine.lineNum);
			//System.out.println("lineNum"+lineNum);
			ArrayList<Integer> answerForNext=copy2(answer);
			ArrayList<LineForMatrix> forNext=copy(linesThis);
			step4(lineNum,forNext,answerForNext);
		}
	}

	public void step3Succ(Integer column,ArrayList<LineForMatrix> linesThis,ArrayList<Integer> answer) {
		//��ʼ��һ�����飬�Ѿ�������C��1���ж��Ž��� 		
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
		
		//System.out.println("������ʼ"+linesThis.size());
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
				//��ȥ��r�����е�ֵΪ1����
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				System.out.println("�����쳣");
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			System.out.println("�����쳣");
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
