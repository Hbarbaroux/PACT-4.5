package fft;

public class Fft extends FftAlgorithm {
    /** 
     * Calcul récursif de la FFT
     * @param a tableau
     * @param root racine primitive n-ième de l'unité
     */
    private static void fftRec(RingNumber[] a, RingNumber root)
    {
	int n = a.length;	// Longueur du tableau
	if (n > 1) {
	    int i;		// Indice de boucle

	    // Séparation des parties paire et impaire
	    RingNumber[] even = new RingNumber [n/2]; // Partie paire
	    RingNumber[] odd  = new RingNumber [n/2]; // Partie impaire
	    for (i = 0; i < n/2; i++) {
		even[i] = a[i*2];
		odd[i]  = a[i*2+1];
	    }

	    // Appels récursifs
	    RingNumber square = root.mult(root); // square = root^2
	    fftRec(even, square);
	    fftRec(odd,  square);

	    // Redistribution
	    RingNumber alpha = root.newNumber(1); // Unité
	    for (i = 0; i < n/2; i++) {
		RingNumber v = alpha.mult(odd[i]);
		a[i] = even[i].add(v);
		a[i+n/2] = even[i].sub(v);
		alpha = alpha.mult(root);
	    }
	}
    }
    /**
     * Calcul de la FFT
     * @param a tableau dont la taille n doit être une puissance de 2
     * @param root racine primitive n-ième de l'unité
     */
    public RingNumber[] fft(RingNumber[] a, RingNumber root) {
	fftRec(a, root);
	return a;
    }
}