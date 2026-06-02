package br.com.pysat.entities;

import br.com.pysat.interfaces.Notificavel;
import java.util.ArrayList;

public class OrgaoResponsavel implements Notificavel {

    private int idOrgao;
    private String nome;
    private String sigla;
    private String tipo;
    private String estado;
    private String emailContato;
    private String telefone;
    private double raioCobertura;
    private double latitudeSede;
    private double longitudeSede;
    private ArrayList<Alerta> alertasRecebidos;

    public OrgaoResponsavel() {
        this.alertasRecebidos = new ArrayList<>();
    }

    public OrgaoResponsavel(double longitudeSede, double latitudeSede, double raioCobertura, String telefone, String emailContato, String estado, String tipo, String sigla, String nome, int idOrgao) {
        this.longitudeSede = longitudeSede;
        this.latitudeSede = latitudeSede;
        this.raioCobertura = raioCobertura;
        this.telefone = telefone;
        this.emailContato = emailContato;
        this.estado = estado;
        this.tipo = tipo;
        this.sigla = sigla;
        this.nome = nome;
        this.idOrgao = idOrgao;
        this.alertasRecebidos = new ArrayList<>();
    }

    @Override
    public void receberAlerta(Alerta alerta) {
        alertasRecebidos.add(alerta);
        System.out.println("  [" + sigla + "] Alerta recebido via " + alerta.getCanal() +
                " — Nível: " + alerta.getNivel());
    }

    @Override
    public String getCanaisNotificacao() {
        return "Email: " + emailContato + " | Telefone: " + (telefone != null ? telefone : "N/A");
    }


    public boolean cobreaDistancia(double distanciaKm) {
        return distanciaKm <= raioCobertura;
    }


    public void exibirRelatorioAlertas() {
        System.out.println("\n=== RELATÓRIO DE ALERTAS — " + sigla + " ===");
        if (alertasRecebidos.isEmpty()) {
            System.out.println("  Nenhum alerta recebido.");
        } else {
            for (Alerta a : alertasRecebidos) {
                System.out.println(a);
            }
            System.out.println("  Total recebido: " + alertasRecebidos.size());
        }
    }

    public int getIdOrgao() {
        return idOrgao;
    }

    public void setIdOrgao(int idOrgao) {
        this.idOrgao = idOrgao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getRaioCobertura() {
        return raioCobertura;
    }

    public void setRaioCobertura(double raioCobertura) {
        this.raioCobertura = raioCobertura;
    }

    public double getLatitudeSede() {
        return latitudeSede;
    }

    public void setLatitudeSede(double latitudeSede) {
        this.latitudeSede = latitudeSede;
    }

    public double getLongitudeSede() {
        return longitudeSede;
    }

    public void setLongitudeSede(double longitudeSede) {
        this.longitudeSede = longitudeSede;
    }

    public ArrayList<Alerta> getAlertasRecebidos(){
        return alertasRecebidos;
    }

    @Override
    public String toString() {
        return "\n=== ÓRGÃO RESPONSÁVEL ===" +
                "\nID: " + idOrgao +
                "\nNome: " + nome +
                "\nSigla: " + sigla +
                "\nTipo: " + tipo +
                "\nEstado: " + estado +
                "\nEmail: " + emailContato +
                "\nRaio de Cobertura: " + raioCobertura + " km" +
                "\nSede: " + latitudeSede + ", " + longitudeSede +
                "\nAlertas recebidos: " + alertasRecebidos.size();
    }
}
