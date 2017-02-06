function [ audio ] = transcription_sheet(midifile)
audio = notes(readmidi(midifile));
end

