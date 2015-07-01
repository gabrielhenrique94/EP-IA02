import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {

		/* Gera um mapa com a posicao de cada cidade nele, no formato {num_cidade: [x, y]} */
		HashMap<Integer, double[]> mapaCidades = ProcessaPopulacao.lerArquivoCidades("src/arquivos/ncit100.txt");

		/* Valor maximo de cidades*/
		int v = mapaCidades.size();

		/* Tamanho da populacao que se deseja testar*/
		int n = Integer.parseInt(args[0]);

		/* Numero de geracoes*/
		int geracoes = Integer.parseInt(args[1]);

		double taxaMutacao = Double.parseDouble(args[2]);

		double taxaCrossover = Double.parseDouble(args[3]);

		/* Dados aceitaveis crossover-mutacao-crosmut-selecao*/
		String[] operadoresSelecao = args[4].split("-");
		ArrayList<String> operadoresValidos = new ArrayList<String>();
		for (int i = 0; i < operadoresSelecao.length; i++) {
			switch (operadoresSelecao[i]) {
			case "crossover":
				operadoresValidos.add("crossover");
				break;
			case "mutacao":
				operadoresValidos.add("mutacao");
				break;
			case "crosmut":
				operadoresValidos.add("crosmut");
				break;
			default:
				System.out.println("Você colocou um operador invalido. Os operadores permitidos são: crossover-mutacao-crosmut");
				break;
			}
		}

		//Backtracking.criaPopulacaoBacktracking(mapaCidades);

		/* Gera a populacao completa que voce pode alcancar com essas cidades, fazendo todas as combinacoes possiveis
		e calculando o percurso utilizado em cada uma delas. Gera os cromossomos que sao os elementos que serao utilizados*/
		ArrayList<Cromossomo> populacao = ProcessaPopulacao.criaPopulacao(n, v, mapaCidades);

		/* Numero de geracoes que o algoritmo genetico deve gerar*/


		/* Passa a populacao completa e o mapa das cidades para o algoritmo genetico que sera responsavel por executar o processo */
		AlgoritmoGenetico ag = new AlgoritmoGenetico(populacao, mapaCidades, geracoes, n, taxaMutacao, taxaCrossover, operadoresValidos);
		ag.iniciaAlgoritmoGenetico();

	}

}

