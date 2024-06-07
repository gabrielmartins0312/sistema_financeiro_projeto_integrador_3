package view;

import javax.swing.*;

import controller.CadastroController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CadastroView extends JFrame {
    private JList<String> cadastroList;
    private DefaultListModel<String> listModel;
    private JTextField entradaField;
    private JTextField saidaField;
    private JTextField descricaoField;
    private JButton addButton;
    private JButton deleteButton;
    private JLabel totalCaixaLabel;
    private CadastroController cadastroController;
    private String nome;

    public CadastroView(String nome) {
        this.nome = nome;
        cadastroController = new CadastroController();

        setTitle("Tela de Cadastro");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        cadastroList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(cadastroList);

        entradaField = new JTextField();
        saidaField = new JTextField();
        descricaoField = new JTextField();

        addButton = new JButton("Adicionar");
        deleteButton = new JButton("Excluir");

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Entrada:"));
        inputPanel.add(entradaField);
        inputPanel.add(new JLabel("Saída:"));
        inputPanel.add(saidaField);
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(descricaoField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        totalCaixaLabel = new JLabel("Total de Caixa: R$ 0.00");
        inputPanel.add(new JLabel());
        inputPanel.add(totalCaixaLabel);

        add(listScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double entrada = Double.parseDouble(entradaField.getText());
                double saida = Double.parseDouble(saidaField.getText());
                String descricao = descricaoField.getText();

                cadastroController.adicionarCadastro(nome, entrada, saida, descricao);
                atualizarLista();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = cadastroList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedValue = cadastroList.getSelectedValue();
                    int idCadastro = Integer.parseInt(selectedValue.split(":")[0]);
                    cadastroController.excluirCadastro(idCadastro);
                    atualizarLista();
                }
            }
        });

        atualizarLista();
    }

    private void atualizarLista() {
        listModel.clear();
        List<String> cadastros = cadastroController.listarCadastros(nome);
        for (String cadastro : cadastros) {
            listModel.addElement(cadastro);
        }
        double totalCaixa = cadastroController.calcularTotalCaixa(nome);
        totalCaixaLabel.setText("Total de Caixa: R$ " + totalCaixa);
    }
}
