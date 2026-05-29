package br.com.pysat.entities;

public class Alerta {

    private int idAlerta;
    private int idFoco;
    private String nivel;
    private String canal;
    private String mensagem;
    private String orgaoDestino;
    private boolean confirmado;
    private String dataHoraDisparo;

    public Alerta(){}

    public Alerta(int idAlerta, int idFoco, String nivel, String canal, String mensagem, String orgaoDestino) {
        this.idAlerta = idAlerta;
        this.idFoco = idFoco;
        this.nivel = nivel;
        this.canal = canal;
        this.mensagem = mensagem;
        this.orgaoDestino = orgaoDestino;
        this.confirmado = false;
        this.dataHoraDisparo = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    public int getIdFoco() {
        return idFoco;
    }

    public void setIdFoco(int idFoco) {
        this.idFoco = idFoco;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getOrgaoDestino() {
        return orgaoDestino;
    }

    public void setOrgaoDestino(String orgaoDestino) {
        this.orgaoDestino = orgaoDestino;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getDataHoraDisparo() {
        return dataHoraDisparo;
    }

    public void setDataHoraDisparo(String dataHoraDisparo) {
        this.dataHoraDisparo = dataHoraDisparo;
    }

    @Override
    public String toString() {
        return "\n  [ALERTA #" + idAlerta + "]" +
                "\n  Nível: " + nivel +
                "\n  Canal: " + canal +
                "\n  Destino: " + orgaoDestino +
                "\n  Mensagem: " + mensagem +
                "\n  Disparado em: " + dataHoraDisparo +
                "\n  Confirmado: " + (confirmado ? "Sim" : "Não");
    }
}

