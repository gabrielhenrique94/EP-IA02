import java.util.ArrayList;


public class Cromossomo {

	/** Ordem das cidades a que esse cromossomo representa*/
	private ArrayList<Integer> genotipo;
	
	/** Resultado da funcao de fitness: soma dos custos de movimentacao para a sequencia */
	private int fi;
	
	/**
	 * Contrutor
	 * @param genotipo
	 * @param fi
	 */
	public Cromossomo(ArrayList<Integer> genotipo, int fi) {
		this.setGenotipo(genotipo);
		this.setFi(fi);
	}

	/**
	 * Retorna o atributo genotipo do objeto
	 * @return
	 */
	public ArrayList<Integer> getGenotipo() {
		return genotipo;
	}

	/**
	 * Atribui um novo valor ao atributo genotipo do objeto
	 * @param genotipo
	 */
	public void setGenotipo(ArrayList<Integer> genotipo) {
		this.genotipo = genotipo;
	}

	/**
	 * Retorna o atributo funcao de fitness(fi) do objeto
	 * @return
	 */
	public int getFi() {
		return fi;
	}
	
	/**
	 * Atribui um novo valor ao atributo funcao de fitness(fi) do objeto
	 * @param fi
	 */
	public void setFi(int fi) {
		this.fi = fi;
	}

}
