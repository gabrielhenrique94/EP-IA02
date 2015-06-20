function [Vori] = desnormaliza(vetor, maxS, minS)
%retorna vetor original apos a desnormalizacao de um vetor normalzado
Vori = vetor*(maxS - minS) + minS;

end
