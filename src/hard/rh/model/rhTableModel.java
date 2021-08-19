package hard.rh.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class rhTableModel extends AbstractTableModel {

    private List<Parcela> dados = new ArrayList<>();
    private String[] colunas = {"Cod. Par.", "Cod. Cop.", "Cod. Fun.", "Funcionário", "Beneficiário", "Valor Parcela", "Data Vencimento", "Status"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getCodParcela();
            case 1:
                return dados.get(linha).getFr_codCop();
            case 2:
                return dados.get(linha).getFr_codFuncionario();
            case 3:
                return dados.get(linha).getFr_nomeFuncionario();
            case 4:
                return dados.get(linha).getFr_nomeBeneficiario();
            case 5:
                return dados.get(linha).getValorParcela();
            case 6:
                return dados.get(linha).getDataVencPar();
            case 7:
                return dados.get(linha).getDiasAtrasoPar();
            case 8:
                return dados.get(linha).getStatusParcelaAtual();
        }

        return null;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {

        java.util.Date datePar = (java.util.Date) valor;
        long dtP = datePar.getTime();
        java.sql.Date dateParcela = new java.sql.Date(dtP);

        switch (coluna) {
            case 0:
                dados.get(linha).setCodParcela(Integer.parseInt(valor.toString()));
            case 1:
                dados.get(linha).setFr_codCop(Integer.parseInt(valor.toString()));
            case 2:
                dados.get(linha).setFr_codFuncionario(Integer.parseInt(valor.toString()));
            case 3:
                dados.get(linha).setFr_nomeFuncionario(valor.toString());
            case 4:
                dados.get(linha).setFr_nomeBeneficiario(valor.toString());
            case 5:
                dados.get(linha).setValorParcela(Double.parseDouble(valor.toString()));
            case 6:
                dados.get(linha).setDataVencPar(dateParcela);
            case 7:
                dados.get(linha).setDiasAtrasoPar(Integer.parseInt(valor.toString()));
            case 8:
                dados.get(linha).setStatusParcelaAtual(valor.toString());
        }

        this.fireTableRowsUpdated(linha, linha);

    }

    public void addRow(Parcela p) {
        this.dados.add(p);
        this.fireTableDataChanged();
    }
}
