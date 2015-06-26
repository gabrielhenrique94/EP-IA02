import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
}
