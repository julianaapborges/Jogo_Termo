// HUGO AUGUSTO SILVA DE FARIA 
// JULIANA APARECIDA BORGES
// LOURRANE LINDSAY ALVES EVARISTO
// MATHEUS MOTA GOMES
// SAMARA PALOMA LOPES AUGUSTO RIBEIRO

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Classe principal do jogo
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Main <caminho_do_arquivo_palavras>");
            System.exit(1);
        }

        String caminhoArquivo = args[0];
        UsuarioManager usuarioManager = new UsuarioManager();

        // Loop até o usuário logar com sucesso ou decidir sair
        String usuario = null;
        while (usuario == null) {
            usuario = usuarioManager.loginOuCadastrar(null);

            if (usuario == null) {
                int opcao = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente sair?",
                    "Confirmar saída",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (opcao == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                // Caso contrário, o loop continua e o diálogo aparece novamente
            }
        }

        String caminhoEstatisticas = "estatisticas_" + usuario + ".dat";

        final String userFinal = usuario;
        final String statsFinal = caminhoEstatisticas;

        SwingUtilities.invokeLater(() -> {
            new TermoGUI(caminhoArquivo, statsFinal, userFinal);
        });
    }
}