package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	// na hora da cria��o da partida, cria o tabuleiro 8/8 e chama o iniciarPartida
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}
	
	public PecaXadrez[][] getPecas(){ // retornar uma matrix de pe�a de xadrez
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		//retorna a matriz de pe�as da partida de xadrez
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	// colocando pe�as passando a posi��o das coordenadas do xadrez
	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	// iniciando a partida de xadrez, colocando as pe�as no tabuleiro
	private void iniciarPartida() {
		lugarNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
	}

}
