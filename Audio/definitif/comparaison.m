function results = comparaison(audio, sheet)
% fonction renvoyant le tableau de booléens correspondant aux résultats de
% l'utilisateur + un entier indiquant le nombre de notes jouées en trop
na=length(audio);
ns=length(sheet);
diff=minecart(sheet)/2; %écart tolérable entre note jouée et note de la partition
dd = diff * 44100;%(obtention du décalage discret depuis le décalage temporel)
results=zeros(1,ns); %indique si chaque note de la partition a été jouée ou non
for k=1:na/2
    lb=audio[k*2]-dd;
    lh=audio[k*2]+dd;
    for j=1:ns/2
        if (sheet[j*2]<=lh) and (sheet[j*2]>=lb) and (sheet[j*2-1]=audio[k*2-1]) and (results[j]==0)
            results[j]=1;
        end
    end
end
results=[results max(0,na-ns)];
return results;
end

