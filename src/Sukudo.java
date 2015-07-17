import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import blockProcess.*;


public class Sukudo {
	BlockPO[][] blocks;
	ArrayList<LineForMatrix> lines;
	public static void main(String[] args) throws IOException {
		// TODO �Զ����ɵķ������
		Sukudo sudo=new Sukudo();
		sudo.startGame();
	}
	
	public void startGame() throws IOException{
		blocks=new BlockPO[9][9];
		
		//�ȴ��û�����
		System.out.println("�������趨��������ʼ�����ݣ���������ʽ��0 0 1���У��У���ֵ�����ÿո������");
		while(true){
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
			
			System.out.println("����������趨��������ʼ�����ݣ���������ʽ��0 0 1���У��У���ֵ�����ÿո��������������ѳ�ʼ����ϣ������롰end����ʾ����");
		}
		
		//��ʼ���������п�
		initializationBlocks();
		//��ʼ�����̵Ĳ�������
		initializationMatrix();
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
					int x=((i+1)/3)*3;
					int y=((j+1)/3)*3;
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
				int x=((i+1)/3)*3;
				int y=((j+1)/3)*3;
				
				//��ÿ������ÿһ�ֵĿ������ɾ����һ��
				for(int k=0;k<block.value.size();k++){
					int value=block.value.get(k);
					
					LineForMatrix aLine=new LineForMatrix();
					int indexOfLimit1=i*9+j;
					int indexOfLimit2=(value-1)*9+i+81;
					int indexOfLimit3=(value-1)*9+j+162;
					int indexOfLimit4=(i-x)*3+(j-y)+243;
					
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
			System.out.println("�����ˣ��㲻����������ģ�������Ϊʲô���ұ���˧");
		}else{
			step2(linesThis);
		}
		
	}
	//Succ��ʾ������ʾ�����ڵݹ��е��ã���ԭ�����в����ϵĲ���
	public void AlogorithmXSucc(ArrayList<LineForMatrix> linesThis,ArrayList<LineForMatrix> answer) {
		if(step1(linesThis)==0){
			//�޽�
			System.out.println("�����ˣ��㲻����������ģ�������Ϊʲô���ұ���˧");
		}else{
			step2Succ(linesThis,answer);
		}
	}
	
	/*public int getLeastColumn(){
		//��ʼ��һ�����������
		int[] numberOfEachColumn=new int[324];
		for(int i=0;i<323;i++){
			numberOfEachColumn[i]=0;
		}
		//��������
		for(int i=0;i<lines.size();i++){
			LineForMatrix aLine=lines.get(i);
			for(int j=0;j<323;j++){
				int value=aLine.line.get(j);
				if(value==1){
					numberOfEachColumn[j]+=1;
				}
			}
		}
		//ð������
		int[] temp=numberOfEachColumn;//������̫���ˣ�������
		int minColumn=0;
		for(int i=0;i<323;i++){
			if(temp[i]<temp[i+1]){
				int a=temp[i];
				temp[i]=temp[i+1];
				temp[i+1]=a;
			}else{
				minColumn=i+1;
			}
		}
		return minColumn;
	}*/
	
	public int step1(ArrayList<LineForMatrix> linesThis){
		//��ʼ��һ�����������
				int length=linesThis.get(0).line.size();
				int[] numberOfEachColumn=new int[length];
				for(int i=0;i<length-1;i++){
					numberOfEachColumn[i]=0;
				}
				//��������
				for(int i=0;i<linesThis.size();i++){
					LineForMatrix aLine=linesThis.get(i);
					for(int j=0;j<aLine.line.size();j++){
						int value=aLine.line.get(j);
						if(value==1){
							numberOfEachColumn[j]+=1;
						}
					}
				}
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
				for(int i=0;i<length-1;i++){
					numberOfEachColumn[i]=0;
				}
				//��������
				for(int i=0;i<linesThis.size();i++){
					LineForMatrix aLine=linesThis.get(i);
					for(int j=0;j<length-1;j++){
						int value=aLine.line.get(j);
						if(value==1){
							numberOfEachColumn[j]+=1;
						}
					}
				}
				//��������С1����c
				int leastSize=step1(linesThis);
				ArrayList<Integer> columnNum=new ArrayList<Integer>();//���������еı��
				for(int i=0;i<linesThis.size();i++){
					if(numberOfEachColumn[i]==leastSize){
						columnNum.add(i);
					}
				}
				//��ʼ����������
				for(int i=0;i<columnNum.size();i++){
					step3(columnNum.get(i),linesThis);
				}
	}

