import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

class editor1 extends JFrame implements ActionListener {
    JTextArea t;
    JFrame f;
    private static final long serialVersionUID = 1L;

    editor1() {
        f = new JFrame("editor");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        t = new JTextArea();

        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");

        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);

        JMenu m2 = new JMenu("Edit");
        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");

        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        JMenu m3 = new JMenu("Format");
        JMenuItem mi7 = new JMenuItem("Font");
        JMenuItem mi8 = new JMenuItem("Color");

        mi7.addActionListener(this);
        mi8.addActionListener(this);

        m3.add(mi7);
        m3.add(mi8);

        JMenuItem mc = new JMenuItem("close");

        mc.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(mc);

        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(800, 800);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("cut")) {
            t.cut();
        } else if (s.equals("copy")) {
            t.copy();
        } else if (s.equals("paste")) {
            t.paste();
        } else if (s.equals("Save")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    FileWriter wr = new FileWriter(fi, false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
            }
        } else if (s.equals("Print")) {
            try {
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        } else if (s.equals("Open")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);
                    sl = br.readLine();
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
                    t.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
            }
        } else if (s.equals("New")) {
            t.setText("");
        } else if (s.equals("close")) {
            f.setVisible(false);
        } else if (s.equals("Font")) {
            JFontChooser fontChooser = new JFontChooser();
            int result = fontChooser.showDialog(f);
            if (result == JFontChooser.OK_OPTION) {
                Font font = fontChooser.getSelectedFont();
                t.setFont(font);
            }
        } else if (s.equals("Color")) {
            Color color = JColorChooser.showDialog(f, "Choose Text Color", t.getForeground());
            if (color != null) {
                t.setForeground(color);
            }
        }
    }

    public static void main(String args[]) {
        editor1 e = new editor1();
    }
}

class JFontChooser extends JDialog {
    private Font selectedFont;
    private JList<String> fontList;
    private JList<Integer> sizeList;
    private static final String[] FONT_NAMES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private static final Integer[] FONT_SIZES = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72};

    public JFontChooser() {
        setModal(true);
        setTitle("Font Chooser");
        setSize(400, 400);
        setLayout(new BorderLayout());

        fontList = new JList<>(FONT_NAMES);
        sizeList = new JList<>(FONT_SIZES);

        add(new JScrollPane(fontList), BorderLayout.CENTER);
        add(new JScrollPane(sizeList), BorderLayout.EAST);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String fontName = fontList.getSelectedValue();
            Integer fontSize = sizeList.getSelectedValue();
            if (fontName != null && fontSize != null) {
                selectedFont = new Font(fontName, Font.PLAIN, fontSize);
                dispose();
            }
        });

        add(okButton, BorderLayout.SOUTH);
    }

    public Font getSelectedFont() {
        return selectedFont;
    }

    public int showDialog(Component parent) {
        setLocationRelativeTo(parent);
        setVisible(true);
        return selectedFont != null ? JFontChooser.OK_OPTION : JFontChooser.CANCEL_OPTION;
    }

    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
}
