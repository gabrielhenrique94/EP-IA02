import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessaCidades {
	
	public static HashMap<Integer, int[]> lerArquivoCidades(String arquivo) {
		BufferedReader br = null;
		HashMap<Integer, int[]> mapaCidades = new HashMap<Integer, int[]>();
		try {
 
			String line;
 
			br = new BufferedReader(new FileReader(arquivo));
			int count = 1;
			
			
			while ((line = br.readLine()) != null) {
				String[] valores = line.split(" ");
				int[] coordenadas = new int[2];
				// Arrumar valores daqui.
				coordenadas[0] = Integer.parseInt(valores[1].split("e")[0]);
				coordenadas[1] = Integer.parseInt(valores[2].split("e")[0]);
				mapaCidades.put(count, coordenadas);
				count++;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return mapaCidades;
	}
	
	protected static ArrayList<ArrayList<Integer>> combinacoesCidades;
	
	/**
	 * Cria a populacao que sera utilizada pelo algoritmo genetico
	 * @param cidades
	 * @param posicaoCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> criaPopulacao (HashMap<Integer, int[]> posicaoCidades) {
		ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();
		
		/*cria todas as possiveis combinacoes de percurso entre as cidades*/
		int[] cidades = new int[100];
		for (int i = 1; i <= 100; i++) {
			cidades[i-1] = i;
		}
		
		permutaCidades(cidades, 0);
		
		/*cria cromossomo para cada combinacao de cidade criada*/
		for (int i = 0; i < combinacoesCidades.size(); i++) {
			Cromossomo novo = new Cromossomo(combinacoesCidades.get(i), calculaPercurso(posicaoCidades, combinacoesCidades.get(i)));
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
	
	/**
	 * Algoritmo de permutacao entre objetos, baseado no artigo disponibilizado no site: 
	 * http://www.sanfoundry.com/java-program-generate-all-possible-combinations-given-list-numbers/
	 * @param cidades
	 * @param k
	 */
	public static void permutaCidades(int[] cidades, int k) {
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
 
                permutaCidades(cidades, k + 1);
 
                temp = cidades[k];
                cidades[k] = cidades[i];
                cidades[i] = temp;
            }
        }
    }

}
