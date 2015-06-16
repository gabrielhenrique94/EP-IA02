import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	/** Armazena todas as combinacoes que sao geradas no permuta cidades*/
	private static ArrayList<ArrayList<Integer>> combinacoesCidades = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * Cria a populacao que sera utilizada pelo algoritmo genetico
	 * @param cidades
	 * @param posicaoCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> criaPopulacao (HashMap<Integer, double[]> mapaCidades) {
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
			Cromossomo novo = new Cromossomo(combinacoesCidades.get(i), calculaPercurso(mapaCidades, combinacoesCidades.get(i)));			
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
