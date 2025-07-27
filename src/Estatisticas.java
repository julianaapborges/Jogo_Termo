import java.io.*;

public class Estatisticas {
    private int partidas;
    private int vitorias;
    private int derrotas;

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
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            partidas = Integer.parseInt(reader.readLine());
            vitorias = Integer.parseInt(reader.readLine());
            derrotas = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            partidas = 0;
            vitorias = 0;
            derrotas = 0;
        }
    }

    private void salvar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(partidas + "\n");
            writer.write(vitorias + "\n");
            writer.write(derrotas + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarVitoria() {
        partidas++;
        vitorias++;
        salvar();
    }

    public void registrarDerrota() {
        partidas++;
        derrotas++;
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
}



