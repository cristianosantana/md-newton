

public class Decoder {

    public static final int MAIS = 0, MENOS = 1, VEZES = 2, DIVIDIDO = 3;

    /**
     * Método recebe uma string por parâmetro e devolve um objeto polinômio com o conteúdo dela
     * Expressões terão formato: 3x^2 + x + 5
     * @param input
     * @return
     */
    public static Polinomio decode(String input) {
        return decode("( " + input + " )", 0);
    }

    /**
     * Resolve um parênteses, recebendo a posição em que ele começa
     * @param input
     * @param i
     * @return
     */
    private static Polinomio decode(String input, int i) {
        // Declaração de variáveis
        String[] expressoes = input.split(" ");   // Vetor de expressões, separadas por espaços
        int num;                                        // Armazenará o coeficiente de uma expressão
        int expoente;                                   // Armazenará o coeficiente de uma expressão
        int posExpoente;                                // Armazenará a posição do coeficiente em uma expressão
        int posX;                                       // Armazenará a posição do X em uma expressão
        int sinalAtual;                                 // Armazenará o sinal desta string
        int ultimoSinal = -1;                           // Armazenará o último sinal visto
        Polinomio polinomio = null;                     // Polinômio a ser retornado

        // Percorrendo as expressões
        for (; i < expressoes.length; i++) {
            String expressao = expressoes[i];

            // Caso esteja fechando este parênteses
            if (expressao.equals(")"))
                return polinomio;

            // Caso esteja abrindo novo parênteses
            if (expressao.equals("(")) {

                // Polinômio temporário será igual ao parênteses resolvido
                Polinomio p = decode(input, i + 1);

                // Avançar para posição de fechamento do parênteses
                i = posFechamentoParenteses(input, i);

                // Definir polinômio a ser retornado
                if (polinomio == null)
                    polinomio = p;
                else
                    polinomio = unirPolinomios(polinomio, p, ultimoSinal);

                continue;
            }

            // Caso seja um sinal
            if ((sinalAtual = sinal(expressao)) != -1) {
                ultimoSinal = sinalAtual;
                continue;
            }

            // Caso contenha X
            if ((posX = expressao.indexOf('x')) != -1) {

                // Pegar coeficiente
                if (!expressao.substring(0, posX).equals(""))
                    num = new Integer(expressao.substring(0, posX));
                else num = 1;

                // Pegar expoente
                if ((posExpoente = expressao.indexOf('^')) != -1) {
                    expoente = new Integer(expressao.substring(posExpoente + 1, expressao.length()));
                }
                else expoente = 1;

                // Definir polinômio a ser retornado
                if (polinomio == null)
                    polinomio = new Polinomio(num, expoente);
                else {
                    Polinomio temp = new Polinomio(num, expoente);
                    polinomio = unirPolinomios(polinomio, temp, ultimoSinal);
                }
            }
            else {
                expoente = 0;

                // Caso haja expoente
                if ((posExpoente = expressao.indexOf('^')) != -1) {

                    // Identificando expoente
                    int exp = new Integer(expressao.substring(posExpoente + 1, expressao.length()));

                    // Caso haja um número antes do expoente, elevá-lo
                    if (!expressao.substring(0, posExpoente).equals("")) {
                        num = new Integer(expressao.substring(0, posExpoente));
                        num = (int) Math.pow(num, exp);
                    }
                    else {
                        // Caso contrário, elevar polinômio atual
                        num = polinomio.getA();
                        if (polinomio.getB() != 0)
                            polinomio = new Polinomio(polinomio.getA(), polinomio.getB() * exp);
                        else polinomio = new Polinomio((int)Math.pow(polinomio.getA(),exp), 0);
                    }
                }
                else num = new Integer(expressao);

                // Definir polinômio a ser retornado
                if (polinomio == null) polinomio = new Polinomio(num, expoente);
                else {
                    Polinomio temp = new Polinomio(num, expoente);
                    polinomio = unirPolinomios(polinomio, temp, ultimoSinal);
                }
            }
        }
        return polinomio;
    }

    /**
     * Caso string passada seja um sinal (+, -, *, /), devolverá uma constante informando qual sinal é
     * @param string
     * @return
     */
    private static int sinal(String string) {
        switch (string) {
            case "+":
                return MAIS;
            case "-":
                return MENOS;
            case "*":
                return VEZES;
            case "/":
                return DIVIDIDO;
            default:
                return -1;
        }
    }

    /**
     * Método une dois polinômios recebidos utilizando determinado sinal
     * @param a
     * @param b
     * @param sinalAtual
     * @return
     */
    private static Polinomio unirPolinomios(Polinomio a, Polinomio b, int sinalAtual) {
        switch (sinalAtual) {
            case MAIS:
                a = a.plus(b);
                break;
            case MENOS:
                a = a.minus(b);
                break;
            case VEZES:
                a = a.times(b);
                break;
            case DIVIDIDO:
                a = a.dividir(b);
                break;
        }
        return a;
    }

    /**
     * Devolve a posição em que um parênteses é fechado
     * @param input
     * @param inicio
     * @return
     */
    private static int posFechamentoParenteses(String input, int inicio) {
        String[] expressoes = input.split(" ");
        int i = inicio + 1;
        int numAbertos = 1;
        while (numAbertos > 0) {
            if (expressoes[i].equals("("))
                numAbertos++;
            else if (expressoes[i].equals(")"))
                numAbertos--;
            i++;
        }
        return i-1;
    }

}
