// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

/**
 * HWJob
 * - FLP: điểm thưởng (ERC20)
 * - REP: điểm uy tín (off-token, mapping)
 * - Custodial: backend là owner
 */
contract HWJob is ERC20, Ownable {

    /* ===================== CONSTANT ===================== */
    uint256 public constant INITIAL_REPUTATION = 80;
    uint256 public constant GOOD_JOB_REWARD = 5;      // +5 REP
    uint256 public constant VIOLATION_PENALTY = 5;    // -5%

    /* ===================== STORAGE ===================== */

    // Điểm thưởng đang bị khóa (escrow)
    mapping(address => uint256) public lockedBalance;

    // Điểm uy tín
    mapping(address => uint256) public reputation;

    // Kiểm tra user đã đăng ký hay chưa
    mapping(address => bool) public isRegistered;

    /* ===================== EVENTS ===================== */

    event PointMinted(address indexed to, uint256 amount);
    event PointBurned(address indexed from, uint256 amount);

    event Locked(address indexed recruiter, uint256 amount);
    event Released(address indexed freelancer, uint256 amount);
    event Refunded(address indexed recruiter, uint256 amount);

    event ReputationInitialized(address indexed user, uint256 value);
    event ReputationIncreased(address indexed user, uint256 newValue);
    event ReputationDecreased(address indexed user, uint256 newValue);

    event UserRegistered(address indexed user);
    event ReputationPenalty(address indexed user, uint256 penalty, uint256 newValue);
    /* ===================== CONSTRUCTOR ===================== */

    constructor()
    ERC20("Freelance Point", "FLP")
    Ownable(msg.sender)
    {}

    /* ===================================================== */
    /* ===================== ADMIN ========================= */
    /* ===================================================== */

    /**
     * Backend đăng ký user lần đầu
     */
    function registerUser(address user) external onlyOwner {
        require(!isRegistered[user], "User da dang ky");

        // Điểm thưởng mặc định = 0
        _mint(user, 0); // optional, đồng nhất luồng mint

        // Khởi tạo uy tín
        reputation[user] = INITIAL_REPUTATION;

        // Flag đăng ký
        isRegistered[user] = true;

        emit ReputationInitialized(user, INITIAL_REPUTATION);
        emit UserRegistered(user);
    }

    /**
     * Backend nạp điểm thưởng sau khi nhận tiền thật
     */
    function mintPoint(address to, uint256 amount) external onlyOwner {
        require(isRegistered[to], "User chua dang ky");
        _mint(to, amount);
        emit PointMinted(to, amount);
    }

    /**
     * Backend burn khi user rút tiền thật
     */
    function burnPoint(address from, uint256 amount) external onlyOwner {
        require(isRegistered[from], "User chua dang ky");
        _burn(from, amount);
        emit PointBurned(from, amount);
    }

    /* ===================================================== */
    /* ===================== ESCROW ======================== */
    /* ===================================================== */

    /**
     * Recruiter lock điểm khi freelancer nhận job
     */
    function lockForJobForRecruiter(
        address recruiter,
        uint256 amount
    ) external onlyOwner {
        require(amount > 0, "Amount phai > 0");
        require(isRegistered[recruiter], "Recruiter chua dang ky");
        require(balanceOf(recruiter) >= amount, "Khong du diem");

        _transfer(recruiter, address(this), amount);
        lockedBalance[recruiter] += amount;

        emit Locked(recruiter, amount);
    }

    /**
     * Backend xác nhận hoàn thành -> trả điểm cho freelancer
     */
    function completeJob(
        address recruiter,
        address freelancer,
        uint256 amount,
        bool goodPerformance
    ) external onlyOwner {
        require(isRegistered[freelancer], "Freelancer chua dang ky");
        require(lockedBalance[recruiter] >= amount, "Khong du diem lock");

        lockedBalance[recruiter] -= amount;
        _transfer(address(this), freelancer, amount);

        if (goodPerformance) {
            reputation[freelancer] += GOOD_JOB_REWARD;
            emit ReputationIncreased(freelancer, reputation[freelancer]);
        }

        emit Released(freelancer, amount);
    }

    /**
     * Backend xác nhận vi phạm / fail -> refund recruiter + trừ uy tín
     */
    function failJob(
        address recruiter,
        address freelancer,
        uint256 amount
    ) external onlyOwner {
        require(isRegistered[freelancer], "Freelancer chua dang ky");
        require(lockedBalance[recruiter] >= amount, "Khong du diem lock");

        lockedBalance[recruiter] -= amount;
        _transfer(address(this), recruiter, amount);

        uint256 penalty = (reputation[freelancer] * VIOLATION_PENALTY) / 100;
        if (penalty > reputation[freelancer]) {
            reputation[freelancer] = 0;
        } else {
            reputation[freelancer] -= penalty;
        }

        emit ReputationDecreased(freelancer, reputation[freelancer]);
        emit Refunded(recruiter, amount);
    }

    /**
    * Backend refund điểm cho recruiter (hủy job, timeout, dispute...)
    */
    function refundToRecruiter(
        address recruiter,
        uint256 amount
    ) external onlyOwner {
        require(isRegistered[recruiter], "Recruiter chua dang ky");
        require(amount > 0, "Amount phai > 0");
        require(lockedBalance[recruiter] >= amount, "Khong du diem lock");

        // Giảm số điểm đang lock
        lockedBalance[recruiter] -= amount;

        // Trả điểm từ escrow về recruiter
        _transfer(address(this), recruiter, amount);

        emit Refunded(recruiter, amount);
    }
    /**
    * Backend trừ điểm uy tín của user (fixed amount)
    */
    function penalizeReputation(
        address user,
        uint256 penalty
    ) external onlyOwner {
        require(isRegistered[user], "User chua dang ky");
        require(penalty > 0, "Penalty phai > 0");

        uint256 current = reputation[user];

        if (penalty >= current) {
            reputation[user] = 0;
        } else {
            reputation[user] = current - penalty;
        }

        emit ReputationPenalty(user, penalty, reputation[user]);
    }

    /* ===================================================== */
    /* ===================== VIEW ========================== */
    /* ===================================================== */

    function escrowBalance() external view returns (uint256) {
        return balanceOf(address(this));
    }

    function getReputation(address user) external view returns (uint256) {
        return reputation[user];
    }

    function getLockedBalance(address recruiter) external view returns (uint256) {
        return lockedBalance[recruiter];
    }

}
