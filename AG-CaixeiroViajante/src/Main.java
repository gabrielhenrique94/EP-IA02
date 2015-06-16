import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		// Fazer um mapa das cidades com chave=numEntrada, valor array[2]= [x,y]
		// pre processamento dos dados, gerar os cromossomos e deixá-los em um ArrayList<Cromossomo>
		//Algoritmo genetico
		HashMap<Integer, double[]> mapaCidades = ProcessaCidades.lerArquivoCidades("src/arquivos/ncit6.txt");
		
		ArrayList<Cromossomo> populacao = ProcessaCidades.criaPopulacao(mapaCidades);
		
		AlgoritmoGenetico.algoritmoGenetico(populacao, mapaCidades);

	}

}

