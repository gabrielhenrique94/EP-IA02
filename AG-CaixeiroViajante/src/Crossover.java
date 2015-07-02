import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Crossover {

	/**
	 * Cria uma subpopulacao de cromossomos por meio da aplicacao de crossover
	 * @param populacao
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> selecaoCrossover (ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades, double taxaCrossover) {
		ArrayList<Cromossomo> subpopulacaoCrossover = new ArrayList<Cromossomo>();
		// Sorteia pelo menos o numero de vezes para todos os elementos terem chance de ser sorteados
		for (int i = 0; i < populacao.size(); i++) {
			if (Math.random() <= taxaCrossover) {
				int pai1 = -1; // com ESSA SELECAO ALTERAR ESSES PAIS
				int pai2 = -1;

				/* ISSO E ROLETA RUSSA sem nenhum parameto de escolha */
				while (pai1 == pai2) {
					pai1 = Helpers.intAleatorio(0, populacao.size() - 1);
					pai2 = Helpers.intAleatorio(0, populacao.size() - 1);
				}

				Cromossomo cpai1 = populacao.get(pai1);
				Cromossomo cpai2 = populacao.get(pai2);

				/* Filhos de todos crossovers */
				subpopulacaoCrossover.addAll(Crossover.crossoverOX(cpai1, cpai2, mapaCidades));
				subpopulacaoCrossover.addAll(Crossover.crossoverPBX(cpai1, cpai2, mapaCidades));
				subpopulacaoCrossover.addAll(Crossover.crossoverOBX(cpai1, cpai2, mapaCidades));

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
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2,
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho2));

		filhos.add(filho1);
		filhos.add(filho2);

		return filhos;

	}

	/**
	 * Metodo order crossover PBX - crossover baseado em posicao
	 *
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> crossoverPBX(Cromossomo pai1, Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
		ArrayList<Integer> genotipoPai1 = pai1.getGenotipo();
		ArrayList<Integer> genotipoPai2 = pai2.getGenotipo();

		int genes = genotipoPai1.size();

		int[] genotipoFilho1 = new int[genes];
		int[] genotipoFilho2 = new int[genes];

		// Mapa de cidades que ja estao nos filhos
		Map<Integer, Boolean> mapaCidadesFilho1 = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> mapaCidadesFilho2 = new HashMap<Integer, Boolean>();

		// inicializando mapas
		for (int i = 1; i <= genes; i++) {
			mapaCidadesFilho1.put(i, false);
			mapaCidadesFilho2.put(i, false);
		}

		Random gerador = new Random();

		Set<Integer> posicoesPais = new LinkedHashSet<Integer>();

		//Define quais posicoes serao pegas dos pais
		for(int i = 0; i < 50; i++){
			boolean inseriu = posicoesPais.add(gerador.nextInt(100));
			if (inseriu == false) i--;
		}

		Set<Integer> posicoesPaisSecundario = new LinkedHashSet<Integer>();
		Iterator<Integer> IteratorPaisSecundario = posicoesPaisSecundario.iterator();
		Iterator<Integer> IteratorPosPais = posicoesPais.iterator();

		//Gerando Filho 1

		//Cria um Set para nao permitir que a mesma cidade seja inserida duas vezes
		Set<Integer> HFilho1 = new LinkedHashSet<Integer>();

		//Adiciona valores do pai 2 nas posicoes selecionadas no auxiliar do Filho 1
		while(IteratorPosPais.hasNext()){
			HFilho1.add(genotipoPai2.get(IteratorPosPais.next()));
		}

		//Pega as posicoes do pai 1
		for(int contador = 0; contador < genotipoPai1.size(); contador++){
			boolean a = HFilho1.add(genotipoPai1.get(contador));
			if (a) posicoesPaisSecundario.add(contador);
		}

		//Preenchendo vetor
		Iterator<Integer> IteratorFilho1 = HFilho1.iterator();
		IteratorPosPais = posicoesPais.iterator();
		IteratorPaisSecundario = posicoesPaisSecundario.iterator();
		while(IteratorFilho1.hasNext())	{
			//Adicionando cidades adivindas do pai2
			while(IteratorPosPais.hasNext()){
				int aux = IteratorPosPais.next();
				genotipoFilho1[aux] = IteratorFilho1.next();
				mapaCidadesFilho1.put(aux, true);
			}
			//Adicionando cidades adivindas do pai1
			int contador = 0;
			while(IteratorPaisSecundario.hasNext()){
				while(posicoesPais.contains(contador)){
					contador++;
				}
				int aux = IteratorPaisSecundario.next();
				genotipoFilho1[contador] = IteratorFilho1.next();
				mapaCidadesFilho1.put(genotipoPai1.get(aux), true);
				contador++;
			}
		}

		//Gerando Filho 2

		//Cria um Set para nao permitir que a mesma cidade seja inserida duas vezes
		Set<Integer> HFilho2 = new LinkedHashSet<Integer>();

		//Pega as posicoes do pai 1
		while(IteratorPosPais.hasNext()){
			HFilho2.add(genotipoPai1.get(IteratorPosPais.next()));
		}

		//Pega as posicoes do pai 2
		for(int contador = 0; contador < genotipoPai2.size(); contador++){
			HFilho2.add(genotipoPai2.get(contador));
		}

		//Preenchendo vetor
		Iterator<Integer> IteratorFilho2 = HFilho2.iterator();
		IteratorPosPais = posicoesPais.iterator();
		IteratorPaisSecundario = posicoesPaisSecundario.iterator();
		while(IteratorFilho2.hasNext())	{
			//Adicionando cidades adivindas do pai1
			while(IteratorPosPais.hasNext()){
				int aux = IteratorPosPais.next();
				genotipoFilho2[aux] = IteratorFilho2.next();
				mapaCidadesFilho2.put(aux, true);
			}
			//Adicionando cidades adivindas do pai2
			int contador = 0;
			while(IteratorPaisSecundario.hasNext()){
				while(posicoesPais.contains(contador)){
					contador++;
				}
				int aux = IteratorPaisSecundario.next();
				genotipoFilho2[contador] = IteratorFilho2.next();
				mapaCidadesFilho2.put(genotipoPai2.get(aux), true);
				contador++;
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
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2,
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho2));

		filhos.add(filho1);
		filhos.add(filho2);

		return filhos;
	}

	/**
	 * Metodo order crossover OBX - crossover baseado em ordem
	 *
	 * @param pai1
	 * @param pai2
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> crossoverOBX(Cromossomo pai1,
			Cromossomo pai2, HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
		ArrayList<Integer> genotipoPai1 = pai1.getGenotipo();
		ArrayList<Integer> genotipoPai2 = pai2.getGenotipo();

		int genes = genotipoPai1.size();

		int[] genotipoFilho1 = new int[genes];
		int[] genotipoFilho2 = new int[genes];

		//Mapa de cidades que ja estao nos filhos
		Map<Integer, Boolean> mapaCidadesFilho1 = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> mapaCidadesFilho2 = new HashMap<Integer, Boolean>();

		//Inicializando mapas
		for (int i = 1; i <= genes; i++) {
			mapaCidadesFilho1.put(i, false);
			mapaCidadesFilho2.put(i, false);
		}

		Random gerador = new Random();

		//Gerando Filho 1 - Filho 1 tera pai 2 como a ordem das selecionadas de um pai

		//passo1:
		//pegou quais são as posições aleatórias do pai1 e inseriu em PosicaoPaiPrincipal
		//Define quais posicoes sera pega do pai1
		Set<Integer> PosicaoSortPaiPrincipal = new LinkedHashSet<Integer>();
		for(int i = 0; i < 50; i++){
			boolean inseriu = PosicaoSortPaiPrincipal.add(gerador.nextInt(100));
			if (!inseriu) i--;
		}

		//passo 2:
		//verificar quais são as cidades q ocupam essa posição em pai2
		Iterator<Integer> IteratorSortPaiPrincipal= PosicaoSortPaiPrincipal.iterator();
		Set<Integer> Auxiliar = new LinkedHashSet<Integer>();
		while(IteratorSortPaiPrincipal.hasNext()){
			Auxiliar.add(genotipoPai2.get(IteratorSortPaiPrincipal.next()));
		}

		//passo 3:
		//verificar em que posição essas cidades vindas do pai2 estariam em pai1
		//Cria um Set do pai 1
		Set<Integer> PosicaoPaiPrincipal = new LinkedHashSet<Integer>();
		Set<Integer> Auxiliar2 = new LinkedHashSet<Integer>();
		for(int contador = 0; contador < genotipoPai1.size(); contador++){
			boolean inseriu = Auxiliar2.add(genotipoPai1.get(contador));
			if (inseriu) PosicaoPaiPrincipal.add(contador);
		}
		Set<Integer> auxiliarPosicao = new LinkedHashSet<Integer>();
		Iterator<Integer> IteratorAuxiliar = Auxiliar.iterator();
		Iterator<Integer> IteratorPosicaoPaiPrincipal = PosicaoPaiPrincipal.iterator();
		Set<Integer> auxiliar3 = new LinkedHashSet<Integer>();
		auxiliar3.addAll(Auxiliar2);
		//para cada item em Auxiliar eu vou verificar a existencia dele em Auxiliar2... faço um contador... onde o contador para é onde aquele item ta
		while(IteratorAuxiliar.hasNext() && IteratorPosicaoPaiPrincipal.hasNext()){
			Iterator<Integer> IteratorAuxiliar2 = Auxiliar2.iterator();
			int aux = IteratorAuxiliar.next();
			int contador = 0;
			while(IteratorAuxiliar2.hasNext()){
				int aux2 = IteratorAuxiliar2.next();
				if (aux == aux2){
					auxiliarPosicao.add(contador);
					auxiliar3.remove(aux2);
					break;
				}
				contador++;
			}
		}

		//passo 4:
		//ordenar cidades nas posicoes definidas no passo3
		Set<Integer> AuxiliarFilho1 = new TreeSet<Integer>();
		IteratorAuxiliar = Auxiliar.iterator();
		while(IteratorAuxiliar.hasNext()){
			AuxiliarFilho1.add(IteratorAuxiliar.next());
		}

		Set<Integer> AuxiliarPosicaoOrdenado = new TreeSet<Integer>();
		AuxiliarPosicaoOrdenado.addAll(auxiliarPosicao);

		//passo 5:
		//preencher restante com cidades do pai1
		Iterator<Integer> IteratorAuxiliarFilho1 = AuxiliarFilho1.iterator();
		Iterator<Integer> IteratorAuxiliarPosicaoOrdenado = AuxiliarPosicaoOrdenado.iterator();
		Set<Integer> AuxiliarInsercao = new LinkedHashSet<Integer>();
		//Adicionando cidades ordenadas
		while(IteratorAuxiliarFilho1.hasNext()){
			int aux = IteratorAuxiliarPosicaoOrdenado.next();
			genotipoFilho1[aux] = IteratorAuxiliarFilho1.next();
			mapaCidadesFilho1.put(genotipoPai1.get(aux), true);
			AuxiliarInsercao.add(genotipoFilho1[aux]);
		}
		//Adicionando cidades adivindas do pai1
		IteratorPosicaoPaiPrincipal = PosicaoPaiPrincipal.iterator();
		Iterator<Integer> IteratorAuxiliar2 = Auxiliar2.iterator();
		while(IteratorAuxiliar2.hasNext()){
			AuxiliarInsercao.add(IteratorAuxiliar2.next());
		}
		Iterator<Integer> IteratorAuxiliarInsercao = AuxiliarInsercao.iterator();
		for(int i = 0; i < 50; i++){
			IteratorAuxiliarInsercao.next();
		}
		for(int contador = 0; contador <= genotipoFilho1.length; contador++){
			while(IteratorAuxiliarInsercao.hasNext()){
				if(genotipoFilho1[contador] == 0){
					int aux = IteratorPosicaoPaiPrincipal.next();
					genotipoFilho1[contador] = IteratorAuxiliarInsercao.next();
					mapaCidadesFilho1.put(genotipoPai1.get(aux), true);
				}
				contador++;
			}
		}

		//Gerando Filho 2 - Filho 2 tera pai 1 como a ordem das selecionadas de um pai

		//passo1: - igual ao do gerando Filho1

		//passo 2:
		//verificar quais são as cidades q ocupam essa posição em pai1
		IteratorSortPaiPrincipal= PosicaoSortPaiPrincipal.iterator();
		Auxiliar = new LinkedHashSet<Integer>();
		while(IteratorSortPaiPrincipal.hasNext()){
			Auxiliar.add(genotipoPai1.get(IteratorSortPaiPrincipal.next()));
		}

		//passo 3:
		//verificar em que posição essas cidades vindas do pai1 estariam em pai2
		//Cria um Set do pai 1
		PosicaoPaiPrincipal = new LinkedHashSet<Integer>();
		Auxiliar2 = new LinkedHashSet<Integer>();
		for(int contador = 0; contador < genotipoPai2.size(); contador++){
			boolean inseriu = Auxiliar2.add(genotipoPai2.get(contador));
			if (inseriu) PosicaoPaiPrincipal.add(contador);
		}
		auxiliarPosicao = new LinkedHashSet<Integer>();
		IteratorAuxiliar = Auxiliar.iterator();
		IteratorPosicaoPaiPrincipal = PosicaoPaiPrincipal.iterator();
		auxiliar3 = new LinkedHashSet<Integer>();
		auxiliar3.addAll(Auxiliar2);
		//para cada item em Auxiliar eu vou verificar a existencia dele em Auxiliar2... faço um contador... onde o contador para é onde aquele item ta
		while(IteratorAuxiliar.hasNext() && IteratorPosicaoPaiPrincipal.hasNext()){
			IteratorAuxiliar2 = Auxiliar2.iterator();
			int aux = IteratorAuxiliar.next();
			int contador = 0;
			while(IteratorAuxiliar2.hasNext()){
				int aux2 = IteratorAuxiliar2.next();
				if (aux == aux2){
					auxiliarPosicao.add(contador);
					auxiliar3.remove(aux2);
					break;
				}
				contador++;
			}
		}

		//passo 4:
		//ordenar cidades nas posicoes definidas no passo3
		AuxiliarFilho1 = new TreeSet<Integer>();
		IteratorAuxiliar = Auxiliar.iterator();
		while(IteratorAuxiliar.hasNext()){
			AuxiliarFilho1.add(IteratorAuxiliar.next());
		}

		AuxiliarPosicaoOrdenado = new TreeSet<Integer>();
		AuxiliarPosicaoOrdenado.addAll(auxiliarPosicao);

		//passo 5:
		//preencher restante com cidades do pai2
		IteratorAuxiliarFilho1 = AuxiliarFilho1.iterator();
		IteratorAuxiliarPosicaoOrdenado = AuxiliarPosicaoOrdenado.iterator();
		AuxiliarInsercao = new LinkedHashSet<Integer>();
		//Adicionando cidades ordenadas
		while(IteratorAuxiliarFilho1.hasNext()){
			int aux = IteratorAuxiliarPosicaoOrdenado.next();
			genotipoFilho2[aux] = IteratorAuxiliarFilho1.next();
			mapaCidadesFilho2.put(genotipoPai2.get(aux), true);
			AuxiliarInsercao.add(genotipoFilho2[aux]);
		}
		//Adicionando cidades adivindas do pai2
		IteratorPosicaoPaiPrincipal = PosicaoPaiPrincipal.iterator();
		IteratorAuxiliar2 = Auxiliar2.iterator();
		while(IteratorAuxiliar2.hasNext()){
			AuxiliarInsercao.add(IteratorAuxiliar2.next());
		}
		IteratorAuxiliarInsercao = AuxiliarInsercao.iterator();
		for(int i = 0; i < 50; i++){
			IteratorAuxiliarInsercao.next();
		}
		for(int contador = 0; contador <= genotipoFilho1.length; contador++){
			while(IteratorAuxiliarInsercao.hasNext()){
				if(genotipoFilho2[contador] == 0){
					int aux = IteratorPosicaoPaiPrincipal.next();
					genotipoFilho2[contador] = IteratorAuxiliarInsercao.next();
					mapaCidadesFilho2.put(genotipoPai2.get(aux), true);
				}
				contador++;
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
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho1));
		Cromossomo filho2 = new Cromossomo(seqFilho2,
				ProcessaPopulacao.calculaPercurso(mapaCidades, seqFilho2));

		filhos.add(filho1);
		filhos.add(filho2);

		return filhos;
	}
}