import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PalavraUtils {
    private final List<String> palavras;
    private final Random random = new Random();

    public PalavraUtils(String caminhoArquivo) throws IOException {
        palavras = Files.readAllLines(Paths.get(caminhoArquivo)).stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(p -> p.length() == 5)
                .distinct()
                .collect(Collectors.toList());

        if (palavras.isEmpty()) {
            throw new IOException("Nenhuma palavra válida com 5 letras foi encontrada.");
        }

        Collections.shuffle(palavras);
    }

    public String sortearPalavra() {
        return palavras.get(random.nextInt(palavras.size())).toUpperCase();
    }

    public boolean contem(String palavra) {
        return palavra != null && palavras.contains(palavra.toLowerCase());
    }
}


