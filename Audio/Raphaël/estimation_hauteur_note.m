function freq = estimation_hauteur_note(signal)
Fs = 10000; % echantillonage
[y, Fs]=audioread(signal);
Y = fft(y); % transformée de fourier
Z = abs([zeros(1,999),Y(1000:length(Y))]); % filtre passe-haut afin de se débarasser des effets de bords
freq = f(find(Z==max(Z)))/10;
end

