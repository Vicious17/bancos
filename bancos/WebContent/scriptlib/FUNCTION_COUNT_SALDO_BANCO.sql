create or replace FUNCTION "COUNT_SALDO_BANCO" (pbusqueda varchar2, pcodban varchar2)

RETURN VARCHAR2 AS 

vcount number;

BEGIN

  SELECT DISTINCT count(*) 
  into vcount 
  FROM SALDO_BANCO A 
  WHERE A.CODBAN || A.VALOR1 || A.VALOR2 like '%'||pbusqueda||'%'
  AND A.CODBAN like '%'||pcodban||'%';
  
return vcount;

END;