function tst=test(y2,a)
n=normalisation(enveloppe(y2,a));
plot([8820:70272],normalisation(AttaqueEnveloppe(y2,a,4410)),[8820:70272],y2(8820:70272),[8820:70272],n(8820:70272));