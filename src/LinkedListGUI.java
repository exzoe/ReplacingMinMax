import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LinkedListGUI extends JFrame {

    private SimpleLinkedList<Integer> list;
    private SimpleLinkedList<Integer> originalList; // Для хранения исходного списка
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;

    public LinkedListGUI() {
        list = new SimpleLinkedList<>();
        originalList = new SimpleLinkedList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Linked List GUI");
        setSize(600, 500); // Увеличим размер окна для удобства
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель для ввода данных
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputTextArea = new JTextArea(5, 20);
        inputTextArea.setLineWrap(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(new JLabel("Введите числа через пробел:"), BorderLayout.NORTH);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // Панель для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Добавить в список");
        JButton loadButton = new JButton("Загрузить из файла");
        JButton swapButton = new JButton("Поменять min и max");
        JButton clearButton = new JButton("Очистить список");

        buttonPanel.add(addButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(swapButton);
        buttonPanel.add(clearButton);

        // Панель для вывода данных
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());

        outputTextArea = new JTextArea(15, 30); // Увеличим размер текстового поля
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(new JLabel("Результат:"), BorderLayout.NORTH);
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        // Добавление панелей на основное окно
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        // Обработчики событий для кнопок
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNumbersFromInput();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNumbersFromFile();
            }
        });

        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapMinMax();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearList();
            }
        });
    }

    // Метод для добавления чисел из текстового поля
    private void addNumbersFromInput() {
        String input = inputTextArea.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите числа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] numbers = input.split("\\s+");
        for (String number : numbers) {
            try {
                int value = Integer.parseInt(number);
                list.addLast(value);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Некорректное число: " + number, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Сохраняем копию исходного списка
        originalList = copyList(list);
        outputTextArea.setText("Исходный список: " + originalList + "\nТекущий список: " + list);
        inputTextArea.setText("");
    }

    // Метод для загрузки чисел из файла
    private void loadNumbersFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] numbers = line.split("\\s+");
                    for (String number : numbers) {
                        try {
                            int value = Integer.parseInt(number);
                            list.addLast(value);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Некорректное число в файле: " + number, "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                // Сохраняем копию исходного списка
                originalList = copyList(list);
                outputTextArea.setText("Исходный список: " + originalList + "\nТекущий список: " + list);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для обмена min и max
    private void swapMinMax() {
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this, "Список пуст!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Сохраняем копию исходного списка перед преобразованием
        originalList = copyList(list);
        list.swapMinMax();
        outputTextArea.setText("Исходный список: " + originalList + "\nСписок после обмена: " + list);
    }

    // Метод для очистки списка
    private void clearList() {
        list = new SimpleLinkedList<>();
        originalList = new SimpleLinkedList<>();
        outputTextArea.setText("Список очищен.");
    }

    // Метод для копирования списка
    private SimpleLinkedList<Integer> copyList(SimpleLinkedList<Integer> source) {
        SimpleLinkedList<Integer> copy = new SimpleLinkedList<>();
        for (int i = 0; i < source.size(); i++) {
            copy.addLast(source.get(i));
        }
        return copy;
    }


}