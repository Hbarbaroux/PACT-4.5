function [results ] = comparaison(audio, sheet, diff)
% fonction renvoyant le tableau de booléens correspondant aux résultats de l'utilisateur
% la difficulté correspond au décalage de temps admissible par l'algo entre-deux notes
na=length(audio);
ns=length(sheet);
dd = diff * 44100;%(obtention du décalage discret depuis le décalage temporel)
marked=zeros(1,ns); %indique si chaque note de la partition a été jouée ou non
results=zeros(1,na);
for k=1:na/2
    lb=audio[k*2]-dd;
    lh=audio[k*2]+dd;
    for j=1:ns/2
        if (sheet[j*2]<=lh) and (sheet[j*2]>=lb) and (sheet[j*2-1]=audio[k*2-1]) and (marked[j]==0)
            results[k]=1;
            marked[j]=1;
        end
    end
end
return results;
end

