import java.util.ArrayList;
import java.util.Collections;
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
		ArrayList<Cromossomo> geracaoFinal = this.getPopulacao();

		while (t < this.geracoesMaximas) {
			System.out.println("Geraçao: " + t + " Populacao: " + geracaoFinal.size());
			ArrayList<Cromossomo> novaPopulacao = selecionarSubpopulacao(geracaoFinal, this.mapaCidades);
			geracaoFinal = avaliaPopulacao(novaPopulacao);
			Collections.shuffle(geracaoFinal);
			t++;
		}

		System.out.println("FIM GA");
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

	private ArrayList<Cromossomo> avaliaPopulacao(ArrayList<Cromossomo> populacao){
		System.out.println("Avalia");
		ArrayList<Cromossomo> populacaoAdaptada = new ArrayList<Cromossomo>();

		// Realiza selecao por torneio para pegar os melhores

		populacaoAdaptada = Selecao.selecaoTorneio(populacao);
		System.out.println("Populacao Apos torneio: " + populacaoAdaptada.size());
		// Aplica roleta russa em cima dos melhores, porem escolhe o melhor de todos de qualquer forma
		populacaoAdaptada = Selecao.selecaoRoletaRussaMelhor(populacaoAdaptada, this.numIndividuos);
		System.out.println("Populacao Apos Roleta: " + populacaoAdaptada.size());
		return populacaoAdaptada;
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
