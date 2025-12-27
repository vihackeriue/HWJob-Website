export const STATUS_APPLICATION = [
  {
    code: "PENDING",
    name: "Đã ứng tuyển",
    className: "bg-blue-100 text-blue-700",

    actions: {
      RECRUITER: [
        { to: "APPROVED", label: "Duyệt hồ sơ", variant: "primary" },
        { to: "REJECTED", label: "Loại ứng viên", variant: "danger" },
      ],
      CANDIDATE: [], // ứng viên không làm gì được
    },
  },

  {
    code: "APPROVED",
    name: "Đã duyệt",
    className: "bg-blue-100 text-blue-700",

    actions: {
      RECRUITER: [
        { to: "ASSIGNED", label: "Giao việc", variant: "secondary" },
        { to: "REJECTED", label: "Loại ứng viên", variant: "danger" },
      ],
      CANDIDATE: [],
    },
  },

  {
    code: "ASSIGNED",
    name: "Chờ xác nhận giao việc",
    className: "bg-purple-100 text-purple-700",

    note: {
      RECRUITER: "Đang chờ ứng viên xác nhận nhận việc",
      CANDIDATE: "Bạn có muốn nhận công việc này không?",
    },

    actions: {
      RECRUITER: [
        { to: "REJECTED", label: "Hủy giao việc", variant: "danger" },
      ],
      CANDIDATE: [
        { to: "ACCEPTED", label: "Nhận việc", variant: "success" },
        // { to: "CANCELLED", label: "Từ chối", variant: "danger" },
      ],
    },
  },

  {
    code: "ACCEPTED",
    name: "Đã nhận việc",
    className: "bg-green-100 text-green-700",
    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },

  {
    code: "CANCELLED",
    name: "Hủy nhận việc",
    className: "bg-gray-100 text-gray-600",
    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },

  {
    code: "REJECTED",
    name: "Bị loại",
    className: "bg-red-100 text-red-700",
    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },
];

export const STATUS_APPLICATION_MAP = STATUS_APPLICATION.reduce((acc, s) => {
  acc[s.code] = s;
  return acc;
}, {});
