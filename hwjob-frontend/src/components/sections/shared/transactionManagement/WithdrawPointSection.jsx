import { useState, useMemo } from "react";
import { toast } from "react-toastify";
import { withdrawPoint } from "../../../../services/LoyaltyPointService";
import { useLoyaltyPoints } from "../../../../hooks/useLoyaltyPoints";

import PrimaryTitle from "../../../ui/title/PrimaryTitle";
import FormInput from "../../../ui/form/FormInput";
import PrimaryButton from "../../../ui/button/PrimaryButton";
import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import MenuTabListHorizontal from "../../../ui/MenuTabListHorizontal";

const POINT_PER_ETH = 96_000_000;
const MIN_WITHDRAW_POINT = 1_000;
const WITHDRAW_FEE_POINT = 1_000;

const TOP_UP_MENUS = [
  { key: "topUp", label: "ETH-BLOCKCHAIN" },
  { key: "withdrawPoint", label: "VND" },
];

const WithdrawPointSection = () => {
  const [point, setPoint] = useState("");
  const [loading, setLoading] = useState(false);

  const { data: maxPoint = 0, isLoading } = useLoyaltyPoints();

  /* =====================================================
   * ETH ESTIMATE (DISPLAY ONLY)
   * ===================================================== */
  const ethAmount = useMemo(() => {
    if (!point || isNaN(point)) return 0;
    return Number(point) / POINT_PER_ETH;
  }, [point]);
  /* =====================================================
   * VND ESTIMATE (DISPLAY ONLY)
   * ===================================================== */
  const vndAmount = useMemo(() => {
    if (!point || isNaN(point)) return 0;
    return Number(point); // 1 point = 1 VND
  }, [point]);
  const formatVND = (value) =>
    value.toLocaleString("vi-VN", {
      style: "currency",
      currency: "VND",
      maximumFractionDigits: 0,
    });
  /* =====================================================
   * SUBMIT
   * ===================================================== */
  const handleWithdraw = async () => {
    const withdrawPointValue = Number(point);

    if (!withdrawPointValue || withdrawPointValue <= 0) {
      toast.error("Số point không hợp lệ");
      return;
    }

    if (withdrawPointValue < MIN_WITHDRAW_POINT) {
      toast.error(
        `Số point rút tối thiểu là ${MIN_WITHDRAW_POINT.toLocaleString()}`
      );
      return;
    }

    if (withdrawPointValue + WITHDRAW_FEE_POINT > maxPoint) {
      toast.error("Không đủ point (bao gồm phí rút)");
      return;
    }

    try {
      setLoading(true);
      await withdrawPoint(withdrawPointValue);
      toast.success("Rút tiền thành công");
      setPoint("");
    } catch (err) {
      toast.error(err?.response?.data?.message || "Rút tiền thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <TabGroup className="flex flex-col gap-3">
      <MenuTabListHorizontal menus={TOP_UP_MENUS} title={"Quản lý giao dịch"} />
      <TabPanels>
        <TabPanel>
          <div className="bg-white p-4 rounded-xl space-y-4">
            <PrimaryTitle>Rút điểm</PrimaryTitle>

            <FormInput
              label="Số point muốn rút"
              type="number"
              min={MIN_WITHDRAW_POINT}
              value={point}
              onChange={(e) => setPoint(e.target.value)}
              disabled={isLoading}
              placeholder="Nhập số point"
              helperText={
                isLoading
                  ? "Đang tải số dư..."
                  : `Số dư: ${maxPoint.toLocaleString()} point`
              }
            />
            <FormInput
              label="Giá trị quy đổi (VND)"
              value={formatVND(vndAmount)}
              readOnly
              disabled
              helperText="Giá trị tương đương theo số point đã nạp"
              className="opacity-70"
            />

            <FormInput
              label="Phí rút"
              value={`${WITHDRAW_FEE_POINT.toLocaleString()} point`}
              readOnly
            />

            <FormInput
              label="Số ETH ước tính nhận được"
              value={`${ethAmount.toFixed(6)} ETH`}
              readOnly
            />

            <PrimaryButton
              onClick={handleWithdraw}
              disabled={loading || isLoading || maxPoint === 0}
              className="w-full"
            >
              {loading ? "Đang xử lý..." : "Rút tiền"}
            </PrimaryButton>
          </div>
        </TabPanel>
        <TabPanel>
          <div>Sớm ra mắt</div>
        </TabPanel>
      </TabPanels>
    </TabGroup>
  );
};

export default WithdrawPointSection;
