function env = enveloppe (signal,a)
%Prend en entrée un signal et renvoie son enveloppe avec paramètre a
n=length(signal)
e=zeros(n,1)
e(1)=(1-a)*signal(1)*signal(1)
for i=2:n
    e(i)=a*e(i-1)+(1-a)*signal(i)*signal(i)
end
