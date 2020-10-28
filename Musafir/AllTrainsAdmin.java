package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.Vector;

public class AllTrainsAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, trainNoLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit, b1;
    private JTextField pnrText, tf1;
    private Connect connection;
    private JScrollPane scroll;
    private int noOfHalts = 0;

    public AllTrainsAdmin(Connect connection) {
        this.connection = connection;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "ALL RUNNING TRAINS");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JButton(i3);
        back.setBackground(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        back.setBorder(emptyBorder);
        back.setBounds(5, 8, 30, 30);
        p1.add(back);

        headLabel = new JLabel("ALL RUNNING TRAINS INFO");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(210, 10, 600, 30);
        p1.add(headLabel);

        p2 = new Panel();
        p2.setLayout(null);

        b1 = new JButton("Get Details");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Times new roman", Font.BOLD, 14));
        b1.setBounds(300, 70, 150, 30);
        add(b1);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 120, 690, 550);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);

        back.addActionListener(this);
        b1.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void showTrainDetails(Vector<TrainBasicInfoAdminInfo> trainDetails) {
        noOfHalts = trainDetails.size();

        // JLabel[] tainNo1Label = new JLabel[noOfHalts];
        // JLabel[] trainName1Label = new JLabel[noOfHalts];
        // JLabel[] source = new JLabel[noOfHalts];
        // JLabel[] destination = new JLabel[noOfHalts];
        // JLabel[] runsOn = new JLabel[noOfHalts];
        // JLabel[] day = new JLabel[noOfHalts];
        // JLabel[] platform = new JLabel[noOfHalts];

        String str;
        int x, y = 5;
        for (int i = 0; i < noOfHalts; i++) {
            x = 2;
            Integer srNo = i + 1;
            JLabel srNo1 = new JLabel(srNo.toString() + ".");
            srNo1.setFont(new Font("Times new roman", Font.BOLD, 30));
            srNo1.setBounds(x, y + 20, 160, 30);
            p2.add(srNo1);

            x = 35;
            JLabel trainNoLabel = new JLabel("TRAIN NO:");
            trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
            trainNoLabel.setBounds(x, y, 160, 30);
            p2.add(trainNoLabel);

            x = 140;
            String trainNox = trainDetails.get(i).getTrain_no().toString();
            JLabel trainNo1Label = new JLabel(trainNox);
            trainNo1Label.setFont(new Font("Times new roman", Font.PLAIN, 15));
            trainNo1Label.setBounds(x, y, 160, 30);
            p2.add(trainNo1Label);

            x = 390;
            JLabel trainNameLabel = new JLabel("TRAIN NAME:");
            trainNameLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
            trainNameLabel.setBounds(x, y, 160, 30);
            p2.add(trainNameLabel);

            x = 535;
            JLabel trainName1Label = new JLabel(trainDetails.get(i).getTrain_name());
            trainName1Label.setFont(new Font("Times new roman", Font.PLAIN, 15));
            trainName1Label.setBounds(x, y, 160, 30);
            p2.add(trainName1Label);

            x = 35;
            JLabel src = new JLabel("SOURCE:");
            src.setFont(new Font("Times new roman", Font.BOLD, 15));
            src.setBounds(x, y + 25, 160, 30);
            p2.add(src);

            x = 170;
            JLabel src1 = new JLabel(trainDetails.get(i).getSrc());
            src1.setFont(new Font("Times new roman", Font.PLAIN, 15));
            src1.setBounds(x, y + 25, 160, 30);
            p2.add(src1);

            x = 390;
            JLabel dest = new JLabel("DESTINATION:");
            dest.setFont(new Font("Times new roman", Font.BOLD, 15));
            dest.setBounds(x, y + 25, 160, 30);
            p2.add(dest);

            x = 535;
            JLabel dest1 = new JLabel(trainDetails.get(i).getDest());
            dest1.setFont(new Font("Times new roman", Font.PLAIN, 15));
            dest1.setBounds(x, y + 25, 160, 30);
            p2.add(dest1);

            x = 35;
            JLabel runningDaysLabel = new JLabel("RUNS ON:");
            runningDaysLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
            runningDaysLabel.setBounds(x, y + 50, 160, 30);
            p2.add(runningDaysLabel);

            JLabel runDays[] = new JLabel[7];
            Integer x1 = 170;
            String runningDays1 = trainDetails.get(i).getRunningDays();
            for (int j = 0; j < 7; j++) {
                String dayx = new String();
                if (j == 0) {
                    dayx = "Mon";
                } else if (j == 1) {
                    dayx = "Tue";
                } else if (j == 2) {
                    dayx = "Wed";
                } else if (j == 3) {
                    dayx = "Thu";
                } else if (j == 4) {
                    dayx = "Fri";
                } else if (j == 5) {
                    dayx = "Sat";
                } else if (j == 6) {
                    dayx = "Sun";
                }
                if (runningDays1.charAt(j) == '1') {
                    runDays[i] = new JLabel(dayx);
                    runDays[i].setFont(new Font("Times new roman", Font.PLAIN, 15));
                    runDays[i].setBounds(x1, y + 50, 160, 30);
                    p2.add(runDays[i]);
                    x1 = x1 + 50;
                }
            }

            x = 35;
            JLabel tsSlrLabel = new JLabel("SEATS IN SLR:");
            tsSlrLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
            tsSlrLabel.setBounds(x, y + 75, 160, 30);
            p2.add(tsSlrLabel);

            x = 170;
            JLabel tsSlr1Label = new JLabel(trainDetails.get(i).getTs_slr().toString());
            tsSlr1Label.setFont(new Font("Times new roman", Font.PLAIN, 15));
            tsSlr1Label.setBounds(x, y + 75, 160, 30);
            p2.add(tsSlr1Label);

            x = 390;
            JLabel tsAcLabel = new JLabel("SEATS IN AC:");
            tsAcLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
            tsAcLabel.setBounds(x, y + 75, 160, 30);
            p2.add(tsAcLabel);

            x = 535;
            JLabel tsAc1Label = new JLabel(trainDetails.get(i).getTs_ac().toString());
            tsAc1Label.setFont(new Font("Times new roman", Font.PLAIN, 15));
            tsAc1Label.setBounds(x, y + 75, 160, 30);
            p2.add(tsAc1Label);

            x = 325;
            JLabel line = new JLabel("----X----");
            line.setFont(new Font("Times new roman", Font.PLAIN, 15));
            line.setBounds(x, y + 125, 160, 30);
            p2.add(line);

            y = y + 175;
        }

    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (noOfHalts > 3)
                return new Dimension(670, 540 + 50 * (noOfHalts - 8));
            else
                return new Dimension(670, 530);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == b1) {
                p2.removeAll();
                ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                os.writeInt(11);
                os.flush();
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                Vector<TrainBasicInfoAdminInfo> trainDetails = (Vector<TrainBasicInfoAdminInfo>) oi.readObject();
                showTrainDetails(trainDetails);

                p2.revalidate();
                p2.repaint();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
