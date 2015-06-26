import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgoritmoGenetico {

	

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

	public AlgoritmoGenetico(ArrayList<Cromossomo> cromossomos,
			HashMap<Integer, double[]> mapaCidades) {
		this.populacaoTotal = cromossomos;
		this.mapaCidades = mapaCidades;
	}

	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma
	 * populacao.
	 *
	 * @param cromossomos
	 *            Populacao que sera analisada.
	 * @param mapaCidades
	 *            Lugar que se encontra cada cidade que compoe as distancias nos
	 *            cromossomos da populacao em um mapa.
	 */
	void algoritmoGenetico(ArrayList<Cromossomo> cromossomos,
			HashMap<Integer, double[]> mapaCidades) {
		/*
		 * t <- 0 inicializar S(T) avaliar S(t) enquanto o criterio de parada
		 * não satisfeito faça t <- t + 1 selecionar S(t) a partir de S(t -1)
		 * aplicar crossover sobre S(t) aplicar mutação sobre S(t) se
		 * TAXA_MUTAÇAO VALIDAR avaliar S(t) fim enquanto
		 *
		 *
		 * EXEMPLO CLODS DE IMPLEMENTACAO: Gera a população Inicial - igual ao
		 * TamPop Avalio cada cromossomo numero gerações = 0; Enquanto numero
		 * geração menor que o maximo Incrementa o numero de gerações Gera
		 * um subpopulação Aplica Roleta - dois inidividuos aleatorios
		 * Crossover Mutação Gera um subpopulação Aplica Roleta - Melhor
		 * Individuo e 1 aleatorio Crossover Mutação
		 *
		 * Gera um subpopulação aleatoria
		 *
		 * Concante todas as subpopulação + população inicial (ou somente os
		 * melhores)
		 *
		 * Avalia a toda a população
		 *
		 * Aplica o criterio de seleção e seleciona o individuos para a
		 * proxima geração
		 *
		 * Avalia este individuos
		 *
		 * Verifica se população convergiu
		 *
		 * Se sim break
		 *
		 * FimEnquanto
		 */

		selecionarSubpopulacao();
		// TODO: Avaliar
		/*
		 * TODO: Tem que fazer a condição de parada que eu nao faco ideia de
		 * qual seja
		 */
		
			this.setPopulacaoTotal(new ArrayList<Cromossomo>()); // ZERA
			// POPULACAO
			// TOTAL

			for (int i = 0; i < getCromossomosSelecionados().size(); i++) {
				ArrayList<Cromossomo> subpopulacao = getCromossomosSelecionados()
						.get(i);
				// TODO: APLICAR OUTRO METODO DE SELECAO PARA ESCOLHER QUEM VAI
				// FAZER CROSSOVER E MUTACAO
				int pai1 = -1; // com ESSA SELECAO ALTERAR ESSES PAIS
				int pai2 = -1;

				/* ISSO E ROLETA RUSSA sem nenhum parameto de escolha */
				while (pai1 == pai2) {
					pai1 = Helpers.intAleatorio(0, subpopulacao.size() - 1);
					pai2 = Helpers.intAleatorio(0, subpopulacao.size() - 1);
				}

				Cromossomo cpai1 = cromossomos.get(pai1);
				Cromossomo cpai2 = cromossomos.get(pai2);

				// TODO: Nao tenho certeza em quem aplicar crossover , se é
				// somente nesses dois pais que vc gera, ou se vc gera varios

				/* Aplica Crossover nesses filhos */
				ArrayList<Cromossomo> filhos = Crossover.crossoverOX(cpai1,
						cpai2, mapaCidades);
				System.out.println("aqui");
				Cromossomo c = Mutacao.mutacaoInversiva(filhos.get(0), mapaCidades);
				/* Testa se quer fazer mutacao 
				for (int j = 0; j < filhos.size(); j++) {
					Cromossomo filho = filhos.get(j);
					for (int k = 0; k < filho.getGenotipo().size(); k++) { // N�o sei se vai dar certo do jeito qeu est�

						if (Math.random() < TAXA_MUTACAO) {
							//Cromossomo c = Mutacao.mutacaoInversiva(filhos.get(j), mapaCidades);

							Cromossomo c1 = Mutacao.mutacaoPorPosicao(filhos.get(j), mapaCidades, k);
							// TODO: Aqui ta cru precisa arrumar
						}
					}
				}*/

				// TODO: Regerar o selecionados com os novos filhos e removendo
				// os ruins (tem que ver como escolhe)

			}

			// TODO: Depois de aplicar os crossover e mutacoes tem que juntar a
			// populacao inteira e começar tudo de novo
			for (int i = 0; i < getCromossomosSelecionados().size(); i++) {
				this.populacaoTotal.addAll(getCromossomosNaoSelecionados().get(
						i)); // ACRESCENTA CROMOSSOMOS NAO SELECIONADOS
				this.populacaoTotal.addAll(getCromossomosSelecionados().get(i)); // ACRESCENTA
				// CROMOSSOMOS
				// SELECIONADOS
				// Talvez gere muitos ruins aqui -- possivelmente teremos que
				// tratar os nao selecionados futuramente.
			}

			selecionarSubpopulacao();
			// TODO: Avaliar

		}


	/**
	 * Seleciona subpopulacao de um conjunto de cromossomos
	 *
	 * @param cromossomos
	 * @return
	 */
	private void selecionarSubpopulacao() {
		// Se der tempo fazer varios selecionar e usar um por vez.
		// int aleatorio = intAleatorio(1, 3);

		setCromossomosSelecionados(new ArrayList<ArrayList<Cromossomo>>()); // ZERAR
		// SELECIONADOS
		setCromossomosNaoSelecionados(new ArrayList<ArrayList<Cromossomo>>()); // ZERAR
		// NAO
		// SELECIONADOS

		selecaoTorneio();

	}

	/**
	 * Selecao por torneio
	 *
	 * @param cromossomos
	 * @return
	 */
	private void selecaoTorneio() {
		ArrayList<Cromossomo> ganhadores = new ArrayList<Cromossomo>();
		ArrayList<Cromossomo> perdedores = new ArrayList<Cromossomo>();

		Map<Cromossomo, Cromossomo> torneio = new HashMap<Cromossomo, Cromossomo>();
		Map<Cromossomo, Boolean> ganhadoresUnicos = new HashMap<Cromossomo, Boolean>();

		for (int i = 0; i < getPopulacaoTotal().size(); i++) {
			Boolean achaConcorrente = false;
			while (!achaConcorrente) {
				int aleatorio = Helpers.intAleatorio(0, getPopulacaoTotal()
						.size() - 1);
				Cromossomo concorrente = getPopulacaoTotal().get(aleatorio);
				if (getPopulacaoTotal().get(i).compareTo(concorrente) == 0) {
					torneio.put(getPopulacaoTotal().get(i), concorrente);
					ganhadoresUnicos.put(getPopulacaoTotal().get(i), false);
					achaConcorrente = true;
				}
			}
		}

		// Faz torneio para achar os campeoes
		for (Cromossomo key : torneio.keySet()) {
			if (key.getFi() > torneio.get(key).getFi()) {
				ganhadoresUnicos.put(key, true);
			} else {
				ganhadoresUnicos.put(torneio.get(key), true);
			}
		}

		// Filtra os campeos para colocar em um arraylist
		for (Cromossomo key : ganhadoresUnicos.keySet()) {
			if (ganhadoresUnicos.get(key)) {
				ganhadores.add(key);
			} else {
				perdedores.add(key);
			}
		}

		this.cromossomosSelecionados.add(ganhadores);
		this.cromossomosNaoSelecionados.add(perdedores);

	}

	/** GETTERS AND SETTERS */

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
