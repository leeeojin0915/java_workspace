package day1111.board;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BoardDetail extends JPanel {
	JTextField t_title, t_writer;
	JTextArea content;
	JScrollPane scroll;

	JButton bt_edit;// 수정
	JButton bt_del;// 삭제
	JButton bt_list;

	BoardApp boardApp;
	Connection con;
	Board board = null;// 수정,삭제시에도 이 안에 들어있는 정보들을 활용하기 위해
						// 지역변수로 선언하지 않고 멤버변수로 선언

	public BoardDetail(BoardApp boardApp) {
		this.boardApp = boardApp;
		con = boardApp.getCon();
		t_title = new JTextField();
		t_writer = new JTextField();
		content = new JTextArea();
		scroll = new JScrollPane(content);
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		bt_list = new JButton("목록");

		// 스타일
		t_title.setPreferredSize(new Dimension(780, 35));
		t_writer.setPreferredSize(new Dimension(780, 35));
		scroll.setPreferredSize(new Dimension(780, 200));

		// 조립
		add(t_title);
		add(t_writer);
		add(scroll);
		add(bt_edit);
		add(bt_del);
		add(bt_list);

		setPreferredSize(new Dimension(780, 500));
		setVisible(true);

		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardList boardlist = (BoardList) boardApp.getPages(BoardApp.BOARD_LIST);// 리스트갱신
				boardlist.getList();

				boardApp.setPage(BoardApp.BOARD_LIST);// 목록보기
			}
		});
		bt_del.addActionListener((e) -> {
			int ans = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까??");
			if (ans == JOptionPane.OK_OPTION) {
				// 이동하는 디자인은 따로 반환해서 처리해주는 것이 좋다
				// 그래서 위로 올렸음
				int result = del(board.getBoard_id());
				if (result == 0) {
					JOptionPane.showMessageDialog(this, "삭제실패");
				} else {
					JOptionPane.showMessageDialog(this, "삭제성공");
					BoardList boardlist = (BoardList) boardApp.getPages(BoardApp.BOARD_LIST);// 리스트갱신
					boardlist.getList();
					boardApp.setPage(BoardApp.BOARD_LIST);// 목록 보여주기
				}
			}
		});
		bt_edit.addActionListener((e) -> {
			int result = edit(board);
			if (result == 0) {
				JOptionPane.showMessageDialog(this, "수정실패");
			} else {
				JOptionPane.showMessageDialog(this, "수정성공");
				BoardList boardlist = (BoardList) boardApp.getPages(BoardApp.BOARD_LIST);// 리스트갱신
				boardlist.getList();
				boardApp.setPage(BoardApp.BOARD_LIST);// 목록 보여주기
			}
		});
	}

	// 조회수 증가
	public void updateHit(int board_id) {
		PreparedStatement pstmt=null;
		String sql="update board set hit=hit+1 where board_id="+board_id;
		try {
			pstmt=con.prepareStatement(sql);//쿼리문 준비
			int result=pstmt.executeUpdate();//쿼리문수행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	// 한건가져오기
	public void getDetail(int board_id) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from board where board_id=" + board_id;
		System.out.println(sql);

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 커서를 한칸 전진
			if (rs.next()) {// 레코드가 있다면
				board = new Board();// create empty object
				board.setBoard_id(rs.getInt("board_id"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));// 조회수는 숫자

				// 데이터채우기
				t_title.setText(board.getTitle());// 제목
				t_writer.setText(board.getWriter());// 작성자
				content.setText(board.getContent());// 상세보기

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int del(int board_id) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "delete from board where board_id=" + board_id;
		// System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);// 쿼리준비
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public int edit(Board board) {// 낱개로 전달하지 말고 1건의 게시물을 담고있는 인스턴스를 전달하자
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "update board set title=?, content=? where board_id=?";// 현재글 board_id

		try {
			pstmt = con.prepareStatement(sql);// 쿼리문수행
			// 사용자가 입력한 값으로 대체
			pstmt.setString(1, t_title.getText());// 사용자가 입력한 값
			pstmt.setString(2, content.getText());// 사용자가 입력한 값
			pstmt.setInt(3, board.getBoard_id());// 기본 상세보기에서의 board_id
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}
}
