function dLF=derivLargeFilter (signal,fc)
dLF=filter([1],[1,1/(2*pi*fc)],signal);
