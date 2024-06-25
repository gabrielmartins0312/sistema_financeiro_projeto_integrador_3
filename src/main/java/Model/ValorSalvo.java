package Model;

import java.sql.Date;

public class ValorSalvo {
    private double valorTotal;
    private Date dataInicio;
    private Date dataFim;

    public ValorSalvo(double valorTotal, Date dataInicio, Date dataFim) {
        this.valorTotal = valorTotal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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
