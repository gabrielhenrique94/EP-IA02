import java.util.Random;

/**
 * Metodos que podem ser utilizados por outras classes e que nao se encaixam como parte do desenvolvimento de algoritmos geneticos
 * @author heloisa
 *
 */
public class Helpers {
	
	/**
	 * Gera um valor inteiro no intervalo [min;max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static int intAleatorio(int min, int max) {
	    Random random = new Random();

	    int randomNum = random.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
