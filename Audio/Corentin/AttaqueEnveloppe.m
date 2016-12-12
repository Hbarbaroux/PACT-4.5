function derenv= AttaqueEnveloppe(signal,a,N)
n=length(signal);
env=enveloppe(signal,a);
derenv=derivLarge(env,N)*n;
derenv=abs(derenv);