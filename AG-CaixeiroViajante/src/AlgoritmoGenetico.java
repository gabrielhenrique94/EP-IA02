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
	 * Armazena a populacao total
	 */
	ArrayList<Cromossomo> populacaoTotal = new ArrayList<Cromossomo>();


	/**
	 * Cromossomos nao selecionados durante um periodo de selecao
	 */
	ArrayList<ArrayList<Cromossomo>> cromossomosNaoSelecionados = new ArrayList<ArrayList<Cromossomo>>();
	
	/**
	 * Cromossomos selecionados para um periodo de selecao
	 */
	ArrayList<ArrayList<Cromossomo>> cromossomosSelecionados = new ArrayList<ArrayList<Cromossomo>>();
	
	/**
	 * Mapa das coordenadas de cada cidade 
	 */
	HashMap<Integer, double[]> mapaCidades = new HashMap<Integer, double[]>();
	
	public AlgoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades ) {
		this.populacaoTotal = cromossomos;
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
		 * 
		 * 
		 * EXEMPLO CLODS DE IMPLEMENTACAO:
		 * Gera a população Inicial  - igual ao TamPop
			Avalio cada cromossomo
			numero gerações = 0;
			Enquanto numero geração menor que o maximo
			     Incrementa o numero de gerações
			     Gera um subpopulação
			             Aplica Roleta - dois inidividuos aleatorios
			             Crossover
			              Mutação
			     Gera um subpopulação
			             Aplica Roleta - Melhor Individuo e 1 aleatorio
			             Crossover
			             Mutação
			
			     Gera um subpopulação aleatoria
			
			     Concante todas as subpopulação + população inicial (ou somente os melhores)
			    
			      Avalia a toda a população
			
			     Aplica o criterio de seleção e seleciona o individuos para a proxima geração
			
			    Avalia este individuos
			
			    Verifica se população convergiu
			
			     Se sim break
			
			FimEnquanto
		 */
		

		selecionarSubpopulacao();
		
		this.setPopulacaoTotal(new ArrayList<Cromossomo>()); // ZERA POPULACAO TOTAL
		
		for (int i = 0; i < getCromossomosSelecionados().size(); i++) {
			this.populacaoTotal.addAll(getCromossomosNaoSelecionados().get(i)); //ACRESCENTA CROMOSSOMOS NAO SELECIONADOS
			this.populacaoTotal.addAll(getCromossomosSelecionados().get(i)); //ACRESCENTA CROMOSSOMOS SELECIONADOS
			// Talvez gere muitos ruins aqui -- possivelmente teremos que tratar os nao selecionados futuramente.
		}
		
		int n = cromossomos.size();
		int pai1 = -1;
		int pai2 = -1;
		
		while (pai1 == pai2) {
			pai1 = Helpers.intAleatorio(0, n-1);;
			pai2 = Helpers.intAleatorio(0, n-1);
		}
		
		Cromossomo cpai1 = cromossomos.get(pai1);
		Cromossomo cpai2 = cromossomos.get(pai2);
				
		ArrayList<Cromossomo> filhos = Crossover.crossoverOX(cpai1, cpai2, mapaCidades);

		Cromossomo c = Mutacao.mutacaoInversiva(filhos.get(0), mapaCidades);

		
	}
	
	/**
	 * Seleciona subpopulacao de um conjunto de cromossomos
	 * @param cromossomos
	 * @return
	 */
	private void selecionarSubpopulacao() {
		//Se der tempo fazer varios selecionar e usar um por vez.
		// int aleatorio = intAleatorio(1, 3);
	
		setCromossomosSelecionados(new ArrayList<ArrayList<Cromossomo>>()); //ZERAR SELECIONADOS
		setCromossomosNaoSelecionados(new ArrayList<ArrayList<Cromossomo>>()); //ZERAR NAO SELECIONADOS
		
		
		selecaoTorneio();

	}
	
	/**
	 * Selecao por torneio
	 * @param cromossomos
	 * @return
	 */
	private void selecaoTorneio() {
		ArrayList<Cromossomo> ganhadores = new ArrayList<Cromossomo>();
		ArrayList<Cromossomo> perdedores = new ArrayList<Cromossomo>();
		
		Map<Cromossomo,Cromossomo> torneio = new HashMap<Cromossomo,Cromossomo>();
		Map<Cromossomo,Boolean> ganhadoresUnicos = new HashMap<Cromossomo,Boolean>();
		
		for (int i = 0; i < getPopulacaoTotal().size(); i++) {
			Boolean achaConcorrente = false;
			while (!achaConcorrente) {
				int aleatorio = Helpers.intAleatorio(0, getPopulacaoTotal().size()-1);
				Cromossomo concorrente = getPopulacaoTotal().get(aleatorio);
				if (getPopulacaoTotal().get(i).compareTo(concorrente) == 0) {
					torneio.put(getPopulacaoTotal().get(i), concorrente);
					ganhadoresUnicos.put(getPopulacaoTotal().get(i), false);
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
		    } else {
		    	perdedores.add(key);
		    }
		}
		
		this.cromossomosSelecionados.add(ganhadores);
		this.cromossomosNaoSelecionados.add(perdedores);
	
	}

	
	
	
	
	
	/** GETTERS AND SETTERS*/
	
	public ArrayList<Cromossomo> getPopulacaoTotal() {
		return populacaoTotal;
	}


	public void setPopulacaoTotal(ArrayList<Cromossomo> cromossomosIniciais) {
		this.populacaoTotal = cromossomosIniciais;
	}


	public ArrayList<ArrayList<Cromossomo>> getCromossomosNaoSelecionados() {
		return cromossomosNaoSelecionados;
	}


	public void setCromossomosNaoSelecionados(
			ArrayList<ArrayList<Cromossomo>> cromossomosNaoSelecionados) {
		this.cromossomosNaoSelecionados = cromossomosNaoSelecionados;
	}


	public ArrayList<ArrayList<Cromossomo>> getCromossomosSelecionados() {
		return cromossomosSelecionados;
	}


	public void setCromossomosSelecionados(
			ArrayList<ArrayList<Cromossomo>> cromossomosSelecionados) {
		this.cromossomosSelecionados = cromossomosSelecionados;
	}

}
