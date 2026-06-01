package br.com.pysat.entities;

public class FocoSuspeito extends FocoCalor {

    private boolean aguardandoRevisao;
    private double umidade;
    private double ndvi;

    public FocoSuspeito(){}

    public FocoSuspeito(int idFoco, double latitude, double longitude,
                        double temperaturaCelsius, String dataHoraDeteccao, double umidade, double ndvi) {
        super(idFoco, latitude, longitude, temperaturaCelsius, "SUSPEITO", dataHoraDeteccao);
        this.aguardandoRevisao = true;
        this.umidade = umidade;
        this.ndvi = ndvi;
    }

    @Override
    public String calcularSeveridade() {
        double temp = getTemperaturaCelsius();
        String nivel;
        double score;

        if (temp >= 200 && umidade < 30 && ndvi < 0.3) {
            nivel = "ATENCAO";
            score = 50.0;
        } else {
            nivel = "MONITORAMENTO";
            score = 25.0;
        }

        setNivelSeveridade(nivel);
        setScoreRisco(score);
        return nivel;
    }

    public String calcularSeveridade(boolean urgenciaManual) {
        if (urgenciaManual) {
            setNivelSeveridade("ATENCAO");
            setScoreRisco(60.0);
            return "ATENCAO (urgência manual)";
        }
        return calcularSeveridade();
    }

    public void descartar() {
        this.aguardandoRevisao = false;
        setClassificacao("DESCARTADO");
    }

    public FocoConfirmado promoverParaConfirmado(String operador) {
        String agora = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.aguardandoRevisao = false;
        return new FocoConfirmado(
                getIdFoco(), getLatitude(), getLongitude(),
                getTemperaturaCelsius(), getDataHoraDeteccao(), operador, agora);
    }

    public boolean isAguardandoRevisao() {
        return aguardandoRevisao;
    }

    public void setAguardandoRevisao(boolean aguardandoRevisao) {
        this.aguardandoRevisao = aguardandoRevisao;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public double getNdvi() {
        return ndvi;
    }

    public void setNdvi(double ndvi) {
        this.ndvi = ndvi;
    }

    @Override
    public String toString() {
        return "\n=== FOCO SUSPEITO ===" +
                "\nID: " + idFoco +
                "\nLatitude: " + latitude +
                "\nLongitude: " + longitude +
                "\nTemperatura: " + temperaturaCelsius + " °C" +
                "\nUmidade: " + umidade + "%" +
                "\nNDVI: " + ndvi +
                "\nNível de Severidade: " + nivelSeveridade +
                "\nScore de Risco: " + scoreRisco +
                "\nAguardando Revisão: " + (aguardandoRevisao ? "Sim" : "Não") +
                "\nDetectado em: " + dataHoraDeteccao;
    }
}