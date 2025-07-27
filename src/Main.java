import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Main <caminho_do_arquivo_palavras>");
            System.exit(1);
        }

        String caminhoArquivo = args[0];
        SwingUtilities.invokeLater(() -> new TermoGUI(caminhoArquivo));
    }
}

