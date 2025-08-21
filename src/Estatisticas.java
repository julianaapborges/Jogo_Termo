import java.io.*;

public class Estatisticas {
    private int partidas;
    private int vitorias;
    private int derrotas;
    private int sequenciaAtual;
    private int melhorSequencia;

    private final File arquivo;

    public Estatisticas(String caminhoArquivo) {
        arquivo = new File(caminhoArquivo);
        carregar();
    }

    private void carregar() {
        partidas = 0;
        vitorias = 0;
        derrotas = 0;
        sequenciaAtual = 0;
        melhorSequencia = 0;
        if (!arquivo.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    if (linha.startsWith("Partidas:")) {
                        partidas = Integer.parseInt(linha.split(": ")[1]);
                    } else if (linha.startsWith("Vitórias:")) {
                        vitorias = Integer.parseInt(linha.split(": ")[1]);
                    } else if (linha.startsWith("Derrotas:")) {
                        derrotas = Integer.parseInt(linha.split(": ")[1]);
                    } else if (linha.startsWith("Sequência Atual:")) {
                        sequenciaAtual = Integer.parseInt(linha.split(": ")[1]);
                    } else if (linha.startsWith("Melhor Sequência:")) {
                        melhorSequencia = Integer.parseInt(linha.split(": ")[1]);
                    }
                } catch (Exception ex) {
                    // Se der erro em uma linha, ignora só aquela linha
                }
            }
        } catch (Exception e) {
            // Se der erro geral, mantém os valores já lidos (ou zero)
        }
    }

    private void salvar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("Partidas: " + partidas + "\n");
            writer.write("Vitórias: " + vitorias + "\n");
            writer.write("Derrotas: " + derrotas + "\n");
            writer.write("Sequência Atual: " + sequenciaAtual + "\n");
            writer.write("Melhor Sequência: " + melhorSequencia + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarVitoria() {
        partidas++;
        vitorias++;
        sequenciaAtual++;
        if (sequenciaAtual > melhorSequencia) {
            melhorSequencia = sequenciaAtual;
        }
        salvar();
    }

    public void registrarDerrota() {
        partidas++;
        derrotas++;
        sequenciaAtual = 0; // zera sequência atual em derrota
        salvar();
    }

    public int getPartidas() {
        return partidas;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getSequenciaAtual() {
        return sequenciaAtual;
    }

    public int getMelhorSequencia() {
        return melhorSequencia;
    }

}




