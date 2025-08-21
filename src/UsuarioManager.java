import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;

public class UsuarioManager {
    private Map<String, String> usuarios = new HashMap<>();
    private final String ARQUIVO_USUARIOS = "usuarios.txt";

    public UsuarioManager() {
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        File file = new File(ARQUIVO_USUARIOS);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    usuarios.put(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public void salvarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Map.Entry<String, String> entry : usuarios.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loginOuCadastrar(JFrame parent) {
        final String[] usuarioRetornado = {null};
    
        // Criar JDialog personalizado
        JDialog dialog = new JDialog(parent, "Login ou Cadastro", true);
        dialog.setSize(400, 500);
        dialog.setMinimumSize(new Dimension(300, 400));
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        // Quando o usuário fecha no X
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                usuarioRetornado[0] = null;
            }
        });
    
        // Mensagem superior
        JTextArea mensagem = new JTextArea("Bem-vindo(a)! Faça seu cadastro ou entre com seu login.");
        mensagem.setWrapStyleWord(true);
        mensagem.setLineWrap(true);
        mensagem.setEditable(false);
        mensagem.setFocusable(false);
        mensagem.setOpaque(false);
        mensagem.setFont(new Font("Arial", Font.PLAIN, 14));
    
        // Campos
        JTextField campoUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();
    
        JPanel painelCampos = new JPanel(new GridLayout(4, 4, 6, 6));
        painelCampos.add(new JLabel("Usuário:"));
        painelCampos.add(campoUsuario);
        painelCampos.add(new JLabel("Senha:"));
        painelCampos.add(campoSenha);
    
        // Botões
        JButton btnConfirmar = new JButton("Entrar/Cadastrar");
        // Faz o botão confirmar ser ativado ao pressionar Enter
        dialog.getRootPane().setDefaultButton(btnConfirmar);
        JButton btnCancelar = new JButton("Sair");
    
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnConfirmar);
    
        // Layout completo
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(mensagem, BorderLayout.NORTH);
        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
    
        dialog.setContentPane(painelPrincipal);
    
        Runnable acaoConfirmar = () -> {
            String usuario = campoUsuario.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (usuarios.containsKey(usuario)) {
                if (usuarios.get(usuario).equals(senha)) {
                    usuarioRetornado[0] = usuario;
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                usuarios.put(usuario, senha);
                salvarUsuarios();
                JOptionPane.showMessageDialog(dialog, "Usuário cadastrado com sucesso! Por favor, faça login.");
                campoUsuario.setText("");
                campoSenha.setText("");
            }
        };

        btnConfirmar.addActionListener(e -> acaoConfirmar.run());
        campoUsuario.addActionListener(e -> acaoConfirmar.run());
        campoSenha.addActionListener(e -> acaoConfirmar.run());
    
        // Ação botão sair
        btnCancelar.addActionListener(e -> {
            usuarioRetornado[0] = null;
            dialog.dispose();
        });
    
        dialog.setVisible(true); // exibe a janela
    
        return usuarioRetornado[0]; // retorna o login feito ou null se cancelado
    }
    
}




