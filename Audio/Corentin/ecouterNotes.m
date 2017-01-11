function audio = ecouterNotes(notes)
%prend en entrée un tableau notes et nous permet de l'écouter
audio=[]
n=length(notes)/2;
for i=1:n-1
    l=notes(2*(i+1))-notes(2*i);
    for j=1:l*44100
        audio=[audio,sin(j*2*pi*notes(2*i-1))];
    end
end
for j=1:44100
    audio=[audio,sin(j*2*pi*notes(2*n-1))];
end

    
        
    