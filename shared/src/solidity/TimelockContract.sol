// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Timelock {
    address public owner;
    address public user2;
    uint256 public releaseTime;
    uint256 public amount;
    uint256 public confirmations;

    event FundDeposited(address indexed depositor, uint256 amount);
    event FundWithdrawn(uint256 amount);
    event Confirmation(address indexed confirmer);

    constructor(uint256 _releaseTime, address _user2) {
        owner = msg.sender;
        user2 = _user2;
        releaseTime = _releaseTime;
    }

    modifier onlyOwnerOrUser2() {
        require(msg.sender == owner || msg.sender == user2, "Not authorized");
        _;
    }

    modifier onlyAfterRelease() {
        require(block.timestamp >= releaseTime, "Not yet released");
        _;
    }

    function deposit() external payable onlyOwnerOrUser2 {
        require(msg.value > 0, "Amount should be greater than 0");
        amount += msg.value;
        emit FundDeposited(msg.sender, msg.value);
    }

    function confirm() external onlyOwnerOrUser2 {
        confirmations++;
        emit Confirmation(msg.sender);
    }

    function withdraw() external onlyOwner onlyAfterRelease {
        require(confirmations == 2, "Not enough confirmations");
        require(amount > 0, "No funds available");
        payable(owner).transfer(amount);
        emit FundWithdrawn(amount);
        amount = 0;
        confirmations = 0; // Reset confirmations after successful withdrawal
    }

    function getReleaseTime() external view returns (uint256) {
        return releaseTime;
    }

    function getAmount() external view returns (uint256) {
        return amount;
    }

    function getConfirmations() external view returns (uint256) {
        return confirmations;
    }
}
