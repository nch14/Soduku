import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import blockProcess.BlockPO;


public class Sukudo {
	BlockPO[][] blocks;
	
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
		
		//初始化矩阵
		initializationMatrix();
		
	}
	
	public void initializationMatrix(){
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
		
		
		
		
	}
}
