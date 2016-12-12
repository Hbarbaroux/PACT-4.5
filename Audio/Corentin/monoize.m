function mono = monoize (stereo)
%Prend en entrée un tableau représentant un fichier audio stéréo et renvoie
%le tableau mono correspondant
n=length(stereo)
mono=(stereo(:,1)+stereo(:,2))/2
end

