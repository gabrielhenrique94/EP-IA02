import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AlgoritmoGenetico {
	
	/**
	 * Taxa utilizada para verificar se deve ser realizado mutacao
	 */
	private static final double TAXA_MUTACAO = 0.1; 
	
	
	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma populacao.
	 * @param cromossomos Populacao que sera analisada.
	 * @param mapaCidades Lugar que se encontra cada cidade que compoe as distancias nos cromossomos da populacao em um mapa.
	 */
	public static void algoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades) {
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
	 * Metodo order crossover (OX)
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	public ArrayList<Cromossomo> crossoverOX (Cromossomo pai1, Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {
		/* Exemplo:
		 * Parent 1: 8 4 7 |3 6 2 5 1| 9 0
		   Parent 2: 0 |1 2 3| 4 |5 6| 7 8 9
				
		   Child 1:  0 4 7 3 6 2 5 1 8 9
		 */
		ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
		ArrayList<Integer> genotipo1 = pai1.getGenotipo();
		ArrayList<Integer> genotipo2 = pai2.getGenotipo();
		
		int genes = genotipo1.size();
		
		int[] genotipoFilho1 = new int[genes];
		int[] genotipoFilho2 = new int[genes];
		
		/*Posicao inicial e final*/
		int p0 = -1;
		int pf = -1;
		
		while (p0 == pf) {
			p0 = intAleatorio(0, genes-1);;
			pf = intAleatorio(0, genes-1);
		}
		
		/* Copiando dados fixos*/
		
		int auxFixo = pf;
		
		// Mapa de cidades que ja estao nos filhos
		Map<Integer, Boolean> mapaCidadesFilho1 = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> mapaCidadesFilho2 = new HashMap<Integer, Boolean>();
		
		genotipoFilho1[p0] = genotipo1.get(p0);
		genotipoFilho2[p0] = genotipo2.get(p0);
		
		mapaCidadesFilho1.put(genotipo1.get(p0), true);
		mapaCidadesFilho2.put(genotipo2.get(p0), true);
		
		while (auxFixo != p0) {
			
			genotipoFilho1[auxFixo] = genotipo1.get(auxFixo);
			genotipoFilho2[auxFixo] = genotipo2.get(auxFixo);
			
			mapaCidadesFilho1.put(genotipo1.get(auxFixo), true);
			mapaCidadesFilho2.put(genotipo2.get(auxFixo), true);
			
			if (auxFixo-1 < 0) {
				auxFixo = genes-1;
			} else {
				auxFixo--;
			}
		}
		
		/*Completa Filho 1*/

		boolean filho1completo = false;
		int posicaoPai2 = pf + 1;
		int posicaoFilho1 = pf + 1;
		while (!filho1completo) {
			if (posicaoPai2 == genes) {
				posicaoPai2 = 0;
			}
			
			if (posicaoFilho1 == genes) {
				posicaoFilho1 = 0;
			}
			
			if (posicaoPai2 == pf) {
				filho1completo = true;
			}
			
			int cidadeAux = genotipo2.get(posicaoPai2);
			
			if (!mapaCidadesFilho1.get(cidadeAux)) {
				genotipoFilho1[posicaoFilho1] = cidadeAux;
				mapaCidadesFilho1.put(cidadeAux, true);
				posicaoFilho1++;
			}
			
			posicaoPai2++;
		}
		
		/*Completa Filho 2*/

		boolean filho2completo = false;
		int posicaoPai1 = pf + 1;
		int posicaoFilho2 = pf + 1;
		while (!filho2completo) {
			if (posicaoPai1 == genes) {
				posicaoPai1 = 0;
			}
			
			if (posicaoFilho2 == genes) {
				posicaoFilho2 = 0;
			}
			
			if (posicaoPai1 == pf) {
				filho2completo = true;
			}
			
			int cidadeAux = genotipo2.get(posicaoPai1);
			
			if (!mapaCidadesFilho1.get(cidadeAux)) {
				genotipoFilho1[posicaoFilho2] = cidadeAux;
				mapaCidadesFilho1.put(cidadeAux, true);
				posicaoFilho2++;
			}
			
			posicaoPai1++;
		}
		
		/*Gerando o cromossomo dos filhos*/
		ArrayList<Integer> seqFilho1 = new ArrayList<Integer>();
		ArrayList<Integer> seqFilho2 = new ArrayList<Integer>();
		
		for (int i = 0; i < genotipoFilho1.length; i++) {
			seqFilho1.add(genotipoFilho1[i]);
			seqFilho2.add(genotipoFilho2[i]);
		}
		
		Cromossomo filho1 = new Cromossomo(seqFilho1, ProcessaCidades.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2, ProcessaCidades.calculaPercurso(mapaCidades, seqFilho2));
		
		filhos.add(filho1);
		filhos.add(filho2);
		
		return filhos;
		
		
	}
	
	/**
	 * Metodo de mutacao de genes, utilizando mutacao inversiva.
	 * @param filho - cromossomo que sofrera mutacao
	 * @param mapaCidades
	 * @return
	 */
	public Cromossomo mutacaoInversiva(Cromossomo filho, HashMap<Integer, double[]> mapaCidades) {
		
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
		filho.setFi(ProcessaCidades.calculaPercurso(mapaCidades, seqFilho));
		
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
