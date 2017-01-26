function dLF=derivLargeFilter (signal,fc)
%on va prendre fc=50Hz environ, pour couper les petites variations de
%l'enveloppe
dLF=filter([1],[1,1/(2*pi*fc)],signal);
