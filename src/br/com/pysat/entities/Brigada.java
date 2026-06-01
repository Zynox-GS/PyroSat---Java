package br.com.pysat.entities;

public class Brigada {

    private int idBrigada;
    private String nome;
    private int qtdBrigadistas;
    private String status;
    private double latitudeAtual;
    private double longitudeAtual;
    private String orgaoVinculado;
    private String ocorrenciaAtiva;

    public Brigada(){}

    public Brigada(int idBrigada, String nome, int qtdBrigadistas, double latitudeAtual, double longitudeAtual, String orgaoVinculado) {
        this.idBrigada = idBrigada;
        this.nome = nome;
        this.qtdBrigadistas = qtdBrigadistas;
        this.latitudeAtual = latitudeAtual;
        this.longitudeAtual = longitudeAtual;
        this.orgaoVinculado = orgaoVinculado;
        this.status = "DISPONIVEL";
        this.ocorrenciaAtiva = null;
    }


    public boolean verificarDisponibilidade() {
        return status.equals("DISPONIVEL") && ocorrenciaAtiva == null;
    }


    public String alocarParaOcorrencia(String protocoloOcorrencia) {
        if (!verificarDisponibilidade()) {
            return "ERRO: Brigada " + nome + " já está em campo na ocorrência " + ocorrenciaAtiva;
        }
        this.ocorrenciaAtiva = protocoloOcorrencia;
        this.status = "EM_DESLOCAMENTO";
        return "OK: Brigada " + nome + " alocada para ocorrência " + protocoloOcorrencia;
    }


    public String liberarBrigada() {
        if (ocorrenciaAtiva == null) {
            return "Brigada " + nome + " já está disponível.";
        }
        String ocAnterior = ocorrenciaAtiva;
        this.ocorrenciaAtiva = null;
        this.status = "DISPONIVEL";
        return "Brigada " + nome + " liberada da ocorrência " + ocAnterior + ".";
    }


    public int getIdBrigada() {
        return idBrigada;
    }

    public void setIdBrigada(int idBrigada) {
        this.idBrigada = idBrigada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdBrigadistas() {
        return qtdBrigadistas;
    }

    public void setQtdBrigadistas(int qtdBrigadistas) {
        this.qtdBrigadistas = qtdBrigadistas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLatitudeAtual() {
        return latitudeAtual;
    }

    public void setLatitudeAtual(double latitudeAtual) {
        this.latitudeAtual = latitudeAtual;
    }

    public double getLongitudeAtual() {
        return longitudeAtual;
    }

    public void setLongitudeAtual(double longitudeAtual) {
        this.longitudeAtual = longitudeAtual;
    }

    public String getOrgaoVinculado() {
        return orgaoVinculado;
    }

    public void setOrgaoVinculado(String orgaoVinculado) {
        this.orgaoVinculado = orgaoVinculado;
    }

    public String getOcorrenciaAtiva() {
        return ocorrenciaAtiva;
    }

    public void setOcorrenciaAtiva(String ocorrenciaAtiva) {
        this.ocorrenciaAtiva = ocorrenciaAtiva;
    }

    @Override
    public String toString() {
        return "\n=== BRIGADA ===" +
                "\nID: " + idBrigada +
                "\nNome: " + nome +
                "\nBrigadistas: " + qtdBrigadistas +
                "\nStatus: " + status +
                "\nLocalização: " + latitudeAtual + ", " + longitudeAtual +
                "\nÓrgão: " + orgaoVinculado +
                "\nOcorrência Ativa: " + (ocorrenciaAtiva != null ? ocorrenciaAtiva : "Nenhuma");
    }
}
