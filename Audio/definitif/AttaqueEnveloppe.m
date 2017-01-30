function derenv= AttaqueEnveloppe(signal,a)
%après différents tests, la valeur de a la plus pertinente est 0.999
n=length(signal);
env=enveloppe(signal,a);
d=derivLarge(env,4410);
derenv =[];
for k=1:length(d)
    derenv = [derenv, max([d(k), 0])]; %#ok<AGROW>
end



