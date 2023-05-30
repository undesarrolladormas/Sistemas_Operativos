package mx.uaemex.sistemas.files;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.Stack;

public class StackFile {
    private Stack<Student> stack;

    public StackFile() {
        File f = new File("./stack.txt");
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stack = new Stack<>();
    }

    public void addStudent(Student student) throws Exception {
        stack.push(student);
        saveStack();
    }

    public void listStudents(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Student e : stack) {
            Object[] aux = {
                    e.getName(),
                    e.getLastName(),
                    e.getAge(),
                    e.getAddress(),
                    e.getZipCode(),
                    e.getMail()
            };
            model.addRow(aux);
        }
        table.setModel(model);
    }

    public void deleteStudent(Student student) throws Exception {
        stack.remove(student);
        saveStack();
    }

    private void saveStack() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("./stack.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(stack);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void loadStack() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./stack.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        stack = (Stack<Student>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
    }
}
