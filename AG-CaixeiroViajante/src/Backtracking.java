import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe puramente para teste, pq quero gerar um arquivo com o melhor para verificarmos depois de o GA ta ok,
 * e para nao perder todo trabalho feito
 * @author heloisa
 *
 */
public class Backtracking {

	// TESTE ------------ NAO DEVEMOS ENVIAR PARA O CLODS


	/** Armazena todas as combinacoes que sao geradas no permuta cidades*/
	private static ArrayList<ArrayList<Integer>> combinacoesCidades = new ArrayList<ArrayList<Integer>>();

	/**
	 * Cria a populacao que sera utilizada pelo algoritmo genetico
	 * @param cidades
	 * @param posicaoCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> criaPopulacaoBacktracking (HashMap<Integer, double[]> mapaCidades) {
		ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();

		/* Inicializa as cidade disponiveis*/
		int[] cidades = new int[mapaCidades.size()];
		for (int i = 1; i <= cidades.length; i++) {
			cidades[i-1] = i;
		}

		/*cria todas as possiveis combinacoes de percurso entre as cidades*/
		permutaCidades(cidades, 0, cidades.length);

		/*cria cromossomo para cada combinacao de cidade criada*/
		for (int i = 0; i < combinacoesCidades.size(); i++) {
			Cromossomo novo = new Cromossomo(combinacoesCidades.get(i), ProcessaCidades.calculaPercurso(mapaCidades, combinacoesCidades.get(i)));
			populacao.add(novo);
		}

		return populacao;

	}

	/**
	 * Algoritmo de permutacao entre objetos, baseado no artigo disponibilizado no site:
	 * http://www.sanfoundry.com/java-program-generate-all-possible-combinations-given-list-numbers/
	 * @param cidades
	 * @param k
	 * @param cidadesMax
	 */
	public static void permutaCidades(int[] cidades, int k, int cidadesMax) {
		if (k == cidades.length) {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			for (int i = 0; i < cidades.length; i++) {
				aux.add(cidades[i]);
			}

			combinacoesCidades.add(aux);

		} else {
			for (int i = k; i < cidades.length; i++) {
				int temp = cidades[k];
				cidades[k] = cidades[i];
				cidades[i] = temp;

				permutaCidades(cidades, k + 1, cidadesMax);

				temp = cidades[k];
				cidades[k] = cidades[i];
				cidades[i] = temp;
			}
		}
	}

}
