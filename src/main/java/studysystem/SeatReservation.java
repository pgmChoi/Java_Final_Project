package studysystem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
//커밋을 하게습니다.
public class SeatReservation extends JFrame {
    private JComboBox<String> dateCombo;
    private JComboBox<String> timeCombo;
    private FlatButton btnReserve;
    private FlatButton btnBack;

    public static void main(String[] args) {
        // FlatLaf 테마 적용
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(() -> new SeatReservation("1").setVisible(true));
    }

    public SeatReservation(String chairNum) {
        initialize(chairNum);
    }

    private void initialize(String chairNum) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/oceans.png")); // 이미지 아이콘 객체 생성함
        setTitle("Seat Reservation"); // 타이틀
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 껏을 때 eclipse에서도 꺼짐
        setSize(550, 850); //크기설정
        setLocationRelativeTo(null); //중앙 나타남
        setJMenuBar(MenuUtill.createMenuBar(this)); // 메뉴바 생성하기
        
        getContentPane().setLayout(null);

        // ✅ 반투명 패널 생성
        JPanel translucentPanel = new JPanel();
        translucentPanel.setBackground(new Color(255, 255, 255, 180)); // 반투명 흰 배경
        translucentPanel.setLayout(null); 
        translucentPanel.setBounds(12, 279, 500, 296);
        getContentPane().add(translucentPanel);

