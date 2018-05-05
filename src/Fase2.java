import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Fase2 {
	
	public static double ERRO = 0.001;
	public static DecimalFormat df;
	
	public static ArrayList calculaIntervalo(List<String> intervalosRaizes,Polinomio p, int casasDecimais){
            ArrayList a =  new ArrayList();
		if(intervalosRaizes.size() == 0) System.out.println("Não existem raízes para essa função");
		else {
				while (intervalosRaizes.size() > 0) {
					StringTokenizer token = new StringTokenizer(intervalosRaizes.remove(0),";");
					double raiz1 = Double.parseDouble(token.nextToken().replace(",", "."));
					double raiz2 = Double.parseDouble(token.nextToken().replace(",", "."));
					Polinomio derivada = p.differentiate();
					double x0 = 0;
					double x1 = 0;
		
					/* define o x0
					if (derivada.evaluate(raiz1) < derivada.evaluate(raiz2)) x0 = derivada.evaluate(raiz1);
					else x0 = derivada.evaluate(raiz2);
					*/
					x0 = (raiz2+raiz1)/2;
					System.out.println(x0);
					x1 = iteracao(x0, p);
		
					double resultado = resultadoErro(x0, x1);
		
					// calcula as iterações até um valor menor do erro
					while (resultado > ERRO) {
						x0 = x1;
						x1 = iteracao(x1, p);
						resultado = resultadoErro(x0, x1);
						//System.out.println(resultado); //PARA MOSTRAR APROXIMA��ES RETIRE O COMENT�RIO
					}
					numeroCasas(casasDecimais);
					System.out.println("Raizes: " + df.format(x1));
                                        a.add(df.format(x1));
			} 
		}
                return a;
	}
	
	
	private static double iteracao(double x0, Polinomio p) {
		double x1 = 0;
		Polinomio derivada = p.differentiate();
		
		x1 = x0 - (p.evaluate(x0) / derivada.evaluate(x0));
		
		return x1;
	}
	
	private static double resultadoErro(double x1, double x2) {
		double resultado = Math.abs(x1 - x2);
		return resultado;
	}
	
	private static void numeroCasas(int casasDecimais) {
		String casas = "#.";
		for(int i = 0; i < casasDecimais;i++) {
			casas += "#";
		}
		df = new DecimalFormat(casas);
	}
}
