

public class Decoder {

    public static final int MAIS = 0, MENOS = 1, VEZES = 2, DIVIDIDO = 3;

    /**
     * M�todo recebe uma string por par�metro e devolve um objeto polin�mio com o conte�do dela
     * Express�es ter�o formato: 3x^2 + x + 5
     * @param input
     * @return
     */
    public static Polinomio decode(String input) {
        return decode("( " + input + " )", 0);
    }

    /**
     * Resolve um par�nteses, recebendo a posi��o em que ele come�a
     * @param input
     * @param i
     * @return
     */
    private static Polinomio decode(String input, int i) {
        // Declara��o de vari�veis
        String[] expressoes = input.split(" ");   // Vetor de express�es, separadas por espa�os
        int num;                                        // Armazenar� o coeficiente de uma express�o
        int expoente;                                   // Armazenar� o coeficiente de uma express�o
        int posExpoente;                                // Armazenar� a posi��o do coeficiente em uma express�o
        int posX;                                       // Armazenar� a posi��o do X em uma express�o
        int sinalAtual;                                 // Armazenar� o sinal desta string
        int ultimoSinal = -1;                           // Armazenar� o �ltimo sinal visto
        Polinomio polinomio = null;                     // Polin�mio a ser retornado

        // Percorrendo as express�es
        for (; i < expressoes.length; i++) {
            String expressao = expressoes[i];

            // Caso esteja fechando este par�nteses
            if (expressao.equals(")"))
                return polinomio;

            // Caso esteja abrindo novo par�nteses
            if (expressao.equals("(")) {

                // Polin�mio tempor�rio ser� igual ao par�nteses resolvido
                Polinomio p = decode(input, i + 1);

                // Avan�ar para posi��o de fechamento do par�nteses
                i = posFechamentoParenteses(input, i);

                // Definir polin�mio a ser retornado
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

                // Definir polin�mio a ser retornado
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

                    // Caso haja um n�mero antes do expoente, elev�-lo
                    if (!expressao.substring(0, posExpoente).equals("")) {
                        num = new Integer(expressao.substring(0, posExpoente));
                        num = (int) Math.pow(num, exp);
                    }
                    else {
                        // Caso contr�rio, elevar polin�mio atual
                        num = polinomio.getA();
                        if (polinomio.getB() != 0)
                            polinomio = new Polinomio(polinomio.getA(), polinomio.getB() * exp);
                        else polinomio = new Polinomio((int)Math.pow(polinomio.getA(),exp), 0);
                    }
                }
                else num = new Integer(expressao);

                // Definir polin�mio a ser retornado
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
     * Caso string passada seja um sinal (+, -, *, /), devolver� uma constante informando qual sinal �
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
     * M�todo une dois polin�mios recebidos utilizando determinado sinal
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
     * Devolve a posi��o em que um par�nteses � fechado
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
