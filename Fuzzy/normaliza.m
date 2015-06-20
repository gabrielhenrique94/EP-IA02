function [Sn, maxS, minS] = normaliza(X)
[l,c] = size(X);
%normaliza as matrizes juntas
maxS = max(X);
minS = min(X);
Sn = (X-ones(l,1)*min(X))./(ones(l,1) * (max(X)-min(X)))

end
