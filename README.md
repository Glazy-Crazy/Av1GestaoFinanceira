# Este projeto foi desenvolvido por Diego Careno Rodrigues e Matheus Henrique Garcia Queiroz.

# 💰 Sistema de Gestão Financeira Pessoal

Este projeto tem como objetivo desenvolver um sistema para **controle financeiro pessoal** utilizando **Java 17** e **Spring Boot**.  
O sistema permite organizar receitas, despesas, contas, cartões de crédito e acompanhar o histórico de transações.

---

## 📌 Funcionalidades Principais
- Gestão financeira pessoal (controle de contas e saldos)  
- Controle de cartão de crédito (limite, vencimento, fechamento de fatura)  
- Gerenciamento de pendências financeiras (situações: previsto, confirmado, pago, conciliado)  
- Histórico de transações  
- Filtros por período, tipo e categoria  

---

## 🛠️ Tecnologias Utilizadas
- Java 17  
- Spring Boot 3.x  
- Maven  
- Spring Data JPA  
- H2 Database (para testes) / PostgreSQL (produção)  
- Lombok  
- Spring Security  

---

## 📂 Estrutura do Projeto
```
com.gestaofinanceirapessoal
 ├─ GestaoFinanceiraApplication.java   # Classe principal
 ├─ domain
 │   ├─ model         # Entidades (Categoria, Transacao, Conta, Usuario, etc.)
 │   ├─ dto           # Objetos de transferência de dados
 │   ├─ enums         # Tipos enumerados (Situacao, TipoConta, TipoFormaPagamento)
 ├─ repositories      # Interfaces JPA Repository
 ├─ services          # Regras de negócio
 ├─ controllers       # Endpoints REST
 └─ config            # Configurações do projeto
```
