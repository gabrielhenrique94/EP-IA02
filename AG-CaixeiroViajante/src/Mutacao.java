import java.util.ArrayList;
import java.util.HashMap;

public class Mutacao {

	/**
	 * Cria uma subpopulacao de elementos mutados
	 * @param populacao
	 * @param mapaCidades
	 * @return
	 */
	public static ArrayList<Cromossomo> selecaoMutacao(ArrayList<Cromossomo> populacao, HashMap<Integer, double[]> mapaCidades, double taxaMutacao) {
		ArrayList<Cromossomo> subpopulacaoCrossover = new ArrayList<Cromossomo>();

		// Faz mutacao, ou pelo menos tenta fazer, em todos os cromossomos da populacao, criando uma populacao do mesmo tamanho da original
		for (int i = 0; i < populacao.size(); i++) {
			Cromossomo cromossomo = populacao.get(i);
			subpopulacaoCrossover.add(mutacaoInversiva(cromossomo, mapaCidades, taxaMutacao));
			subpopulacaoCrossover.add(mutacaoPorPosicao(cromossomo, mapaCidades, taxaMutacao));
		}
		return subpopulacaoCrossover;
	}

	/**
	 * Metodo de mutacao de genes, utilizando mutacao inversiva.
	 *
	 * @param filho - cromossomo que sofrera mutacao
	 * @param mapaCidades
	 * @return
	 */
	public static Cromossomo mutacaoInversiva(Cromossomo filho, HashMap<Integer, double[]> mapaCidades, double taxaMutacao) {
		ArrayList<Integer> genotipo = filho.getGenotipo();

		/*Fazer teste de mutacao para pelo menos o numero de alelos no cromossomo*/
		for (int i = 0; i < genotipo.size(); i++) {
			if (Math.random() <= taxaMutacao) {
				int aleatorio1 = -1;
				int aleatorio2 = -1;

				while (aleatorio1 == aleatorio2) {
					aleatorio1 = Helpers.intAleatorio(0, genotipo.size() - 1);
					aleatorio2 = Helpers.intAleatorio(0, genotipo.size() - 1);
				}


				/* Troca as cidades de posicao */
				int cidade1 = genotipo.get(aleatorio1);
				int cidade2 = genotipo.get(aleatorio2);

				genotipo.remove(aleatorio1);
				genotipo.add(aleatorio1, cidade2);

				genotipo.remove(aleatorio2);
				genotipo.add(aleatorio2, cidade1);
			}

		}



		filho.setGenotipo(genotipo);
		filho.setFi(ProcessaPopulacao.calculaPercurso(mapaCidades, genotipo));

		return filho;
	}

	public static Cromossomo mutacaoPorPosicao(Cromossomo filho, HashMap<Integer, double[]> mapaCidades, double taxaMutacao) {
		ArrayList<Integer> seqAtualFilho = filho.getGenotipo();
		ArrayList<Integer> seqNovaFilho = seqAtualFilho;
		int posicaoAlelo=-1;
		int aleatorio = -1;
		int valor = -1;

		for (int i = 0; i < seqAtualFilho.size(); i++) {
			if (Math.random() <= taxaMutacao) {
				posicaoAlelo = i;

				aleatorio = Helpers.intAleatorio(0, seqAtualFilho.size()-1);
				valor = seqAtualFilho.get(posicaoAlelo);
				int valor2 = -1;

				if(aleatorio>posicaoAlelo){
					for (int j=posicaoAlelo;j<aleatorio; j++){
						valor2= seqAtualFilho.get(j+1);
						seqNovaFilho.remove(j);
						seqNovaFilho.add(j, valor2 );

					}
					seqNovaFilho.remove(aleatorio);
					seqNovaFilho.add(aleatorio, valor);

				}else if(aleatorio<i){

					for (int j=i;j>aleatorio; j--){
						valor2= seqAtualFilho.get(j-1);
						seqNovaFilho.remove(j);
						seqNovaFilho.add(j, valor2);
					}
					seqNovaFilho.remove(aleatorio);
					seqNovaFilho.add(aleatorio, valor);

				}else continue;

				filho.setGenotipo(seqNovaFilho);
				filho.setFi(ProcessaPopulacao.calculaPercurso(mapaCidades, seqNovaFilho));
			}


		}



		return filho;

	}


}
