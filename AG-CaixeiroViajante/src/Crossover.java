import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Crossover {
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
			;
			pf = Helpers.intAleatorio(0, genes - 1);
		}

		// System.out.println("Posições sorteadas:" + p0 + " " + pf);

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
}
