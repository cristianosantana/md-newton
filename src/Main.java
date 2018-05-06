import java.util.List;

public class Main {

    public static void main(String[] args) {

        Polinomio p = null;


        p = Decoder.decode("3x^2 - 5x");

        //p = Decoder.decode("( 20x^2 + 2x ) * 2x )");

        //p = Decoder.decode("20x / 2x");

        //p = Decoder.decode("2x^3 + ( 4 * ( x^2 + 5 ) ) + 2");

        //p = Decoder.decode("( ( 2x + 4 ) * x^2 ) + 22");

        //p = Decoder.decode("2 * ( x^3 + ( 2 * x^2 ) + 11 )");

        //p = Decoder.decode("( ( 12^2 ) * x^2 ) + 3x + 10");

        //p = Decoder.decode("3x^2 + x + 5");
        //System.out.println(p);

        System.out.println();

        List<Double> funcoes = Fase1.calcularNoIntervalo(p);
        List<Double> funcoesDerivadas = Fase1.calcularNoIntervalo(p.differentiate());

        Fase1.mostrarFuncoesCalculadas(funcoes);
        Fase1.mostrarFuncoesCalculadas(funcoesDerivadas);

        List<String> raizesNoIntervalo = Fase1.raizesNoIntervalo(p, funcoes, funcoesDerivadas);
        System.out.println("-----------Fase1---------");
        Fase1.mostrarIntervalosComRaiz(raizesNoIntervalo);
        List<String> intervalosRaizes =  Fase1.retornaIntervalosComRaiz(raizesNoIntervalo);
        System.out.println("-----------Fase2---------");
        Fase2.calculaIntervalo(intervalosRaizes,p,4);

    }

}
