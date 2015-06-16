import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessaCidades {
	
	public static HashMap<Integer, double[]> lerArquivoCidades(String arquivo) {
		BufferedReader br = null;
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
	public static double calculaPercurso(HashMap<Integer, int[]> posicaoCidades, ArrayList<Integer> sequenciaCidade) {
		int distanciaPercorrida = 0;
		int cidadeAtual = sequenciaCidade.get(0);
		
		for (int i = 1; i < sequenciaCidade.size(); i++) {
			int cidadeAux = sequenciaCidade.get(i);
			distanciaPercorrida += Math.sqrt(
					(posicaoCidades.get(cidadeAtual)[0] - posicaoCidades.get(cidadeAux)[0]) + 
					(posicaoCidades.get(cidadeAtual)[1] - posicaoCidades.get(cidadeAux)[1]));
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
