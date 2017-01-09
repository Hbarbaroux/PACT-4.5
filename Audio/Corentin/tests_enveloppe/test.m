function tst=test(y2,a)
n=normalisation(enveloppe(y2,a));
plot([1:70272],normalisation(AttaqueEnveloppe(y2,a)),[1:70272],n);