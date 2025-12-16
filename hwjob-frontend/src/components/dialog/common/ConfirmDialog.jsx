import React from "react";
import PrimaryButton from "../../ui/button/PrimaryButton";

const ConfirmDialog = ({
  open,
  onClose,
  onConfirm,
  title = "Xác nhận",
  description = "Bạn có chắc chắn muốn thực hiện hành động này?",
  confirmText = "Xác nhận",
  cancelText = "Hủy",
}) => {
  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-full max-w-md shadow-lg">
        <h2 className="text-xl font-semibold mb-3">{title}</h2>
        <p className="text-gray-600">{description}</p>

        <div className="flex justify-end gap-3 mt-6">
          <PrimaryButton variant="cancel" onClick={onClose}>
            {cancelText}
          </PrimaryButton>

          <PrimaryButton variant="primary" onClick={onConfirm}>
            {confirmText}
          </PrimaryButton>
        </div>
      </div>
    </div>
  );
};

export default ConfirmDialog;
