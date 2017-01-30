
function freq = estimation_hauteur_note(signal)
Y = fft(signal);
Y=Y(1:floor(length(Y)/2));% transformée de fourier, on ne prend que la première moitié car elle est symétrique
Y=abs(Y);
n = length(signal);
Z=zeros(1, length(Y));
for i=floor(100*44100/n):min([length(Y), floor(2000*44100/n)])
    Z(i)=Y(i);
end
 % filtre passe-haut afin de se débarasser des effets de bords (la simple
 % concaténation renvoie une erreur...)
f=findmax(Z);
freq = f*44100/(length(signal)); %on divise par le nombre de secondes du signal, si on est à 44100 Hz de fréquence d'échantillonage
end

