# TP02-POO
Trabalho Prático 2 : Programação Orientada a Objetos 

Consiste em desenvolver o jogo TERMO, conhecido como também como Wordle em Português,
utilizando a linguagem Java e a biblioteca Swing para interface gráfica. Neste jogo, o
objetivo é descobrir a palavra secreta de 5 letras, considerando as dicas: ao inserir uma
letra, o jogador irá ser informado se ela está na posição correta (indicado pela cor verde), na
posição errada (cor amarela) ou se ela não compõem a palavra (cor cinza).

## Como compilar e executar

```bash
javac -d bin src/*.java

java -cp bin Main palavras.txt
