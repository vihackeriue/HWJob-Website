import { FaCheckCircle } from "react-icons/fa";
import { SiEthereum } from "react-icons/si";

const WEI_TO_ETH = 1e18;

const PaymentHistoryCard = ({ payment }) => {
  const isWithdraw = payment.paymentType === "WITHDRAW";
  const isTopUp = payment.paymentType === "TOP_UP";

  const ethAmount = isWithdraw ? payment.amount / WEI_TO_ETH : null;

  return (
    <div className="bg-lightGrayishBlue border border-gray-300 rounded-xl p-4 hover:border-brightOrange transition">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <span className="text-sm">Mã giao dịch</span>

        <span className="flex items-center gap-1 text-teal-500 text-sm font-medium">
          <FaCheckCircle />
          {payment.status}
        </span>
      </div>

      <p className="text-sm font-mono truncate">{payment.paymentId}</p>

      {/* Amount */}
      <div className="flex justify-between items-center mt-3">
        <div>
          <p className="text-xs text-gray-500">
            {isTopUp ? "Số tiền nạp" : "Số ETH nhận"}
          </p>

          <p className="text-lg font-semibold">
            {isTopUp && <>{payment.amount.toLocaleString()} ₫</>}

            {isWithdraw && <>{ethAmount.toFixed(6)} ETH</>}
          </p>
        </div>

        <div className="text-right">
          <p className="text-xs text-gray-500">Points</p>
          <p
            className={`text-lg font-semibold ${
              isWithdraw ? "text-red-500" : "text-teal-600"
            }`}
          >
            {isWithdraw ? "-" : "+"}
            {payment.points.toLocaleString()}
          </p>
        </div>
      </div>

      {/* Footer */}
      <div className="border-t border-gray-200 mt-3 pt-3 flex justify-between items-center text-xs text-gray-500">
        <span>
          {payment.createdAt
            ? new Date(payment.createdAt).toLocaleString("vi-VN")
            : "--"}
        </span>

        <div className="flex items-center gap-2">
          <span className="px-2 py-0.5 rounded bg-gray-800 text-white">
            {payment.paymentMethod}
          </span>

          {payment.blockchainTxHash && (
            <a
              href={`https://etherscan.io/tx/${payment.blockchainTxHash}`}
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-1 text-purple-500 hover:underline"
            >
              <SiEthereum />
              Tx
            </a>
          )}
        </div>
      </div>
    </div>
  );
};

export default PaymentHistoryCard;
