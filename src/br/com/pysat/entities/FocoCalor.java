package br.com.pysat.entities;

public abstract class FocoCalor {

    protected int idFoco;
    protected double latitude;
    protected double longitude;
    protected double temperaturaCelsius;
    protected String classificacao;
    protected String nivelSeveridade;
    protected double scoreRisco;
    protected String dataHoraDeteccao;

    public FocoCalor(){}

    public FocoCalor(int idFoco, double latitude, double longitude, double temperaturaCelsius, String classificacao, String dataHoraDeteccao) {
        this.idFoco = idFoco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperaturaCelsius = temperaturaCelsius;
        this.classificacao = classificacao;
        this.dataHoraDeteccao = dataHoraDeteccao;
        this.scoreRisco = 0.0;
        this.nivelSeveridade = "Monitorando...";
    }

    public abstract String calcularSeveridade();

    public int getIdFoco() {
        return idFoco;
    }

    public void setIdFoco(int idFoco) {
        this.idFoco = idFoco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperaturaCelsius() {
        return temperaturaCelsius;
    }

    public void setTemperaturaCelsius(double temperaturaCelsius) {
        this.temperaturaCelsius = temperaturaCelsius;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getNivelSeveridade() {
        return nivelSeveridade;
    }

    public void setNivelSeveridade(String nivelSeveridade) {
        this.nivelSeveridade = nivelSeveridade;
    }

    public double getScoreRisco() {
        return scoreRisco;
    }

    public void setScoreRisco(double scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    public String getDataHoraDeteccao() {
        return dataHoraDeteccao;
    }

    public void setDataHoraDeteccao(String dataHoraDeteccao) {
        this.dataHoraDeteccao = dataHoraDeteccao;
    }
}
