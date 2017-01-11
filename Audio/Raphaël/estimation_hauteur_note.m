
function freq = estimation_hauteur_note(signal)
Y = fft(signal);
Y=Y(1:floor(length(Y)/2));% transformée de fourier, on ne prend que la première moitié car elle est symétrique
Y=abs(Y);
Z=zeros(1, length(Y));
for i=1000:length(Y)
    Z(i)=Y(i);
end
 % filtre passe-haut afin de se débarasser des effets de bords (la simple
 % concaténation renvoie une erreur...)
f = (find(Z==max(Z)));
freq = f*44100/(length(signal); %on divise par le nombre de secondes du signal, si on est à 44100 Hz de fréquence d'échantillonage
end

