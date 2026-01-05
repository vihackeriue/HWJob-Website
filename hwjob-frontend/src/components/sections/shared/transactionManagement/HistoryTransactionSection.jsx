import React from "react";
import { useList } from "../../../../hooks/useList";
import { getHistoryPayment } from "../../../../services/LoyaltyPointService";
import PaymentHistoryCard from "../../../ui/cards/PaymentHistoryCard";

import Pagination from "../../../ui/pagination/Pagination";

import PrimaryTitle from "../../../ui/title/PrimaryTitle";

const HistoryTransactionSection = () => {
  const historyPayments = useList(getHistoryPayment);
  return (
    <div className="bg-white rounded-2xl p-5 min-h-150 space-y-2">
      <PrimaryTitle>Lịch sử thanh toán </PrimaryTitle>
      {historyPayments.data.length === 0 ? (
        <div className="text-center text-gray-500 py-10">
          Không có thanh toán nào
        </div>
      ) : (
        <>
          <div className="space-y-3">
            {historyPayments.data.map((payment) => (
              <PaymentHistoryCard payment={payment} />
            ))}
          </div>
          <div className="flex justify-center m-3">
            <Pagination
              pagination={{
                page: historyPayments.page,
                totalPages: historyPayments.totalPages,
                setPage: historyPayments.setPage,
              }}
            />
          </div>
        </>
      )}
    </div>
  );
};

export default HistoryTransactionSection;
