import { useEffect } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import PrimaryButton from "../../../components/ui/button/PrimaryButton";
import { FaCheckCircle, FaTimesCircle, FaWallet } from "react-icons/fa";

// abc
const PaymentResult = () => {
  const [params] = useSearchParams();
  const navigate = useNavigate();

  const responseCode = params.get("vnp_ResponseCode");
  const txnRef = params.get("vnp_TxnRef");
  const isSuccess = responseCode === "00";

  useEffect(() => {
    if (isSuccess) {
      console.log("Payment success:", txnRef);
    } else {
      console.log("Payment failed:", responseCode);
    }
  }, []);

  return (
    <>
      <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
        <div className="bg-white shadow-xl rounded-2xl p-8 max-w-md w-full text-center">
          {/* Icon */}
          <div className="flex justify-center mb-4">
            {isSuccess ? (
              <FaCheckCircle className="text-teal-500 text-6xl" />
            ) : (
              <FaTimesCircle className="text-red-500 text-6xl" />
            )}
          </div>

          {/* Title */}
          <h2
            className={`text-2xl font-bold mb-2 ${
              isSuccess ? "text-teal-600" : "text-red-600"
            }`}
          >
            {isSuccess ? "Nạp điểm thành công" : "Thanh toán thất bại"}
          </h2>

          {/* Description */}
          <p className="text-gray-600 mb-4">
            {isSuccess
              ? "Điểm đã được cộng vào ví của bạn."
              : "Giao dịch không thành công. Vui lòng thử lại."}
          </p>

          {/* Transaction info */}
          <div className="bg-gray-50 rounded-lg p-4 mb-6 text-left text-sm">
            <p className="text-gray-500">Mã giao dịch</p>
            <p className="font-semibold text-gray-800 break-all">{txnRef}</p>
          </div>

          {/* Button */}
          <div className="flex justify-center">
            <PrimaryButton onClick={() => navigate("/transaction")}>
              <FaWallet />
              Về ví của tôi
            </PrimaryButton>
          </div>
        </div>
      </div>
    </>
  );
};

export default PaymentResult;
