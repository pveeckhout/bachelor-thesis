/*
 * The MIT License
 *
 * Copyright 2013 Pieter Van Eeckhout.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package bachelorthesis.captchapreviewer.ui;

import bachelorthesis.captchabuilder.util.CaptchaDAO;
import bachelorthesis.captchapreviewer.domain.Domain;
import bachelorthesis.captchapreviewer.domain.utils.IObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PreviewGUI.java (UTF-8)
 *
 * <p>This class is a user interface for the captcha-preview program, it
 * implements the IObserver and ActionListener interfaces.</p>
 *
 * @see IObserver
 *
 * 2013/04/22
 *
 * @author Pieter Van Eeckhout <vaneeckhout.pieter@gmail.com>
 * @author Pieter Van Eeckhout <pieter.vaneeckhout.q1295@student.hogent.be>
 * @author Hogent StudentID <2000901295>
 * @since 1.0.0
 * @version 1.0.0
 */
public class PreviewGUI implements IObserver<CaptchaDAO>, ActionListener {

    private JFrame guiFrame;
    private GroupLayout layout;
    private JPanel guiPanel;
    private JLabel messageLbl;
    private JTextField textField;
    private JButton previewBtn;
    private JLabel imageLbl;
    private Domain dom;

    /**
     *
     * The standard constructor, this will build the GUI JFrame and it's
     * components.
     */
    public PreviewGUI(Domain dom) {

        this.dom = dom;

        // Create the frame
        guiFrame = new JFrame();

        // Set frame properties
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("CAPTCHA previewer");
        guiFrame.setSize(800, 600);
        guiFrame.setLocationRelativeTo(null);

        // Create GUI componenets
        messageLbl = new JLabel(
                "Type a buildSequence and press \"Previeuw\" to get a preview of the captchas this buildstring will produce.");
        previewBtn = new JButton("Preview");
        imageLbl = new JLabel();
        textField = new JTextField();

        // Add this class as actionListener to the button and textField.
        previewBtn.addActionListener(this);
        textField.addActionListener(this);

        // .reate panel and layout manager
        guiPanel = new JPanel(true);
        layout = new GroupLayout(guiPanel);

        guiPanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains one parallel group.
        // the parallel group contains the labels, the the text field, the button
        // and the image lbl
        //
        // Variable indentation is used to reinforce the level of grouping.
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(textField)
                .addComponent(previewBtn)
                .addComponent(messageLbl)
                .addComponent(imageLbl));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis and add components
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup()
                .addComponent(textField)
                .addComponent(previewBtn)
                .addComponent(messageLbl)
                .addComponent(imageLbl);
        layout.setVerticalGroup(vGroup);

        //add the panel to the frame
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);
        guiFrame.pack();
    }

    @Override
    public void update(CaptchaDAO data) {
        messageLbl.setText(data.getParserMessage());
        imageLbl.setIcon(new ImageIcon(data.getImage()));
        guiFrame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(previewBtn) || e.getSource().equals(textField)) {
            dom.setBuildString(textField.getText());
        }
    }
}
