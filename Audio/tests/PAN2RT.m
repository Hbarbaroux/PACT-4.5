Fs = 44100;
[q, Fs] = audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/tests/signal1.wav'); 
estim_note_autoc(q)
plot(q)
