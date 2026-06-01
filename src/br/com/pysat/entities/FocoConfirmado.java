package br.com.pysat.entities;

import java.util.ArrayList;

public class FocoConfirmado extends FocoCalor {

    private String operadorConfirmacao;
    private String dataHoraConfirmacao;
    private ArrayList<Alerta> alertasGerados;

    public FocoConfirmado() {
        this.alertasGerados = new ArrayList<>();
    }

    public FocoConfirmado(int idFoco, double latitude, double longitude,
                          double temperaturaKelvin, String dataHoraDeteccao,
                          String operadorConfirmacao, String dataHoraConfirmacao) {
        super(idFoco, latitude, longitude, temperaturaKelvin, "CONFIRMADO", dataHoraDeteccao);
        this.operadorConfirmacao = operadorConfirmacao;
        this.dataHoraConfirmacao = dataHoraConfirmacao;
        this.alertasGerados = new ArrayList<>();
    }

    @Override
    public String calcularSeveridade() {
        String nivel;
        double score;
        if (temperaturaCelsius >= 600) {
            nivel = "EMERGENCIA";
            score = 95.0;
        } else if (temperaturaCelsius >= 400) {
            nivel = "ALERTA";
            score = 75.0;
        } else if (temperaturaCelsius >= 200) {
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

    public String gerarProtocolo() {
        return "OC-" + getIdFoco() + "-" +
                java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("HHmmss"));
    }

    public ArrayList<Alerta> acionarProtocoloCascata(OrgaoResponsavel... orgaos) {
        calcularSeveridade();
        alertasGerados.clear();

        String nivel = getNivelSeveridade();
        String[] canais;

        if (nivel.equals("EMERGENCIA")) {
            canais = new String[]{"WHATSAPP", "SMS", "RADIO", "PUSH"};
        } else if (nivel.equals("ALERTA")) {
            canais = new String[]{"WHATSAPP", "SMS", "PUSH"};
        } else {
            canais = new String[]{"PUSH"};
        }

        int idAlerta = 100 + getIdFoco();
        for (OrgaoResponsavel orgao : orgaos) {
            for (String canal : canais) {
                Alerta alerta = new Alerta(
                        idAlerta++,
                        getIdFoco(),
                        nivel,
                        canal,
                        "Foco CONFIRMADO detectado. Severidade: " + nivel +
                                " | Score: " + getScoreRisco() +
                                " | Coord: " + getLatitude() + ", " + getLongitude(),
                        orgao.getNome()
                );
                alertasGerados.add(alerta);
                orgao.receberAlerta(alerta);
            }
        }

        return alertasGerados;
    }

    public String getOperadorConfirmacao() {
        return operadorConfirmacao;
    }

    public void setOperadorConfirmacao(String operadorConfirmacao) {
        this.operadorConfirmacao = operadorConfirmacao;
    }

    public String getDataHoraConfirmacao() {
        return dataHoraConfirmacao;
    }

    public void setDataHoraConfirmacao(String dataHoraConfirmacao) {
        this.dataHoraConfirmacao = dataHoraConfirmacao;
    }

    public ArrayList<Alerta> getAlertasGerados() {
        return alertasGerados;
    }

    @Override
    public String toString() {
        return "\n=== FOCO CONFIRMADO ===" +
                "\nID: " + idFoco +
                "\nLatitude: " + latitude +
                "\nLongitude: " + longitude +
                "\nTemperatura: " + temperaturaCelsius + " °C" +
                "\nNível de Severidade: " + nivelSeveridade +
                "\nScore de Risco: " + scoreRisco +
                "\nOperador: " + operadorConfirmacao +
                "\nDetectado em: " + dataHoraDeteccao +
                "\nConfirmado em: " + dataHoraConfirmacao +
                "\nAlertas gerados: " + alertasGerados.size();
    }
}
