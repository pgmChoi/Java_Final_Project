package studysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatLightLaf;

import studysystem.DB.ButtonEditor;
import studysystem.DB.ButtonRenderer;
import studysystem.DB.HistoryButtonEditor;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class History extends JFrame {
	//커밋을 하겠습니다.
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new History().setVisible(true);
        });
    }
	
    public History() {
        setTitle("Reservation History");
        setSize(550, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 534, 778);
        mainPanel.setLayout(null); //setBounds로 위치 설정하려고 씀
      //메뉴바 추가
        setJMenuBar(MenuUtill.createMenuBar(this));

        JLabel titleLabel = new JLabel("       예약 내역");
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setBackground(new Color(255, 255, 255));
        titleLabel.setBounds(22, 210, 407, 32);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);
        String reSeat;
        String[] columnNames = { "data", "time", "seat", "delete" }; // 테이블에서 표시될 컬럼 이름
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 기본 테이블 관리 모델 ,열 4개, 0 은 초기 행
        String sql = "SELECT date, time, seat FROM reservations WHERE user_id = ?"; // 날짜, 시간, 좌석 가져오기
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){ // 문구 넣기
            stmt.setString(1, UserSession.getUsername()); // 아이디

            ResultSet rs = stmt.executeQuery(); // select라서 excute 사용함
            while (rs.next()) {
                String date = rs.getString("date"); // reservations 테이블에서 날짜 가지고 오기
                String time = rs.getString("time"); // 시간 가지고 오기
                String seat = rs.getString("seat"); // 좌석 가지고 오기
                
                Object[] col = new Object[]{date, time, seat, "delete"};
                model.addRow(col); //addRow 함수가 Object 타입으로 매개변수를 받음
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 16));       // 표 내용 폰트
        table.setRowHeight(30);                                    // 행 높이
        table.setGridColor(Color.LIGHT_GRAY); 
        table.setGridColor(new Color(230, 230, 230));
        table.setShowHorizontalLines(true); // 가로줄 표시 
        
        // 셀 정렬 (가운데 정렬)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //셀 안의 텍스트나 컴포넌트를 어떻게 표시할지 지정
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // 가운데로 정렬
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // 가로 중앙 정렬
        }

        // 테이블 배경색 및 라인 없애기
        table.setShowHorizontalLines(false);// 수평 라인 없애기
        table.setShowVerticalLines(false); // 수직 라인 없애기
        table.setBackground(new Color(255, 255, 255, 180)); // 약간 투명하게
        table.setOpaque(false); //투명 설정
        
        getContentPane().setLayout(null);
        table.getColumn("delete").setCellEditor(new HistoryButtonEditor(new JCheckBox(), table, model)); // Jtable에 delete라는 셀을 클릭했을 때
        
        //스크롤 기능 추가
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // 투명화
        scrollPane.getViewport().setOpaque(false); //스크롤 패널을 투명화
        scrollPane.setBounds(12, 241, 510, 233);
        mainPanel.setFont(new Font("굴림", Font.BOLD, 20));
        mainPanel.add(scrollPane);
        

        // 스크롤 가능한 전체 프레임에 mainPanel 추가
        getContentPane().add(mainPanel);
        
        JButton btnNewButton = new JButton("<");

        // 👉 스타일 설정
        btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        btnNewButton.setBackground(new Color(173, 216, 230)); // 연한 하늘색
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton.setFocusPainted(false);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setOpaque(true);
        btnNewButton.setContentAreaFilled(true); // 배경 채우기
        btnNewButton.addActionListener(e -> {
        	   new SeatStatus().setVisible(true); // 다시 좌석 칸으로 돌아가기
        	   dispose();
        });
        btnNewButton.setBounds(456, 210, 66, 32);
        mainPanel.add(btnNewButton);
        
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(History.class.getResource("/images/oceans.png"))); // 백그라운드 이미지 생성
        lblNewLabel.setBounds(0, 0, 550, 850);
        mainPanel.add(lblNewLabel);
    }
}