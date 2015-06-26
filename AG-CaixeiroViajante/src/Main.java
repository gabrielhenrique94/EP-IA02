import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		/* Gera um mapa com a posicao de cada cidade nele, no formato {num_cidade: [x, y]} */
		HashMap<Integer, double[]> mapaCidades = ProcessaCidades.lerArquivoCidades("src/arquivos/ncit6.txt");

		/* Tamanho da populacao que se deseja testar*/
		int n = 100;

		/* Valor maximo de cidades*/
		int v = mapaCidades.size();

		/* Gera a populacao completa que voce pode alcancar com essas cidades, fazendo todas as combinacoes possiveis
		e calculando o percurso utilizado em cada uma delas. Gera os cromossomos que sao os elementos que serao utilizados*/
		ArrayList<Cromossomo> populacao = ProcessaCidades.criaPopulacao(n, v, mapaCidades);

		System.out.println(populacao.size());

		/* Passa a populacao completa e o mapa das cidades para o algoritmo genetico que sera responsavel por executar o processo */
		AlgoritmoGenetico ag = new AlgoritmoGenetico(populacao, mapaCidades);
		ag.algoritmoGenetico(populacao, mapaCidades);

	}

}

