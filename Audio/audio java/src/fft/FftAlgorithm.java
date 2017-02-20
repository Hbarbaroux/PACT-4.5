package fft;

public abstract class FftAlgorithm {
    /**
     * Calcul du papillon
     */
    protected static void butterfly(RingNumber[] a, int i, int j, RingNumber alpha)
    {
	RingNumber u = a[i];
	RingNumber v = alpha.mult(a[j]);
	a[i] = u.add(v);
	a[j] = u.sub(v);
    }
    /**
     * Calcul de la FFT
     * @param a tableau dont la taille n doit être une puissance de 2
     * @param root racine primitive n-ième de l'unité
     */
    public abstract RingNumber[] fft(RingNumber[] a, RingNumber root);
}