function void = afficheNotes(notes)
%prend en entrée le tableau notes et 'affiche' les notes 
n=length(notes)/2;
for i=1:n
    plot([notes(2*i) notes(2*i)],[0 notes(2*i-1)]);
    hold on
end
end

    
