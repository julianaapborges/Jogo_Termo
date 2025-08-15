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
        if (!arquivo.exists()) {
            partidas = 0;
            vitorias = 0;
            derrotas = 0;
            sequenciaAtual = 0;
            melhorSequencia = 0;
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            partidas = Integer.parseInt(reader.readLine().split(": ")[1]);
            vitorias = Integer.parseInt(reader.readLine().split(": ")[1]);
            derrotas = Integer.parseInt(reader.readLine().split(": ")[1]);
            String linha;
            // Se existir a 4ª linha, lê sequenciaAtual
            if ((linha = reader.readLine()) != null) {
                sequenciaAtual = Integer.parseInt(linha.split(": ")[1]);
                // Se existir a 5ª linha, lê melhorSequencia
                if ((linha = reader.readLine()) != null) {
                    melhorSequencia = Integer.parseInt(linha.split(": ")[1]);
                } else {
                    melhorSequencia = 0;
                }
            } else {
                sequenciaAtual = 0;
                melhorSequencia = 0;
            }
        } catch (Exception e) {
            partidas = 0;
            vitorias = 0;
            derrotas = 0;
            sequenciaAtual = 0;
            melhorSequencia = 0;
        }
    }

    private void salvar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("Partidas: " + partidas + "\n");
            writer.write("Vitórias: " + vitorias + "\n");
            writer.write("Derrotas: " + derrotas + "\n");
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




