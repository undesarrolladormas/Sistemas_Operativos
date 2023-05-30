package mx.uaemex.sistemas.files;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

public class SequentialFile {
    private ArrayList<Student> secuencial;

    public SequentialFile() {
        File f = new File("./sequential.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        secuencial = new ArrayList<>();
    }

    public void listStudent(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Student e : secuencial) {
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

    public void addStudent(String key, Student student) throws Exception {
        secuencial.add(Integer.parseInt(key), student);
        saveSequential();
    }

    public void saveSequential() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("./sequential.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(secuencial);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    public void loadSequential() throws IOException, ClassNotFoundException {
        File file = new File("./sequential.txt");
        if (file.length() > 0) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            if (obj instanceof ArrayList) {
                secuencial = (ArrayList<Student>) obj;
            }
            objectInputStream.close();
            fileInputStream.close();
        }
    }


    public void deleteStudent(int index) throws Exception {
        secuencial.remove(index);
        saveSequential();
    }

    public void modifyStudent(int index, Student student) throws IOException {
        if (index >= 0 && index < secuencial.size()) {
            Student student1 = secuencial.get(index);
            try {
                student1.setName(student.getName());
                student1.setLastName(student.getLastName());
                student1.setAddress(student.getAddress());
                student1.setAge(student.getAge());
                student1.setMail(student.getMail());
                student1.setZipCode(student.getZipCode());
                saveSequential();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Student searchStudent(String key) {
        for (Student student : secuencial) {
            if (student.toString().equals(key)) {
                return student;
            }
        }
        return null;
    }
}


