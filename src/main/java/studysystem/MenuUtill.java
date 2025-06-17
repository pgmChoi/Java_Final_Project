package studysystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUtill {

    public static JMenuBar createMenuBar(JFrame currentFrame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("메뉴");
        
        JMenuItem seatStatusItem = new JMenuItem("좌석 보기");
        JMenuItem historyItem = new JMenuItem("예약 현황"); 
        JMenuItem logOutItem = new JMenuItem("로그아웃");

        // 이동 이벤트 설정

        seatStatusItem.addActionListener(e -> {
            currentFrame.dispose();
            new SeatStatus().setVisible(true);
        });

        historyItem.addActionListener(e -> {
            currentFrame.dispose();
            new History().setVisible(true);
        });
        
        // 로그아웃
        logOutItem.addActionListener(e ->{
           currentFrame.dispose();
           JOptionPane.showMessageDialog(currentFrame, "로그아웃 되었습니다.");
           new Login().setVisible(true);
        });

        // 메뉴에 추가
        menu.add(seatStatusItem);
        menu.add(historyItem);
        menu.add(logOutItem);
        menuBar.add(menu);

        return menuBar;
    }
}