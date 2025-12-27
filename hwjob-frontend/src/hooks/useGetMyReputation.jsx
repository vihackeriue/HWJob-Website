import { useQuery } from "@tanstack/react-query";
import { getMyReputation } from "../services/reputationService";

export const useGetMyReputation = () => {
  return useQuery({
    queryKey: ["userReputation"], // Đây là "ID" của dữ liệu này trong bộ nhớ code
    queryFn: async () => {
      const response = await getMyReputation();
      return response.result.points; // Giả sử trả về { points: 1500 }
    },
    staleTime: 1000 * 60 * 5, // Dữ liệu được coi là "mới" trong 5 phút
    refetchInterval: 15000,
  });
};
