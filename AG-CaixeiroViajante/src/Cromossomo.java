import java.util.ArrayList;


public class Cromossomo implements Comparable<Cromossomo> {

	/** Ordem das cidades a que esse cromossomo representa*/
	private ArrayList<Integer> genotipo;
	
	/** Resultado da funcao de fitness: soma dos custos de movimentacao para a sequencia */
	private double fi;
	
	/**
	 * Contrutor
	 * @param genotipo
	 * @param fi
	 */
	public Cromossomo(ArrayList<Integer> genotipo, double fi) {
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
	public double getFi() {
		return fi;
	}
	
	/**
	 * Atribui um novo valor ao atributo funcao de fitness(fi) do objeto, que e calculado por meio da expressao 1/fi,
	 * uma vez que o fitness do melhor cromossomo deve ser o maior possivel, e como queremos calcular para achar a menor
	 * distancia entre as cidades, ao fazer 1/distancia, a distancia menor dara um valor mais alto.
	 * @param fi
	 */
	public void setFi(double fi) {
		this.fi = 1/fi;
	}

	@Override
	public int compareTo(Cromossomo o) {
		int equals = 0;
		
		if (this.fi == o.fi) {
			int genotipoIgual = 1;
			for (int i = 0; i < this.genotipo.size(); i++) {
				if (this.genotipo.get(i) != o.genotipo.get(i)){
					genotipoIgual = 0;
				}
			}
			if (equals != genotipoIgual) {
				equals = genotipoIgual;
			}
		}
		
		return equals;
	}

}
