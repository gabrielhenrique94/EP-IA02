import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AlgoritmoGenetico {
	
	public void algoritmoGenetico(ArrayList<Cromossomo> cromossomos, HashMap<Integer, int[]> posicaoCidades) {
		
		
	}
	
	public void crossoverOX (Cromossomo pai1, Cromossomo pai2, HashMap<Integer, int[]> posicaoCidades) {
		/*
		 * Parent 1: 8 4 7 |3 6 2 5 1| 9 0
		   Parent 2: 0 |1 2 3| 4 |5 6| 7 8 9
				
		   Child 1:  0 4 7 3 6 2 5 1 8 9
		 */
		ArrayList<Integer> seqPai1 = pai1.getGenotipo();
		ArrayList<Integer> seqPai2 = pai2.getGenotipo();
		
		ArrayList<Integer> filho = new ArrayList<Integer>();
		
		int genes = seqPai1.size();
		
		Map<Integer, Boolean> mascaraPai1 = new HashMap<Integer, Boolean>();
		
		/* Inicializa Mascara Pai 1 */
		for (int i = 0; i < genes; i++) {
			mascaraPai1.put(i, false);
		}
		
		int aleatorio = intAleatorio(0, genes-1);
		
		int genesTrocados = genes/2;
		
		while (genesTrocados > 0) {
			if (aleatorio <= (genes-1) && !mascaraPai1.get(aleatorio)) {
				mascaraPai1.put(aleatorio, true);
				aleatorio++;
				genesTrocados--;
			} else if (aleatorio > (genes-1)) {
				aleatorio = 0;
			}
		}
		
		/* Criando filho*/
		
		ArrayList<Integer> novaSeqPai2 = new ArrayList<Integer>();
		for (int i = 0; i < seqPai2.size(); i++) {
			int cidade2 = seqPai2.get(i);
			if (!mascaraPai1.get(cidade2)) {
				novaSeqPai2.add(cidade2);
			}
		}
		
		int contadorPai2 = 0;
		for (Integer key : mascaraPai1.keySet()) {
		    if(mascaraPai1.get(key)) {
		    	filho.add(seqPai1.get(key));
		    } else {
		    	filho.add(novaSeqPai2.get(contadorPai2));
		    	contadorPai2++;
		    }
		}
		
		
		
	}
	
	public static int intAleatorio(int min, int max) {
	    Random random = new Random();

	    int randomNum = random.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}
