package org.iushu.plugins.calendar;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by iuShu on 19-7-15
 */
public class CalendarWindow {

    private static final String HIDE_ICON_PATH = "/images/plus.png";
    private static final String CALENDAR_ICON_PATH = "/images/Calendar-icon.png";
    private static final String TIME_ICON_PATH = "/images/Time-icon.png";
    private static final String ZONE_ICON_PATH = "/images/Time-zone-icon.png";

    private JButton refreshToolWindowButton;
    private JButton hideToolWindowButton;
    private JLabel currentDate;
    private JLabel currentTime;
    private JLabel timeZone;
    private JPanel toolContent;

    public CalendarWindow(ToolWindow toolWindow) {
        timeZone = new JLabel();
        currentDate = new JLabel();
        currentTime = new JLabel();
        GridLayoutManager layoutManager = new GridLayoutManager(2, 5);
        hideToolWindowButton = new JButton("Hide", new ImageIcon(getClass().getResource(HIDE_ICON_PATH)));
        refreshToolWindowButton = new JButton("Refresh", new ImageIcon(getClass().getResource(HIDE_ICON_PATH)));

        GridConstraints constraints = new GridConstraints(0, 2, 1, 1, 8, 0, 0, 0, null, null, null, 0, false);
        layoutManager.addLayoutComponent(timeZone, constraints);
        constraints = new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, null, null, null, 0, false);
        layoutManager.addLayoutComponent(currentDate, constraints);
        constraints = new GridConstraints(0, 3, 1, 1, 8, 0, 0, 0, null, null, null, 0, false);
        layoutManager.addLayoutComponent(currentTime, constraints);
        constraints = new GridConstraints(1, 3, 1, 1, 0, 1, 3, 0, null, null, null, 0, false);
        layoutManager.addLayoutComponent(hideToolWindowButton, constraints);
        constraints = new GridConstraints(1, 1, 1, 1, 0, 1, 3, 0, null, null, null, 0, false);
        layoutManager.addLayoutComponent(refreshToolWindowButton, constraints);

        toolContent = new JPanel(layoutManager);
        toolContent.setBounds(20, 20, 500, 400);
        toolContent.show();

        hideToolWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolWindow.hide(null);
            }
        });
        refreshToolWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDateTime();
            }
        });
        currentDateTime();
    }

    public void currentDateTime() {
        Calendar calendar = Calendar.getInstance();
        currentDate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(calendar.get(Calendar.YEAR)));
        currentDate.setIcon(new ImageIcon(getClass().getResource(CALENDAR_ICON_PATH)));

        int minutes = calendar.get(Calendar.MINUTE);
        String minutesText = minutes < 10 ? "0" + minutes : minutes + "";
        currentTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + minutesText);
        currentTime.setIcon(new ImageIcon(getClass().getResource(TIME_ICON_PATH)));

        long gmtOffset = calendar.get(Calendar.ZONE_OFFSET);
        String gmtOffsetText = gmtOffset / 3600000 + "";
        gmtOffsetText = gmtOffset > 0 ? "GMT + " + gmtOffsetText : "GMT - " + gmtOffsetText;
        timeZone.setText(gmtOffsetText);
        timeZone.setIcon(new ImageIcon(getClass().getResource(ZONE_ICON_PATH)));
    }

    public JPanel getToolContent() {
        return toolContent;
    }

}
