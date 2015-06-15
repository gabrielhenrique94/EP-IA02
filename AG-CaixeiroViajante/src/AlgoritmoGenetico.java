import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AlgoritmoGenetico {
	
	/**
	 * Taxa utilizada para verificar se deve ser realizado mutacao
	 */
	private static final double TAXA_MUTACAO = 0.04; // Recomendado < 5% (valor para variar nos testes)
	
	
	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma populacao.
	 * @param cromossomos Populacao que sera analisada.
	 * @param posicaoCidades Lugar que se encontra cada cidade que compoe as distancias nos cromossomos da populacao em um mapa.
	 */
	public static void algoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, int[]> posicaoCidades) {
		/*
		 * t <- 0
		 * inicializar S(T)
		 * avaliar S(t)
		 * enquanto o criterio de parada não satisfeito faça
		 * 		t <- t + 1
		 * 		selecionar S(t) a partir de S(t -1)
		 * 		aplicar crossover sobre S(t)
		 * 		aplicar mutação sobre S(t) se TAXA_MUTAÇAO VALIDAR
		 * 		avaliar S(t)
		 * fim enquanto
		 */
		
	}
	
	/**
	 * Metodo de order crossover (OX)
	 * @param pai1
	 * @param pai2
	 * @param posicaoCidades
	 * @return
	 */
	public Cromossomo crossoverOX (Cromossomo pai1, Cromossomo pai2, HashMap<Integer, int[]> posicaoCidades) {
		/* Exemplo:
		 * Parent 1: 8 4 7 |3 6 2 5 1| 9 0
		   Parent 2: 0 |1 2 3| 4 |5 6| 7 8 9
				
		   Child 1:  0 4 7 3 6 2 5 1 8 9
		 */
		ArrayList<Integer> seqPai1 = pai1.getGenotipo();
		ArrayList<Integer> seqPai2 = pai2.getGenotipo();
		
		ArrayList<Integer> seqFilho = new ArrayList<Integer>();
		
		int genes = seqPai1.size();
		
		Map<Integer, Boolean> mascaraPai1 = new HashMap<Integer, Boolean>();
		
		/* Inicializa Mascara Pai 1 */
		for (int i = 0; i < genes; i++) {
			mascaraPai1.put(i, false);
		}
		
		int aleatorio = intAleatorio(0, genes-1);
		
		int genesTrocados = genes/2;
		
		while (genesTrocados > 0) {
			if (aleatorio <= (genes-1) && !mascaraPai1.get(aleatorio)) {
				mascaraPai1.put(aleatorio, true);
				aleatorio++;
				genesTrocados--;
			} else if (aleatorio > (genes-1)) {
				aleatorio = 0;
			}
		}
		
		/* Criando filho*/
		
		ArrayList<Integer> novaSeqPai2 = new ArrayList<Integer>();
		for (int i = 0; i < seqPai2.size(); i++) {
			int cidade2 = seqPai2.get(i);
			if (!mascaraPai1.get(cidade2)) {
				novaSeqPai2.add(cidade2);
			}
		}
		
		int contadorPai2 = 0;
		for (Integer key : mascaraPai1.keySet()) {
		    if(mascaraPai1.get(key)) {
		    	seqFilho.add(seqPai1.get(key));
		    } else {
		    	seqFilho.add(novaSeqPai2.get(contadorPai2));
		    	contadorPai2++;
		    }
		}
		
		Cromossomo filho = new Cromossomo(seqFilho, ProcessaCidades.calculaPercurso(posicaoCidades, seqFilho));
		
		return filho;
		
		
	}
	
	/**
	 * Metodo de mutacao de genes, utilizando mutacao inversiva.
	 * @param filho - cromossomo que sofrera mutacao
	 * @param posicaoCidades
	 * @return
	 */
	public Cromossomo mutacaoInversiva(Cromossomo filho, HashMap<Integer, int[]> posicaoCidades) {
		
		ArrayList<Integer> seqFilho = filho.getGenotipo();
		int aleatorio1 = -1;
		int aleatorio2 = -1;
		
		while (aleatorio1 == aleatorio2 ) {
			aleatorio1 = intAleatorio(0, seqFilho.size()-1);
			aleatorio2 = intAleatorio(0, seqFilho.size()-1);
		}
		
		/*Troca as cidades de posicao*/
		int cidade1 = seqFilho.get(aleatorio1);
		
		seqFilho.add(aleatorio1, seqFilho.get(aleatorio2));
		seqFilho.add(aleatorio2, cidade1);
		
		filho.setGenotipo(seqFilho);
		filho.setFi(ProcessaCidades.calculaPercurso(posicaoCidades, seqFilho));
		
		return filho;
	}
	
	
	/**
	 * Gera um valor inteiro no intervalo [min;max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static int intAleatorio(int min, int max) {
	    Random random = new Random();

	    int randomNum = random.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}
