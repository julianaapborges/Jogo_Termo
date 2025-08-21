import java.util.HashSet;
import java.util.Set;

// Classe responsável pela lógica do jogo
public class TermoGame {
    private final String palavraSecreta;
    private final Set<String> tentativas;
    private boolean venceu;
    private final int maxTentativas = 6;
    private final Estatisticas estatisticas;

    // Construtor
    public TermoGame(String palavraSecreta, Estatisticas estatisticas) {
        this.palavraSecreta = palavraSecreta.toUpperCase();
        this.tentativas = new HashSet<>();
        this.venceu = false;
        this.estatisticas = estatisticas;
    }

    // Tenta uma palavra. Retorna false se repetida ou jogo encerrado.
    public boolean tentativa(String palavra) {
        palavra = palavra.toUpperCase();
        if (tentativas.contains(palavra) || venceu || tentativas.size() >= maxTentativas)
            return false;

        tentativas.add(palavra);

        // Verifica se a palavra está correta
        if (removerAcentos(palavra).equals(removerAcentos(palavraSecreta))) {
            venceu = true;
            estatisticas.registrarVitoria();
        }
        // Verifica se o jogo terminou
        if (!venceu && tentativas.size() == maxTentativas) {
            estatisticas.registrarDerrota();
        }
        return true;
    }

    // Remove acentos de um texto
    private String removerAcentos(String texto) {
        return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace("Ç", "C").replace("ç", "c");
    }

    // Getters
    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public boolean venceu() {
        return venceu;
    }

    public boolean terminou() {
        return venceu || tentativas.size() >= maxTentativas;
    }

    public int getTentativasRestantes() {
        return maxTentativas - tentativas.size();
    }
}