	public void step2Succ(ArrayList<LineForMatrix> linesThis,ArrayList<LineForMatrix> answer){
		//��ʼ��һ�����������
		int length=linesThis.get(0).line.size();
		int[] numberOfEachColumn=new int[length];
		for(int i=0;i<length-1;i++){
			numberOfEachColumn[i]=0;
		}
		//��������
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			for(int j=0;j<length-1;j++){
				int value=aLine.line.get(j);
				if(value==1){
					numberOfEachColumn[j]+=1;
				}
			}
		}
		//��������С1����c
		int leastSize=step1(linesThis);
		ArrayList<Integer> columnNum=new ArrayList<Integer>();//���������еı��
		for(int i=0;i<linesThis.size();i++){
			if(numberOfEachColumn[i]==leastSize){
				columnNum.add(i);
			}
		}
		//��ʼ����������
		for(int i=0;i<columnNum.size();i++){
			step3Succ(columnNum.get(i),linesThis,answer);
		}
}
	
	public void step3(Integer column,ArrayList<LineForMatrix> linesThis) {
		//��ʼ��һ�����飬�Ѿ�������C��1���ж��Ž�ȥ
		ArrayList<Integer> linesContainThis=new ArrayList<Integer>();
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			if(aLine.line.get(column)==1){
				linesContainThis.add(i);
			}
		}
		
		for(int i=0;i<linesContainThis.size();i++){
			int lineNum=linesContainThis.get(i);
			ArrayList<LineForMatrix> answer=new ArrayList<LineForMatrix>();//��Ǵ𰸵�array
			LineForMatrix aLine=linesThis.get(lineNum);
			answer.add(aLine);
			step4(lineNum,linesThis,answer);
		}
		
		
	}

	public void step3Succ(Integer column,ArrayList<LineForMatrix> linesThis,ArrayList<LineForMatrix> answer) {
		//��ʼ��һ�����飬�Ѿ�������C��1���ж��Ž�ȥ
		ArrayList<Integer> linesContainThis=new ArrayList<Integer>();
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix aLine=linesThis.get(i);
			if(aLine.line.get(column)==1){
				linesContainThis.add(i);
			}
		}
		
		for(int i=0;i<linesContainThis.size();i++){
			int lineNum=linesContainThis.get(i);
			LineForMatrix aLine=linesThis.get(lineNum);
			answer.add(aLine);
			step4(lineNum,linesThis,answer);
		}
		
		
	}
	
	public void step4(int lineNum, ArrayList<LineForMatrix> linesThis,
			ArrayList<LineForMatrix> answer) {
		
		LineForMatrix aLine=linesThis.get(lineNum);
		
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix tempLine=linesThis.get(i);
			if(i==lineNum){
				//ʲô������
			}else{
				for(int j=0;j<aLine.line.size();j++){
					if(tempLine.line.get(j)==aLine.line.get(j)){
						linesThis.remove(i);
						i=i-1;
						break;
					}	
				}
			}		
		}
		//��ȥ��r�����е�ֵΪ1����
		ArrayList<Integer> numberOfColumn=new ArrayList<Integer>();
		for(int i=0;i<aLine.line.size();i++){
			if(aLine.line.get(i)==1){
				numberOfColumn.add(i);
			}
		}
		for(int i=0;i<linesThis.size();i++){
			LineForMatrix lineTemp=linesThis.get(i);
			
			for(int j=0;j<numberOfColumn.size();j++){
				lineTemp.line.remove(numberOfColumn.get(j));
			}
		}
		
		linesThis.remove(lineNum);
		if(linesThis.size()==0){
			System.out.println("YOU GET IT!");
		}else{
			AlogorithmXSucc(linesThis,answer);
		}
	}
	
	
}
