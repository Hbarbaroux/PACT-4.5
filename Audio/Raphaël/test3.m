q = audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/tests/test2.mp3');
audio = ecouterNotes(noteszeropadding(q));
audiowrite('/cal/homes/rteboul/workspace/PACT-4.5/Audio/tests/test2_5.wav', audio, 44100);