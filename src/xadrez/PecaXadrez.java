package xadrez;

import tabuleiro.Peca;
import tabuleiro.Tabuleiro;

public class PecaXadrez extends Peca{
	
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro); // passando a chamada pro construtor da super classe
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	

}
