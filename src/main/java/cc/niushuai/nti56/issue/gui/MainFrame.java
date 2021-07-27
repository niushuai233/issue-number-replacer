package cc.niushuai.nti56.issue.gui;

import cc.niushuai.nti56.issue.util.RegUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

/**
 * @author niushuai
 */
public class MainFrame extends JDialog {
    private JPanel contentPane;
    private JTextField sourceIssueStrTextField;
    private JTextField orgaOnlyTextField;
    private JTextField copyTextField;
    private JTextField projectGroupOnlyTextField;
    private JTextField projectOnlyTextField;
    private JTextField groupOnlyTextField;
    private JTextField issueTextField;
    private JTextField demoStrTextField;
    private JTextField patternTextField;
    private JButton pattenSetBtn;
    private JLabel lax;
    private JButton pattenResetBtn;
    private JTextField commitMsgTextField;
    private JTextField gitCommitMsgTextField;
    private JRadioButton featRadioButton;
    private JRadioButton fixRadioButton;
    private JRadioButton refactorRadioButton;
    private JRadioButton docsRadioButton;
    private JRadioButton styleRadioButton;
    private JRadioButton testRadioButton;
    private JRadioButton choreRadioButton;

    private ButtonGroup buttonGroup = new ButtonGroup();

    public MainFrame() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("issue提交剪切工具");

        setBounds(new Rectangle(500, 255, 300, 300));

        patternTextField.setText(RegUtil.SOURCE_STR_PATTERN);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        commitMsgTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                rebuildStr();
            }
        });
        sourceIssueStrTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                rebuildStr();
            }
        });

        pattenSetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegUtil.SOURCE_STR_PATTERN = patternTextField.getText();
                rebuildStr();
                setTooltipText("设置成功", Color.GREEN);
            }
        });
        pattenResetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegUtil.SOURCE_STR_PATTERN = RegUtil.DEFAULT_SOURCE_STR_PATTERN;
                patternTextField.setText(RegUtil.SOURCE_STR_PATTERN);
                rebuildStr();
                setTooltipText("重置成功", Color.GREEN);
            }
        });


        buttonGroup.add(featRadioButton);
        buttonGroup.add(fixRadioButton);
        buttonGroup.add(docsRadioButton);
        buttonGroup.add(styleRadioButton);
        buttonGroup.add(refactorRadioButton);
        buttonGroup.add(testRadioButton);
        buttonGroup.add(choreRadioButton);

        buttonGroup.setSelected(featRadioButton.getModel(), true);

        addListener();

        setSize(550, 380);
        setLocationRelativeTo(null);

        this.setVisible(true);

    }

    private void addListener() {
        Enumeration<AbstractButton> elements = buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            JRadioButton button = (JRadioButton) elements.nextElement();
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    rebuildStr();
                }
            });
        }
    }

    private void setTooltipText(String text, Color color) {
        lax.setText(text);
        lax.setForeground(color);
    }

    private void rebuildStr() {

        String text = sourceIssueStrTextField.getText();

        boolean match = RegUtil.isMatch(text);
        if (!match) {
            setTooltipText("源串非法", Color.RED);
            return;
        }

        String[] split = text.split("/");

        try {
            fillText(split);
        }catch (Exception e) {
            setTooltipText("异常: " + e.getMessage(), Color.YELLOW);
        }
    }

    private void fillText(String[] sources) {

        orgaOnlyTextField.setText(sources[0]);
        projectGroupOnlyTextField.setText(sources[1]);
        groupOnlyTextField.setText(sources[2]);

        String[] copySource = sources[3].split("#");

        projectOnlyTextField.setText(copySource[0]);
        issueTextField.setText(copySource[1]);

        String btnText = getBtnText();

        String text = btnText + ": " + commitMsgTextField.getText() + " " + sources[3];
        copyTextField.setText(text);
        gitCommitMsgTextField.setText("git commit -m '" + text + "'");

        setTooltipText("解析成功", Color.GREEN);
    }

    private String getBtnText() {

        Enumeration<AbstractButton> elements = buttonGroup.getElements();

        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return "";
    }
}
