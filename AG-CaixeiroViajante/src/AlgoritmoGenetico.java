import java.util.ArrayList;
import java.util.HashMap;

public class AlgoritmoGenetico {

	/**
	 * Armazena a populacao total
	 */
	private ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();

	/**
	 * Numero de geracoes esperada
	 */
	private int geracoesMaximas;

	/**
	 * Numero de individuos que devem ter em uma populacao final
	 */
	private int numIndividuos;

	/**
	 * Mapa das coordenadas de cada cidade
	 */
	private HashMap<Integer, double[]> mapaCidades = new HashMap<Integer, double[]>();

	/**
	 * Construtor da classe
	 * @param cromossomos
	 * @param mapaCidades
	 * @param geracoesMaximas
	 */
	public AlgoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades, int geracoesMaximas, int numIndividuos) {
		this.setPopulacao(cromossomos);
		this.setMapaCidades(mapaCidades);
		this.setGeracoesMaximas(geracoesMaximas);
		this.setNumIndividuos(numIndividuos);
	}

	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma populacao.
	 *
	 * @param cromossomos Populacao que sera analisada.
	 * @param mapaCidades Lugar que se encontra cada cidade que compoe as distancias nos cromossomos da populacao em um mapa.
	 */
	public void iniciaAlgoritmoGenetico() {
		int t = 0;
		System.out.println("Gerações: " + geracoesMaximas);
		ArrayList<Cromossomo> geracaoFinal = selecionaNovaPopulacao(this.getPopulacao(), this.mapaCidades);
		boolean populacaoAdaptada = false;

		while (!populacaoAdaptada && t < this.geracoesMaximas) {
			System.out.println("Geraçao: " + t + " Populacao: " + geracaoFinal.size());
			populacaoAdaptada = avaliarPopulacao(geracaoFinal);

			if (!populacaoAdaptada) {
				geracaoFinal = selecionaNovaPopulacao(geracaoFinal, this.mapaCidades);
			} else {
				populacaoAdaptada = true;
			}
			t++;
		}
		Cromossomo vencedor = Selecao.melhorIndividuo(geracaoFinal);
		System.out.println("Melhor Cromossomo");
		for (int i = 0;i<vencedor.getGenotipo().size();i++) {
			System.out.print(vencedor.getGenotipo().get(i)+" ");
		}
		System.out.println();
		System.out.println(vencedor.getFi());
		System.out.println("FIM GA");
	}

	private boolean avaliarPopulacao(ArrayList<Cromossomo> populacao) {
		boolean populacaoAdaptada = false;
		int igualdade = 5;
		int countSemelhantes = 0;

		for (int i = 0; i < populacao.size();i++) {
			int achaConcorrente = 0;
			while (countSemelhantes < igualdade && achaConcorrente < populacao.size()) {
				int aleatorio = Helpers.intAleatorio(0, populacao.size() - 1);
				Cromossomo concorrente = populacao.get(aleatorio);
				if (populacao.get(i).compareTo(concorrente) == 1) {
					countSemelhantes++;
				}
				achaConcorrente++;

			}
			countSemelhantes = 0;
		}

		if(countSemelhantes >= igualdade) {
			populacaoAdaptada = true;
		}

		return populacaoAdaptada;
	}

	/**
	 * Seleciona uma populacao nova a partir de uma populacao gerada por subpopulacoes que sofreram interferencias
	 * @param populacao
	 * @param mapaCidades
	 * @return
	 */
	private ArrayList<Cromossomo> selecionaNovaPopulacao(ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades){
		/*Gera populacao com subpopulacoes*/
		ArrayList<Cromossomo> subPopulacao= selecionarSubpopulacao(populacao, mapaCidades);

		// Aplica roleta russa em cima dos melhores, porem escolhe o melhor de todos de qualquer forma
		subPopulacao = Selecao.selecaoRoletaRussaMelhor(subPopulacao, this.numIndividuos);

		return subPopulacao;
	}

	/**
	 * Seleciona subpopulacao de um conjunto de cromossomos
	 *
	 * @param cromossomos
	 * @return
	 */
	private ArrayList<Cromossomo> selecionarSubpopulacao(ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Cromossomo> novaPopulacao = new ArrayList<Cromossomo>();
		novaPopulacao.addAll(populacao);

		ArrayList<Cromossomo> subpopulacaoTorneio = Selecao.selecaoTorneio(populacao);
		novaPopulacao.addAll(subpopulacaoTorneio);

		ArrayList<Cromossomo> subpopulacaoCrossovers = Crossover.selecaoCrossover(populacao, mapaCidades);
		novaPopulacao.addAll(subpopulacaoCrossovers);

		ArrayList<Cromossomo> subpopulacaoMutacoes = Mutacao.selecaoMutacao(populacao, mapaCidades);
		novaPopulacao.addAll(subpopulacaoMutacoes);

		return novaPopulacao;
	}

	public ArrayList<Cromossomo> getPopulacao() {
		return populacao;
	}

	public void setPopulacao(ArrayList<Cromossomo> populacao) {
		this.populacao = populacao;
	}

	public int getGeracoesMaximas() {
		return geracoesMaximas;
	}

	public void setGeracoesMaximas(int geracoesMaximas) {
		this.geracoesMaximas = geracoesMaximas;
	}

	public HashMap<Integer, double[]> getMapaCidades() {
		return mapaCidades;
	}

	public void setMapaCidades(HashMap<Integer, double[]> mapaCidades) {
		this.mapaCidades = mapaCidades;
	}

	public int getNumIndividuos() {
		return numIndividuos;
	}

	public void setNumIndividuos(int numIndividuos) {
		this.numIndividuos = numIndividuos;
	}
}
