import { TabGroup, TabPanel, TabPanels } from "@headlessui/react";
import React from "react";
import MenuTabListVertical from "../../../components/ui/MenuTabListVertical";
import TopUpPointSection from "../../../components/sections/shared/transactionManagement/TopUpPointSection";
import { useLoyaltyPoints } from "../../../hooks/useLoyaltyPoints";
import Loading from "../../../components/ui/Loading";
import { PiCoinsFill } from "react-icons/pi";
import { useDetail } from "../../../hooks/useDetail";
import { getMyLockedLoyaltyPoint } from "../../../services/LoyaltyPointService";
import WithdrawPointSection from "../../../components/sections/shared/transactionManagement/WithdrawPointSection";
import HistoryTransactionSection from "../../../components/sections/shared/transactionManagement/HistoryTransactionSection";

const TRANSACTION_MANAGEMENT_MENUS = [
  { key: "topUp", label: "Nạp điểm thưởng" },
  { key: "withdrawPoint", label: "Rút tiền" },
  { key: "historyTopUp", label: "Lịch sử đổi điểm" },
  { key: "historyWithdraw", label: "Lịch sử giao dịch" },
];

const TransactionManagement = () => {
  const { data: points, isLoading } = useLoyaltyPoints();
  const lockedPoint = useDetail(getMyLockedLoyaltyPoint);
  return (
    <TabGroup>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 my-5">
        <div className="flex flex-col gap-3 col-span-1">
          <div className="bg-white p-3 rounded-xl flex flex-col gap-3">
            <div>
              {isLoading ? (
                <Loading size={24} />
              ) : (
                <div className="flex gap-1 items-center bg-lightGrayishBlue font-semibold  p-3 border  text-lg md:text-xl border-gray-300 rounded-lg">
                  <h1>Số điểm hiện tại:</h1>
                  <p className="text-amber-600">
                    {Number(points ?? 0).toLocaleString()}
                  </p>
                  <PiCoinsFill className="size-6 text-amber-600" />
                </div>
              )}
            </div>
            <div>
              {isLoading ? (
                <Loading size={24} />
              ) : (
                <div className="flex gap-1 items-center  bg-lightGrayishBlue font-semibold  p-3 border  text-lg md:text-xl border-gray-300 rounded-lg">
                  <h1>Số điểm đang bị giữ:</h1>
                  <p className="text-amber-600">
                    {Number(lockedPoint.data?.points ?? 0).toLocaleString()}
                  </p>
                  <PiCoinsFill className="size-6 text-amber-600" />
                </div>
              )}
            </div>
          </div>

          <MenuTabListVertical
            menus={TRANSACTION_MANAGEMENT_MENUS}
            title={"Quản lý giao dịch"}
          />
        </div>
        <div className="col-span-2 flex flex-col gap-3">
          <TabPanels>
            <TabPanel>
              <TopUpPointSection />
            </TabPanel>
            <TabPanel>
              <WithdrawPointSection />
            </TabPanel>
            <TabPanel>
              <HistoryTransactionSection />
            </TabPanel>
          </TabPanels>
        </div>
      </div>
    </TabGroup>
  );
};

export default TransactionManagement;
