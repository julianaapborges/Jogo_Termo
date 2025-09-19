# 🎮 Jogo Termo

Implementação do famoso jogo de palavras "Termo", desenvolvida em grupo para a disciplina de Programação Orientada a Objetos (POO) na UFOP.

## ✨ Sobre o projeto

- Interface gráfica intuitiva feita com Java Swing
- Verificação de palavras em tempo real
- Sistema de dicas por cores (estilo Wordle)
- Princípios de POO aplicados: encapsulamento, herança e polimorfismo

## 🛠️ Tecnologias

- Java
- Java Swing
- Git

## 🚀 Como rodar

1. Clone o repositório:
   ```bash
   git clone https://github.com/julianaapborges/Jogo_Termo.git
   cd Jogo_Termo
   ```
2. Compile os arquivos Java (crie a pasta bin se necessário):
   ```bash
   javac -d bin src/*.java
   ```
3. Gere o executável JAR:
   ```bash
   jar cfe termo.jar Main -C bin .
   ```
4. Execute o jogo (usando o arquivo de palavras):
   ```bash
   java -jar termo.jar palavras.txt
   ```

---

Feito com 💙 para a disciplina de POO na UFOP.
