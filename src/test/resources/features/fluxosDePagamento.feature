# language: pt
Funcionalidade: Valida API de Pagamentos

  Cenário: Realizar um pagamento com sucesso
    Dado uma requisição
    Quando processar a requisição
    Então o pagamento será realizado com sucesso

#  Cenário: Consultar os status de um pagamento com sucesso
#    Dado uma requisição
#    Quando processar a requisição
#    Então o pagamento será realizado com sucesso
#
#  Cenário: Confirmar um pagamento com sucesso
#    Dado uma requisição
#    Quando processar a requisição
#    Então o pagamento será realizado com sucesso