        // Title
        JLabel lblTitle = new JLabel("Seat Reservation") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setFont(getFont());
                g2.setColor(Color.BLACK);
                g2.drawString(getText(), 3, getBaseline(getWidth(), getHeight()) + 3); // 그림자 효과 만들기
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), 0, getBaseline(getWidth(), getHeight())); // 윤곽선 만들기
                g2.dispose(); //메모리 관리
            }
        };
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setBounds(121, 199, 400, 50);
        getContentPane().add(lblTitle);

        // Seat Label
        JLabel lblSeatTitle = new JLabel("Seat:");
        lblSeatTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        lblSeatTitle.setBounds(30, 20, 60, 30);
        translucentPanel.add(lblSeatTitle);

        JLabel lblSeat = new JLabel(chairNum + "번 좌석");
        lblSeat.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        lblSeat.setBounds(100, 20, 300, 30);
        translucentPanel.add(lblSeat);

        // Date
        JLabel lblDate = new JLabel("Date:");
        lblDate.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        lblDate.setBounds(30, 80, 60, 30);
        translucentPanel.add(lblDate);

        dateCombo = new JComboBox<>(); // 시간 콤보박스
        dateCombo.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        dateCombo.setBounds(100, 80, 350, 35);
        LocalDate today = LocalDate.now(); // 현재 날짜 import java.time.local
        for (int i = 0; i < 7; i++) { // 7일 
            LocalDate d = today.plusDays(i); // 현재 날짜에서 7일 추가하기 today에 년도까지 저장되어있음
            dateCombo.addItem(d.getMonthValue() + "월 " + d.getDayOfMonth() + "일"); // O월O일 표시하기
        }
        translucentPanel.add(dateCombo); // 패널에 넣기

        // Time
        JLabel lblTime = new JLabel("Time:");
        lblTime.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        lblTime.setBounds(30, 140, 60, 30);
        translucentPanel.add(lblTime);

        timeCombo = new JComboBox<>(); // 시간 콤보박스 
        timeCombo.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        timeCombo.setBounds(100, 140, 350, 35);
        timeCombo.setVisible(false);  // 처음에는 숨김
        translucentPanel.add(timeCombo);    // 패널에 시간 콤보박스를 보여줌
        dateCombo.addActionListener(e -> {
            updateTimeOptions(today);          // 시간 리스트 갱신d
            timeCombo.setVisible(true);   // 날짜 선택 시 보여줌
        });

        // Reserve Button 
        btnReserve = new FlatButton();  // 버튼 이미지 꾸미기
        btnReserve.setText("Reserve");
        btnReserve.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnReserve.setBackground(new Color(66, 133, 244));
        btnReserve.setForeground(Color.WHITE);
        btnReserve.setBounds(160, 210, 180, 45);
        btnReserve.addActionListener(e -> reserveSeat(chairNum)); // 버튼 눌렀을 때 
        translucentPanel.add(btnReserve);

        // Back 버튼
        btnBack = new FlatButton();
        btnBack.setText("<");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack.setBackground(new Color(238, 238, 238));
        btnBack.setBounds(20, 20, 45, 30);
        btnBack.addActionListener(e -> {
            new SeatStatus().setVisible(true);
            dispose();
        });
        getContentPane().add(btnBack);

        // 배경 이미지 마지막에 추가
        JLabel lblBackground = new JLabel("");
        lblBackground.setBounds(0, 0, 550, 850);
        lblBackground.setIcon(icon);
        getContentPane().add(lblBackground);
    }

    private void reserveSeat(String chairNum) {
        String selectedDate = (String) dateCombo.getSelectedItem(); // 날짜 콤보 박스에 선택된 값을 String으로 파싱해서 변수에 넣음
        String selectedTime = (String) timeCombo.getSelectedItem(); // 시간 콤보 박스에 선택된 값을 String으로 파싱해서 변수에 넣음
        String userId = UserSession.getUsername(); // 유저 아이디를 변수에 넣음

        String sql = "INSERT INTO reservations (user_id, seat, date, `time`) VALUES (?, ?, ?, ?)"; // DB에 추가 명령어를 변수에 넣음 

        try (Connection conn = DB.getConnection(); //DB 연동
             PreparedStatement stmt = conn.prepareStatement(sql)) { //SQL문 입력

            stmt.setString(1, userId); // values의 첫번 째 ?에 값 넣기
            stmt.setString(2, "Seat " + String.format("%02d", Integer.parseInt(chairNum)));// values의 두번 째 ?에 값 넣기 01, 02... 이렇게 넣기
            stmt.setString(3, selectedDate); // values의 세번 째 ?에 값 넣기
            stmt.setString(4, selectedTime); // // values의 네번 째 ?에 값 넣기

            stmt.executeUpdate(); // SQL문 실행
            JOptionPane.showMessageDialog(this, "예약이 완료되었습니다!");
            new History().setVisible(true);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "예약 중 오류가 발생했습니다:\n" + ex.getMessage(),
                "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void updateTimeOptions(LocalDate today) { // 현재 날짜 가져오기
    	timeCombo.removeAllItems(); // 기존 옵션 제거
        String selectedDateStr = (String) dateCombo.getSelectedItem(); // 선택된 날짜 가져오기
        LocalDate selectedDate = parseDate(selectedDateStr); // 날짜를 Data로 파싱
        int currentHour = LocalTime.now().getHour(); //현재 시간 에서 몇 시인지 가져오기 

        if (selectedDate != null && selectedDate.isEqual(today)) { // 현재 날짜이면
            // 오늘 날짜: 현재 시간 기준 + 1시간 부터 생성
            for (int h = currentHour + 1; h < 24; h++) {
                timeCombo.addItem(String.format("%02d:00", h));
            }
        } else {
            // 이후 날짜: 0시부터 23시까지 생성
            for (int h = 0; h < 24; h++) {
                timeCombo.addItem(String.format("%02d:00", h));
            }
        }

        // 예외 처리: 더 이상 선택 가능한 시간이 없는 경우
        if (timeCombo.getItemCount() == 0) {
            timeCombo.addItem("예약 불가");
            timeCombo.setEnabled(false);
        } else {
            timeCombo.setEnabled(true);
        }
    }
    
    //
    private LocalDate parseDate(String dateStr) { //현재 날짜
        try {
            String[] parts = dateStr.replace("월", "").replace("일", "").trim().split(" "); // 공백 기준으로 나눠서 받아온 날짜 배열에 넣기 예 : 6월 18일 -> 6 18
            int month = Integer.parseInt(parts[0]); // 월
            int day = Integer.parseInt(parts[1]); // 일
            return LocalDate.of(LocalDate.now().getYear(), month, day); //년, 월, 일 예 : 2025-06-18
        } catch (Exception e) {
            return null;
        }
    }
}