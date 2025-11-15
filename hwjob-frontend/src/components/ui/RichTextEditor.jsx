import ReactQuill from "react-quill-new";

import "react-quill-new/dist/quill.snow.css";
const RichTextEditor = ({ value, placeholder, label, error, onChange }) => {
  const modules = {
    toolbar: [
      [{ header: [1, 2, 3, false] }],
      ["bold", "italic", "underline", "strike"],
      [{ list: "ordered" }, { list: "bullet" }],
      [{ color: [] }, { background: [] }],
      ["link"],
      ["clean"],
    ],
  };

  const formats = [
    "header",
    "bold",
    "italic",
    "underline",
    "strike",
    "list",
    "link",
    "color",
    "background",
  ];

  return (
    <div className="[&_.ql-toolbar]:dark:bg-gray-100">
      <div className="flex justify-between items-center mb-1">
        {label && (
          <label className="text-md font-medium" htmlFor={name}>
            {label}
          </label>
        )}
        {error && <span className="text-red-500 text-sm">{error}</span>}
      </div>
      <ReactQuill
        theme="snow"
        value={value}
        onChange={onChange}
        modules={modules}
        formats={formats}
        placeholder={placeholder}
        className="max-h-72 overflow-y-auto"
      />
    </div>
  );
};

export default RichTextEditor;
