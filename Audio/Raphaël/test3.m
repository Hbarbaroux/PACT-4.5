q = audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/tests/test2.mp3');
audio = ecouterNotes(notes(q));
audiowrite('/cal/homes/rteboul/workspace/PACT-4.5/Audio/tests/test2_2.wav', audio, 44100);