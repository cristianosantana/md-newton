import java.util.stream.IntStream;

public class Polinomio {
    private int[] coef;
    private int degree;

    public Polinomio(int a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("exponent cannot be negative: " + b);
        }
        coef = new int[b+1];
        coef[b] = a;
        reduce();
    }

    private void reduce() {
        degree = -1;
        for (int i = coef.length - 1; i >= 0; i--) {
            if (coef[i] != 0) {
                degree = i;
                return;
            }
        }
    }

    public int degree() {
        return degree;
    }

    public Polinomio plus(Polinomio that) {
        Polinomio poly = new Polinomio(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; i++) poly.coef[i] += this.coef[i];
        for (int i = 0; i <= that.degree; i++) poly.coef[i] += that.coef[i];
        poly.reduce();
        return poly;
    }

    public Polinomio minus(Polinomio that) {
        Polinomio poly = new Polinomio(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; i++) poly.coef[i] += this.coef[i];
        for (int i = 0; i <= that.degree; i++) poly.coef[i] -= that.coef[i];
        poly.reduce();
        return poly;
    }

    public Polinomio times(Polinomio that) {
        Polinomio poly = new Polinomio(0, this.degree + that.degree);
        for (int i = 0; i <= this.degree; i++)
            for (int j = 0; j <= that.degree; j++)
                poly.coef[i+j] += (this.coef[i] * that.coef[j]);
        poly.reduce();
        return poly;
    }

    public Polinomio dividir(Polinomio that) {
        Polinomio coeficienteDivisao = null, poly, resto;
        int grau = this.degree;
        if (grau == 0) return new Polinomio(this.coef[0] / that.coef[0], 0);

        for (int dividendo = that.degree; dividendo >= 0; dividendo--) {
            for (int divisor = this.degree; divisor >= 0; divisor--) {
                if ((grau = this.degree - (this.degree - divisor)) >= that.degree && this.coef[divisor] != 0 && that.coef[dividendo] != 0) {
                    if (grau == 0) { poly = new Polinomio(this.coef[dividendo] / that.coef[divisor], 0); }
                    else {
                        if (that.degree - (that.degree - dividendo) == 0) grau++;
                        poly = new Polinomio(this.coef[divisor] / that.coef[dividendo], grau - 1);
                    }
                    if (coeficienteDivisao == null)
                        coeficienteDivisao = poly;
                    else coeficienteDivisao = coeficienteDivisao.plus(poly);
                }
                else break;
            }
        }
        if (coeficienteDivisao != null) {
            resto = this.minus(coeficienteDivisao.times(that));
            return coeficienteDivisao.plus(resto);
        }
        else return this;
    }

    public Polinomio compose(Polinomio that) {
        Polinomio poly = new Polinomio(0, 0);
        for (int i = this.degree; i >= 0; i--) {
            Polinomio term = new Polinomio(this.coef[i], 0);
            poly = term.plus(that.times(poly));
        }
        return poly;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Polinomio that = (Polinomio) other;
        if (this.degree != that.degree) return false;
        for (int i = this.degree; i >= 0; i--)
            if (this.coef[i] != that.coef[i]) return false;
        return true;
    }

    public Polinomio differentiate() {
        if (degree == 0) return new Polinomio(0, 0);
        Polinomio poly = new Polinomio(0, degree - 1);
        poly.degree = degree - 1;
        for (int i = 0; i < degree; i++)
            poly.coef[i] = (i + 1) * coef[i + 1];
        return poly;
    }

    public double evaluate(double x) {
        double p = 0;
        for (int i = degree; i >= 0; i--)
            p = coef[i] + (x * p);
        return p;
    }

    public int compareTo(Polinomio that) {
        if (this.degree < that.degree) return -1;
        if (this.degree > that.degree) return +1;
        for (int i = this.degree; i >= 0; i--) {
            if (this.coef[i] < that.coef[i]) return -1;
            if (this.coef[i] > that.coef[i]) return +1;
        }
        return 0;
    }

    @Override
    public String toString() {
        if      (degree == -1) return "0";
        else if (degree ==  0) return "" + coef[0];
        else if (degree ==  1) return coef[1] + "x + " + coef[0];
        String s = coef[degree] + "x^" + degree;
        for (int i = degree - 1; i >= 0; i--) {
            if      (coef[i] == 0) continue;
            else if (coef[i]  > 0) s = s + " + " + ( coef[i]);
            else if (coef[i]  < 0) s = s + " - " + (-coef[i]);
            if      (i == 1) s = s + "x";
            else if (i >  1) s = s + "x^" + i;
        }
        return s;
    }

    public int getA() {
        return coef[getB()];
    }

    public int getB() {
        return coef.length - 1;
    }

    public int[] getCoef() {
        return coef;
    }

    public boolean ehPolinomioComUmTermo(){

        /**
         * Este IF verifica se o polinomio tem apenas um termo, pois isso faz diferença quando os intervalos
         * das raízes forem encontrados.
         *
         * Ele verifica se a soma dos coeficientes do polinomio é igual ao coeficiente de maior grau,
         * o que significa que todos os coeficientes, com exceção do de maior grau, são ZERO.
         */
        if (IntStream.of(getCoef()).sum() == getCoef()[ degree() ]){
            return true;
        }
        return false;
    }
}


