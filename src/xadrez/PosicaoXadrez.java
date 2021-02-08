package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {
<<<<<<< HEAD

	private char coluna;
	private int linha;

=======
	
	private char coluna;
	private int linha;
	
>>>>>>> 4589c2fd38ff326c05dfebf4cf6c435d11c8013d
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro instanciando PosicaoXadrez. Valores de a1 até h8");
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
<<<<<<< HEAD

=======
	
>>>>>>> 4589c2fd38ff326c05dfebf4cf6c435d11c8013d
	// metodo retornando a forma inversa do toPosicao
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez ((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
<<<<<<< HEAD

=======
	
>>>>>>> 4589c2fd38ff326c05dfebf4cf6c435d11c8013d
	@Override
	public String toString() {
		return "" + coluna + linha; // imprimindo a posicao
	}
<<<<<<< HEAD



}
=======
	
	

}
>>>>>>> 4589c2fd38ff326c05dfebf4cf6c435d11c8013d
