import React from "react";

const ApplyJobDialog = ({ open, onClose, onSubmit }) => {
  if (!open) return null;
  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-96 shadow-lg">
        <h2 className="text-xl font-semibold mb-4">Upload Avatar</h2>
        <p>Bạn chắc chắc ứng tuyển công việc này?</p>
        <div className="flex justify-end gap-3 mt-4">
          <button
            className="px-4 py-2 bg-gray-200 rounded-lg"
            onClick={onClose}
          >
            Hủy
          </button>
          <button
            className="px-4 py-2 bg-teal-600 text-white rounded-lg"
            onClick={onSubmit}
          >
            Ứng tuyển ngay
          </button>
        </div>
      </div>
    </div>
  );
};

export default ApplyJobDialog;
