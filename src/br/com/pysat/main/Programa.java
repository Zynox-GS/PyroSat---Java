package br.com.pysat.main;

import br.com.pysat.entities.*;
import javax.swing.JOptionPane;

public class Programa {

    // --- Métodos de entrada (Baseados no seu modelo) ---
    static String texto(String j) {
        return JOptionPane.showInputDialog(j);
    }

    static int inteiro(String j) {
        return Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    static double real(String j) {
        return Double.parseDouble(JOptionPane.showInputDialog(j));
    }

    // --- Método novo para simular o "timer" (delay) ---
    static void esperar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            System.out.println("Erro no timer do sistema.");
        }
    }

    public static void main(String[] args) {

        // 1. Instanciar objetos base
        Coordenador coordenador = new Coordenador(1, "carlos.silva@pysat.com", "CBM", "Carlos Silva");

        OrgaoResponsavel ibama = new OrgaoResponsavel(-47.8822, -15.7942, 500.0, "0800-111-222",
                "contato@ibama.gov.br", "DF", "Federal", "IBAMA", "Instituto Brasileiro do Meio Ambiente", 1);

        OrgaoResponsavel defCivil = new OrgaoResponsavel(-46.6333, -23.5505, 150.0, "199",
                "defesa@sp.gov.br", "SP", "Estadual", "DEFESA CIVIL", "Defesa Civil do Estado", 2);

        Brigada brigadaAlfa = new Brigada(1, "Brigada Alfa", 15, -15.8000, -47.9000, "IBAMA");

        Area parqueNacional = new Area(1, "Parque Nacional", "DF", "Brasília", "Cerrado",
                -15.7000, -47.8000, 420.5);

        JOptionPane.showMessageDialog(null, "Bem-vindo ao Sistema PYSAT - Pressione OK para iniciar as leituras.");

        // 2. Entradas para Foco Suspeito via JOptionPane
        double tempSuspeito = real("Análise de Foco Suspeito\nDigite a temperatura detectada (em °C):");
        double umidade = real("Digite a umidade do ar (%):");
        double ndvi = real("Digite o índice NDVI (0.0 a 1.0):");

        FocoSuspeito suspeito = new FocoSuspeito(101, -15.7200, -47.8100, tempSuspeito,
                "29/05/2026 14:00:00", umidade, ndvi);

        suspeito.calcularSeveridade();

        int respUrgencia = JOptionPane.showConfirmDialog(null, "Deseja forçar estado de URGÊNCIA MANUAL neste foco suspeito?", "Revisão", JOptionPane.YES_NO_OPTION);
        boolean urgenciaManual = (respUrgencia == JOptionPane.YES_OPTION);
        suspeito.calcularSeveridade(urgenciaManual);

        // 3. Entradas para Foco Confirmado via JOptionPane
        double tempConfirmado = real("Registro de Incêndio Real\nDigite a temperatura do fogo confirmado (em °C):");
        String operador = texto("Digite o nome do operador responsável:");

        FocoConfirmado confirmado = new FocoConfirmado(102, -15.7500, -47.8500, tempConfirmado,
                "29/05/2026 14:30:00", operador, "29/05/2026 14:35:00");

        parqueNacional.adicionarFoco(confirmado);

        // 4. Acionamento de Protocolos
        JOptionPane.showMessageDialog(null, "Sistema processando os dados...\nAcompanhe o relatório dinâmico no Console (System.out).");

        // --- INÍCIO DA SIMULAÇÃO DE TERMINAL NO CONSOLE ---

        System.out.println("[SISTEMA] Iniciando varredura e cálculo de rotas...");
        esperar(1500); // Pausa de 1.5 segundos

        coordenador.acionarProtocoloCascata(confirmado, ibama, defCivil);
        esperar(2000); // Pausa de 2 segundos para o professor ler os alertas

        System.out.println("\n[SISTEMA] Localizando brigadas disponíveis...");
        esperar(1500);
        coordenador.coordenarBrigadas(confirmado, "OCORRENCIA-102", brigadaAlfa);
        esperar(2000);

        // 5. Saídas dinâmicas (Aparecendo aos poucos)
        System.out.println("\n=================================================");
        System.out.println("              GERANDO RELATÓRIO FINAL...");
        System.out.println("=================================================");
        esperar(1500);

        System.out.println(suspeito);
        esperar(1500);

        System.out.println(confirmado);
        esperar(1500);

        System.out.println(parqueNacional);
        esperar(1500);

        System.out.println("\n[Status das Brigadas]");
        System.out.println(brigadaAlfa.getNome() + " -> " + brigadaAlfa.getStatus() +
                " | Ocorrência: " + brigadaAlfa.getOcorrenciaAtiva());
        esperar(1500);

        System.out.println("\n[Log de Atividades do Coordenador]");
        coordenador.exibirLogOperacoes();

        esperar(1000);
        System.out.println("\n[SISTEMA] Operação finalizada com sucesso.");
    }
}