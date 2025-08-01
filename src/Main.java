import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Main <caminho_do_arquivo_palavras>");
            System.exit(1);
        }

        String caminhoArquivo = args[0];
        UsuarioManager usuarioManager = new UsuarioManager();

        // Loop até usuário logar com sucesso
        String usuario = null;
        while (usuario == null) {
            usuario = usuarioManager.loginOuCadastrar(null);
        }

        String caminhoEstatisticas = "estatisticas_" + usuario + ".dat";

        final String userFinal = usuario;
        final String statsFinal = caminhoEstatisticas;

        SwingUtilities.invokeLater(() -> {
            new TermoGUI(caminhoArquivo, statsFinal, userFinal);
        });
    }
}


