/*
    Пример смарт-контракта для блокчейна Ethereum на языке Solidity
    TODO: добавить комментарии
    TODO: добавить тесты
    TODO: добавить документацию
    TODO: добавить линтеры
    TODO: добавить CI/CD
    TODO: добавить деплой
    TODO: добавить примеры вызова смарт-контракта
    TODO: добавить примеры вызова смарт-контракта с помощью web3.java
    TODO: добавить примеры вызова смарт-контракта с помощью web3.kotlin

*/
pragma solidity ^0.8.0;

contract BookExchange {
    mapping(address => uint) public balances;

    function reserveFunds(address receiver, uint amount) public returns (bool) {
        require(balances[msg.sender] >= amount, "Insufficient balance");
        balances[msg.sender] -= amount;
        balances[receiver] += amount;
        return true;
    }

    function releaseFunds(address receiver, uint amount) public returns (bool) {
        require(balances[receiver] >= amount, "Insufficient balance");
        balances[receiver] -= amount;
        balances[msg.sender] += amount;
        return true;
    }
}
