function derenv= AttaqueEnveloppe(signal,a)
[A,B]=butter(2,20000/44100, 'high');
signal=filter(A,B,signal);
%après différents tests, la valeur de a la plus pertinente est 0.999
n=length(signal);
env=enveloppe(signal,a);
d=derivLargeFilter(env,100,20)*n;
derenv =[];
for k=1:length(d)
    derenv = [derenv, max([d(k), 0])]; %#ok<AGROW>
end



