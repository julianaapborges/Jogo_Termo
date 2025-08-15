import java.util.HashSet;
import java.util.Set;

public class TermoGame {
    private final String palavraSecreta;
    private final Set<String> tentativas;
    private boolean venceu;
    private final int maxTentativas = 6;
    private final Estatisticas estatisticas;

    public TermoGame(String palavraSecreta, Estatisticas estatisticas) {
        this.palavraSecreta = palavraSecreta.toUpperCase();
        this.tentativas = new HashSet<>();
        this.venceu = false;
        this.estatisticas = estatisticas;
    }

    /**
     * Tenta uma palavra. Retorna false se repetida ou jogo encerrado.
     */
    public boolean tentativa(String palavra) {
        palavra = palavra.toUpperCase();
        if (tentativas.contains(palavra) || venceu || tentativas.size() >= maxTentativas)
            return false;

        tentativas.add(palavra);

        if (palavra.equals(palavraSecreta)) {
            venceu = true;
            estatisticas.registrarVitoria();
        } 
        if (!venceu && tentativas.size() == maxTentativas) {
            estatisticas.registrarDerrota();
        }
        return true;
    }

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




