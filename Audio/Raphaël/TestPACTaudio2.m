Fs = 10000
[y, Fs]=audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/note.mp3'); % on charge le son enregistré
T = 1/Fs ;
L = 10000;
t = (0:L-1)*T;
Y = fft(y);
env = abs(hilbert(y)); % enveloppe du signal
denv = derivLarge(denv,4400) ./ t(2);
plot(t(1:L),denv(1:L))

