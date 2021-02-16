package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	// na hora da criação da partida, cria o tabuleiro 8/8 e chama o iniciarPartida
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}

	public PecaXadrez[][] getPecas() { // retornar uma matrix de peça de xadrez
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

		// retorna a matriz de peças da partida de xadrez
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturarPeca = fazerMovimento(origem, destino);
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezExcecao("Voce nao pode se colocar em check");
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (PecaXadrez) capturarPeca;
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem); // remover peça na posicao de origem
		Peca pecaCapturada = tabuleiro.removerPeca(destino); // remove a possivel peça na posicao de destino
		tabuleiro.lugarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezExcecao("Nao existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peca escolhida nao eh sua");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimento()) {
			throw new XadrezExcecao("Nao existe movimentos possiveis para peca escolhida");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) { // se a peça de origem a posicao de destino ñ é um movimento possivel entao n pode mexer pra la
			throw new XadrezExcecao("A peca escolhida nao pode se mover para posicao de destino");
		}
	}
	
	private void proximoTurno() {
		turno++;
		// se jogador atual for branco ele troca pra preto caso contraria ele fica ou troca pra branco
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == cor.BRANCO) ? cor.PRETO : cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
 	}
	
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}				
		}
		return false;
	}

	// colocando peças passando a posição das coordenadas do xadrez
	private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	// iniciando a partida de xadrez, colocando as peças no tabuleiro
	private void iniciarPartida() {
		lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
