import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import blockProcess.BlockPO;


public class Sukudo {
	BlockPO[][] blocks;
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
			int x=Integer.parseInt(datas[0]);
			int y=Integer.parseInt(datas[1]);
			int value=Integer.parseInt(datas[2]);
			
			blocks[x][y].isSet=true;
			blocks[x][y].value.add(value);
			
			System.out.println("����������趨��������ʼ�����ݣ���������ʽ��0 0 1���У��У���ֵ�����ÿո��������������ѳ�ʼ����ϣ������롰end����ʾ����");
		}
		
		//��ʼ������
		
		
	}
}
