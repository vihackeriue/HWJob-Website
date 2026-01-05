import React, { useState, useMemo } from "react";
import { boostJobPost } from "../../../services/jobPostService";
import { toast } from "react-toastify";
import PrimaryButton from "../../ui/button/PrimaryButton";
import FormInput from "../../ui/form/FormInput";
import { FaTimes } from "react-icons/fa";
import { FaMoneyBillTrendUp } from "react-icons/fa6";
import { LuPackageOpen } from "react-icons/lu";
import { FiAlertTriangle, FiClock, FiTag } from "react-icons/fi";
/* ================= CONFIG ================= */
const COST_PER_DAY = 10_000;

const BOOST_PACKAGES = [
  { key: "3_days", label: "3 ngày", days: 3, discountPercent: 5 },
  {
    key: "7_days",
    label: "Theo tuần",
    days: 7,
    discountPercent: 10,
    popular: true,
  },
  { key: "30_days", label: "Theo tháng", days: 30, discountPercent: 20 },
];

/* ================= PRICE CALC ================= */
function calcPackagePrice(pkg) {
  const original = pkg.days * COST_PER_DAY;
  const discount = Math.floor((original * pkg.discountPercent) / 100);

  return {
    original,
    final: original - discount,
    discount,
  };
}

/* ================= COMPONENT ================= */
const BoostJobPostDialog = ({ open, onClose, jobId, onSuccess }) => {
  const [days, setDays] = useState(1);
  const [step, setStep] = useState("FORM");
  const [loading, setLoading] = useState(false);
  const [selectedPackage, setSelectedPackage] = useState(null);

  /* ================= TOTAL COST ================= */
  const totalCost = useMemo(() => {
    if (days <= 0) return 0;

    if (selectedPackage) {
      return calcPackagePrice(selectedPackage).final;
    }

    return days * COST_PER_DAY;
  }, [days, selectedPackage]);

  if (!open) return null;

  /* ================= HANDLERS ================= */
  const handleSelectPackage = (pkg) => {
    setSelectedPackage(pkg);
    setDays(pkg.days);
  };

  const handlePayment = async () => {
    try {
      setLoading(true);

      const payload = selectedPackage
        ? { packageKey: selectedPackage.key }
        : { days };

      await boostJobPost(jobId, payload);

      toast.success("Đẩy tin thành công");
      onSuccess?.();
      onClose();
    } catch (err) {
      toast.error(
        err?.response?.data?.message || err?.message || "Thanh toán thất bại"
      );
    } finally {
      setLoading(false);
    }
  };

  /* ================= UI ================= */
  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-2xl w-[48rem] shadow-lg">
        <div className="mb-4 pb-3 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <FaMoneyBillTrendUp className="text-orange-500 text-xl" />
              <h2 className="text-xl font-semibold">
                Đẩy tin của bạn lên xu hướng
              </h2>
            </div>

            {/* Close icon */}
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-red-500 transition"
            >
              <FaTimes size={20} />
            </button>
          </div>
        </div>

        {/* ================= STEP 1 ================= */}
        {step === "FORM" && (
          <>
            <p className="text-lg font-semibold mb-3">Chọn gói ưu đãi</p>
            <div className="grid grid-cols-3 gap-3 mb-4">
              {BOOST_PACKAGES.map((pkg) => {
                const price = calcPackagePrice(pkg);
                const isActive = selectedPackage?.key === pkg.key;

                return (
                  <div
                    key={pkg.key}
                    onClick={() => handleSelectPackage(pkg)}
                    className={`relative cursor-pointer border rounded-xl p-3 text-center transition ${
                      isActive
                        ? "border-brightOrange bg-amber-50"
                        : "hover:border-amber-500"
                    }`}
                  >
                    {pkg.popular && (
                      <span className="absolute -top-4 left-1/2 -translate-x-1/2 bg-brightOrange text-white text-md px-2 py-0.5 rounded-lg">
                        Phổ biến
                      </span>
                    )}

                    <p className="font-semibold">{pkg.label}</p>
                    <p className="text-xs text-gray-500 line-through mt-1">
                      {price.original.toLocaleString()}
                    </p>
                    <p className="text-lg font-bold text-teal-900">
                      {price.final.toLocaleString()}
                    </p>
                    <p className="text-xs text-green-600 mt-1">
                      -{pkg.discountPercent}%
                    </p>
                  </div>
                );
              })}
            </div>
            {/* MANUAL INPUT */}
            <FormInput
              label="Hoặc nhập số ngày"
              type="number"
              min={1}
              value={days}
              onChange={(e) => {
                setDays(Number(e.target.value));
                setSelectedPackage(null);
              }}
            />
            {/* ===== COST ===== */}
            <div className="mt-4 rounded-xl border border-gray-200 bg-gradient-to-br from-gray-50 to-white p-4">
              <div className="flex items-center gap-3">
                <div className="flex h-10 w-10 items-center justify-center rounded-full bg-teal-50 text-teal-900">
                  <LuPackageOpen className="size-8" />
                </div>

                <div className="flex-1">
                  <p className="text-sm ">Chi phí cần thanh toán</p>
                  <p className="text-xl font-bold text-teal-600">
                    {totalCost.toLocaleString()}{" "}
                    <span className="text-sm font-medium text-gray-500">
                      điểm
                    </span>
                  </p>
                </div>
              </div>

              {/* PACKAGE INFO */}
              <div className="mt-3 flex items-center gap-2 text-sm">
                {selectedPackage ? (
                  <>
                    <FiTag className="text-green-600" size={14} />
                    <span className="text-green-600 font-medium">
                      Gói {selectedPackage.label}
                    </span>
                    <span className="text-green-600">
                      (giảm {selectedPackage.discountPercent}%)
                    </span>
                  </>
                ) : (
                  <>
                    <span className="text-gray-500">
                      {COST_PER_DAY.toLocaleString()} điểm / ngày
                    </span>
                  </>
                )}
              </div>
            </div>
            {/* ACTIONS */}
            <div className="flex justify-end gap-3 mt-5">
              <PrimaryButton variant="cancel" onClick={onClose}>
                Hủy
              </PrimaryButton>

              <PrimaryButton
                onClick={() => setStep("PAYMENT")}
                disabled={days <= 0}
              >
                Xác nhận
              </PrimaryButton>
            </div>
          </>
        )}

        {/* ================= STEP 2 ================= */}
        {step === "PAYMENT" && (
          <>
            {/* ===== PAYMENT SUMMARY ===== */}
            <div className="mb-4 rounded-xl border border-gray-200 bg-white p-4 space-y-3">
              {/* BOOST TIME */}
              <div className="flex items-center gap-3">
                <div className="flex h-9 w-9 items-center justify-center rounded-full bg-blue-100 text-blue-600">
                  <FiClock size={16} />
                </div>

                <p className="text-md text-gray-700">
                  Thời gian boost: <b className="text-gray-900">{days}</b> ngày
                </p>
              </div>

              {/* PACKAGE INFO */}
              {selectedPackage && (
                <div className="flex items-center gap-2 text-sm text-teal-600">
                  <FiTag size={14} />
                  <span className="font-medium">
                    Gói {selectedPackage.label}
                  </span>
                  <span>(giảm {selectedPackage.discountPercent}%)</span>
                </div>
              )}

              {/* TOTAL COST */}
              <div className="flex justify-between items-center border-t pt-3">
                <span className="text-sm text-gray-500">
                  Tổng điểm thanh toán
                </span>
                <span className="text-lg font-bold text-teal-900">
                  {totalCost.toLocaleString()} điểm
                </span>
              </div>
            </div>
            {/* ===== WARNING ===== */}
            <div className="mb-4 flex items-start gap-3 rounded-xl border border-yellow-300 bg-yellow-50 p-3 text-sm text-yellow-800">
              <FiAlertTriangle className="mt-0.5" size={18} />
              <p>
                Điểm sẽ bị trừ trực tiếp trên blockchain và{" "}
                <b>không thể hoàn tác</b>.
              </p>
            </div>
            <div className="flex justify-end gap-3">
              <PrimaryButton
                variant="cancel"
                onClick={() => setStep("FORM")}
                disabled={loading}
              >
                Quay lại
              </PrimaryButton>

              <PrimaryButton
                variant="primary"
                loading={loading}
                onClick={handlePayment}
              >
                Thanh toán
              </PrimaryButton>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default BoostJobPostDialog;
