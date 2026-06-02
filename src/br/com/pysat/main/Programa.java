package br.com.pysat.main;

import br.com.pysat.entities.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Programa {

    static Scanner sc = new Scanner(System.in);

    static String texto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    static int inteiro(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int v = Integer.parseInt(sc.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  !! Valor inválido. Digite um número inteiro.");
            }
        }
    }

    static double real(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                double v = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  !! Valor inválido. Digite um número (ex: 123.45).");
            }
        }
    }

    static void linha() {
        System.out.println("=================================================");
    }

    static void pausar() {
        System.out.println();
        System.out.print("  Pressione ENTER para voltar ao menu...");
        sc.nextLine();
    }

    static ArrayList<Coordenador>          coordenadores = new ArrayList<>();
    static ArrayList<OrgaoResponsavel>     orgaos        = new ArrayList<>();
    static ArrayList<Brigada>              brigadas      = new ArrayList<>();
    static ArrayList<Area>                 areas         = new ArrayList<>();
    static ArrayList<FocoCalor>            focos         = new ArrayList<>();
    static ArrayList<ComunidadeCadastrada> comunidades   = new ArrayList<>();

    static int idCoordenador = 1;
    static int idOrgao       = 1;
    static int idBrigada     = 1;
    static int idArea        = 1;
    static int idFoco        = 100;
    static int idComunidade  = 1;

    public static void main(String[] args) {
        linha();
        System.out.println("     PYROSAT GLOBAL — Global Wildfire Detection System");
        System.out.println("         Monitoramento via Satélite | v1.0");
        linha();

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            int opcao = inteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrarCoordenador();
                case 2 -> cadastrarOrgaoResponsavel();
                case 3 -> cadastrarBrigada();
                case 4 -> cadastrarArea();
                case 5 -> registrarFoco();
                case 6 -> revisarFocoSuspeito();
                case 7 -> acionarProtocolo();
                case 8 -> liberarBrigada();
                case 9 -> exibirRelatorioGeral();
                case 10 -> cadastrarComunidade();
                case 0 -> {
                    System.out.println("\n[SISTEMA] Encerrando PyroSat Global. Até logo!\n");
                    rodando = false;
                }
                default -> System.out.println("  !! Opção inválida. Tente novamente.");
            }
        }
        sc.close();
    }

    static void exibirMenuPrincipal() {
        System.out.println();
        linha();
        System.out.println("  MENU PRINCIPAL — PYROSAT GLOBAL");
        linha();
        System.out.println("  [1] Cadastrar Coordenador");
        System.out.println("  [2] Cadastrar Órgão Responsável");
        System.out.println("  [3] Cadastrar Brigada");
        System.out.println("  [4] Cadastrar Zona de Monitoramento Global");
        System.out.println("  [5] Registrar Novo Foco de Calor");
        System.out.println("  [6] Revisar Foco Suspeito");
        System.out.println("  [7] Acionar Protocolo de Emergência");
        System.out.println("  [8] Liberar Brigada de Ocorrência");
        System.out.println("  [9] Relatório Geral do Sistema");
        System.out.println("  [10] Cadastrar Comunidade Global");
        System.out.println("  [0] Sair");
        linha();
    }

    static Coordenador selecionarCoordenador(String titulo) {
        if (coordenadores.isEmpty()) {
            System.out.println("  !! Nenhum coordenador cadastrado. Cadastre um primeiro.");
            return null;
        }
        System.out.println("\n  " + titulo);
        for (int i = 0; i < coordenadores.size(); i++) {
            Coordenador c = coordenadores.get(i);
            System.out.println("    [" + (i + 1) + "] " + c.getNome() + " — " + c.getOrgao());
        }
        int idx = inteiro("  Selecione o coordenador: ") - 1;
        if (idx < 0 || idx >= coordenadores.size()) {
            System.out.println("  !! Seleção inválida.");
            return null;
        }
        return coordenadores.get(idx);
    }

    static void cadastrarCoordenador() {
        System.out.println("\n--- CADASTRO DE COORDENADOR ---");
        String nome  = texto("  Nome completo: ");
        String orgao = texto("  Órgão / Agência: ");
        String email = texto("  E-mail: ");

        Coordenador c = new Coordenador(idCoordenador++, email, orgao, nome);
        coordenadores.add(c);

        System.out.println("\n  [OK] Coordenador cadastrado!");
        System.out.println(c);
        pausar();
    }

    static void cadastrarOrgaoResponsavel() {
        System.out.println("\n--- CADASTRO DE ÓRGÃO RESPONSÁVEL ---");
        String nome   = texto("  Nome do órgão: ");
        String sigla  = texto("  Sigla: ");
        String tipo   = texto("  Tipo (Local/Regional/Nacional/Internacional): ");
        String estado = texto("  País/Região: ");
        String email  = texto("  E-mail de contato: ");
        String tel    = texto("  Telefone (DDI obrigatório, ex: +1...): ");
        double lat    = real("  Latitude da sede (ex: -15.7942): ");
        double lon    = real("  Longitude da sede (ex: -47.8822): ");
        double raio   = real("  Raio de cobertura global (km): ");

        OrgaoResponsavel o = new OrgaoResponsavel(lon, lat, raio, tel, email, estado, tipo, sigla, nome, idOrgao++);
        orgaos.add(o);

        System.out.println("\n  [OK] Órgão cadastrado!");
        System.out.println(o);
        System.out.println("  Canais de notificação: " + o.getCanaisNotificacao());
        pausar();
    }

    static void cadastrarBrigada() {
        System.out.println("\n--- CADASTRO DE BRIGADA ---");
        String nome  = texto("  Nome da brigada: ");
        int    qtd   = inteiro("  Quantidade de brigadistas: ");
        String orgao = texto("  Órgão/Agência vinculada: ");
        double lat   = real("  Latitude atual (ex: -15.8000): ");
        double lon   = real("  Longitude atual (ex: -47.9000): ");

        Brigada b = new Brigada(idBrigada++, nome, qtd, lat, lon, orgao);
        brigadas.add(b);

        System.out.println("\n  [OK] Brigada cadastrada!");
        System.out.println("  Status inicial: " + (b.verificarDisponibilidade() ? "DISPONÍVEL" : "INDISPONÍVEL"));
        System.out.println(b);
        pausar();
    }

    static void cadastrarArea() {
        System.out.println("\n--- CADASTRO DE ZONA DE MONITORAMENTO GLOBAL ---");
        String nome      = texto("  Nome da área: ");
        String estado    = texto("  País/Região: ");
        String municipio = texto("  Cidade/Província: ");
        String bioma     = texto("  Bioma (ex: Savana, Floresta Tropical, Tundra): ");
        double lat       = real("  Latitude do centróide: ");
        double lon       = real("  Longitude do centróide: ");
        double areaKm2   = real("  Área em km²: ");

        Area a = new Area(idArea++, nome, estado, municipio, bioma, lat, lon, areaKm2);
        areas.add(a);

        System.out.println("\n  [OK] Zona de monitoramento cadastrada!");
        System.out.println(a);
        pausar();
    }

    static void cadastrarComunidade() {
        System.out.println("\n--- CADASTRO DE COMUNIDADE GLOBAL ---");
        String nome = texto("  Nome da comunidade ou assentamento: ");
        String tipo = texto("  Tipo (Nativa/Tradicional, Rural, Urbana, Assentamento): ");
        String whatsapp = texto("  WhatsApp Global (DDI obrigatório, ex: +1 415..., +55 11...): ");
        double raio = real("  Raio de alerta (km): ");

        System.out.println("\n  Vincular a uma zona de monitoramento existente:");
        Area area = selecionarArea();
        int idAreaVinculada = (area != null) ? area.getIdArea() : 0;

        ComunidadeCadastrada c = new ComunidadeCadastrada(idComunidade++, idAreaVinculada, nome, tipo, whatsapp, raio);
        comunidades.add(c);

        System.out.println("\n  [OK] Comunidade global cadastrada com sucesso!");
        System.out.println(c);
        pausar();
    }

    static void registrarFoco() {
        System.out.println("\n--- REGISTRO DE NOVO FOCO DE CALOR ---");
        System.out.println("  [1] Foco Suspeito  (aguarda revisão humana)");
        System.out.println("  [2] Foco Confirmado (incêndio verificado)");
        int tipo = inteiro("  Tipo do foco: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("  !! Tipo inválido.");
            pausar();
            return;
        }

        double lat   = real("  Latitude do foco (ex: -15.7200): ");
        double lon   = real("  Longitude do foco (ex: -47.8100): ");
        double temp  = real("  Temperatura detectada (°C): ");
        String dataH = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        Area areaSelecionada = selecionarArea();

        if (tipo == 1) {
            double umidade = real("  Umidade do ar (%): ");
            double ndvi    = real("  Índice NDVI (0.0 a 1.0): ");

            FocoSuspeito fs = new FocoSuspeito(idFoco++, lat, lon, temp, dataH, umidade, ndvi);
            fs.calcularSeveridade();
            focos.add(fs);
            if (areaSelecionada != null) areaSelecionada.adicionarFoco(fs);

            System.out.println("\n  [OK] Foco Suspeito registrado!");
            System.out.println(fs);

            String urgencia = texto("\n  Forçar URGÊNCIA MANUAL neste foco? (s/n): ");
            if (urgencia.equalsIgnoreCase("s")) {
                fs.calcularSeveridade(true);
                System.out.println("  [!] Urgência manual aplicada. Severidade: " + fs.getNivelSeveridade());
            }

        } else {
            Coordenador coord = selecionarCoordenador("Coordenador responsável pela confirmação:");
            if (coord == null) {
                pausar();
                return;
            }

            FocoConfirmado fc = new FocoConfirmado(idFoco++, lat, lon, temp, dataH, coord.getNome(), dataH);
            fc.calcularSeveridade();
            focos.add(fc);
            if (areaSelecionada != null) areaSelecionada.adicionarFoco(fc);

            System.out.println("\n  [OK] Foco Confirmado registrado!");
            System.out.println(fc);
            System.out.println("  Severidade: " + fc.getNivelSeveridade() + " | Score: " + fc.getScoreRisco());
        }
        pausar();
    }

    static void revisarFocoSuspeito() {
        System.out.println("\n--- REVISÃO DE FOCO SUSPEITO ---");

        ArrayList<FocoSuspeito> suspeitos = new ArrayList<>();
        for (FocoCalor f : focos) {
            if (f instanceof FocoSuspeito fs && fs.isAguardandoRevisao()) {
                suspeitos.add(fs);
            }
        }

        if (suspeitos.isEmpty()) {
            System.out.println("  Nenhum foco suspeito pendente de revisão.");
            pausar();
            return;
        }

        System.out.println("  Focos suspeitos pendentes:");
        for (int i = 0; i < suspeitos.size(); i++) {
            FocoSuspeito fs = suspeitos.get(i);
            System.out.println("    [" + (i + 1) + "] Foco #" + fs.getIdFoco()
                    + " | " + fs.getLatitude() + ", " + fs.getLongitude()
                    + " | Temp: " + fs.getTemperaturaCelsius() + "°C"
                    + " | Severidade: " + fs.getNivelSeveridade());
        }

        int escolha = inteiro("  Escolha o foco (0 = cancelar): ");
        if (escolha == 0 || escolha > suspeitos.size()) {
            pausar();
            return;
        }
        FocoSuspeito fs = suspeitos.get(escolha - 1);

        System.out.println(fs);
        System.out.println("\n  [1] Confirmar como incêndio real");
        System.out.println("  [2] Atualizar dados e manter suspeito");
        System.out.println("  [3] Descartar (falso alarme)");
        int decisao = inteiro("  Decisão: ");

        if (decisao == 1) {
            Coordenador coord = selecionarCoordenador("Coordenador responsável pela confirmação:");
            if (coord == null) {
                pausar();
                return;
            }

            FocoConfirmado fc = fs.promoverParaConfirmado(coord.getNome());
            fc.calcularSeveridade();

            focos.remove(fs);
            focos.add(fc);
            for (Area a : areas) {
                if (a.removerFoco(fs)) a.adicionarFoco(fc);
            }

            System.out.println("\n  [OK] Foco #" + fc.getIdFoco() + " promovido para CONFIRMADO!");
            System.out.println(fc);

            if (coord.exigeConfirmacaoHumana(fc)) {
                System.out.println("\n  [!] Nível " + fc.getNivelSeveridade()
                        + " exige ação imediata — coordenador " + coord.getNome() + " notificado.");
            }

            String acionar = texto("\n  Acionar protocolo de emergência agora? (s/n): ");
            if (acionar.equalsIgnoreCase("s")) executarProtocolo(fc, coord);

        } else if (decisao == 2) {
            double novaTemp    = real("  Nova temperatura (°C): ");
            double novaUmidade = real("  Nova umidade (%): ");
            double novoNdvi    = real("  Novo NDVI (0.0 a 1.0): ");
            fs.setTemperaturaCelsius(novaTemp);
            fs.setUmidade(novaUmidade);
            fs.setNdvi(novoNdvi);
            fs.calcularSeveridade();
            System.out.println("  [OK] Foco atualizado. Nova severidade: " + fs.getNivelSeveridade());

        } else if (decisao == 3) {
            fs.descartar();
            focos.remove(fs);
            for (Area a : areas) a.removerFoco(fs);
            System.out.println("  [OK] Foco #" + fs.getIdFoco() + " descartado (falso alarme).");

        } else {
            System.out.println("  !! Opção inválida.");
        }
        pausar();
    }

    static void acionarProtocolo() {
        System.out.println("\n--- ACIONAMENTO DE PROTOCOLO DE EMERGÊNCIA ---");

        if (coordenadores.isEmpty()) {
            System.out.println("  !! Cadastre ao menos um Coordenador primeiro.");
            pausar();
            return;
        }
        if (orgaos.isEmpty()) {
            System.out.println("  !! Cadastre ao menos um Órgão Responsável primeiro.");
            pausar();
            return;
        }

        Coordenador coord = selecionarCoordenador("Coordenador que acionará o protocolo:");
        if (coord == null) {
            pausar();
            return;
        }

        ArrayList<FocoConfirmado> confirmados = new ArrayList<>();
        for (FocoCalor f : focos) {
            if (f instanceof FocoConfirmado fc) confirmados.add(fc);
        }

        if (confirmados.isEmpty()) {
            System.out.println("  !! Nenhum foco confirmado disponível.");
            pausar();
            return;
        }

        System.out.println("\n  Focos confirmados:");
        for (int i = 0; i < confirmados.size(); i++) {
            FocoConfirmado fc = confirmados.get(i);
            System.out.println("    [" + (i + 1) + "] Foco #" + fc.getIdFoco()
                    + " | Temp: " + fc.getTemperaturaCelsius() + "°C"
                    + " | Severidade: " + fc.getNivelSeveridade());
        }
        int idxFoco = inteiro("  Selecione o foco: ") - 1;
        if (idxFoco < 0 || idxFoco >= confirmados.size()) {
            System.out.println("  !! Seleção inválida.");
            pausar();
            return;
        }

        executarProtocolo(confirmados.get(idxFoco), coord);
        pausar();
    }

    static void executarProtocolo(FocoConfirmado fc, Coordenador coord) {
        linha();
        System.out.println("  Coordenador: " + coord.getNome());
        System.out.println("  Foco #" + fc.getIdFoco() + " | Severidade: " + fc.getNivelSeveridade());
        System.out.println("  Temperatura: " + fc.getTemperaturaCelsius() + "°C"
                + "  (ALERTA ≥ 400°C | EMERGENCIA ≥ 600°C)");
        linha();

        System.out.println("\n[SISTEMA] Selecionando órgãos dentro do raio de cobertura global...");
        OrgaoResponsavel[] arrayOrgaos = orgaos.toArray(new OrgaoResponsavel[0]);
        ArrayList<Alerta> alertas = coord.acionarProtocoloCascata(fc, arrayOrgaos);

        System.out.println("\n[SISTEMA] Coordenando brigadas...");
        Brigada[] arrayBrigadas = brigadas.toArray(new Brigada[0]);
        if (arrayBrigadas.length > 0) {
            String protocolo = fc.gerarProtocolo();
            coord.coordenarBrigadas(fc, protocolo, arrayBrigadas);
        } else {
            System.out.println("  [!] Nenhuma brigada cadastrada para alocação.");
        }

        System.out.println("\n[SISTEMA] " + alertas.size() + " alerta(s) disparado(s) com sucesso.");
        coord.exibirLogOperacoes();
    }

    static void liberarBrigada() {
        System.out.println("\n--- LIBERAR BRIGADA ---");

        if (brigadas.isEmpty()) {
            System.out.println("  Nenhuma brigada cadastrada.");
            pausar();
            return;
        }

        ArrayList<Brigada> emCampo = new ArrayList<>();
        for (Brigada b : brigadas) {
            if (!b.verificarDisponibilidade()) {
                emCampo.add(b);
            }
        }

        if (emCampo.isEmpty()) {
            System.out.println("  Nenhuma brigada está em campo no momento.");
            pausar();
            return;
        }

        System.out.println("  Brigadas em campo:");
        for (int i = 0; i < emCampo.size(); i++) {
            System.out.println("    [" + (i + 1) + "] " + emCampo.get(i).getNome()
                    + " — Ocorrência: " + emCampo.get(i).getOcorrenciaAtiva());
        }

        int escolha = inteiro("  Selecione a brigada (0 = cancelar): ");
        if (escolha == 0 || escolha > emCampo.size()) {
            pausar();
            return;
        }

        Brigada b = emCampo.get(escolha - 1);
        System.out.println("  " + b.liberarBrigada());
        System.out.println("  Disponível agora: " + (b.verificarDisponibilidade() ? "Sim" : "Não"));
        pausar();
    }

    static void exibirRelatorioGeral() {
        linha();
        System.out.println("          RELATÓRIO GERAL — PYROSAT GLOBAL");
        linha();

        System.out.println("\n>>> COORDENADORES (" + coordenadores.size() + ")");
        if (coordenadores.isEmpty()) System.out.println("  Nenhum cadastrado.");
        for (Coordenador c : coordenadores) System.out.println(c);

        System.out.println("\n>>> ÓRGÃOS RESPONSÁVEIS (" + orgaos.size() + ")");
        if (orgaos.isEmpty()) System.out.println("  Nenhum cadastrado.");
        for (OrgaoResponsavel o : orgaos) {
            System.out.println(o);
            o.exibirRelatorioAlertas();
        }

        System.out.println("\n>>> BRIGADAS (" + brigadas.size() + ")");
        if (brigadas.isEmpty()) System.out.println("  Nenhuma cadastrada.");
        for (Brigada b : brigadas) {
            System.out.println(b);
            System.out.println("  Disponível: " + (b.verificarDisponibilidade() ? "Sim" : "Não"));
        }

        System.out.println("\n>>> COMUNIDADES GLOBAIS CADASTRADAS (" + comunidades.size() + ")");
        if (comunidades.isEmpty()) System.out.println("  Nenhuma cadastrada.");
        for (ComunidadeCadastrada c : comunidades) System.out.println(c);

        System.out.println("\n>>> ZONAS DE MONITORAMENTO (" + areas.size() + ")");
        if (areas.isEmpty()) System.out.println("  Nenhuma cadastrada.");
        for (Area a : areas) {
            System.out.println(a);
            System.out.println("  Risco Agregado: " + String.format("%.1f", a.calcularRiscoAgregado()));
        }

        System.out.println("\n>>> FOCOS DE CALOR (" + focos.size() + ")");
        if (focos.isEmpty()) System.out.println("  Nenhum registrado.");
        for (FocoCalor f : focos) System.out.println(f);

        linha();
        pausar();
    }

    static Area selecionarArea() {
        if (areas.isEmpty()) return null;
        System.out.println("\n  Zonas cadastradas:");
        for (int i = 0; i < areas.size(); i++) {
            System.out.println("    [" + (i + 1) + "] " + areas.get(i).getNome()
                    + " — " + areas.get(i).getEstado());
        }
        int escolha = inteiro("  Vincular a zona (0 = nenhuma): ");
        if (escolha > 0 && escolha <= areas.size()) return areas.get(escolha - 1);
        return null;
    }
}