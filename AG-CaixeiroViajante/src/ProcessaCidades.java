import java.util.ArrayList;
import java.util.HashMap;

public class ProcessaCidades {
	
	/**
	 * Calcula o percurso em uma sequencia de cidades.
	 * @param posicaoCidades
	 * @param sequenciaCidade
	 * @return
	 */
	public static int calculaPercurso(HashMap<Integer, int[]> posicaoCidades, ArrayList<Integer> sequenciaCidade) {
		int distanciaPercorrida = 0;
		int cidadeAtual = sequenciaCidade.get(0);
		
		for (int i = 1; i < sequenciaCidade.size(); i++) {
			int cidadeAux = sequenciaCidade.get(i);
			distanciaPercorrida += (int) Math.ceil(Math.sqrt(
					(posicaoCidades.get(cidadeAtual)[0] - posicaoCidades.get(cidadeAux)[0]) + 
					(posicaoCidades.get(cidadeAtual)[1] - posicaoCidades.get(cidadeAux)[1])));
			cidadeAtual = cidadeAux;
		}
		
		return distanciaPercorrida;
	}

}
