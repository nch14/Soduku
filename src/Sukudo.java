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
					int indexOfLimit4=(i-x)*3+(j-y);
					
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
	
}
