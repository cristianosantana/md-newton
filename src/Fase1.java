import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

public class Fase1 {

    // Fixar valores em 2 casas decimais
    public static DecimalFormat df = new DecimalFormat("#.##");

    // Define o intervalo
    public static double INTERVALO_INCIO = -10;
    public static double INTERVALO_FIM = 10;
    public static double INTERVALO_TAMANHO = 100;
    public static double INTERVALO_PASSO = (abs(INTERVALO_FIM) + abs(INTERVALO_INCIO)) / INTERVALO_TAMANHO;


    public static List<Double> calcularNoIntervalo(Polinomio p) {

        double fx = 0;
        List<Double> funcoes = new ArrayList<>(100);

        for (double x = INTERVALO_INCIO; x <= INTERVALO_FIM; x+=INTERVALO_PASSO) {
            fx = 0;
            for (int i = 0; i < p.getCoef().length; i++) {

                fx += (p.getCoef()[i])*(Math.pow(x,i));

            }
            funcoes.add(fx);
        }
        return funcoes;
    }

    public static void mostrarFuncoesCalculadas(List<Double> funcoes) {

        double x = INTERVALO_INCIO;

        // Percorre a lista de funcoes e imprime o valor da funcao para cada valor de x
        for (double funcao : funcoes) {
            System.out.println("Para x = " + df.format(x) + ":   f(x) = " + df.format(funcao));
            x+=INTERVALO_PASSO;
        }
        System.out.println("\n\n -------------------------- \n");

    }

    public static List<String> raizesNoIntervalo(List<Double> funcoes, List<Double> funcoesDerivadas){

        double x = INTERVALO_INCIO;
        List<String> intervalosComRaiz = new ArrayList<>();

        for (int i = 0; i < funcoes.size()-1; i++) {
            String intervalo = "[" + df.format(x) + "|" + df.format(x+INTERVALO_PASSO) + "]";


            /**
             *  Adicione    
             *  ao if abaixo para tambem receber o intervalo que contem os pontos minimos e maximos
             */
            
            if ( (funcoes.get(i)*funcoes.get(i+1) > 0 && funcoesDerivadas.get(i)*funcoesDerivadas.get(i+1) < 0) ||
                    funcoes.get(i)*funcoes.get(i+1) < 0) {

                intervalosComRaiz.add(intervalo);
            }

            x+=INTERVALO_PASSO;
        }
        return intervalosComRaiz;
    }

    public static void mostrarIntervalosComRaiz(List<String> intervalosComRaiz){
        if (intervalosComRaiz.size() > 0) {
            for (String intervalo : intervalosComRaiz) {
                System.out.println(intervalo);
            }
        } else System.out.println("Não existem raízes para essa função");
    }
    
    public static List<String> retornaIntervalosComRaiz(List<String> intervalosComRaiz){
    	List<String> intervalos = new ArrayList<String>();
        while(intervalosComRaiz.size() > 0) {
            String formataIntervalosRaiz = intervalosComRaiz.remove(0).replace("[", "").replace("]", "").replace("|", ";");
            intervalos.add(formataIntervalosRaiz);
        }
        return intervalos;
    }

}
