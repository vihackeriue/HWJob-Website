// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract HWJob is ERC20, Ownable {

    struct JobHistory {
        uint256 jobId;
        address client;
        address freelancer;
        uint256 amount;
        string metadataHash; // Mã băm nội dung công việc (từ backend)
        uint256 timestamp;
    }

    // Lưu trữ lịch sử theo Freelancer và theo JobId
    mapping(uint256 => JobHistory) public jobs;
    mapping(address => uint256[]) public freelancerJobs;

    event JobCompleted(uint256 indexed jobId, address indexed freelancer, uint256 amount);

    constructor() ERC20("Freelance Point", "FLP") Ownable(msg.sender) {}

    // 1. Chức năng nạp điểm (Chỉ Admin/Backend gọi sau khi nhận tiền thật)
    function mintPoint(address to, uint256 amount) external onlyOwner {
        _mint(to, amount);
    }

    // 2. Thanh toán & Ghi lịch sử công việc
    // Hàm này chuyển điểm từ Recruiter sang Freelancer và lưu vết dự án
    function payAndRecordJob(
        uint256 _jobId,
        address _freelancer,
        uint256 _amount,
        string memory _metadataHash
    ) external {
        require(balanceOf(msg.sender) >= _amount, "Khong du diem de thanh toan");

        // Chuyển điểm
        _transfer(msg.sender, _freelancer, _amount);

        // Lưu lịch sử
        JobHistory memory newJob = JobHistory({
            jobId: _jobId,
            client: msg.sender,
            freelancer: _freelancer,
            amount: _amount,
            metadataHash: _metadataHash,
            timestamp: block.timestamp
        });

        jobs[_jobId] = newJob;
        freelancerJobs[_freelancer].push(_jobId);

        emit JobCompleted(_jobId, _freelancer, _amount);
    }

    // 3. Rút điểm (Đốt điểm để nhận tiền thật)
    function withdrawPoint(uint256 amount) external {
        _burn(msg.sender, amount);
    }
}