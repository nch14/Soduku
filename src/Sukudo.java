import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import blockProcess.*;


public class Sukudo {
	BlockPO[][] blocks;
	ArrayList<LineForMatrix> lines;
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		Sukudo sudo=new Sukudo();
		sudo.startGame();
	}
	
	public void startGame() throws IOException{
		blocks=new BlockPO[9][9];
		
		//等待用户输入
		System.out.println("请输入设定的数独初始化数据，以如下形式：0 0 1（行，列，数值，并用空格隔开）");
		while(true){
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
			
			System.out.println("请继续输入设定的数独初始化数据，以如下形式：0 0 1（行，列，数值，并用空格隔开），如果您已初始化完毕，请敲入“end”表示结束");
		}
		
		//初始化棋盘所有块
		initializationBlocks();
		//初始化棋盘的布尔矩阵
		initializationMatrix();
		
		
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
				int x=((i+1)/3)*3;
				int y=((j+1)/3)*3;
				
				//对每个格子每一种的可能生成矩阵的一行
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
