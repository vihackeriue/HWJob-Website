import { useState } from "react";
import { topUpPoint } from "../../../../services/LoyaltyPointService";
import PrimaryTitle from "../../../ui/title/PrimaryTitle";
import FormInput from "../../../ui/form/FormInput";
import FormSelect from "../../../ui/form/FormSelect";
import PrimaryButton from "../../../ui/button/PrimaryButton";
// import transaction from "../../../../assets/lottie/transaction.json";
// import Lottie from "lottie-react";
const PAYMENT_METHODS = [
  { name: "VNPay", code: "VNPAY" },
  { name: "MoMo", code: "MOMO" },
];

const POINT_RATE = 1; // 1000 VND = 1 điểm

const TopUpPointSection = () => {
  const [paymentForm, setPaymentForm] = useState({
    amount: 10000,
    paymentMethod: "VNPAY",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPaymentForm((prev) => ({
      ...prev,
      [name]: name === "amount" ? Number(value) : value,
    }));
  };

  const rewardPoints = Math.floor(paymentForm.amount / POINT_RATE);

  const handleTopUp = async () => {
    try {
      setLoading(true);
      setError(null);

      const res = await topUpPoint(paymentForm);
      const { payUrl } = res.data;

      window.location.href = payUrl;
    } catch (err) {
      console.error(err);
      setError("Không thể tạo giao dịch nạp điểm");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white p-4 rounded-xl space-y-4">
      {/* <div className="flex justify-center">
        <Lottie animationData={transaction} loop className="w-25 h-25" />
      </div> */}
      <PrimaryTitle>Nạp điểm</PrimaryTitle>

      {/* Số tiền */}
      <FormInput
        label="Số tiền (VND)"
        type="number"
        name="amount"
        value={paymentForm.amount}
        onChange={handleChange}
        min={1000}
      />

      <FormInput
        label="Số điểm nhận được"
        type="text"
        value={`${rewardPoints.toLocaleString()} điểm`}
        readOnly
      />

      {/* Phương thức thanh toán */}
      <FormSelect
        label="Phương thức thanh toán"
        name="paymentMethod"
        selected={PAYMENT_METHODS.find(
          (j) => j.code === paymentForm.paymentMethod
        )}
        value={paymentForm.paymentMethod}
        onChange={handleChange}
        options={PAYMENT_METHODS}
      />

      <PrimaryButton
        onClick={handleTopUp}
        disabled={loading || rewardPoints <= 0}
        className="w-full"
      >
        {loading ? "Đang xử lý..." : "Nạp điểm"}
      </PrimaryButton>

      {error && <p className="text-red-500 text-sm text-center">{error}</p>}
    </div>
  );
};

export default TopUpPointSection;
