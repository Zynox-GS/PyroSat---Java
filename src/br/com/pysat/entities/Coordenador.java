package br.com.pysat.entities;

import java.util.ArrayList;

public class Coordenador {

    private int idCoordenador;
    private String nome;
    private String orgao;
    private String email;
    private ArrayList<String> logOperacoes;

    public Coordenador() {
        this.logOperacoes = new ArrayList<>();
    }

    public Coordenador(int idCoordenador, String email, String orgao, String nome) {
        this.idCoordenador = idCoordenador;
        this.email = email;
        this.orgao = orgao;
        this.nome = nome;
        this.logOperacoes = new ArrayList<>();
    }


    public boolean exigeConfirmacaoHumana(FocoConfirmado foco) {
        String nivel = foco.calcularSeveridade();
        return nivel.equals("ALERTA") || nivel.equals("EMERGENCIA");
    }


    public ArrayList<OrgaoResponsavel> selecionarOrgaos(FocoConfirmado foco, OrgaoResponsavel... orgaos) {
        ArrayList<OrgaoResponsavel> selecionados = new ArrayList<>();

        double latFoco = foco.getLatitude();
        double lonFoco = foco.getLongitude();

        for (OrgaoResponsavel orgao : orgaos) {
            double distancia = calcularDistanciaKm(latFoco, lonFoco,
                    orgao.getLatitudeSede(), orgao.getLongitudeSede());

            if (orgao.cobreaDistancia(distancia)) {
                selecionados.add(orgao);
                System.out.println("    [SELECIONADO] " + orgao.getSigla() +
                        " — distância: " + String.format("%.1f", distancia) + " km");
            } else {
                System.out.println("    [FORA DO RAIO] " + orgao.getSigla() +
                        " — distância: " + String.format("%.1f", distancia) + " km");
            }
        }

        return selecionados;
    }


    public ArrayList<Alerta> acionarProtocoloCascata(FocoConfirmado foco, OrgaoResponsavel... orgaos) {
        System.out.println("\n>>> COORDENADOR " + nome + " acionando protocolo em cascata...");

        if (exigeConfirmacaoHumana(foco)) {
            System.out.println("    [RN04] Nível crítico detectado — confirmação humana registrada pelo coordenador " + nome);
        }

        ArrayList<OrgaoResponsavel> orgaosSelecionados = selecionarOrgaos(foco, orgaos);

        if (orgaosSelecionados.isEmpty()) {
            System.out.println("    Nenhum órgão dentro do raio de cobertura.");
            return new ArrayList<>();
        }

        OrgaoResponsavel[] array = orgaosSelecionados.toArray(new OrgaoResponsavel[0]);
        ArrayList<Alerta> alertas = foco.acionarProtocoloCascata(array);

        String log = "Protocolo acionado | Foco #" + foco.getIdFoco() +
                " | Severidade: " + foco.getNivelSeveridade() +
                " | Alertas: " + alertas.size() +
                " | Órgãos selecionados: " + orgaosSelecionados.size();
        logOperacoes.add(log);

        System.out.println("    " + alertas.size() + " alerta(s) disparado(s) para " +
                orgaosSelecionados.size() + " órgão(s).");
        return alertas;
    }


    public int coordenarBrigadas(FocoConfirmado foco, String protocoloOcorrencia, Brigada... brigadas) {
        System.out.println("\n>>> Coordenando brigadas para ocorrência " + protocoloOcorrencia + "...");
        String nivel = foco.getNivelSeveridade();
        int alocadas = 0;

        for (Brigada b : brigadas) {
            if (nivel.equals("EMERGENCIA")) {
                String resultado = b.alocarParaOcorrencia(protocoloOcorrencia);
                System.out.println("    " + resultado);
                if (resultado.startsWith("OK")) alocadas++;

            } else if (nivel.equals("ALERTA") && alocadas == 0) {
                String resultado = b.alocarParaOcorrencia(protocoloOcorrencia);
                System.out.println("    " + resultado);
                if (resultado.startsWith("OK")) alocadas++;

            } else {
                System.out.println("    Nível " + nivel + " não requer alocação de brigadas.");
                break;
            }
        }

        String log = "Brigadas coordenadas | Ocorrência: " + protocoloOcorrencia +
                " | Nível: " + nivel +
                " | Solicitadas: " + brigadas.length +
                " | Alocadas: " + alocadas;
        logOperacoes.add(log);

        return alocadas;
    }


    private double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        final double RAIO_TERRA = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return RAIO_TERRA * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public void exibirLogOperacoes() {
        System.out.println("\n=== LOG DO COORDENADOR: " + nome + " ===");
        if (logOperacoes.isEmpty()) {
            System.out.println("  Nenhuma operação registrada.");
        } else {
            for (int i = 0; i < logOperacoes.size(); i++) {
                System.out.println("  [" + (i + 1) + "] " + logOperacoes.get(i));
            }
        }
    }

    public int getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(int idCoordenador) {
        this.idCoordenador = idCoordenador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getLogOperacoes() {
        return logOperacoes;
    }

    @Override
    public String toString() {
        return "\n=== COORDENADOR ===" +
                "\nID: " + idCoordenador +
                "\nNome: " + nome +
                "\nÓrgão: " + orgao +
                "\nEmail: " + email +
                "\nOperações registradas: " + logOperacoes.size();

    }
}
