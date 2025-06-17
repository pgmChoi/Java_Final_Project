package studysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
	// === DB 설정 ===
	private static final String URL = "jdbc:mysql://localhost:3306/studysystem";
	private static final String USER = "root";
	private static final String PASSWORD = "rootroot";

	// === DB 연결 ===
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// === 로그인 체크 ===
	public static boolean loginCheck(String userId, String password) { // 로그인 체크
		String sql = "SELECT * FROM member WHERE user_id = ? AND password = ?"; // DB 아이디 확인
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) { // 연동 과정에서 에러가 나는 지 확인

			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery(); // 조회한 결과를 행 단위 저장
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// === Member 클래스 ===
		public static class Member {
			public String id, email;

			public Member(String id, String email) {
				this.id = id;
				this.email = email;
			}
		}

	// === 전체 회원 조회 ===
	public static List<Member> getAllMembers() {
		List<Member> list = new ArrayList<>();
		String sql = "SELECT user_id, email FROM member";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String id = rs.getString("user_id");
				String email = rs.getString("email");
				list.add(new Member(id, email));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// === 회원 삭제 ===
	public static boolean deleteMemberById(String userId) {
		String sql = "DELETE FROM member WHERE user_id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, userId);
			return stmt.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// === 예약 삭제 ===
	public static boolean deleteReservation(String userId, String date, String time, String seat) {
		String sql = "DELETE FROM reservations WHERE user_id = ? AND date = ? AND time = ? AND seat = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, userId);
			stmt.setString(2, date);
			stmt.setString(3, time);
			stmt.setString(4, seat);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// === JTable 삭제 버튼 렌더러 === 디자인 생성
	public static class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
		    setText("삭제");
		    setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		    setForeground(Color.WHITE);
		    setBackground(new Color(220, 53, 69));  // 부트스트랩의 빨간 계열
		    setFocusPainted(false);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return this;
		}
	}

	// === JTable 삭제 버튼 에디터 === 계정 삭제 동작하는 event라고 보면 됨
	public static class ButtonEditor extends DefaultCellEditor {
		private final JButton button;
		private String userId;
		private boolean clicked;
		private final UserManagement panel;

		public ButtonEditor(JCheckBox checkBox, UserManagement panel) {
			super(checkBox);
			this.panel = panel;
			button = new JButton("삭제");
			button.addActionListener(e -> fireEditingStopped());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			userId = table.getValueAt(row, 0).toString();
			clicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
		    if (clicked) {
		        int result = JOptionPane.showConfirmDialog(button, userId + " 계정을 삭제하시겠습니까?", "확인",
		                JOptionPane.YES_NO_OPTION);
		        if (result == JOptionPane.YES_OPTION) {
		            if (DB.deleteMemberById(userId)) {
		                JOptionPane.showMessageDialog(button, "삭제 성공");

		                // ✅ 테이블 리프레시를 안전하게 큐에 등록
		                SwingUtilities.invokeLater(() -> panel.refreshTable());
		            } else {
		                JOptionPane.showMessageDialog(button, "삭제 실패");
		            }
		        }
		    }
		    clicked = false;
		    return "삭제";
		}
	}

	// === 버튼 에디터 (예약 삭제 기능 연동) ===
    public static class HistoryButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private boolean isPushed = false;
        private int selectedRow = -1;
        private final DefaultTableModel model;
        private final JTable table;

        public HistoryButtonEditor(JCheckBox checkBox, JTable table, DefaultTableModel model) {
            super(checkBox);
            this.table = table;
            this.model = model;
            this.button = new JButton("delete");
            this.button.setOpaque(true);

            button.addActionListener(e -> {
                try {
                   // 값 가져오기 (컬럼 개수 먼저 검증)
                    String date = model.getValueAt(selectedRow, 0).toString();
                    String time = model.getValueAt(selectedRow, 1).toString();
                    String seat = model.getValueAt(selectedRow, 2).toString();
                    String userId = UserSession.getUsername();

                    // DB 삭제
                    String sql = "DELETE FROM reservations WHERE user_id = ? AND date = ? AND time = ? AND seat = ?";
                    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, userId);
                        stmt.setString(2, date);
                        stmt.setString(3, time);
                        stmt.setString(4, seat);
                        int affected = stmt.executeUpdate(); //삭제이기에 Update
                        
                        if (affected > 0) {
                           fireEditingStopped();
                            model.removeRow(selectedRow); // UI 갱신
                            JOptionPane.showMessageDialog(null, "예약이 삭제되었습니다.");
                        } else {
                            JOptionPane.showMessageDialog(null, "삭제 실패: 예약을 찾을 수 없습니다.");
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "DB 오류: " + ex.getMessage());
                }
                isPushed = false;
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "삭제";
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }	
}