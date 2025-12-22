const { ethers } = require("hardhat");

async function main() {
  console.log("Đang triển khai hợp đồng...");
  const HWJob = await ethers.getContractFactory("HWJob");
  const contract = await HWJob.deploy();

  console.log(`HWJob deployed to: ${contract.target}`);
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
