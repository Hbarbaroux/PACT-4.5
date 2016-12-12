function derenv = AttaqueHilbert(signal)
n=length(signal)
env=abs(hilbert(signal))
derenv=diff(env)*n
