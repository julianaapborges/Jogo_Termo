import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Classe utilitária para manipulação de palavras
public class PalavraUtils {
    private List<String> palavras;
    private Random random = new Random();

    // Construtor que carrega as palavras de um arquivo
    public PalavraUtils(String caminhoArquivo) {
        try {
            palavras = Files.lines(Paths.get(caminhoArquivo))
                .map(this::removerAcentos)
                .map(String::toLowerCase)
                .filter(p -> p.length() == 5)
                .distinct()
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Erro ao carregar palavras: " + e.getMessage());
            palavras = List.of();
        }
    }

    // Método para sortear uma palavra aleatória
    public String sortearPalavra() {
        return palavras.get(random.nextInt(palavras.size())).toUpperCase();
    }

    // Verifica se uma palavra está contida na lista
    public boolean contem(String palavra) {
        return palavra != null && palavras.contains(removerAcentos(palavra.toLowerCase()));
    }

    // Remove acentos de um texto
    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace("ç", "c")
                .replace("Ç", "C");
    }
}