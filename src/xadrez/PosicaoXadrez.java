package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {

	private char coluna;
	private int linha;

	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro instanciando PosicaoXadrez. Valores de a1 ate h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	// metodo convertendo a posicao do tabuleiro pro xadrez
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	// metodo retornando a forma inversa do toPosicao
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez ((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha; // imprimindo a posicao
	}



}