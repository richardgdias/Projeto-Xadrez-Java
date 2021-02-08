package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro criando tabuleiro: Eh necessario que haja pelo menos 1 linha e 1 coluna!");
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
	
	// metodo para retornar a peça dada uma linha e uma coluna
	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) { // se essa posição n existe
			throw new TabuleiroExcecao("Essa posicao nao existe no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	//sobrecarga do metodo
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("Essa posicao nao existe no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	// colocando a peça que veio como argumento no lugar dela
	// pegando a matrix pecas na posicao dada e atribuir a ela a peca informada
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao)) {
			throw new TabuleiroExcecao("Ja tem uma peca nessa posiçao: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao; // tirando o null da peça
	}
	
	public Peca removerPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) { // se essa posicao nao existe, lance uma excecao
			throw new TabuleiroExcecao("Essa posicao nao existe no tabuleiro!");
		}
		if (peca(posicao) == null) {
			return null; // se for vdd n tem nenhuma peca nessa posicao
		}
		Peca aux = peca(posicao);
		aux.posicao = null; // peça retirada no tabuleiro recebendo null
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	// metodo para ver se existe essa posicao passando linha e coluna
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	// metodo para ver se existe essa posicao passando posicao
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna()); // aproveitando o metodo de cima
		
	}
	
	// metodo testando se tem uma peça nessa posicao
	public boolean temUmaPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroExcecao("Essa posicao nao existe no tabuleiro!");
		}
		return peca(posicao) != null;
	}

}
