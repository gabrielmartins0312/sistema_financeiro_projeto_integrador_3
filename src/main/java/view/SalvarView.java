package view;

import javax.swing.*;
import controller.SalvarController;
import controller.CadastroController;
import Model.Salvar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class SalvarView extends JFrame {
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JButton salvarButton;
    private JButton listarButton;
    private JButton excluirButton;
    private JList<Salvar> valoresSalvosList;
    private DefaultListModel<Salvar> listModel;
    private SalvarController salvarController;
    private CadastroController cadastroController;
    private String nome;

    public SalvarView(String nome) {
        this.nome = nome;
        salvarController = new SalvarController();
        cadastroController = new CadastroController();

        setTitle("Salvar Valor Total");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        dataInicioField = new JTextField();
        dataFimField = new JTextField();
        salvarButton = new JButton("Salvar");
        listarButton = new JButton("Listar Valores Salvos");
        excluirButton = new JButton("Excluir Valor Salvo");

        listModel = new DefaultListModel<>();
        valoresSalvosList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(valoresSalvosList);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Data do início (yyyy-mm-dd):"));
        inputPanel.add(dataInicioField);
        inputPanel.add(new JLabel("Data do fim (yyyy-mm-dd):"));
        inputPanel.add(dataFimField);
        inputPanel.add(salvarButton);
        inputPanel.add(listarButton);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(excluirButton, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date dataInicio = Date.valueOf(dataInicioField.getText());
                    Date dataFim = Date.valueOf(dataFimField.getText());
                    double valorTotal = cadastroController.calcularTotalCaixa(nome);

                    int confirm = JOptionPane.showConfirmDialog(SalvarView.this, "Deseja salvar o valor total?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        salvarController.salvarTotalCaixa(valorTotal, dataInicio, dataFim, nome);
                        JOptionPane.showMessageDialog(SalvarView.this, "Valor total salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SalvarView.this, "Data inválida. Use o formato yyyy-mm-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarListaValoresSalvos();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = valoresSalvosList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Salvar selectedValue = valoresSalvosList.getSelectedValue();
                    int idSalvo = selectedValue.getIdSalvo();
                    salvarController.excluirValorSalvo(idSalvo);
                    atualizarListaValoresSalvos();
                }
            }
        });
    }

    private void atualizarListaValoresSalvos() {
        listModel.clear();
        List<Salvar> valoresSalvos = salvarController.listarValoresSalvos(nome);
        for (Salvar valor : valoresSalvos) {
            listModel.addElement(valor);
        }
    }
}
