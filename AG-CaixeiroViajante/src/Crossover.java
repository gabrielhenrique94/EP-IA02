import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Crossover {

	/**
	 * Taxa utilizada para verificar se deve ser realizado crossover no cromossomo
	 */
	private static final double TAXA_CROSSOVER = 0.85;
	private static int[] posicoesPai2s;


	/**
	 * Cria uma subpopulacao de cromossomos por meio da aplicacao de crossover
	 * @param populacao
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> selecaoCrossover (ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades) {
		ArrayList<Cromossomo> subpopulacaoCrossover = new ArrayList<Cromossomo>();
		// Sorteia pelo menos o numero de vezes para todos os elementos terem chance de ser sorteados
		for (int i = 0; i < populacao.size(); i++) {
			if (Math.random() <= TAXA_CROSSOVER) {
				int pai1 = -1; // com ESSA SELECAO ALTERAR ESSES PAIS
				int pai2 = -1;

				/* ISSO E ROLETA RUSSA sem nenhum parameto de escolha */
				while (pai1 == pai2) {
					pai1 = Helpers.intAleatorio(0, populacao.size() - 1);
					pai2 = Helpers.intAleatorio(0, populacao.size() - 1);
				}

				Cromossomo cpai1 = populacao.get(pai1);
				Cromossomo cpai2 = populacao.get(pai2);

				/* Filhos 1 e 2 gerados */
				ArrayList<Cromossomo> filhos = Crossover.crossoverOX(cpai1, cpai2, mapaCidades);
				subpopulacaoCrossover.addAll(filhos);
			}
		}

		return subpopulacaoCrossover;
	}

	/**
	 * Metodo order crossover (OX)
	 *
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> crossoverOX(Cromossomo pai1,
			Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
		ArrayList<Integer> genotipo1 = pai1.getGenotipo();
		ArrayList<Integer> genotipo2 = pai2.getGenotipo();

		int genes = genotipo1.size();

		int[] genotipoFilho1 = new int[genes];
		int[] genotipoFilho2 = new int[genes];

		/* Posicao inicial e final */
		int p0 = -1;
		int pf = -1;

		while (p0 == pf) {
			p0 = Helpers.intAleatorio(0, genes - 1);
			pf = Helpers.intAleatorio(0, genes - 1);
		}



		/* Copiando dados fixos */

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

			if (auxFixo - 1 < 0) {
				auxFixo = genes - 1;
			} else {
				auxFixo--;
			}
		}

		/* Completa Filho 1 */

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

		/* Completa Filho 2 */

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

		/* Gerando o cromossomo dos filhos */
		ArrayList<Integer> seqFilho1 = new ArrayList<Integer>();
		ArrayList<Integer> seqFilho2 = new ArrayList<Integer>();

		for (int i = 0; i < genotipoFilho1.length; i++) {

			seqFilho1.add(genotipoFilho1[i]);
			seqFilho2.add(genotipoFilho2[i]);
		}

		Cromossomo filho1 = new Cromossomo(seqFilho1,
				ProcessaCidades.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2,
				ProcessaCidades.calculaPercurso(mapaCidades, seqFilho2));

		filhos.add(filho1);
		filhos.add(filho2);

		return filhos;

	}


	/**
	 * Metodo order crossover (Posicao)
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> crossoverPosicao(Cromossomo pai1,
			Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
		ArrayList<Integer> genotipoPai1 = pai1.getGenotipo();
		ArrayList<Integer> genotipoPai2 = pai2.getGenotipo();

		int genes = genotipoPai1.size();

		int[] genotipoFilho1 = new int[genes];
		int[] genotipoFilho2 = new int[genes];


		// Posicao inicial e final
		int p0 = -1;
		int pf = -1;

		while (p0 == pf) {
			p0 = Helpers.intAleatorio(0, genes - 1);
			pf = Helpers.intAleatorio(0, genes - 1);
		}

		// Copiando dados fixos
		int auxFixo = pf;

		// Mapa de cidades que ja estao nos filhos
		Map<Integer, Boolean> mapaCidadesFilho1 = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> mapaCidadesFilho2 = new HashMap<Integer, Boolean>();

		// inicializando mapas
		for (int i = 1; i <= genes; i++) {
			mapaCidadesFilho1.put(i, false);
			mapaCidadesFilho2.put(i, false);
		}

		//Gerando Filho 1

		//Cria um Set para nao permitir que a mesma cidade seja inserida duas vezes
		Set<Integer> HFilho1 = new HashSet<Integer>();

		ArrayList<Integer> posicoesPai1F1 = new ArrayList<Integer>();
		ArrayList<Integer> posicoesPai2F1 = new ArrayList<Integer>();

		//Pega as posicoes do pai 2
		for(int posicao = 3; posicao < genotipoPai2.size(); posicao = posicao+3){
			HFilho1.add(genotipoPai2.get(posicao));
			posicoesPai2F1.add(posicao);
		}

		//Pega as posicoes do pai 1
		for(int contador = 0; contador < genotipoPai1.size(); contador++){
			boolean a = HFilho1.add(genotipoPai1.get(contador));
			if (a) posicoesPai1F1.add(contador);
		}

		//Preenchendo vetor
		Iterator<Integer> It = HFilho1.iterator();
		while(It.hasNext())	{
			int aux = 0;
			Integer valor = 0;
			//Adicionando cidades adivindas do pai2
			for(int contador = 0; contador < posicoesPai2F1.size(); contador++){
				valor = It.next();
				aux = posicoesPai2F1.get(contador);
				genotipoFilho1[aux] = valor;
				mapaCidadesFilho1.put(genotipoPai2.get(aux), true);
			}
			//Adicionando cidades adivindas do pai1
			for(int contador = posicoesPai2F1.size(); contador < genotipoFilho1.length; contador++){
				valor = It.next();
				if (contador%3 == 0){
					contador++;
				}
				aux = posicoesPai1F1.get(contador);
				genotipoFilho1[contador] = valor;
				mapaCidadesFilho1.put(genotipoPai1.get(aux), true);
			}
		}

		//Gerando Filho 2

		//Cria um Set para nao permitir que a mesma cidade seja inserida duas vezes
		Set<Integer> HFilho2 = new HashSet<Integer>();

		ArrayList<Integer> posicoesPai1F2 = new ArrayList<Integer>();
		ArrayList<Integer> posicoesPai2F2 = new ArrayList<Integer>();

		//Pega as posicoes do pai 1
		for(int posicao = 3; posicao < genotipoPai2.size(); posicao = posicao+3){
			HFilho2.add(genotipoPai1.get(posicao));
			posicoesPai1F2.add(posicao);
		}

		//Pega as posicoes do pai 2
		for(int contador = 0; contador < genotipoPai2.size(); contador++){
			boolean a = HFilho1.add(genotipoPai2.get(contador));
			if (a) posicoesPai2F2.add(contador);
		}

		//Preenchendo vetor
		Iterator<Integer> IteratorFilho2 = HFilho2.iterator();
		while(IteratorFilho2.hasNext())	{
			int aux = 0;
			Integer valor = 0;
			//Adicionando cidades adivindas do pai1
			for(int contador = 0; contador < posicoesPai1F2.size(); contador++){
				valor = IteratorFilho2.next();
				aux = posicoesPai1F2.get(contador);
				genotipoFilho2[aux] = valor;
				mapaCidadesFilho2.put(genotipoPai1.get(aux), true);
			}
			//Adicionando cidades adivindas do pai2
			for(int contador = posicoesPai1F2.size(); contador < genotipoFilho1.length; contador++){
				valor = It.next();
				if (contador%3 == 0){
					contador++;
				}
				aux = posicoesPai1F1.get(contador);
				genotipoFilho2[contador] = valor;
				mapaCidadesFilho2.put(genotipoPai2.get(aux), true);
			}
		}

		// Gerando o cromossomo dos filhos
		ArrayList<Integer> seqFilho1 = new ArrayList<Integer>();
		ArrayList<Integer> seqFilho2 = new ArrayList<Integer>();

		for (int i = 0; i < genotipoFilho1.length; i++) {
			seqFilho1.add(genotipoFilho1[i]);
			seqFilho2.add(genotipoFilho2[i]);
		}

		Cromossomo filho1 = new Cromossomo(seqFilho1,
				ProcessaCidades.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2,
				ProcessaCidades.calculaPercurso(mapaCidades, seqFilho2));

		filhos.add(filho1);
		filhos.add(filho2);

		return filhos;
	}
}
