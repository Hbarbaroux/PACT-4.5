package hauteur;

import java.util.ArrayList;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class Signal {

    public final static double pas = Math.pow(10, -6);

    public Array2DRowRealMatrix toMatrice(ArrayList<double[]> liste,
                                          boolean hann, boolean positive) {
        Array2DRowRealMatrix res = new Array2DRowRealMatrix(liste.size(),
                liste.get(0).length);
        for (int i = 0 ; i < liste.size() ; i++) {
            res.setColumn(i, transform(liste.get(i), hann, positive));
        }
        return res;
    }


    /** Fonction appelee par le constructeur qui fait la tf
     * de signal
     * @param signal : signal dont on fait la tf
     * @param hann ; si true, on effectue un fenetrage de hann
     * @param positive : si true, prend la norme de la tf, sinon la partie imaginaire
     * @return
     */
    public static double[] transform(double[] signal, boolean hann, boolean positive) {
        int n = signal.length;

		/* On effectue le fenetrage de Hann */
        if (hann) {
            for(int i = 0; i<n; i++) {
                signal[i] = signal[i] * (0.5 + 0.5 * Math.cos((2 * Math.PI * i)/(n-1)));
            }
        }

		/* On effectue la tf */
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        double[] vecteur = new double[n];

		/* si positive == true, on prend la norme */
        if (positive) {
            vecteur = abs(fft.transform(signal, TransformType.FORWARD));
        }
		/* sinon, on prend la partie imaginaire */
        else {
            vecteur = imag(fft.transform(signal, TransformType.FORWARD));
        }

        return vecteur;
    }


    /** transforme un tableau de complexes en le tableau de
     * ses valeurs absolues
     * @param table
     * @return
     */
    private static double[] abs(Complex[] table) {
        int n = table.length;
        double[] res = new double[n];
        for (int i = 0 ; i < n ; i++) {
            res[i] = table[i].abs();
        }
        return res;
    }



    /** transforme un tableau de complexes en le tableau de
     * ses parties imaginaires
     * @param table
     * @return
     */
    private static double[] imag(Complex[] table) {
        int n = table.length;
        double[] res = new double[n];
        for (int i = 0 ; i < n ; i++) {
            res[i] = table[i].getImaginary();
        }
        return res;
    }


    /** Met tous les termes du vecteur a la puissance alpha
     * @param alpha
     * @return
     */
    public static double[] ebePower(double[] vecteur, double alpha) {
        int n = vecteur.length;
        double[] result = new double[n];
        double x;
        for (int i = 0 ; i < n; i++) {
            x = vecteur[i];
            if (alpha <= 0 && Math.abs(x) <= Math.pow(10, -10)) {
                result[i] = 0.0; //TODO trouver une meilleure solution
            }
            else {
                result[i] = Math.pow(x, alpha);
            }
        }
        return result;
    }


    /** Multiplication de deux vecteurs terme a terme
     * @param A
     * @param B
     * @return
     */
    public static double[] ebeMultiply(double[] A, double[] B) {
        assert(A.length == B.length);
        double[] res = new double[A.length];
        for (int i = 0 ; i < A.length; i++) {
            res[i] = A[i] * B[i];
        }
        return res;
    }


    /** Division de deux vecteurs terme a terme
     * @param A
     * @param B
     * @return
     */
    public static double[] ebeDivide(double[] A, double[] B) {
        assert(A.length == B.length);
        double[] res = new double[A.length];
        for (int i = 0 ; i < A.length; i++) {
            if (Math.abs(B[i]) < pas) {
                res[i] = 0; //TODO trouver une meilleure solution !
            }
            else {
                res[i] = A[i] / B[i];
            }
        }
        return res;
    }


    /** soustraction de deux vecteurs terme a terme
     * @param A
     * @param B
     * @return
     */
    public static double[] subtract(double[] A, double[] B) {
        assert(A.length == B.length);
        double[] res = new double[A.length];
        for (int i = 0 ; i < A.length; i++) {
            res[i] = A[i] - B[i];
        }
        return res;
    }

    /** norme L1 d'un vecteur
     * @param A
     * @return
     */
    public static double L1Norm(double[] A) {
        double res = 0;
        for (int i = 0 ; i < A.length; i++) {
            res += Math.abs(A[i]);
        }
        return res;
    }
}
