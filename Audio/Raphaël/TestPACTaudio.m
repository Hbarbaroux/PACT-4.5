Fs = 1000
[y, Fs]=audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/note.mp3'); % on charge le son enregistré
T = 1/Fs ;
L = 1000;
t = (0:L-1)*T;
Y = fft(y);
env=abs(hilbert(y)); % enveloppe du signal
denv = diff(env) ./ t(1); % dérive l'enveloppe du signal (pour un echantillonage constant)
plot(1000*t(1:L), env(1:L), 1000*t(1:L), denv(1:L))

