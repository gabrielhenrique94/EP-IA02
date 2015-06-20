function [S, X, Y, Sn, Xtre, Ytre, Xtes, Ytes, treino, teste, maxS, minS] = main(lag, caminhoTreino, caminhoTeste)
%leio os arquivos
treino = le_arquivo(caminhoTreino);
teste =  le_arquivo(caminhoTeste);

%junto os 2
S = [treino;teste];
%normalizo S e guardo os valores max e min
[Sn, maxS, minS] = normaliza(S);
%preparo as matrizes com o lag
[X,Y] = prepara(Sn, lag);

%Separo as variaveis em treino e teste novamente
Xtre = X([1:end-length(teste)], :);
Ytre = Y([1:end-length(teste)], :);
Xtes = X(length(treino)-1:end, :);
Ytes = Y(length(treino)-1:end, :);



%rodo o fuzzy
fis = try_fuzzy(lag, Ytre, Xtre, maxS, minS);


end
