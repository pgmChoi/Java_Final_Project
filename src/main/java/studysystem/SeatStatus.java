package studysystem;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class SeatStatus extends JFrame{
//커밋을 하겠습니다.
	
	private Map<String, JButton> seatButtons = new HashMap<>();

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
	   EventQueue.invokeLater(() -> new SeatStatus().setVisible(true));
   }

   /**
    * Create the application.
    */
   public SeatStatus() {
      initialize();
      updateReservedSeats();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      setBounds(100, 100, 469, 297);
      setSize(550, 850);
      
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ImageIcon icon = new ImageIcon(SeatStatus.class.getResource("/images/Seat.png"));
    //메뉴바 추가
      setJMenuBar(MenuUtill.createMenuBar(this));
      
      // 클래스 필드 상단에 추가
      getContentPane().setLayout(null);
      
      JButton btnSeat1 = new JButton("1");
      btnSeat1.setBounds(29, 248, 51, 36);
      seatButtons.put("1", btnSeat1);
      getContentPane().add(btnSeat1);
      btnSeat1.addActionListener(e -> {
        	 OpenClose(btnSeat1.getText());
      });
      
      JButton btnSeat2 = new JButton("2");
      btnSeat2.setBounds(79, 248, 51, 36);
      seatButtons.put("2", btnSeat2);
      getContentPane().add(btnSeat2);
      btnSeat2.addActionListener(e -> {
     	 OpenClose(btnSeat2.getText());
  	});
      
      JButton btnSeat3 = new JButton("3");
      btnSeat3.setBounds(129, 248, 51, 36);
      seatButtons.put("3", btnSeat3);
      getContentPane().add(btnSeat3);
      btnSeat3.addActionListener(e -> {
     	 OpenClose(btnSeat3.getText());
      });
      
      JButton btnSeat4 = new JButton("4");
      btnSeat4.setBounds(179, 248, 51, 36);
      seatButtons.put("4", btnSeat4);
      getContentPane().add(btnSeat4);
      btnSeat4.addActionListener(e -> {
      	 OpenClose(btnSeat4.getText());
       });
      
      JButton btnSeat5 = new JButton("5");
      btnSeat5.setBounds(229, 248, 51, 36);
      seatButtons.put("5", btnSeat5);
      getContentPane().add(btnSeat5);
      btnSeat5.addActionListener(e -> {
       	 OpenClose(btnSeat5.getText());
        });
      
      JButton btnSeat6 = new JButton("6");
      btnSeat6.setBounds(279, 248, 51, 36);
      seatButtons.put("6", btnSeat6);
      getContentPane().add(btnSeat6);
      btnSeat6.addActionListener(e -> {
       	 OpenClose(btnSeat6.getText());
        });
      
      JButton btnSeat7 = new JButton("7");
      btnSeat7.setBounds(329, 248, 51, 36);
      seatButtons.put("7", btnSeat7);
      getContentPane().add(btnSeat7);
      btnSeat7.addActionListener(e -> {
       	 OpenClose(btnSeat7.getText());
        });
      
      JButton btnSeat8 = new JButton("8");
      btnSeat8.setBounds(379, 248, 51, 36);
      seatButtons.put("8", btnSeat8);
      getContentPane().add(btnSeat8);
      btnSeat8.addActionListener(e -> {
       	 OpenClose(btnSeat8.getText());
        });
      
      JButton btnSeat9 = new JButton("9");
      btnSeat9.setBounds(431, 248, 51, 36);
      seatButtons.put("9", btnSeat9);
      getContentPane().add(btnSeat9);
      btnSeat9.addActionListener(e -> {
       	 OpenClose(btnSeat9.getText());
        });
      
      JButton btnSeat10 = new JButton("10");
      btnSeat10.setBounds(79, 294, 51, 36);
      seatButtons.put("10", btnSeat10);
      getContentPane().add(btnSeat10);
      btnSeat10.addActionListener(e -> {
        	 OpenClose(btnSeat10.getText());
         });
      
      JButton btnSeat11 = new JButton("11");
      btnSeat11.setBounds(129, 294, 51, 36);
      seatButtons.put("11", btnSeat11);
      getContentPane().add(btnSeat11);
      btnSeat11.addActionListener(e -> {
        	 OpenClose(btnSeat11.getText());
         });
      
      JButton btnSeat12 = new JButton("12");
      btnSeat12.setBounds(79, 329, 51, 36);
      seatButtons.put("12", btnSeat12);
      getContentPane().add(btnSeat12);
      btnSeat12.addActionListener(e -> {
        	 OpenClose(btnSeat12.getText());
         });
      
      JButton btnSeat13 = new JButton("13");
      btnSeat13.setBounds(129, 329, 51, 36);
      seatButtons.put("13", btnSeat13);
      getContentPane().add(btnSeat13);
      btnSeat13.addActionListener(e -> {
        	 OpenClose(btnSeat13.getText());
         });
      
      JButton btnSeat14 = new JButton("14");
      btnSeat14.setBounds(329, 294, 51, 36);
      seatButtons.put("14", btnSeat14);
      getContentPane().add(btnSeat14);
      btnSeat14.addActionListener(e -> {
        	 OpenClose(btnSeat14.getText());
         });
      
      JButton btnSeat15 = new JButton("15");
      btnSeat15.setBounds(379, 294, 51, 36);
      seatButtons.put("15", btnSeat15);
      getContentPane().add(btnSeat15);
      btnSeat15.addActionListener(e -> {
        	 OpenClose(btnSeat15.getText());
         });
      
      JButton btnSeat16 = new JButton("16");
      btnSeat16.setBounds(329, 329, 51, 36);
      seatButtons.put("16", btnSeat16);
      getContentPane().add(btnSeat16);
      btnSeat16.addActionListener(e -> {
        	 OpenClose(btnSeat16.getText());
         });
      
      JButton btnSeat17 = new JButton("17");
      btnSeat17.setBounds(379, 329, 51, 36);
      seatButtons.put("17", btnSeat17);
      getContentPane().add(btnSeat17);
      btnSeat17.addActionListener(e -> {
        	 OpenClose(btnSeat17.getText());
         });
      
      JButton btnSeat18 = new JButton("18");
      btnSeat18.setBounds(79, 385, 51, 36);
      seatButtons.put("18", btnSeat18);
      getContentPane().add(btnSeat18);
      btnSeat18.addActionListener(e -> {
        	 OpenClose(btnSeat18.getText());
         });
      
      JButton btnSeat19 = new JButton("19");
      btnSeat19.setBounds(129, 385, 51, 36);
      seatButtons.put("19", btnSeat19);
      getContentPane().add(btnSeat19);
      btnSeat19.addActionListener(e -> {
        	 OpenClose(btnSeat19.getText());
         });
      
      JButton btnSeat20 = new JButton("20");
      btnSeat20.setBounds(79, 420, 51, 36);
      seatButtons.put("20", btnSeat20);
      getContentPane().add(btnSeat20);
      btnSeat20.addActionListener(e -> {
        	 OpenClose(btnSeat20.getText());
         });
      
      JButton btnSeat21 = new JButton("21");
      btnSeat21.setBounds(129, 420, 51, 36);
      seatButtons.put("21", btnSeat21);
      getContentPane().add(btnSeat21);
      btnSeat21.addActionListener(e -> {
        	 OpenClose(btnSeat21.getText());
         });
      
      JButton btnSeat22 = new JButton("22");
      btnSeat22.setBounds(329, 385, 51, 36);
      seatButtons.put("22", btnSeat22);
      getContentPane().add(btnSeat22);
      btnSeat22.addActionListener(e -> {
        	 OpenClose(btnSeat22.getText());
         });
      
      JButton btnSeat23 = new JButton("23");
      btnSeat23.setBounds(379, 385, 51, 36);
      seatButtons.put("23", btnSeat23);
      getContentPane().add(btnSeat23);
      btnSeat23.addActionListener(e -> {
        	 OpenClose(btnSeat23.getText());
         });
      
      JButton btnSeat24 = new JButton("24");
      btnSeat24.setBounds(329, 420, 51, 36);
      seatButtons.put("24", btnSeat24);
      getContentPane().add(btnSeat24);
      btnSeat24.addActionListener(e -> {
        	 OpenClose(btnSeat24.getText());
         });
      
      JButton btnSeat25 = new JButton("25");
      btnSeat25.setBounds(379, 420, 51, 36);
      seatButtons.put("25", btnSeat25);
      getContentPane().add(btnSeat25);
      btnSeat25.addActionListener(e -> {
        	 OpenClose(btnSeat25.getText());
         });
      
      JButton btnSeat26 = new JButton("26");
      btnSeat26.setBounds(29, 470, 51, 36);
      seatButtons.put("26", btnSeat26);
      getContentPane().add(btnSeat26);
      btnSeat26.addActionListener(e -> {
        	 OpenClose(btnSeat26.getText());
         });
      
      JButton btnSeat27 = new JButton("27");
      btnSeat27.setBounds(79, 470, 51, 36);
      seatButtons.put("27", btnSeat27);
      getContentPane().add(btnSeat27);
      btnSeat27.addActionListener(e -> {
        	 OpenClose(btnSeat27.getText());
         });
      
      JButton btnSeat28 = new JButton("28");
      btnSeat28.setBounds(129, 470, 51, 36);
      seatButtons.put("28", btnSeat28);
      getContentPane().add(btnSeat28);
      btnSeat28.addActionListener(e -> {
        	 OpenClose(btnSeat28.getText());
         });
      
      JButton btnSeat29 = new JButton("29");
      btnSeat29.setBounds(179, 470, 51, 36);
      seatButtons.put("29", btnSeat29);
      getContentPane().add(btnSeat29);
      btnSeat29.addActionListener(e -> {
        	 OpenClose(btnSeat29.getText());
         });
      
      JButton btnSeat30 = new JButton("30");
      btnSeat30.setBounds(229, 470, 51, 36);
      seatButtons.put("30", btnSeat30);
      getContentPane().add(btnSeat30);
      btnSeat30.addActionListener(e -> {
        	 OpenClose(btnSeat30.getText());
         });
      
      JButton btnSeat31 = new JButton("31");
      btnSeat31.setBounds(279, 470, 51, 36);
      seatButtons.put("31", btnSeat31);
      getContentPane().add(btnSeat31);
      btnSeat31.addActionListener(e -> {
        	 OpenClose(btnSeat31.getText());
         });
      
      JButton btnSeat32 = new JButton("32");
      btnSeat32.setBounds(329, 470, 51, 36);
      seatButtons.put("32", btnSeat32);
      getContentPane().add(btnSeat32);
      btnSeat32.addActionListener(e -> {
        	 OpenClose(btnSeat32.getText());
         });
      
      JButton btnSeat33 = new JButton("33");
      btnSeat33.setBounds(379, 470, 51, 36);
      seatButtons.put("33", btnSeat33);
      getContentPane().add(btnSeat33);
      btnSeat33.addActionListener(e -> {
        	 OpenClose(btnSeat33.getText());
         });
      
      JButton btnSeat34 = new JButton("34");
      btnSeat34.setBounds(431, 470, 51, 36);
      seatButtons.put("34", btnSeat34);
      getContentPane().add(btnSeat34);
      btnSeat34.addActionListener(e -> {
        	 OpenClose(btnSeat34.getText());
         });
      
      JLabel lblNewLabel = new JLabel("");
      lblNewLabel.setIcon(icon);
      lblNewLabel.setBounds(-16, -62, 550, 840);
      getContentPane().add(lblNewLabel);
      
      //예약된 좌석 처리
      try (Connection conn = DB.getConnection()) {
    	    String sql = "SELECT seat FROM reservations";
    	    PreparedStatement stmt = conn.prepareStatement(sql);
    	    ResultSet rs = stmt.executeQuery();
    	    while (rs.next()) {
    	        String seat = rs.getString("seat").replace("Seat ", "").trim();
    	        JButton btn = seatButtons.get(seat);
    	        if (btn != null) {
    	            btn.setBackground(Color.RED);
    	            btn.setEnabled(true);  // 색은 바꾸되 누를 수 있도록 유지
    	        }
    	    }
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}
   }
   
   private void updateReservedSeats() {
	    // 1. 모든 좌석 초기화
	    seatButtons.values().forEach(btn -> {
	        btn.setBackground(null);  // 기본 색 (null 또는 원하는 색)
	        btn.setEnabled(true);
	    });

	    // 2. 예약된 좌석만 색상 변경
	    try (Connection conn = DB.getConnection()){
	    	String sql = "SELECT seat FROM reservations";
	    	PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String seatRaw = rs.getString("seat"); // 예: "Seat 01" d
	            String seatNum = seatRaw.replace("Seat ", "").trim(); // "01"
	            int seatIndex = Integer.parseInt(seatNum);            // 1~34
	            String key = String.valueOf(seatIndex);               // "1" ~ "34"

	            JButton btn = seatButtons.get(key);
	            if (btn != null) {
	                btn.setBackground(Color.RED);
	            } else {
	                System.err.println("등록되지 않은 좌석 번호: " + seatRaw);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
   
   // 닫고 열기 함수
   private void OpenClose(String chairNum) {
	   JButton btn = seatButtons.get(chairNum);
	    if (btn != null && Color.RED.equals(btn.getBackground())) {
	        JOptionPane.showMessageDialog(this, "예약된 좌석입니다.");
	        return;
	    }

	    dispose();
	    SeatReservation stst = new SeatReservation(chairNum);
	    stst.setVisible(true);
	}
}