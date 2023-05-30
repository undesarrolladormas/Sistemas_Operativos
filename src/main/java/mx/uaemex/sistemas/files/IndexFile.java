package mx.uaemex.sistemas.files;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class IndexFile {
    private Map<String, Student> index;

    public IndexFile() {
        File f = new File("./index.txt");
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        index = new HashMap<>();
    }

    public void addStudent(String key, Student student) throws Exception {
        index.put(key,student);
        saveIndex();
    }

    public void modifyStudent(String key, Student student)
    {
        Student student1 = searchStudent(key);
        if (student1 != null)
        {
            try {
                student1.setName(student.getName());
                student1.setLastName(student.getLastName());
                student1.setAddress(student.getAddress());
                student1.setAge(student.getAge());
                student1.setMail(student.getMail());
                student1.setZipCode(student.getZipCode());
                saveIndex();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Student searchStudent(String key) {
        if (index.containsKey(key)) {
            return index.get(key);
        }
        return null;
    }

    public void listStudent(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Student> entry : index.entrySet()) {
            Object[] aux = {
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getLastName(),
                    entry.getValue().getAge(),
                    entry.getValue().getAddress(),
                    entry.getValue().getZipCode(),
                    entry.getValue().getMail()
            };
            model.addRow(aux);
        }
        table.setModel(model);
    }

    public void deleteStudent(String fileLine) throws Exception {
        index.remove(fileLine);
        saveIndex();
    }

    private void saveIndex() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("./index.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(index);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void loadIndex() throws Exception {
        int lines = 0;
        BufferedReader br = new BufferedReader(new FileReader("./index.txt"));
        while (br.readLine() != null) {
            lines++;
        }
        br.close();

        if (lines > 1) {
            FileInputStream fileInputStream = new FileInputStream("./index.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            index = (Map<String, Student>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
    }

}
