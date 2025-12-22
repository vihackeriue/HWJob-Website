export const STATUS_WORK = [
  {
    code: "PENDING",
    name: "Chờ bắt đầu",
    className: "bg-gray-100 text-gray-700",

    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },

  {
    code: "IN_PROGRESS",
    name: "Đang tiến hành",
    className: "bg-blue-100 text-blue-700",

    note: {
      CANDIDATE: "Bạn có thể nộp kết quả hoặc hủy công việc",
    },

    actions: {
      RECRUITER: [],
      CANDIDATE: [
        { to: "SUBMITTED", label: "Nộp kết quả", variant: "primary" },
        { to: "CANCELLED", label: "Hủy công việc", variant: "danger" },
      ],
    },
  },

  {
    code: "SUBMITTED",
    name: "Đã nộp kết quả",
    className: "bg-purple-100 text-purple-700",

    note: {
      RECRUITER: "Ứng viên đã nộp kết quả, vui lòng xác nhận",
    },

    actions: {
      RECRUITER: [
        { to: "PAID", label: "Thanh toán", variant: "success" },
        { to: "REJECTED", label: "Từ chối kết quả", variant: "danger" },
      ],
      CANDIDATE: [],
    },
  },

  {
    code: "REJECTED",
    name: "Kết quả bị từ chối",
    className: "bg-red-100 text-red-700",

    note: {
      CANDIDATE: "Kết quả bị từ chối, bạn có thể khiếu nại",
    },

    actions: {
      RECRUITER: [],
      CANDIDATE: [{ to: "DISPUTED", label: "Khiếu nại", variant: "warning" }],
    },
  },

  {
    code: "DISPUTED",
    name: "Đang tranh chấp",
    className: "bg-yellow-100 text-yellow-700",

    note: {
      RECRUITER: "Công việc đang trong trạng thái tranh chấp",
      CANDIDATE: "Yêu cầu tranh chấp đang được xử lý",
    },

    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },

  {
    code: "PAID",
    name: "Đã thanh toán",
    className: "bg-green-100 text-green-700",

    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },

  {
    code: "CANCELLED",
    name: "Đã hủy",
    className: "bg-gray-100 text-gray-600",

    actions: {
      RECRUITER: [],
      CANDIDATE: [],
    },
  },
];

export const STATUS_WORK_MAP = STATUS_WORK.reduce((acc, s) => {
  acc[s.code] = s;
  return acc;
}, {});
