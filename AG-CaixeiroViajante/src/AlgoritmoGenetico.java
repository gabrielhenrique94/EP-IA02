import java.util.ArrayList;
import java.util.HashMap;

public class AlgoritmoGenetico {



	/**
	 * Armazena a populacao total
	 */
	ArrayList<Cromossomo> populacaoInicial = new ArrayList<Cromossomo>();

	/**
	 * Mapa das coordenadas de cada cidade
	 */
	HashMap<Integer, double[]> mapaCidades = new HashMap<Integer, double[]>();

	public AlgoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades) {
		this.populacaoInicial = cromossomos;
		this.mapaCidades = mapaCidades;
	}

	/**
	 * Metodo chamado para iniciar um algoritmo genetico em cima de uma populacao.
	 *
	 * @param cromossomos Populacao que sera analisada.
	 * @param mapaCidades Lugar que se encontra cada cidade que compoe as distancias nos cromossomos da populacao em um mapa.
	 */
	void algoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, double[]> mapaCidades) {
		selecionarSubpopulacao(cromossomos, mapaCidades);
	}


	/**
	 * Seleciona subpopulacao de um conjunto de cromossomos
	 *
	 * @param cromossomos
	 * @return
	 */
	private void selecionarSubpopulacao(ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades) {
		System.out.println("Inicio");
		ArrayList<Cromossomo> novaPopulacao = new ArrayList<Cromossomo>();
		novaPopulacao.addAll(populacao);
		System.out.println(novaPopulacao.size());
		System.out.println("Torneio");
		ArrayList<Cromossomo> subpopulacaoTorneio = Selecao.selecaoTorneio(populacao);
		novaPopulacao.addAll(subpopulacaoTorneio);
		System.out.println(subpopulacaoTorneio.size());
		System.out.println("Crossover");
		ArrayList<Cromossomo> subpopulacaoCrossovers = Crossover.selecaoCrossover(populacao, mapaCidades);
		novaPopulacao.addAll(subpopulacaoCrossovers);
		System.out.println(subpopulacaoCrossovers.size());
		System.out.println("Mutacao");
		ArrayList<Cromossomo> subpopulacaoMutacoes = Mutacao.selecaoMutacao(populacao, mapaCidades);
		novaPopulacao.addAll(subpopulacaoMutacoes);
		System.out.println(subpopulacaoMutacoes.size());
		System.out.println("Fim");
	}



}
