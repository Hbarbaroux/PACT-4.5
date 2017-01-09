function derenv= AttaqueEnveloppe(signal,a)
%après différents tests, la valeur de a la plus pertinente est 0.999
n=length(signal);
env=enveloppe(signal,a);
derenv=derivLarge(env,441)*n;
derenv=abs(derenv);