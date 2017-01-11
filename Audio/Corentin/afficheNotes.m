function void = afficheNotes(notes)
%prend en entrée le tableau notes et 'affiche' les notes 
n=length(notes)/2;
for i=1:n
    f = [f,notes (2*i)];
    t = [t, notes (2*i+1)]; 
    plot (f,t)
end
end

    
