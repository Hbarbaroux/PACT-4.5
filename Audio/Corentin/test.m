mel=audioread('melodie.mp3');
notesmel=notes(mel);
audiom=ecouterNotes(notesmel);
soundsc(audiom,44100)