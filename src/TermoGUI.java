import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TermoGUI extends JFrame {
    private TermoGame jogo;
    private Estatisticas estatisticas;
    private PalavraUtils palavraUtils;
    private UsuarioManager usuarioManager;

    private JPanel painelTentativas;
    private JTextField campoEntrada;
    private JLabel labelTentativasRestantes;

    private String usuarioAtual = null;

    // Construtor antigo, mantém compatibilidade usando valores padrão
    public TermoGUI(String caminhoArquivoPalavras) {
        this(caminhoArquivoPalavras, "estatisticas.dat", null);
    }

    // Novo construtor para quando houver usuário e arquivo de estatísticas personalizados
    public TermoGUI(String caminhoArquivoPalavras, String caminhoArquivoEstatisticas, String usuario) {
        try {
            palavraUtils = new PalavraUtils(caminhoArquivoPalavras);
            usuarioManager = new UsuarioManager();

            usuarioAtual = usuario;
            estatisticas = new Estatisticas(caminhoArquivoEstatisticas);

            configurarUI();
            iniciarNovaPartida();

            if (usuarioAtual != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuarioAtual + "!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar o jogo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void configurarUI() {
        setTitle("TERMO - Jogo de Palavras");
        setSize(700, 800);
        setMinimumSize(new Dimension(500, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Painel tentativas (exibe as tentativas coloridas)
        painelTentativas = new JPanel();
        painelTentativas.setLayout(new GridLayout(6, 1, 5, 5));
        painelTentativas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(painelTentativas, BorderLayout.CENTER);

        // Entrada da palavra
        JPanel painelEntrada = new JPanel(new BorderLayout());
        campoEntrada = new JTextField();
        campoEntrada.setFont(new Font("Arial", Font.PLAIN, 24));
        campoEntrada.addActionListener(this::processarTentativa); // Permite acionar o botão Tentar com Enter
        painelEntrada.add(new JLabel("Digite uma palavra de 5 letras:"), BorderLayout.NORTH);
        painelEntrada.add(campoEntrada, BorderLayout.CENTER);

        // Status tentativas restantes
        labelTentativasRestantes = new JLabel("Tentativas restantes: 6");
        labelTentativasRestantes.setFont(new Font("Arial", Font.BOLD, 16));
        painelEntrada.add(labelTentativasRestantes, BorderLayout.SOUTH);

        add(painelEntrada, BorderLayout.NORTH);

        // Botões inferiores
        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton botaoTentar = new JButton("Tentar");
        botaoTentar.setBackground(Color.GREEN.darker());
        botaoTentar.setForeground(Color.WHITE);
        botaoTentar.addActionListener(this::processarTentativa);
        painelBotoes.add(botaoTentar);

        JButton botaoNovaPartida = new JButton("Nova Partida");
        botaoNovaPartida.addActionListener(e -> {
            iniciarNovaPartida();
            atualizarInterface();
        });
        painelBotoes.add(botaoNovaPartida);

        JButton botaoEstatisticas = new JButton("Ver Estatísticas");
        botaoEstatisticas.setBackground(Color.BLUE.darker());
        botaoEstatisticas.setForeground(Color.WHITE);
        botaoEstatisticas.addActionListener(e -> mostrarEstatisticas());
        painelBotoes.add(botaoEstatisticas);

        JButton botaoLogin = new JButton("Login / Usuário");
        botaoLogin.setBackground(Color.ORANGE.darker());
        botaoLogin.setForeground(Color.WHITE);
        botaoLogin.addActionListener(e -> fazerLogin());
        painelBotoes.add(botaoLogin);

        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> {
            mostrarEstatisticas();
            System.exit(0);
        });
        painelBotoes.add(botaoSair);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void iniciarNovaPartida() {
        String palavraSecreta = palavraUtils.sortearPalavra();
        jogo = new TermoGame(palavraSecreta, estatisticas);
        if (painelTentativas != null) {
            painelTentativas.removeAll();
            painelTentativas.revalidate();
            painelTentativas.repaint();
        }

        if (campoEntrada != null) {
            campoEntrada.setEnabled(true);
            campoEntrada.setText("");
            campoEntrada.requestFocusInWindow();
        }
    }

    private void processarTentativa(ActionEvent e) {
        String tentativa = campoEntrada.getText().trim().toUpperCase();

        if (tentativa.length() != 5) {
            JOptionPane.showMessageDialog(this, "A palavra deve ter 5 letras!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!palavraUtils.contem(tentativa)) {
            JOptionPane.showMessageDialog(this, "Palavra não está na lista!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!jogo.tentativa(tentativa)) {
            JOptionPane.showMessageDialog(this, "Tentativa repetida", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        adicionarTentativaColorida(tentativa);

        if (jogo.venceu()) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você acertou a palavra!");
            campoEntrada.setEnabled(false);
        } else if (jogo.terminou()) {
            JOptionPane.showMessageDialog(this, "Fim de jogo! A palavra era: " + jogo.getPalavraSecreta());
            campoEntrada.setEnabled(false);
        }
        

        atualizarInterface();
        campoEntrada.setText("");
    }

    private void adicionarTentativaColorida(String tentativa) {
        JPanel linha = new JPanel(new GridLayout(1, 5, 5, 5));
        linha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        char[] resultado = calcularResultado(tentativa, jogo.getPalavraSecreta());

        for (int i = 0; i < 5; i++) {
            JLabel letraLabel = new JLabel(String.valueOf(tentativa.charAt(i)), SwingConstants.CENTER);
            letraLabel.setOpaque(true);
            letraLabel.setFont(new Font("Arial", Font.BOLD, 32));
            letraLabel.setPreferredSize(new Dimension(50, 50));
            letraLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

            switch (resultado[i]) {
                case 'V' -> letraLabel.setBackground(Color.GREEN);
                case 'A' -> letraLabel.setBackground(Color.YELLOW);
                default -> letraLabel.setBackground(Color.LIGHT_GRAY);
            }

            linha.add(letraLabel);
        }

        painelTentativas.add(linha);
        painelTentativas.revalidate();
        painelTentativas.repaint();
    }

    private char[] calcularResultado(String tentativa, String palavraSecreta) {
        char[] resultado = new char[5];
        boolean[] letraUsada = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (tentativa.charAt(i) == palavraSecreta.charAt(i)) {
                resultado[i] = 'V';
                letraUsada[i] = true;
            } else {
                resultado[i] = '-';
            }
        }

        for (int i = 0; i < 5; i++) {
            if (resultado[i] == '-') {
                char c = tentativa.charAt(i);
                for (int j = 0; j < 5; j++) {
                    if (!letraUsada[j] && palavraSecreta.charAt(j) == c) {
                        resultado[i] = 'A';
                        letraUsada[j] = true;
                        break;
                    }
                }
            }
        }

        return resultado;
    }

    private void atualizarInterface() {
        labelTentativasRestantes.setText("Tentativas restantes: " + jogo.getTentativasRestantes());
    }

    private void mostrarEstatisticas() {
    String nomeExibir = (usuarioAtual != null) ? usuarioAtual : "Convidado";


    String mensagem = String.format(
        "Jogador: %s\nPartidas: %d\nVitórias: %d\nDerrotas: %d\nSequência Atual: %d\nMelhor Sequência: %d",
        nomeExibir,
        estatisticas.getPartidas(),
        estatisticas.getVitorias(),
        estatisticas.getDerrotas(),
        estatisticas.getSequenciaAtual(),
        estatisticas.getMelhorSequencia());

    JOptionPane.showMessageDialog(this, mensagem, "Estatísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void fazerLogin() {
        String usuario = usuarioManager.loginOuCadastrar(this);
        if (usuario != null) {
            usuarioAtual = usuario;
            String caminhoEstatisticas = "estatisticas_" + usuarioAtual + ".dat";
            estatisticas = new Estatisticas(caminhoEstatisticas);
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuarioAtual + "!");
            atualizarInterface();
        }
    }
}











