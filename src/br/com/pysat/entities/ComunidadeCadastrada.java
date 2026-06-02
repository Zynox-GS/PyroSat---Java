package br.com.pysat.entities;

import br.com.pysat.interfaces.Notificavel;
import java.util.ArrayList;

public class ComunidadeCadastrada implements Notificavel {

    private int idComunidade;
    private int idArea;
    private String nome;
    private String tipo; // Ex: Nativa/Tradicional, Rural, Urbana, Assentamento
    private String whatsapp; // Exigirá formato internacional (+CountryCode)
    private double raioAlertaKm;
    private String dataCadastro;
    private boolean ativo;
    private ArrayList<Alerta> alertasRecebidos;

    public ComunidadeCadastrada() {
        this.alertasRecebidos = new ArrayList<>();
    }

    public ComunidadeCadastrada(int idComunidade, int idArea, String nome,
                                String tipo, String whatsapp, double raioAlertaKm) {
        this.idComunidade = idComunidade;
        this.idArea       = idArea;
        this.nome         = nome;
        this.tipo         = tipo;
        this.whatsapp     = whatsapp;
        this.raioAlertaKm = raioAlertaKm;
        this.ativo        = true;
        this.dataCadastro = java.time.LocalDate.now().toString();
        this.alertasRecebidos = new ArrayList<>();
    }

    public boolean focoNaZonaDeAlerta(double latFoco, double lonFoco,
                                      double latRef, double lonRef) {
        double distancia = calcularDistanciaKm(latRef, lonRef, latFoco, lonFoco);
        return distancia <= raioAlertaKm;
    }

    @Override
    public void receberAlerta(Alerta alerta) {
        if (!ativo) return;
        alertasRecebidos.add(alerta);
        System.out.println("  [GLOBAL COMMUNITY: " + nome + "] Alerta recebido via WhatsApp " +
                whatsapp + " — Nível: " + alerta.getNivel());
    }

    @Override
    public String getCanaisNotificacao() { return "WhatsApp Internacional: " + whatsapp; }

    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        final double RAIO_TERRA = 6371.0; // Raio global médio da Terra
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return RAIO_TERRA * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    // Getters e Setters


    public int getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(int idComunidade) {
        this.idComunidade = idComunidade;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public double getRaioAlertaKm() {
        return raioAlertaKm;
    }

    public void setRaioAlertaKm(double raioAlertaKm) {
        this.raioAlertaKm = raioAlertaKm;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setAlertasRecebidos(ArrayList<Alerta> alertasRecebidos) {
        this.alertasRecebidos = alertasRecebidos;
    }

    @Override
    public String toString() {
        return "\n=== GLOBAL COMMUNITY RECORD ===" +
                "\nID: " + idComunidade +
                "\nNome: " + nome +
                "\nTipo de Assentamento: " + tipo +
                "\nÁrea (Zone ID): " + idArea +
                "\nWhatsApp (Global): " + whatsapp +
                "\nRaio de Cobertura: " + raioAlertaKm + " km" +
                "\nData de Registro: " + dataCadastro +
                "\nStatus Ativo: " + (ativo ? "Yes" : "No") +
                "\nAlertas Recebidos: " + alertasRecebidos.size();
    }
}