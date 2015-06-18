import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AlgoritmoGenetico {
	
	/**
	 * Taxa utilizada para verificar se deve ser realizado mutacao
	 */
	private static final double TAXA_MUTACAO = 0.1; 
	
	/**
	 * Armazena a populacao pura inicial
	 */
	ArrayList<Cromossomo> cromossomosIniciais = new ArrayList<Cromossomo>();


	/**
	 * Cromossomos nao selecionados durante um periodo de selecao
	 */
	ArrayList<Cromossomo> cromossomosNaoSelecionados = new ArrayList<Cromossomo>();
	
	/**
	 * Cromossomos selecionados para um periodo de selecao
	 */
	ArrayList<Cromossomo> cromossomosSelecionados = new ArrayList<Cromossomo>();
	
	/**
	 * Mapa das coordenadas de cada cidade 
	 */
	HashMap<Integer, double[]> mapaCidades = new HashMap<Integer, double[]>();
	
	public AlgoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades ) {
		this.cromossomosIniciais = cromossomos;
		this.mapaCidades = mapaCidades;
	}
	
	
	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma populacao.
	 * @param cromossomos Populacao que sera analisada.
	 * @param mapaCidades Lugar que se encontra cada cidade que compoe as distancias nos cromossomos da populacao em um mapa.
	 */
	void algoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades) {
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
		
		int t = 0;
		ArrayList<Cromossomo> s = selecionarSubpopulacao(cromossomos);
		
		//TESTE CROSSOVER OX
		System.out.println("Crossover OX");
		int n = cromossomos.size();
		int pai1 = -1;
		int pai2 = -1;
		
		while (pai1 == pai2) {
			pai1 = intAleatorio(0, n-1);;
			pai2 = intAleatorio(0, n-1);
		}
		
		Cromossomo cpai1 = cromossomos.get(pai1);
		Cromossomo cpai2 = cromossomos.get(pai2);
		
		System.out.print("Pai1: ");
		ArrayList<Integer> gen1 = cpai1.getGenotipo();
		for (int i = 0; i < gen1.size(); i++) {
			System.out.print(gen1.get(i) + " ");
		}
		System.out.println();
		
		System.out.print("Pai2: ");
		ArrayList<Integer> gen2 = cpai2.getGenotipo();
		for (int i = 0; i < gen2.size(); i++) {
			System.out.print(gen2.get(i) + " ");
		}
		System.out.println();
		
		
		ArrayList<Cromossomo> filhos = crossoverOX(cpai1, cpai2, mapaCidades);
		for (int j = 0; j < filhos.size(); j++) {
			System.out.print("Filho"+j+": ");
			for (int i = 0; i < filhos.get(j).getGenotipo().size(); i++) {
				System.out.print(filhos.get(j).getGenotipo().get(i) + " ");
			}
			System.out.println();
		}
		
		// Teste mutação
		System.out.println("Mutação no cromossomo filho 0");
		Cromossomo c = mutacaoInversiva(filhos.get(0), mapaCidades);
		for (int i = 0; i < filhos.get(0).getGenotipo().size(); i++) {
			System.out.print(filhos.get(0).getGenotipo().get(i) + " ");
		}
		System.out.println();
		
	}
	
	/**
	 * Seleciona subpopulacao de um conjunto de cromossomos
	 * @param cromossomos
	 * @return
	 */
	private ArrayList<Cromossomo> selecionarSubpopulacao(ArrayList<Cromossomo> cromossomos) {
		//Se der tempo fazer varios selecionar e usar um por vez.
		// int aleatorio = intAleatorio(1, 3);

		ArrayList<Cromossomo> subpopulacao = selecaoTorneio(cromossomos);
		
		return subpopulacao;
	}
	
	/**
	 * Selecao por torneio
	 * @param cromossomos
	 * @return
	 */
	private ArrayList<Cromossomo> selecaoTorneio(ArrayList<Cromossomo> cromossomos) {
		ArrayList<Cromossomo> ganhadores = new ArrayList<Cromossomo>();
		
		Map<Cromossomo,Cromossomo> torneio = new HashMap<Cromossomo,Cromossomo>();
		Map<Cromossomo,Boolean> ganhadoresUnicos = new HashMap<Cromossomo,Boolean>();
		
		for (int i = 0; i < cromossomos.size(); i++) {
			Boolean achaConcorrente = false;
			while (!achaConcorrente) {
				int aleatorio = intAleatorio(0, cromossomos.size()-1);
				Cromossomo concorrente = cromossomos.get(aleatorio);
				if (cromossomos.get(i).compareTo(concorrente) == 0) {
					torneio.put(cromossomos.get(i), concorrente);
					ganhadoresUnicos.put(cromossomos.get(i), false);
					achaConcorrente = true;
				}
			}
		}
		
		// Faz torneio para achar os campeoes
		for ( Cromossomo key : torneio.keySet() ) {
		    if (key.getFi() > torneio.get(key).getFi()) {
		    	ganhadoresUnicos.put(key, true);
		    } else {
		    	ganhadoresUnicos.put(torneio.get(key), true);
		    }
		}
		
		// Filtra os campeos para colocar em um arraylist
		for ( Cromossomo key : ganhadoresUnicos.keySet() ) {
		    if (ganhadoresUnicos.get(key)) {
		    	ganhadores.add(key);
		    }
		}
		
		return ganhadores;
	}

	/**
	 * Metodo order crossover (OX)
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	private ArrayList<Cromossomo> crossoverOX (Cromossomo pai1, Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {
		
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
		
		System.out.println("Posições sorteadas:" + p0 + " " + pf);
		
		/* Copiando dados fixos*/
		
		int auxFixo = pf;
		
		// Mapa de cidades que ja estao nos filhos
		Map<Integer, Boolean> mapaCidadesFilho1 = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> mapaCidadesFilho2 = new HashMap<Integer, Boolean>();
		
		// inicializando mapas
		for (int i = 1; i <= genes; i++) {
			mapaCidadesFilho1.put(i, false);
			mapaCidadesFilho2.put(i, false);
		}
		
		
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
			
			int cidadeAux = genotipo1.get(posicaoPai1);
			
			if (!mapaCidadesFilho2.get(cidadeAux)) {
				genotipoFilho2[posicaoFilho2] = cidadeAux;
				mapaCidadesFilho2.put(cidadeAux, true);
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
	private Cromossomo mutacaoInversiva(Cromossomo filho, HashMap<Integer, double[]> mapaCidades) {
		
		ArrayList<Integer> seqAtualFilho = filho.getGenotipo();
		ArrayList<Integer> seqNovaFilho = new ArrayList<Integer>();
		int aleatorio1 = -1;
		int aleatorio2 = -1;
		
		while (aleatorio1 == aleatorio2 ) {
			aleatorio1 = intAleatorio(0, seqAtualFilho.size()-1);
			aleatorio2 = intAleatorio(0, seqAtualFilho.size()-1);
		}
		System.out.println("Posicoes de mutação: " +aleatorio1+" " + aleatorio2);

		
		/*Troca as cidades de posicao*/
		int cidade1 = seqAtualFilho.get(aleatorio1);
		int cidade2 = seqAtualFilho.get(aleatorio2);

		for (int i = 0; i < seqAtualFilho.size(); i++) {
			if (i == aleatorio2) {
				seqNovaFilho.add(cidade1);
			} else if (i == aleatorio1) {
				seqNovaFilho.add(cidade2);
			} else {
				seqNovaFilho.add(seqAtualFilho.get(i));
			}
			
		}
		
		filho.setGenotipo(seqNovaFilho);
		filho.setFi(ProcessaCidades.calculaPercurso(mapaCidades, seqNovaFilho));
		
		return filho;
	}
	
	
	/**
	 * Gera um valor inteiro no intervalo [min;max]
	 * @param min
	 * @param max
	 * @return
	 */
	public int intAleatorio(int min, int max) {
	    Random random = new Random();

	    int randomNum = random.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	/** GETTERS AND SETTERS*/
	
	public ArrayList<Cromossomo> getCromossomosIniciais() {
		return cromossomosIniciais;
	}


	public void setCromossomosIniciais(ArrayList<Cromossomo> cromossomosIniciais) {
		this.cromossomosIniciais = cromossomosIniciais;
	}


	public ArrayList<Cromossomo> getCromossomosNaoSelecionados() {
		return cromossomosNaoSelecionados;
	}


	public void setCromossomosNaoSelecionados(
			ArrayList<Cromossomo> cromossomosNaoSelecionados) {
		this.cromossomosNaoSelecionados = cromossomosNaoSelecionados;
	}


	public ArrayList<Cromossomo> getCromossomosSelecionados() {
		return cromossomosSelecionados;
	}


	public void setCromossomosSelecionados(
			ArrayList<Cromossomo> cromossomosSelecionados) {
		this.cromossomosSelecionados = cromossomosSelecionados;
	}

}
