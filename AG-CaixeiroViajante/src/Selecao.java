import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Selecao {

	/**
	 * Selecao por torneio
	 *
	 * @param cromossomos
	 * @return
	 */
	public static ArrayList<Cromossomo> selecaoTorneio(ArrayList<Cromossomo> populacao) {
		ArrayList<Cromossomo> ganhadores = new ArrayList<Cromossomo>();

		Map<Cromossomo, Cromossomo> torneio = new HashMap<Cromossomo, Cromossomo>();
		Map<Cromossomo, Boolean> ganhadoresUnicos = new HashMap<Cromossomo, Boolean>();

		for (int i = 0; i < populacao.size(); i++) {
			Boolean achaConcorrente = false;
			while (!achaConcorrente) {
				int aleatorio = Helpers.intAleatorio(0, populacao.size() - 1);
				Cromossomo concorrente = populacao.get(aleatorio);
				if (populacao.get(i).compareTo(concorrente) == 0) {
					torneio.put(populacao.get(i), concorrente);
					ganhadoresUnicos.put(populacao.get(i), false);
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
			}
		}

		return ganhadores;

	}

	/**
	 * Selecao pelo metodo de roleta russa, com a certeza de manter o melhor
	 * @param populacao
	 * @param n numero de individuos que devem ser selecionados
	 * @return
	 */
	//tem de calcular o fitness acumulado
	//random*fitness = 0 a 1 o random
	/*
	 * primeiro passo = soma o fitness acumulado
	 * segundo = cria vetor com fitness acumulado
	 * terceiro = seleciona um numero random * fitness acumulado
	 * quarto = verifica que faixa ele cai e seleciona esse individuo
	 * (pega o da frente)
	 */
	public static ArrayList<Cromossomo> selecaoRoletaRussaMelhor(ArrayList<Cromossomo> populacao, int n) {
		ArrayList<Cromossomo> novaPopulacao = new ArrayList<Cromossomo>();
		Cromossomo melhorIndividuo = melhorIndividuo(populacao);
		novaPopulacao.add(melhorIndividuo);
		populacao.remove(melhorIndividuo);
		int numIndividuos = 1;

		HashMap<Double, Cromossomo> fiAcumulado = new HashMap<Double, Cromossomo>();
		double acumulador = 0;

		// Cria mapa com fi acumulado para o sorteio
		for (int i = 0; i < populacao.size(); i++) {
			acumulador = acumulador + populacao.get(i).getFi();
			fiAcumulado.put(acumulador, populacao.get(i));

		}

		Random rd = new Random();
		while (numIndividuos != n) {
			// Aleatorio para escolha de individuo seguindo o fi acumulado
			double aleatorio = acumulador * rd.nextDouble();
			double chaveSelecionado = 0.0;
			boolean verificaCromossomo = false;

			for (double key: fiAcumulado.keySet()) {
				if (!verificaCromossomo && key > aleatorio) {
					chaveSelecionado = key;
					verificaCromossomo = true;
					break;
				}
			}

			if (verificaCromossomo) {
				novaPopulacao.add(fiAcumulado.get(chaveSelecionado));
				fiAcumulado.remove(chaveSelecionado);
				numIndividuos++;
			}
		}

		return novaPopulacao;

	}

	/**
	 * Pega melhor individuo da populacao
	 * @param populacao
	 * @return
	 */
	public static Cromossomo melhorIndividuo(ArrayList<Cromossomo> populacao) {
		Cromossomo melhorIndividuo = populacao.get(0);

		for (int i = 0; i < populacao.size(); i++) {
			Cromossomo c = populacao.get(i);
			if (c.getFi() > melhorIndividuo.getFi()) {
				melhorIndividuo = c;
			}
		}

		return melhorIndividuo;
	}
}
