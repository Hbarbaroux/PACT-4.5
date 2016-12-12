function env = enveloppe (signal,a)
%Prend en entrée un signal et renvoie son enveloppe avec paramètre a
n=length(signal)
env=zeros(1,n)
env(1)=(1-a)*signal(1)*signal(1)
for i=2:n
    env(i)=a*env(i-1)+(1-a)*signal(i)*signal(i)
end
