function signal = creer_signal(Fe, f, m)
signal = []; 
t = (0:Fe)/Fe;
signal=[(sin(2*pi*t*f)).*(1+(0.1*sin(2*pi*t*m)))];
end

