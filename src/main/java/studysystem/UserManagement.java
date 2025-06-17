package studysystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class UserManagement extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagement() {
    	
    	//메뉴바 추가
        setJMenuBar(MenuUtill.createMenuBar(this));
        // 프레임 기본 설정
        setTitle("User Management");
        setSize(550, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // 상단 타이틀 라벨
        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(60, 207, 407, 32);
        getContentPane().add(titleLabel);

        // 테이블 모델 생성 및 삭제 컬럼만 수정 가능하도록 설정
        String[] columnNames = {"아이디", "이메일", "삭제"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        // 테이블 구성
        userTable = new JTable(tableModel);
        userTable.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        userTable.setRowHeight(30);
        userTable.setShowHorizontalLines(false);
        userTable.setShowVerticalLines(false);
        userTable.setOpaque(false);
        userTable.setBackground(new Color(255, 255, 255, 180));
        userTable.setGridColor(new Color(230, 230, 230));

        // 컬럼 헤더 스타일 지정
        JTableHeader header = userTable.getTableHeader();
        header.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        header.setBackground(new Color(200, 230, 250));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false); // 컬럼 이동 비활성화

        // 셀 가운데 정렬 적용
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 스크롤 패널로 테이블 감싸기
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(12, 241, 510, 233);
        getContentPane().add(scrollPane);

        // 사용자 목록 불러오기
        loadUserList();

        // 삭제 버튼 설정
        userTable.getColumn("삭제").setCellRenderer(new studysystem.DB.ButtonRenderer());
        userTable.getColumn("삭제").setCellEditor(new studysystem.DB.ButtonEditor(new JCheckBox(), this));

        // 배경 이미지 추가
        JLabel background = new JLabel(new ImageIcon(History.class.getResource("/images/oceans.png")));
        background.setBounds(-16, 0, 550, 850);
        getContentPane().add(background);
    }

    // DB에서 모든 사용자 목록을 불러와 테이블에 적용
    private void loadUserList() {
        List<studysystem.DB.Member> members = studysystem.DB.getAllMembers();
        tableModel.setRowCount(0); // 기존 테이블 데이터 초기화 a
        for (studysystem.DB.Member m : members) {
            tableModel.addRow(new Object[]{m.id, m.email, "삭제"});
        }
    }

    // 외부에서 테이블 갱신 요청 시 사용
    public void refreshTable() {
        loadUserList();
    }

}