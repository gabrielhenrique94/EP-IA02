import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessaCidades {

	public static HashMap<Integer, double[]> lerArquivoCidades(String arquivo) {
		HashMap<Integer, double[]> mapaCidades = new HashMap<Integer, double[]>();

		File file = new File(arquivo);

		try {

			Scanner scanner = new Scanner(file);
			int count = 1;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] valores = line.split(" ");
				double[] coordenadas = new double[2];
				// Arrumar valores daqui.
				coordenadas[0] = Double.parseDouble(valores[1]);
				coordenadas[1] = Double.parseDouble(valores[2]);
				mapaCidades.put(count, coordenadas);
				count++;

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return mapaCidades;
	}

	/**
	 * Cria uma populacao randomica de n individuos, compostos de 1 a v cidades
	 * @param n - Numero de elementos que se espera ter nessa populacao
	 * @param v - numero maximo da cidade que se deseja encontrar
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> criaPopulacao (int n, int v, HashMap<Integer, double[]> mapaCidades) {
		ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();

		for (int i = 0; i < n; i++) {

			ArrayList<Integer> genotipoi = new ArrayList<Integer>();

			// preenche com os valores de cidade, no caso so vao de 1 a v (100 no caso desse arquivo)
			for (int j = 1; j <= v; j++) {
				genotipoi.add(j);
			}

			Collections.shuffle(genotipoi);
			Cromossomo novo = new Cromossomo(genotipoi, calculaPercurso(mapaCidades, genotipoi));

			populacao.add(novo);
		}

		return populacao;
	}

	/**
	 * Calcula o percurso em uma sequencia de cidades.
	 * @param posicaoCidades
	 * @param sequenciaCidade
	 * @return
	 */
	public static double calculaPercurso(HashMap<Integer, double[]> mapaCidades, ArrayList<Integer> sequenciaCidade) {

		double distanciaPercorrida = 0.0;
		int cidadeAtual = sequenciaCidade.get(0);

		for (int i = 1; i < sequenciaCidade.size(); i++) {
			int cidadeAux = sequenciaCidade.get(i);
			distanciaPercorrida += Math.sqrt(
					Math.pow((mapaCidades.get(cidadeAtual)[0] - mapaCidades.get(cidadeAux)[0]), 2) +
					Math.pow((mapaCidades.get(cidadeAtual)[1] - mapaCidades.get(cidadeAux)[1]), 2));
			cidadeAtual = cidadeAux;
		}

		// Calcula a distancia do ultimo elemento da lista com o primeiro (para formar o circulo entre as cidades)
		distanciaPercorrida += Math.sqrt(
				Math.pow((mapaCidades.get(cidadeAtual)[0] - mapaCidades.get(sequenciaCidade.get(0))[0]), 2) +
				Math.pow((mapaCidades.get(cidadeAtual)[1] - mapaCidades.get(sequenciaCidade.get(0))[1]), 2));


		return distanciaPercorrida;
	}

}
