import java.util.ArrayList;
import java.util.HashMap;

public class Mutacao {
	/**
	 * Metodo de mutacao de genes, utilizando mutacao inversiva.
	 *
	 * @param filho
	 *            - cromossomo que sofrera mutacao
	 * @param mapaCidades
	 * @return
	 */
	public static Cromossomo mutacaoInversiva(Cromossomo filho,
			HashMap<Integer, double[]> mapaCidades) {

		ArrayList<Integer> seqAtualFilho = filho.getGenotipo();
		ArrayList<Integer> seqNovaFilho = new ArrayList<Integer>();
		int aleatorio1 = -1;
		int aleatorio2 = -1;

		while (aleatorio1 == aleatorio2) {
			aleatorio1 = Helpers.intAleatorio(0, seqAtualFilho.size() - 1);
			aleatorio2 = Helpers.intAleatorio(0, seqAtualFilho.size() - 1);
		}

		/* Troca as cidades de posicao */
		int cidade1 = seqAtualFilho.get(aleatorio1);
		int cidade2 = seqAtualFilho.get(aleatorio2);

		for (int i = 0; i < seqAtualFilho.size(); i++) {
			if (i == aleatorio2) {
				seqNovaFilho.add(cidade1);
			} else if (i == aleatorio1) {
				seqNovaFilho.add(cidade2);
			} else {
				seqNovaFilho.add(seqAtualFilho.get(i));
			}

		}

		filho.setGenotipo(seqNovaFilho);
		filho.setFi(ProcessaCidades.calculaPercurso(mapaCidades, seqNovaFilho));

		return filho;
	}

	public static Cromossomo mutacaoPorPosicao(Cromossomo filho,
			HashMap<Integer, double[]> mapaCidades, int posicaoAlelo) {
		ArrayList<Integer> seqAtualFilho = filho.getGenotipo();
		ArrayList<Integer> seqNovaFilho = filho.getGenotipo();

		int aleatorio = -1;
		aleatorio = Helpers.intAleatorio(0, seqAtualFilho.size() - 1);
		int valor = -1;

		/* Joga cidade para posicao 'aleatorio' */

		valor = seqAtualFilho.get(posicaoAlelo);

		for (int i = posicaoAlelo; i < aleatorio; i++) {
			seqNovaFilho.add(i, seqAtualFilho.get(i + 1));
		}

		seqNovaFilho.add(aleatorio, valor);

		filho.setGenotipo(seqNovaFilho);
		filho.setFi(ProcessaCidades.calculaPercurso(mapaCidades, seqNovaFilho));

		return filho;

	}
}
