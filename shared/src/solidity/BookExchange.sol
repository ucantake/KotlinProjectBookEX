// Пример смарт-контракта для блокчейна Ethereum на языке Solidity
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
