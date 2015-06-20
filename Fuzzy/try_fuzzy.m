function fis = try_fuzzy(lag, Yn, Xn, maxS, minS)

    %while eqm > 0.05 
    %crio novo modelo fuzzy
    fis = fuzzyDin(lag);
    
    %calculo saida obtida pelo modelo(normalizada)
    saida = evalfis(Xn, fis);
    
    
    %desnormalizo saida do modelo fuzzy
    saidaDesnorm = desnormaliza(saida, maxS, minS);
    disp('SAIDA DESNORMALIZADA');
    disp(saidaDesnorm);
    
    %desnormalizo X
    yDesnorm = desnormaliza(Yn, maxS, minS);
    
    disp('Y DESNORM');
    disp(yDesnorm);
    plot(saidaDesnorm);
    hold on
    plot(yDesnorm, '--r');
    
    
    %calculo erro
    erro = calcErro(saidaDesnorm, yDesnorm);
    %eqm = calcEqm(erro);
          
end
