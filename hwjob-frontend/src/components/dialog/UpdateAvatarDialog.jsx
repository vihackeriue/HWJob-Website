import React, { useState } from "react";

const UpdateAvatarDialog = ({ open, onClose, onSubmit }) => {
  const [preview, setPreview] = useState(null);
  const [file, setFile] = useState(null);

  if (!open) return null; // ẩn dialog khi open = false

  return (
    <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
      <div className="bg-white p-6 rounded-xl w-96 shadow-lg">
        <h2 className="text-xl font-semibold mb-4">Upload Avatar</h2>

        {preview && (
          <img
            src={preview}
            alt="preview"
            className="w-32 h-32 mx-auto rounded-full object-cover mb-4"
          />
        )}

        <input
          type="file"
          accept="image/*"
          onChange={(e) => {
            const file = e.target.files[0];
            setFile(file);
            setPreview(URL.createObjectURL(file));
          }}
        />

        <div className="flex justify-end gap-3 mt-4">
          <button
            className="px-4 py-2 bg-gray-200 rounded-lg"
            onClick={onClose}
          >
            Hủy
          </button>
          <button
            className="px-4 py-2 bg-teal-600 text-white rounded-lg"
            onClick={() => {
              onSubmit(file);
            }}
          >
            Lưu
          </button>
        </div>
      </div>
    </div>
  );
};

export default UpdateAvatarDialog;
