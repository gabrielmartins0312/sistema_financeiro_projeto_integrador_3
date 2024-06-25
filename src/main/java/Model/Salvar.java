package Model;

import java.sql.Date;

public class Salvar {
    private int idSalvo;
    private double valorTotal;
    private Date dataInicio;
    private Date dataFim;

    public Salvar(int idSalvo, double valorTotal, Date dataInicio, Date dataFim) {
        this.idSalvo = idSalvo;
        this.valorTotal = valorTotal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getIdSalvo() {
        return idSalvo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "Fechamento do mês de (" + dataInicio + ") até (" + dataFim + ") ficou no valor de R$" + valorTotal;
    }
}
