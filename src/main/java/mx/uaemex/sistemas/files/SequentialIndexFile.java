package mx.uaemex.sistemas.files;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SequentialIndexFile {
    private Map<String, Integer> index; // Índice en memoria

    public SequentialIndexFile() {
        File f = new File("./seq-value.txt");
        File f2 = new File("./seq-index.txt");
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(!f2.exists()) {
            try {
                f2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        index = new HashMap<>();
    }

    public void addStudent(String file, Student student) throws Exception {
        try {
            RandomAccessFile dataFile = new RandomAccessFile("./seq-value.txt", "rw");
            dataFile.seek(dataFile.length());
            long position = dataFile.getFilePointer();

            dataFile.writeUTF(student.getName());
            dataFile.writeUTF(student.getLastName());
            dataFile.write(student.getAge());
            dataFile.writeUTF(student.getAddress());
            dataFile.write(student.getZipCode());
            dataFile.writeUTF(student.getMail());
            index.put(student.getName(), (int) position);
            updateIndex();
            dataFile.close();
        } catch (Exception e) {
            throw new Exception("Error while writing to sec index");
        }
    }

    public void deleteStudent(String key) throws Exception {
        try {
            if(index.containsKey(key)){
                int position = index.get(key);
                RandomAccessFile dataFile = new RandomAccessFile("./seq-value.txt","rw");
                dataFile.seek(position);

                // Escribe los campos a vacio
                dataFile.writeUTF("");
                dataFile.writeUTF("");
                dataFile.write(-1);
                dataFile.writeUTF("");
                dataFile.write(-1);
                dataFile.writeUTF("");

                index.remove(key);
                updateIndex();
                dataFile.close();
            }
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    public void modifyStudent(String key, Student newStudent) throws Exception {
        try {
            if(index.containsKey(key)) {
                int position = index.get(key);
                RandomAccessFile dataFile = new RandomAccessFile("./seq-value.txt", "rw");

                dataFile.seek(position);
                dataFile.writeUTF(newStudent.getName());
                dataFile.writeUTF(newStudent.getLastName());
                dataFile.write(newStudent.getAge());
                dataFile.writeUTF(newStudent.getAddress());
                dataFile.write(newStudent.getZipCode());
                dataFile.writeUTF(newStudent.getMail());

                updateIndex();

                dataFile.close();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Student searchStudent(String key) {
        Student st;
        try {
            int position = index.get(key);
            RandomAccessFile dataFile = new RandomAccessFile("./seq-value.txt","r");
            dataFile.seek(position);
            st = new Student(
                    dataFile.readUTF(),
                    dataFile.readUTF(),
                    dataFile.readInt(),
                    dataFile.readUTF(),
                    dataFile.readInt(),
                    dataFile.readUTF()
            );
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
        return st;
    }

    public void listStudent(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            RandomAccessFile dataFile = new RandomAccessFile("./seq-value.txt","r");
            for (Map.Entry<String,Integer> entry : index.entrySet()) {
                int position = entry.getValue();
                dataFile.seek(position);
                Object[] aux = new Object[]{
                        position,
                        dataFile.readUTF(),
                        dataFile.readUTF(),
                        dataFile.readInt(),
                        dataFile.readUTF(),
                        dataFile.readInt(),
                        dataFile.readUTF()
                };
                model.addRow(aux);
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
        table.setModel(model);
    }

    public void loadIndex() throws Exception {
        try {
            ObjectInputStream indexFile = new ObjectInputStream(new FileInputStream("./seq-index.txt"));
            this.index = (Map<String, Integer>) indexFile.readObject();
            indexFile.close();
        } catch (Exception e) {
            System.out.println("Error leyendo Secuencial-Indexado");
        }
    }


    public void updateIndex() throws Exception {
        try {
            ObjectOutputStream indexFile = new ObjectOutputStream(new FileOutputStream("./seq-index.txt"));
            indexFile.writeObject(index);
            indexFile.close();
        }
        catch (Exception e){
            System.out.println("Error leyendo Secuencial-Indexado");
        }
    }

}

