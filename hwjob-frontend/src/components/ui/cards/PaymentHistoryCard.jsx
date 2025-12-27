import { FaCheckCircle } from "react-icons/fa";
import { SiEthereum } from "react-icons/si";

const PaymentHistoryCard = ({ payment }) => {
  return (
    <div className="bg-lightGrayishBlue border border-gray-300 rounded-xl p-4 hover:border-brightOrange transition">
      {/* Header */}
      <div className="flex items-center justify-between mb-2">
        <span className="text-sm">Mã giao dịch</span>

        <span className="flex items-center gap-1 text-teal-400 text-sm font-medium">
          <FaCheckCircle />
          {payment.status}
        </span>
      </div>

      <p className="text-sm font-mono truncate">{payment.paymentId}</p>

      {/* Amount */}
      <div className="flex justify-between items-center mt-2">
        <div>
          <p className="text-xs ">Số tiền nạp</p>
          <p className="text-lg font-semibold ">
            {payment.amount.toLocaleString()} ₫
          </p>
        </div>

        <div className="text-right">
          <p className="text-xs ">Points</p>
          <p className="text-lg font-semibold text-teal-500">
            +{payment.points.toLocaleString()}
          </p>
        </div>
      </div>

      {/* Footer */}
      <div className="border-t border-gray-800 mt-2 pt-3 flex justify-between items-center text-xs text-gray-400">
        <span>{new Date(payment.createdAt).toLocaleString("vi-VN")}</span>

        <div className="flex items-center gap-2">
          <span className="px-2 py-0.5 rounded bg-stoneBrown-900 text-white">
            {payment.paymentMethod}
          </span>

          {payment.blockchainTxHash && (
            <a
              href={`http://localhost:4000/tx/${payment.blockchainTxHash}`}
              target="_blank"
              rel="noreferrer"
              className="flex items-center gap-1 text-purple-400 hover:underline"
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
