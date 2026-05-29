package br.com.pysat.entities;

import java.util.ArrayList;

public class Area {

    private int idArea;
    private String nome;
    private String estado;
    private String municipio;
    private String bioma;
    private double latitudeCentroide;
    private double longitudeCentroide;
    private double areaKm2;
    private ArrayList<FocoCalor> focosAtivos;

    public Area() {}

    public Area(int idArea, String nome, String estado, String municipio, String bioma, double latitudeCentroide, double longitudeCentroide, double areaKm2) {
        this.idArea = idArea;
        this.nome = nome;
        this.estado = estado;
        this.municipio = municipio;
        this.bioma = bioma;
        this.latitudeCentroide = latitudeCentroide;
        this.longitudeCentroide = longitudeCentroide;
        this.areaKm2 = areaKm2;
        this.focosAtivos = new ArrayList<>();
    }

    /**
     * Calcula o risco agregado da área com base nos focos ativos (RN09).
     * Combina score de todos os focos para gerar um índice geral de perigo.
     * Essencial para priorização de recursos e brigadas pelo Coordenador.
     */
    public double calcularRiscoAgregado() {
        if (focosAtivos.isEmpty()) {
            return 0.0;
        }

        double somaScores = 0;
        int focosConfirmados = 0;

        for (FocoCalor foco : focosAtivos) {
            somaScores += foco.getScoreRisco();
            if (foco.getClassificacao().equals("CONFIRMADO")) {
                focosConfirmados++;
            }
        }

        double mediScore = somaScores / focosAtivos.size();
        // Focos confirmados aumentam o risco agregado em 10% cada
        double fatorConfirmados = 1.0 + (focosConfirmados * 0.10);
        double riscoFinal = mediScore * fatorConfirmados;

        return Math.min(riscoFinal, 100.0);  // máximo 100
    }

    /**
     * Adiciona foco à lista de focos ativos da área (RN09).
     */
    public void adicionarFoco(FocoCalor foco) {
        focosAtivos.add(foco);
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBioma() {
        return bioma;
    }

    public void setBioma(String bioma) {
        this.bioma = bioma;
    }

    public double getLatitudeCentroide() {
        return latitudeCentroide;
    }

    public void setLatitudeCentroide(double latitudeCentroide) {
        this.latitudeCentroide = latitudeCentroide;
    }

    public double getLongitudeCentroide() {
        return longitudeCentroide;
    }

    public void setLongitudeCentroide(double longitudeCentroide) {
        this.longitudeCentroide = longitudeCentroide;
    }

    public double getAreaKm2() {
        return areaKm2;
    }

    public void setAreaKm2(double areaKm2) {
        this.areaKm2 = areaKm2;
    }
    public ArrayList<FocoCalor> getFocosAtivos(){
        return focosAtivos;
    }

    @Override
    public String toString() {
        return "\n=== ÁREA MONITORADA ===" +
                "\nID: " + idArea +
                "\nNome: " + nome +
                "\nEstado: " + estado + " | Município: " + municipio +
                "\nBioma: " + bioma +
                "\nÁrea: " + areaKm2 + " km²" +
                "\nFocos Ativos: " + focosAtivos.size() +
                "\nRisco Agregado: " + String.format("%.1f", calcularRiscoAgregado());
    }
}

