/*인터넷으로 제품 이미지 가져오기*/
package day1106.db;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.file.FileManager;

public class CollectorFrame extends JFrame {
	JTextField t_url;
	JButton bt;
	JButton bt_apply;//이미지 그리기 반영하기
	
	BufferedImage buffr;//url로 가져온 이미지 정보를 담은 객체
	ShoppingApp shoppingApp;//null
	File file;//인터넷으로부터 수집된 파일

	public CollectorFrame(ShoppingApp shoppingApp) {
		this.shoppingApp=shoppingApp; 
		setLayout(new FlowLayout());
		
		t_url = new JTextField();
		bt = new JButton("수집실행");
		bt_apply=new JButton("반영하기");
		
		t_url.setPreferredSize(new Dimension(580,40));
		
		add(t_url);
		add(bt);
		add(bt_apply);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collect();
			}
		});
		bt_apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ShoppingApp 클래스의 img 변수의 값을 인터넷상 이미지로 교체하고
				shoppingApp.getTargetImage(file.getAbsolutePath());//디렉토리포함한 파일의 풀경로
				//ShoppingApp 클래스의 preview()메서드호출
				shoppingApp.preview();
			}
		});
		
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(600,250);
	}
	
	public void collect() {
		//가져올 데이터가 이미지인 경우엔 아래의 방법이 유용
		try {
			URL url=new URL(t_url.getText());
			buffr=ImageIO.read(url);
			
			//현재까지는 메모리에 존재하므로 실제 파일로 저장해놓자
			//저장 할 파일명은 우리가 지정.규칙필요 시 분 초 밀리초
			//1.파일명 규칙-파일명가장 마지막 슬래스의 다음 문자열부터 마지막 문자열까지
			//2.파일명에서.(점)을 기준으로 문자열을 분리시키면 배열이 생성되고 두번째 요소가 확장
			long time=System.currentTimeMillis();//현재시간반영-파일이름자동설정 겹치지않게
			System.out.println(time);
			String filename=FileManager.getFilename(t_url.getText());
			String extend=FileManager.getExtend(filename);
			
			//빈파일생성
			file=new File("D:/koreaIT/Workspace/java_workspace/SeoProject/sre/travel/"+time+"."+extend);
			//빈파일에다가 이미지 데이터를 쓰자(출력)
			ImageIO.write(buffr, extend, file);
			JOptionPane.showMessageDialog(this, "가져오기 완료");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
