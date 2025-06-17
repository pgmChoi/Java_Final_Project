package studysystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import org.pushingpixels.trident.Timeline;

public class SignUp extends JFrame {

    private RoundedTextField IdText;
    private RoundedPasswordField PasswordText;
    private RoundedPasswordField PWCheckText;
    private RoundedTextField EmailText;
    private RoundedTextField NameText;

    private FlatButton btnPrev;
    private FlatButton btnComplete;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            SignUp window = new SignUp();
            window.setVisible(true);
        });
    }

    public SignUp() {
        setTitle("회원가입");
        setBounds(100, 100, 550, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // 배경 이미지
        JLabel bg = new JLabel(new ImageIcon(getClass().getResource("/images/Debugging Room.gif")));
        bg.setBounds(0, 0, 534, 811);
        getContentPane().add(bg);


        // 카드 패널
        RoundedPanel formPanel = new RoundedPanel(30);
        formPanel.setBackground(new Color(255, 255, 255, 180)); // 반투명
        formPanel.setBounds(20, 200, 500, 500);
        formPanel.setLayout(null);
        bg.add(formPanel);
        // 제목
        JLabel title = new JLabel("회원가입");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        title.setForeground(Color.DARK_GRAY);
        title.setBounds(150, 15, 200, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(title);  // ✅ 여기로 수정!

        // 각 필드 및 라벨
        int y = 50;
        int gap = 70;

        JLabel idLabel = label("아이디", 20, y);
        formPanel.add(idLabel);
        IdText = roundedField("아이디", 20, y + 20);
        formPanel.add(IdText);

        y += gap;
        JLabel pwLabel = label("비밀번호", 20, y);
        formPanel.add(pwLabel);
        PasswordText = roundedPassword("비밀번호", 20, y + 20);
        formPanel.add(PasswordText);

        y += gap;
        JLabel pwcLabel = label("비밀번호 확인", 20, y);
        formPanel.add(pwcLabel);
        PWCheckText = roundedPassword("비밀번호 확인", 20, y + 20);
        formPanel.add(PWCheckText);

        y += gap;
        JLabel emailLabel = label("이메일", 20, y);
        formPanel.add(emailLabel);
        EmailText = roundedField("이메일", 20, y + 20);
        formPanel.add(EmailText);

        y += gap;
        JLabel nameLabel = label("닉네임", 20, y);
        formPanel.add(nameLabel);
        NameText = roundedField("닉네임", 20, y + 20);
        formPanel.add(NameText);

     // 이전 버튼
        btnPrev = new FlatButton();
        btnPrev.setText("이전");
        btnPrev.setFont(new Font("굴림", Font.BOLD, 16));
        btnPrev.setBounds(110, 440, 120, 40);
        btnPrev.setBackground(new Color(220, 220, 220)); // 연한 회색
        btnPrev.setForeground(Color.BLACK);
        btnPrev.setBorderPainted(false);
        formPanel.add(btnPrev);
        addHoverAnimation(btnPrev);
        btnPrev.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        // 완료 버튼
        btnComplete = new FlatButton();
        btnComplete.setText("완료");
        btnComplete.setFont(new Font("굴림", Font.BOLD, 16));
        btnComplete.setBounds(260, 440, 120, 40);
        btnComplete.setBackground(new Color(220, 220, 220)); // 같은 색상으로 통일
        btnComplete.setForeground(Color.BLACK);
        btnComplete.setBorderPainted(false);
        formPanel.add(btnComplete);
        addHoverAnimation(btnComplete);
        btnComplete.addActionListener(e -> handleSignUp());
    }

    private JLabel label(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        lbl.setForeground(Color.DARK_GRAY);
        lbl.setBounds(x, y, 200, 20);
        return lbl;
    }

    private RoundedTextField roundedField(String placeholder, int x, int y) {
        RoundedTextField field = new RoundedTextField(20);
        field.setBounds(x, y, 460, 40);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        return field;
    }

    private RoundedPasswordField roundedPassword(String placeholder, int x, int y) {
        RoundedPasswordField field = new RoundedPasswordField(20);
        field.setBounds(x, y, 460, 40);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.setEchoChar((char)0);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String pw = String.valueOf(field.getPassword());
                if (pw.equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('•');
                }
            }
            public void focusLost(FocusEvent e) {
                String pw = String.valueOf(field.getPassword());
                if (pw.isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char)0);
                }
            }
        });
        return field;
    }

    private void handleSignUp() {
        String userId = IdText.getText().trim();
        String password = new String(PasswordText.getPassword()).trim();
        String pwCheck = new String(PWCheckText.getPassword()).trim();
        String email = EmailText.getText().trim();
        String nickname = NameText.getText().trim();

        if (!password.equals(pwCheck)) {
            JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@") || !email.contains(".com")) {
            JOptionPane.showMessageDialog(this, "옳지 않은 이메일 형식입니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idCheckSql = "SELECT COUNT(*) FROM member WHERE user_id = ?";
        String insertSql = "INSERT INTO member (password, email, nickname, user_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement idStmt = conn.prepareStatement(idCheckSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            idStmt.setString(1, userId);
            ResultSet idRs = idStmt.executeQuery();
            if (idRs.next() && idRs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "아이디가 이미 존재합니다.", "중복 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            insertStmt.setString(1, password);
            insertStmt.setString(2, email);
            insertStmt.setString(3, nickname);
            insertStmt.setString(4, userId);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
            dispose();
            new Login().setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB 오류: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addHoverAnimation(FlatButton btn) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Timeline t = new Timeline(btn);
                t.addPropertyToInterpolate("background", btn.getBackground(), btn.getBackground().brighter());
                t.setDuration(200);
                t.play();
            }

            public void mouseExited(MouseEvent e) {
                Timeline t = new Timeline(btn);
                t.addPropertyToInterpolate("background", btn.getBackground().brighter(), btn.getBackground());
                t.setDuration(200);
                t.play();
            }
        });
    }
}