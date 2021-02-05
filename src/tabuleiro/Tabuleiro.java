package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro criando tabuleiro: � necess�rio que haja pelo menos 1 linha e 1 coluna");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	// metodo para retornar a pe�a dada uma linha e uma coluna
	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) { // se essa posi��o n existe
			throw new TabuleiroExcecao("Essa posi��o n�o existe no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	//sobrecarga do metodo
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("Essa posi��o n�o existe no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	// colocando a pe�a que veio como argumento no lugar dela
	// pegando a matrix pecas na posicao dada e atribuir a ela a peca informada
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao)) {
			throw new TabuleiroExcecao("J� tem uma pe�a nessa posi��o: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao; // tirando o null da pe�a
	}
	
	// metodo para ver se existe essa posicao passando linha e coluna
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	// metodo para ver se existe essa posicao passando posicao
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna()); // aproveitando o metodo de cima
		
	}
	
	// metodo testando se tem uma pe�a nessa posicao
	public boolean temUmaPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("Essa posi��o n�o existe no tabuleiro!");
		}
		return peca(posicao) != null;
	}

}
