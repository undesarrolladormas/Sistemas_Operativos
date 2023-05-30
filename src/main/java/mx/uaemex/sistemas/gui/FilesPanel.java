package mx.uaemex.sistemas.gui;

import mx.uaemex.sistemas.files.*;

import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class FilesPanel extends JPanel {

    private final JTextField idTextField;
    private final JTextField nombreTextField;
    private final JTextField apellidoTextField;
    private final JTextField edadTextField;

    private final JTextField dirTextField;
    private final JTextField cpTextField;
    private final JTextField correoTextField;
    private final JTable simulationTable;
    private FileType actual;

    private IndexFile indexFile;
    private SequentialFile sequentialFile;
    private StackFile stackFile;
    private SequentialIndexFile sequentialIndexFile;

    FilesPanel() {
        super();

        JPanel frame = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST + 5;
        gbc.insets = new Insets(10, 10, 30, 10);
        frame.setSize(800, 600);

        JPanel formularioPanel = new JPanel(new GridBagLayout());

        formularioPanel.add(new JLabel("ID:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("Edad:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("C.P:"), gbc);
        gbc.gridy++;

        formularioPanel.add(new JLabel("Correo Electrónico:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx ++;
        JButton buscarButton = new JButton("Buscar");
        formularioPanel.add(buscarButton, gbc);
        gbc.gridx --;

        idTextField = new JTextField();
        formularioPanel.add(idTextField, gbc);
        gbc.gridy++;


        nombreTextField = new JTextField();
        formularioPanel.add(nombreTextField, gbc);
        gbc.gridy++;

        apellidoTextField = new JTextField();
        formularioPanel.add(apellidoTextField, gbc);
        gbc.gridy++;

        edadTextField = new JTextField();
        formularioPanel.add(edadTextField, gbc);
        gbc.gridy++;


        dirTextField = new JTextField();
        formularioPanel.add(dirTextField, gbc);
        gbc.gridy++;

        cpTextField = new JTextField();
        formularioPanel.add(cpTextField, gbc);
        gbc.gridy++;

        correoTextField = new JTextField();
        formularioPanel.add(correoTextField, gbc);
        gbc.gridy++;
        gbc.gridy++;

        gbc.gridx = 0;

        JPanel simulacionPanel = new JPanel();
        simulacionPanel.setLayout(new BorderLayout());
        simulacionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel model = new DefaultTableModel(new String[] {"ID", "Nombre", "Apellido", "Edad", "Direccion", "C.P.", "Correo"}, 0);
        simulationTable = new JTable(model);
        simulacionPanel.add(new JScrollPane(simulationTable), BorderLayout.CENTER);

        String[] archivos = { "=Selecciona el tipo","Indexado", "Secuencial", "Pila", "Secuencial-Indexado"};
        JComboBox<String> comboBoxArchivos = new JComboBox<>(archivos);
        add(comboBoxArchivos, BorderLayout.NORTH);

        JButton agregarButton = new JButton("Agregar");
        formularioPanel.add(agregarButton, gbc);
        gbc.gridx++;

        JButton modificarButton = new JButton("Modificar");
        formularioPanel.add(modificarButton, gbc);
        gbc.gridx++;

        JButton eliminarButton = new JButton("Eliminar");
        formularioPanel.add(eliminarButton, gbc);

        // Panel principal con los dos paneles anteriores
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(formularioPanel);
        mainPanel.add(simulacionPanel);

        this.add(mainPanel);

        this.getFiles();

        comboBoxArchivos.addActionListener(e -> {
            switch ((String) Objects.requireNonNull(comboBoxArchivos.getSelectedItem())) {
                case "Indexado" -> {
                    actual = FileType.Index;
                    loadFile();
                }
                //case "Secuencial" -> {
                //    actual = FileType.Sequential;
                //    loadFile();
                //}
                //case "Pila" -> {
                //    actual = FileType.Stack;
                //    loadFile();
                //}
                //case "Secuencial-Indexado" -> {
                //    actual = FileType.SequentialIndex;
                //    loadFile();
                default -> {
                    model.setRowCount(0);
                    simulationTable.setModel(model);
            }
            }
        });

        agregarButton.addActionListener(e -> {
            try {
                Student student = new Student(
                        nombreTextField.getText(),
                        apellidoTextField.getText(),
                        Integer.parseInt(edadTextField.getText()),
                        dirTextField.getText(),
                        Integer.parseInt(cpTextField.getText()),
                        correoTextField.getText()
                );
                addToFile(idTextField.getText(),student);
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        });

        eliminarButton.addActionListener(e ->{
            String key = idTextField.getText();
            deleteFromFile(key);
        });

        modificarButton.addActionListener(e -> {
            try {
                Student student = new Student(
                        nombreTextField.getText(),
                        apellidoTextField.getText(),
                        Integer.parseInt(edadTextField.getText()),
                        dirTextField.getText(),
                        Integer.parseInt(cpTextField.getText()),
                        correoTextField.getText()
                );
                modifyToFile(idTextField.getText(), student);
            } catch (Exception ex) {
              throw new RuntimeException();
            }
        });

        buscarButton.addActionListener(e->{
            searchInFile(idTextField.getText());
        });

    }

    private void getFiles() {
        indexFile = new IndexFile();
        //sequentialFile = new SequentialFile();
        //stackFile = new StackFile();
        //sequentialIndexFile = new SequentialIndexFile();
        try {
            indexFile.loadIndex();
            //sequentialFile.loadSequential();
            //stackFile.loadStack();
            //sequentialIndexFile.loadIndex();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFile() {
        switch (actual) {
            case Index -> indexFile.listStudent(simulationTable);
            //case Stack -> stackFile.listStudent(simulationTable);
            case Sequential -> sequentialFile.listStudent(simulationTable);
            case SequentialIndex -> sequentialIndexFile.listStudent(simulationTable);
        }
        idTextField.setText("");
        nombreTextField.setText("");
        apellidoTextField.setText("");
        dirTextField.setText("");
        edadTextField.setText("");
        correoTextField.setText("");
        cpTextField.setText("");
    }

    private void addToFile(String key, Student student) {
        switch (actual) {
            case Index -> {
                try {
                    indexFile.addStudent(key,student);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            //case Stack -> {
            //    try {
            //        stackFile.addStudent(student);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
            //case Sequential -> {
            //    try {
            //        sequentialFile.addStudent(key,student);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
            //case SequentialIndex -> {
            //    try {
            //        sequentialIndexFile.addStudent(student.getName(), student);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}

        }
        loadFile();
    }

    private void deleteFromFile(String key) {
        switch (actual)
        {
            case Index -> {
                try {
                    indexFile.deleteStudent(key);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            //case Stack -> {
            //    stackFile.deleteStudent(key);
            //}
            //case Sequential -> {
            //    try {
            //        int aux =  Integer.parseInt(key);
            //        sequentialFile.deleteStudent(aux);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
            //case SequentialIndex -> {
            //    try {
            //        sequentialIndexFile.deleteStudent(nombreTextField.getText());
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
        }
        loadFile();
    }

    private void modifyToFile(String key, Student student) {
        switch (actual) {
            case Index -> {
                indexFile.modifyStudent(key,student);
            }
            //case Stack -> {
            //}
            //case Sequential -> {
            //    try {
            //        int aux = Integer.parseInt(key);
            //        sequentialFile.modifyStudent(aux,student);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
            //case SequentialIndex -> {
            //    try {
            //        sequentialIndexFile.modifyStudent(student.getName(),student);
            //    } catch (Exception e) {
            //        throw new RuntimeException(e);
            //    }
            //}
        }
        loadFile();
    }

    private void searchInFile(String key) {
        Student aux = null;
        switch (actual) {
            case Index -> {
                aux = indexFile.searchStudent(key);
            }
            //case Stack -> {
            //}
            //case Sequential -> {
            //}
            //case SequentialIndex -> {
            //    aux = sequentialIndexFile.searchStudent(key);
            //}
        }
        if (aux != null) {
            nombreTextField.setText(aux.getName());
            apellidoTextField.setText(aux.getLastName());
            dirTextField.setText(aux.getAddress());
            edadTextField.setText(String.valueOf(aux.getAge()));
            correoTextField.setText(aux.getMail());
            cpTextField.setText(String.valueOf(aux.getZipCode()));
        }
        else {
            nombreTextField.setText("");
            apellidoTextField.setText("");
            dirTextField.setText("");
            edadTextField.setText("");
            correoTextField.setText("");
            cpTextField.setText("");
        }
    }

    enum FileType {
        Sequential,
        Index,
        Stack,
        SequentialIndex
    }
}
