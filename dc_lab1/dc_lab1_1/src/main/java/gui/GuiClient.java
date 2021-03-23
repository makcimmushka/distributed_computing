package gui;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiClient {
    private final JFrame jFrame = new JFrame();
    private final JPanel jPanel = new JPanel();
    private final JProgressBar jProgressBar = new JProgressBar();
    private AtomicInteger sliderValue = new AtomicInteger(50);

    private Thread thread1;
    private Thread thread2;

    private final Runnable runnable1 = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            int sliderValue = this.sliderValue.get();

            if (sliderValue > 10) {
                this.sliderValue.set(sliderValue - 1);
                jProgressBar.setValue(this.sliderValue.get());
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ignored) {
//                    Thread.currentThread().interrupt();
//                }
            }
        }
    };

    private final Runnable runnable2 = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            int sliderValue = this.sliderValue.get();

            if (sliderValue < 90) {
                this.sliderValue.set(sliderValue + 1);
                jProgressBar.setValue(this.sliderValue.get());
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ignored) {
//                    Thread.currentThread().interrupt();
//                }
            }
        }
    };

    public GuiClient() {
        jProgressBar.setValue(50);
        performJFrameSetup();
        performJPanelSetup();
    }

    private void performJFrameSetup() {
        jFrame.setVisible(true);
        jFrame.setBounds(500, 250, 750, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
    }

    private void performJPanelSetup() {
        jPanel.add(jProgressBar);
        jPanel.add(createStartButton());
        jPanel.add(createStopButton());
        performPrioritySelectorsSetup();
    }

    private JButton createStartButton() {
        JButton startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            thread1 = new Thread(runnable1);
            thread1.start();

            thread2 = new Thread(runnable2);
            thread2.start();
        });

        return startButton;
    }

    private JButton createStopButton() {
        JButton stopButton = new JButton("Stop");

        stopButton.addActionListener(e -> {
            thread1.interrupt();
            thread2.interrupt();
        });

        return stopButton;
    }

    private void performPrioritySelectorsSetup() {
        final Severity[] items = Severity.values();

        final JComboBox threadFirstPriority = new JComboBox(items);

        threadFirstPriority.addActionListener(e -> {
            Object selectedItem = threadFirstPriority.getSelectedItem();

            System.out.println(selectedItem + " priority for thread1");

            if (selectedItem == Severity.LOW) {
                thread1.setPriority(Thread.MIN_PRIORITY); // 1
            } else if (selectedItem == Severity.MEDIUM) {
                thread1.setPriority(Thread.NORM_PRIORITY); // 5
            } else if (selectedItem == Severity.HIGH) {
                thread1.setPriority(Thread.MAX_PRIORITY); // 10
            }
        });

        final JComboBox threadSecondPriority = new JComboBox(items);

        threadSecondPriority.addActionListener(e -> {
            Object selectedItem = threadSecondPriority.getSelectedItem();

            System.out.println(selectedItem + " priority for thread2");

            if (selectedItem == Severity.LOW) {
                thread2.setPriority(Thread.MIN_PRIORITY); // 1
            } else if (selectedItem == Severity.MEDIUM) {
                thread2.setPriority(Thread.NORM_PRIORITY); // 5
            } else if (selectedItem == Severity.HIGH) {
                thread2.setPriority(Thread.MAX_PRIORITY); // 10
            }
        });

        jPanel.add(threadFirstPriority);
        jPanel.add(threadSecondPriority);
    }

    private enum Severity {
        LOW, MEDIUM, HIGH
    }
}